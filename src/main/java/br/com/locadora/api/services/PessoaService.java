package br.com.locadora.api.services;

<<<<<<< Updated upstream:src/main/java/br/com/locadora/api/services/PessoaService.java
import br.com.locadora.api.domain.pessoa.Funcionario;
import br.com.locadora.api.domain.pessoa.Motorista;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.repositories.PessoaRepository;
=======
import br.com.locadora.domain.pessoa.*;
import br.com.locadora.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
>>>>>>> Stashed changes:src/main/java/br/com/locadora/services/PessoaService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() { return pessoaRepository.findAll(); }
    public void cadastrarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = converterDTOparaEntidade(pessoaDTO);
        pessoaRepository.save(pessoa); // Persiste a entidade no banco de dados
    }

    private Pessoa converterDTOparaEntidade(PessoaDTO pessoaDTO) {
        Pessoa pessoa;

        if (pessoaDTO.getMatricula() != null) {
            pessoa = new Funcionario();
        } else if (pessoaDTO.getNumeroCNH() != null) {
            pessoa = new Motorista();
        } else {
            pessoa = new Pessoa();
        }

        try {
            LocalDate dataDeNascimento = converterData(pessoaDTO.getDataDeNascimento());
            pessoa.setDataDeNascimento(dataDeNascimento);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data de nascimento inválida: " + pessoaDTO.getDataDeNascimento());
        }

        try {
            pessoa.setSexo(converterSexo(pessoaDTO.getSexo()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sexo inválido: " + pessoaDTO.getSexo());
        }

        return pessoa;
    }

    public void deletarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
        if (pessoa != null) {
            pessoaRepository.delete(pessoa);
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada!");
        }
    }

    public void atualizarPessoa(Pessoa pessoa) { pessoaRepository.save(pessoa); }
    private LocalDate converterData(String data) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("ddMMyyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yy"),
                DateTimeFormatter.ofPattern("dd.MM.yy"),
                DateTimeFormatter.ofPattern("dd-MM-yy"),
                DateTimeFormatter.ofPattern("ddMMyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(data, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException("Formato de data inválido: " + data);
    }

    private Sexo converterSexo(String sexoString) {
        String sexoUpperCase = sexoString.toUpperCase();

        if (sexoUpperCase.equals("M") || sexoUpperCase.equals("MASCULINO")) {
            return Sexo.MASCULINO;
        } else if (sexoUpperCase.equals("F") || sexoUpperCase.equals("FEMININO")) {
            return Sexo.FEMININO;
        } else {
            throw new IllegalArgumentException("Sexo inválido: " + sexoString);
        }
    }

}
