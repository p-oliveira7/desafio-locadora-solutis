package br.com.locadora.api.domain.carro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ModeloCarro extends Carro{
    @Column(name = "modelo")
    private String descricao;

}
