package br.com.locadora.api.domain.carro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Acessorio extends Carro{

    @Column(name = "acessorio")
    private String acessorio;

    @Override
    public String getAcessorio() {
        return super.getAcessorio();
    }
    }



