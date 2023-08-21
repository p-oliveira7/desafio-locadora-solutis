package br.com.locadora.api.mappers;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaResponseDTO;
public interface PessoaMapper {
    PessoaResponseDTO toDto(Pessoa pessoa);
}
