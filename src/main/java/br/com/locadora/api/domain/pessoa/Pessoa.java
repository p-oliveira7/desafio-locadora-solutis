package br.com.locadora.api.domain.pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
        name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataDeNascimento;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

}