package br.com.locadora.api.domain.carro;

import br.com.locadora.api.domain.pessoa.Sexo;
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
public class Carro {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String placa;
    private String chassi;
    private String cor;
    private BigDecimal valorDiaria;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;



}
