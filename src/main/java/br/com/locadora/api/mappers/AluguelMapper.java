package br.com.locadora.api.mappers;

import br.com.locadora.api.domain.aluguel.Aluguel;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import org.springframework.stereotype.Component;

@Component
public class AluguelMapper {

    public ListarCarrinhoDTO toListarCarrinhoDTO(Aluguel aluguel) {
        return new ListarCarrinhoDTO(
                aluguel.getTemporaryId(),
                aluguel.getCarro().getPlaca(),
                aluguel.getDataPedido(),
                aluguel.getDataEntrega(),
                aluguel.getDataDevolucao(),
                aluguel.getApoliceSeguro().getValorFranquia(),
                aluguel.getApoliceSeguro().getProtecaoTerceiro(),
                aluguel.getApoliceSeguro().getProtecaoCausaNatural(),
                aluguel.getApoliceSeguro().getProtecaoRoubo()
        );
    }
}
