package br.com.locadora.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Motorista extends Pessoa {
    @Column(name = "cnh")
    private String numeroCNH;
}
