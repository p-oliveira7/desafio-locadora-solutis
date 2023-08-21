package br.com.locadora.api.mappers.impl;

import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.carro.CarroResponseDTO;
import br.com.locadora.api.mappers.CarroMapper;
import org.springframework.stereotype.Component;

@Component
public class CarroMapperImpl implements CarroMapper {

    @Override
    public CarroResponseDTO toDto(Carro carro) {
        return new CarroResponseDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.getCategoria(),
                carro.getAcessorio(),
                carro.getDescricao(),
                carro.getNome(),
                carro.getImagePath()
        );
    }
}
