package br.com.locadora.api.domain.aluguel;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public record AluguelApoliceInputDTO(
        @NotNull
        Calendar dataPedido,
        @NotNull
        Date dataEntrega,
        @NotNull
        Date dataDevolucao,
        @NotNull
        BigDecimal valorFranquia,
        @NotNull
        Boolean protecaoTerceiro,
        @NotNull
        Boolean protecaoCausaNatural,
        @NotNull
        Boolean protecaoRoubo
) {
}
