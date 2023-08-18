package br.com.locadora.api.domain.carro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fabricante extends Carro {
    @Column(name = "fabricante")
    private String nome;

    @Override
    public String getNome() {
        return nome;
    }
}

