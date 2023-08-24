package br.com.locadora.api.services.impl;

import br.com.locadora.api.domain.pessoa.*;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.domain.usuario.UsuarioRepository;
import br.com.locadora.api.mappers.PessoaMapper;
import br.com.locadora.api.repositories.PessoaRepository;
import br.com.locadora.api.services.PessoaServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService implements PessoaServiceInterface {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private PessoaMapper pessoaMapper;

    @Override
    public Page<PessoaResponseDTO> listarPessoas(Usuario user, Pageable pageable) {
        Page<Pessoa> pessoasPage = pessoaRepository.findByUsuario(user, pageable);
        Page<PessoaResponseDTO> pessoasResponsePage = pessoasPage.map(pessoaMapper::toDto);
        return pessoasResponsePage;
    }

    @Override
    @Transactional
    public void cadastrarPessoa(@NotNull @Valid PessoaDTO pessoaDTO, Usuario user) {
        Pessoa pessoa = pessoaDTO.dtoToEntity();

        if (pessoa != null) {
            // Verificar se já existe uma pessoa com o mesmo CPF
            Optional<Pessoa> pessoaExistente = pessoaRepository.findByCpf(pessoa.getCpf());
            if (pessoaExistente.isPresent()) {
                throw new IllegalArgumentException("Já existe uma pessoa cadastrada com este CPF.");
            }

            pessoaRepository.save(pessoa);
            user.setPessoa(pessoa);
            usuarioRepository.save(user); // Persiste a entidade no banco de dados
        } else {
            throw new IllegalArgumentException("A conversão do DTO para entidade Pessoa falhou.");
        }
    }

@Override
    public void deletarPessoaPorCPF(String cpf) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findByCpf(cpf);

        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();

            // Verifique se a pessoa está associada a algum usuário
            if (pessoa.getUsuario() != null) {
                // Se a pessoa estiver associada a um usuário, remova a associação
                Usuario usuario = pessoa.getUsuario();
                usuario.setPessoa(null);
                usuarioRepository.save(usuario);
            }

            pessoaRepository.delete(pessoa);
            return;
        }

        throw new EntityNotFoundException("Pessoa não encontrada!");
    }

    @Override
    public void atualizarPessoa(String cpf, Usuario user, @Valid PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findByCpf(cpf);

        if (pessoaExistente.isPresent()) {
            Pessoa pessoa = pessoaExistente.get();

            // Verifica se a pessoa está associada ao usuário autenticado
            if (pessoa.getUsuario().equals(user)) {
                Pessoa pessoaAtualizada = pessoaDTO.dtoToEntity();
                if (pessoaAtualizada != null) {
                    pessoaAtualizada.setId(pessoa.getId());

                    // Atualiza os campos da pessoa existente com os valores da pessoa atualizada
                    pessoa.setNome(pessoaAtualizada.getNome());
                    pessoa.setCpf(pessoaAtualizada.getCpf());
                    pessoaExistente.get().setSexo(pessoaAtualizada.getSexo());
                    pessoaExistente.get().setNumeroCNH(pessoaAtualizada.getNumeroCNH());

                    pessoaRepository.save(pessoa);
                } else {
                    throw new IllegalArgumentException("A atualização não gerou uma pessoa válida.");
                }
            } else {
                throw new IllegalStateException("A pessoa não está associada ao usuário autenticado.");
            }
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada!");
        }
    }


}


