package br.com.locadora.api.domain.carro;


import br.com.locadora.api.domain.aluguel.*;
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
@Entity
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
    private String acessorio;
    private String descricao;
    private String nome;

    @OneToOne(mappedBy = "carro")
    private Aluguel aluguel;

    public Carro(String placa, String chassi, String cor, BigDecimal bigDecimal, Categoria categoria, Acessorio acessorio, ModeloCarro descricao, Fabricante nome) {
    }

   /* public String getAcessorio() {
        return acessorio;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getNome() {
        return nome;
    }


public void Carro(String placa, String chassi, String cor, BigDecimal valorDiaria, Categoria categoria, String acessorio, String descricao, String nome) {
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.categoria = categoria;

    }*/



}
