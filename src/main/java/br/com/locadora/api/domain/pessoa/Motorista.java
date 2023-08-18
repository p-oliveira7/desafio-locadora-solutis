package br.com.locadora.api.domain.pessoa;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Motorista extends Pessoa {
    @Column(name = "cnh")
    private String numeroCNH;
}
