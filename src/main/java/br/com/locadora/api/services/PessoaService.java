package br.com.locadora.api.services;

import br.com.locadora.api.domain.pessoa.*;
import br.com.locadora.api.mappers.PessoaMapper;
import br.com.locadora.api.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(PessoaMapper.INSTANCE::pessoaToPessoaDTO) // Usa o método gerado pelo MapStruct
                .collect(Collectors.toList());
    }

    @Transactional
    public void cadastrarPessoa(@NotNull PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaDTO.converterDTOparaEntidade();
        pessoaRepository.save(pessoa); // Persiste a entidade no banco de dados
    }

    @Transactional
    public void deletarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
        if (pessoa != null) {
            pessoaRepository.delete(pessoa);
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada!");
        }
    }


    @Transactional
    public void atualizarPessoa(String cpf, PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com o CPF: " + cpf));

        Pessoa pessoaAtualizada = pessoaDTO.converterDTOparaEntidade();
        pessoaAtualizada.setId(pessoaExistente.getId()); // Mantém o ID da pessoa existente
        pessoaRepository.save(pessoaAtualizada);
    }

}
