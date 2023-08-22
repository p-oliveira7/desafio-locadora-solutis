package br.com.locadora.api.domain.carro;

import lombok.Getter;

@Getter
public enum Categoria {

    HATCH_COMPACTO("Hatch Compacto"),
    HATCH_MEDIO("Hatch Médio"),
    SEDAN_COMPACTO("Sedan Compacto"),
    SEDAN_MEDIO("Sedan Médio"),
    SEDAN_GRANDE("Sedan Grande"),
    MINIVAN("Minivan"),
    ESPORTIVO("Esportivo"),
    UTILITARIO_COMERCIAL("Utilitário Comercial");


    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }
}
