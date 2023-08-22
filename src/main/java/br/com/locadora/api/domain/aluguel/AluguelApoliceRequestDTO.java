package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguroRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
        ApoliceSeguroRequestDTO apolice
) {
        @Override
        public Calendar dataPedido() {
                return dataPedido;
        }

        @Override
        public Date dataEntrega() {
                return dataEntrega;
        }

        @Override
        public Date dataDevolucao() {
                return dataDevolucao;
        }

        public ApoliceSeguroRequestDTO getApoliceSeguro() {
                return this.apolice;
        }
}
