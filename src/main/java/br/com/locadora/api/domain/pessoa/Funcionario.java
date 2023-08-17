package br.com.locadora.api.domain.pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends Pessoa {
    @Column(name = "matricula")
    private String matricula;
}
