package br.com.locadora.api.services;

import br.com.locadora.api.domain.pessoa.*;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.domain.usuario.UsuarioRepository;
import br.com.locadora.api.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<PessoaDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaDTO> dtos = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            PessoaDTO dto = pessoa.toDTO();
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public void cadastrarPessoa(@NotNull @Valid PessoaDTO pessoaDTO, Usuario user) {
        Pessoa pessoa = pessoaDTO.dtoToEntity();

        if (pessoa != null) {
            pessoaRepository.save(pessoa);
            user.setPessoa(pessoa);
            usuarioRepository.save(user);// Persiste a entidade no banco de dados
        } else {
            throw new IllegalArgumentException("A conversão do DTO para entidade Pessoa falhou.");
        }
    }

    @Transactional
    public void deletarPessoa(Usuario user) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(user.getPessoa().getId());
        if (pessoaExistente.isPresent()) {
            // Atualiza o registro correspondente na tabela usuarios
            user.setPessoa(null);
            usuarioRepository.save(user);

            pessoaRepository.delete(pessoaExistente.get());
            return;
        }
        throw new EntityNotFoundException("Pessoa não encontrada!");
    }


    @Transactional
    public void atualizarPessoa(Usuario user, @Valid PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(user.getPessoa().getId());

        if (pessoaExistente.isPresent()) {

            Pessoa pessoaAtualizada = pessoaDTO.dtoToEntity();
            assert pessoaAtualizada != null;
            pessoaAtualizada.setId(pessoaExistente.get().getId());
            pessoaRepository.save(pessoaAtualizada);

        } else {
            throw new EntityNotFoundException("Pessoa não encontrada!");
        }
    }
}


