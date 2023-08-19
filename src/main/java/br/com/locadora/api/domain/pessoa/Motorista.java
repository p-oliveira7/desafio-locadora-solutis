package br.com.locadora.api.domain.pessoa;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "motorista")
@DiscriminatorValue("motorista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Motorista extends Pessoa {
    @Column(name = "cnh")
    private String numeroCNH;


    @Override
    public PessoaDTO toDTO() {
        String dataDeNascimentoFormatada = this.getDataDeNascimentoFormatada();
        return new PessoaDTO(this.getNome(), dataDeNascimentoFormatada, this.getCpf(), this.getSexo().getDescricao(), null, this.getNumeroCNH());
    }
}