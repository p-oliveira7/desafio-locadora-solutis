package br.com.locadora.api.domain.carro;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarroDTO {
    @NotNull
    private Long id;
    @NotNull
    private String placa;
    @NotNull
    private String chassi;
    @NotNull
    private String cor;
    @NotNull
    private BigDecimal valorDiaria;
    @NotNull
    private String categoria;
    private String acessorio;
    @NotNull
    private String descricao;
    @NotNull
    private String nome;
}
