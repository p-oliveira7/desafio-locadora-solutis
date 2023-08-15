package br.com.locadora.api.domain.carro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Acessorio extends Carro{
    @Column(name = "acessorio")
    private String descricao;

    public void descricao(String descricao){
        this.descricao = descricao;
    }
}
