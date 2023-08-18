package br.com.locadora.api.domain.aluguel;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public record AluguelResponseDTO(
        String placa,
        Calendar dataPedido,
        Date dataEntrega,

        Date dataDevolucao,

        BigDecimal valorFranquia,

        Boolean protecaoTerceiro,

        Boolean protecaoCausaNatural,

        Boolean protecaoRoubo) {
}
