package br.com.locadora.entity;

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
