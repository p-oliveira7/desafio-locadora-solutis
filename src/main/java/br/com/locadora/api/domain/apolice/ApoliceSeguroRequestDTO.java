package br.com.locadora.api.domain.apolice;

import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
public record ApoliceSeguroRequestDTO(
        @NotNull
        Boolean protecaoTerceiro,
        @NotNull
        Boolean protecaoCausaNatural,
        @NotNull
        Boolean protecaoRoubo
) {

        @Override
        public Boolean protecaoTerceiro() {
                return protecaoTerceiro;
        }

        @Override
        public Boolean protecaoCausaNatural() {
                return protecaoCausaNatural;
        }

        @Override
        public Boolean protecaoRoubo() {
                return protecaoRoubo;
        }
}
