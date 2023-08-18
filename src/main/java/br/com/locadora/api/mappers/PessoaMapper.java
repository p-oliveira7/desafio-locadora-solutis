package br.com.locadora.api.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import br.com.locadora.api.domain.pessoa.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    @Mapping(target = "matricula", qualifiedByName = "mapToFuncionarioMatricula")
    @Mapping(target = "numeroCNH", qualifiedByName = "mapToMotoristaCNH")
    PessoaDTO pessoaToPessoaDTO(Pessoa pessoa);

    List<PessoaDTO> pessoasToPessoasDTO(List<Pessoa> pessoas);

    @Named("mapToFuncionarioMatricula")
    default String mapToFuncionarioMatricula(Funcionario funcionario) {
        return funcionario.getMatricula();
    }

    @Named("mapToMotoristaCNH")
    default String mapToMotoristaCNH(Motorista motorista) {
        return motorista.getNumeroCNH();
    }
}


