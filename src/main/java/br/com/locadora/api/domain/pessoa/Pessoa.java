package br.com.locadora.api.domain.pessoa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table
@Entity
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataDeNascimento;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public Pessoa(Long id, String nome, String cpf, Sexo genero) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = genero;
    }
}
