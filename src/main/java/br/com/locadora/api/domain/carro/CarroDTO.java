package br.com.locadora.api.domain.carro;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record CarroDTO(
    @NotNull
    String placa,
    @NotNull
    String chassi,
    @NotNull
    String cor,
    @NotNull
 BigDecimal valorDiaria,
    @NotNull
    Categoria categoria,

    Acessorio acessorio,
    @NotNull
    ModeloCarro descricao,
    @NotNull
    Fabricante nome){
}
