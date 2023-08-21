package br.com.locadora.api.domain.carro;

import java.math.BigDecimal;

public record CarroResponseDTO(
        Long id,
        String placa,

        String chassi,

        String cor,

        BigDecimal valorDiaria,

        Categoria categoria,

        String acessorio,

        String descricao,

        String nome,

        String imagePath
){
}