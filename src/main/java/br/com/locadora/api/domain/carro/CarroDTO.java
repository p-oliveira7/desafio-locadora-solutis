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
    @NotNull
    String acessorio,
    @NotNull
    String descricao,
    @NotNull
    String nome,
    @NotNull
    String imagePath
){
}
