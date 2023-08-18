package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguroRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public record AluguelApoliceRequestDTO(
        @NotNull
        Calendar dataPedido,
        @NotNull
        Date dataEntrega,
        @NotNull
        Date dataDevolucao,
        @NotNull
        @Valid
        ApoliceSeguroRequestDTO apolice,
        @NotNull
        Long idCarro
) {
}
