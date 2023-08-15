package br.com.locadora.api.domain.pessoa;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String nome;

    @NotBlank
    private String dataDeNascimento;

    @NotBlank
    private String cpf;

    @NotBlank
    private String sexo;

    private String matricula;
    private String numeroCNH;
}
