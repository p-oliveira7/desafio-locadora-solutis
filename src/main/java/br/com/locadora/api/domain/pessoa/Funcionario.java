package br.com.locadora.api.domain.pessoa;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "funcionario")
@DiscriminatorValue("funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario extends Pessoa {
    @Column(name = "matricula")
    private String matricula;
    @Override
    public void atualizar(PessoaDTO pessoaAtualizada) {
        if (pessoaAtualizada.matricula()!= null) {
            this.setMatricula(pessoaAtualizada.matricula());
        }
    }

    public PessoaDTO toDTO() {
        String dataDeNascimentoFormatada = this.getDataDeNascimentoFormatada();
        return new PessoaDTO(this.getNome(), dataDeNascimentoFormatada, this.getCpf(), this.getSexo().getDescricao(), this.getMatricula(), null);
    }

    @Override
    public String getMatricula() {
        return super.getMatricula();
    }
}
