package br.com.locadora.api.services;

import br.com.locadora.api.domain.pessoa.*;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.domain.usuario.UsuarioRepository;
import br.com.locadora.api.mappers.PessoaMapper;
import br.com.locadora.api.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(PessoaMapper.INSTANCE::pessoaToPessoaDTO)
                .collect(Collectors.toList());
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
    public void deletarPessoa(String cpf) {
        Optional<Funcionario> funcionario = pessoaRepository.findFuncionarioByCpf(cpf);
        if (funcionario.isPresent()) {
            pessoaRepository.delete(funcionario.get());
            return;
        }

        Optional<Motorista> motorista = pessoaRepository.findMotoristaByCpf(cpf);
        if (motorista.isPresent()) {
            pessoaRepository.delete(motorista.get());
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


