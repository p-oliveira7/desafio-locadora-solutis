package br.com.locadora.api.mappers;

import br.com.locadora.api.domain.aluguel.Aluguel;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.carro.CarroResponseDTO;

public interface AluguelMapper {
    ListarCarrinhoDTO toDto(Aluguel aluguel);
}
