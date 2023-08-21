package br.com.locadora.api.mappers.impl;

import br.com.locadora.api.domain.aluguel.Aluguel;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.mappers.AluguelMapper;
import org.springframework.stereotype.Component;

@Component
public class AluguelMapperImpl implements AluguelMapper {
    @Override
    public ListarCarrinhoDTO toDto(Aluguel aluguel) {
        return new ListarCarrinhoDTO(
                aluguel.getId(),
                aluguel.getCarro().getPlaca(),
                aluguel.getCarro().getImagePath(),
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucao(),
                aluguel.getApoliceSeguro().getValorFranquia(),
                aluguel.getApoliceSeguro().getProtecaoTerceiro(),
                aluguel.getApoliceSeguro().getProtecaoCausaNatural(),
                aluguel.getApoliceSeguro().getProtecaoRoubo(),
                aluguel.getValorTotal()
        );
    }
}