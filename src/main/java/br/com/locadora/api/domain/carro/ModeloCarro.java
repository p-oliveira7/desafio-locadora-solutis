package br.com.locadora.api.domain.carro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class ModeloCarro extends Carro{
    @Column(name = "modelo")
    private String descricao;
@Override
    public String getDescricao() {
        return descricao;
    }
}
