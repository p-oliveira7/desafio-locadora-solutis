package br.com.locadora.api.domain.pessoa;

import br.com.locadora.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity

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

    @OneToOne(mappedBy = "pessoa")
    private Usuario usuario;

    public abstract void atualizar(PessoaDTO pessoaAtualizada);

    public Pessoa(String nome, LocalDate dataDeNascimento, String cpf, Sexo sexo, String matricula, String numeroCNH){
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
        this.sexo = sexo;
        this.matricula = matricula;
        this.numeroCNH = numeroCNH;

    }

    public String getDataDeNascimentoFormatada() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.getDataDeNascimento().format(dateFormatter);
    }

}