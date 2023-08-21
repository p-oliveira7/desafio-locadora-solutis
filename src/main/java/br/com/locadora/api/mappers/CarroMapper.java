package br.com.locadora.api.mappers;

import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.carro.CarroResponseDTO;

public interface CarroMapper {
    CarroResponseDTO toDto(Carro carro);
}
