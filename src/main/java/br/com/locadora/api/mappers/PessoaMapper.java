package br.com.locadora.api.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaDTO;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    @Mapping(target = "matricula", source = "matricula")
    @Mapping(target = "numeroCNH", source = "numeroCNH")
    PessoaDTO pessoaToPessoaDTO(Pessoa pessoa);
}
