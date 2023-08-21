package br.com.locadora.api.domain.pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
        name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataDeNascimento;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private String matricula;
    private String numeroCNH;

    public abstract void atualizar(PessoaDTO pessoaAtualizada);
    public String getDataDeNascimentoFormatada() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.getDataDeNascimento().format(dateFormatter);
    }

    public PessoaDTO toDTO() {
        return new PessoaDTO(this.getNome(), getDataDeNascimentoFormatada(), this.getCpf(), this.getSexo().getDescricao(), null, null);
    }

    public Pessoa(String nome, LocalDate dataDeNascimento, String cpf, Sexo sexo, String matricula, String numeroCNH){
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
        this.sexo = sexo;
        this.matricula = matricula;
        this.numeroCNH = numeroCNH;

    }

}