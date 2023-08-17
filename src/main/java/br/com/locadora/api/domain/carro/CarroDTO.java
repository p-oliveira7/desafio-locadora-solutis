package br.com.locadora.api.domain.carro;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


public record CarroDTO(
    @NotNull
    Long id,
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
