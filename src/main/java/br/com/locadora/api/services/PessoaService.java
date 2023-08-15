package br.com.locadora.api.services;

import br.com.locadora.api.domain.pessoa.Funcionario;
import br.com.locadora.api.domain.pessoa.Motorista;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() { return pessoaRepository.findAll(); }
    public void cadastrarPessoa(PessoaDTO pessoaDTO) {
        // fazer conversão da String dataDeNascimento
        Pessoa pessoa = converterDTOparaEntidade(pessoaDTO);
        pessoaRepository.save(pessoa); // Persiste a entidade no banco de dados
    }

    private Pessoa converterDTOparaEntidade(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getMatricula() != null && pessoaDTO.getNumeroCNH() != null) {
            return new Funcionario();
        } else if (pessoaDTO.getNumeroCNH() != null) {
            return new Motorista();
        } else {
            return new Pessoa();
        }
    }

    public void deletarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
        if (pessoa != null) {
            pessoaRepository.delete(pessoa);
        } else {
            throw new RuntimeException("Pessoa não encontrada!");
        }
    }
    public void atualizarPessoa(Pessoa pessoa) { pessoaRepository.save(pessoa); }
}
