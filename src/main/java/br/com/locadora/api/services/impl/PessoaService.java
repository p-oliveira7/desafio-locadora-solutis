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
    @Transactional(readOnly = true)
    public Page<PessoaResponseDTO> listarPessoas(Pageable pageable) {
        Page<Pessoa> pessoasPage = pessoaRepository.findAll(pageable);

        Page<PessoaResponseDTO> pessoasResponsePage = pessoasPage.map(pessoaMapper::toDto);

        return pessoasResponsePage;
    }
    @Override
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

            // Verifique se a pessoa está associada ao usuário autenticado
            if (pessoa.getUsuario().equals(user)) {
                Pessoa pessoaAtualizada = pessoaDTO.dtoToEntity();
                if (pessoaAtualizada != null) {
                    pessoaAtualizada.setId(pessoa.getId());

                    // Atualize os campos da pessoa existente com os valores da pessoa atualizada
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
   /* @Override
    @Transactional
    public void atualizarPessoa(Usuario user, @Valid PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(user.getPessoa().getId());

        if (pessoaExistente.isPresent()) {
            Pessoa pessoaAtualizada = pessoaDTO.dtoToEntity(); // Supondo que esse método converte DTO para entidade
            if (pessoaAtualizada != null) {
                pessoaAtualizada.setId(pessoaExistente.get().getId());

                // Atualizar campos da pessoa existente com os valores da pessoa atualizada
                pessoaExistente.get().setNome(pessoaAtualizada.getNome());
                pessoaExistente.get().setDataDeNascimento(pessoaAtualizada.getDataDeNascimento());
                pessoaExistente.get().setSexo(pessoaAtualizada.getSexo());
                pessoaExistente.get().setNumeroCNH(pessoaAtualizada.getNumeroCNH());

                // ... Considerando que o CPF é válido e é único por pessoa, não há como alterar.

                pessoaRepository.save(pessoaExistente.get());
            } else {
                throw new IllegalArgumentException("A atualização não gerou uma pessoa válida.");
            }
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada!");
        }
    }*/

}


