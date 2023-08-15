package br.com.locadora.api.domain.pessoa;

import lombok.Getter;

@Getter
public enum Sexo {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");

    private final String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

}
