package br.com.locadora.api.services;

import br.com.locadora.api.domain.pessoa.*;
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

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(PessoaMapper.INSTANCE::pessoaToPessoaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cadastrarPessoa(@NotNull @Valid PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaDTO.converterDTOparaEntidade();

        if (pessoa != null) {
            pessoaRepository.save(pessoa); // Persiste a entidade no banco de dados
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
    public void atualizarPessoa(String cpf, PessoaDTO pessoaDTO) {
        Optional<Funcionario> funcionarioExistente = pessoaRepository.findFuncionarioByCpf(cpf);
        if (funcionarioExistente.isPresent()) {
            Pessoa pessoaAtualizada = pessoaDTO.converterDTOparaEntidade();
            if (pessoaAtualizada instanceof Funcionario funcionarioAtualizado) {
                funcionarioAtualizado.setId(funcionarioExistente.get().getId()); // Mantém o ID do funcionário existente
                pessoaRepository.save(funcionarioAtualizado);
                return;
            }
        }

        Optional<Motorista> motoristaExistente = pessoaRepository.findMotoristaByCpf(cpf);
        if (motoristaExistente.isPresent()) {
            Pessoa pessoaAtualizada = pessoaDTO.converterDTOparaEntidade();
            if (pessoaAtualizada instanceof Motorista motoristaAtualizado) {
                motoristaAtualizado.setId(motoristaExistente.get().getId()); // Mantém o ID do motorista existente
                pessoaRepository.save(motoristaAtualizado);
                return;
            }
        }

        throw new EntityNotFoundException("Pessoa não encontrada com o CPF: " + cpf);
    }
}
