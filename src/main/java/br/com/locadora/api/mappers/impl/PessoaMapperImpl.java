package br.com.locadora.api.mappers.impl;

import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaResponseDTO;
import br.com.locadora.api.mappers.PessoaMapper;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public PessoaResponseDTO toDto(Pessoa pessoa){
        return new PessoaResponseDTO(
                pessoa.getNome(),
                pessoa.getDataDeNascimentoFormatada(),
                pessoa.getCpf(),
                pessoa.getSexo(),
                pessoa.getMatricula(),
                pessoa.getNumeroCNH());

    }
}
