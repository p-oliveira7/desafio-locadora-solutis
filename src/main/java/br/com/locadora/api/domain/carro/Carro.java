package br.com.locadora.api.domain.carro;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "carro")
@Table(name="carros")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Carro {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String chassi;
    private String cor;
    private BigDecimal valorDiaria;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Carro(String placa, String chassi, String cor, BigDecimal valorDiaria, Categoria categoria, String acessorio, String descricao, String nome){
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.categoria=categoria;
    }



}
