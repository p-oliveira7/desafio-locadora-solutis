package br.com.locadora.api.domain.pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private String nome;
    private String dataDeNascimento;
    private String cpf;
    private String matricula;
    private String numeroCNH;
}
