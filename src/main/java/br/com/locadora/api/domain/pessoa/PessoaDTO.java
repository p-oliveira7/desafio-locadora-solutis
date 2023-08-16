package br.com.locadora.api.domain.pessoa;

import jakarta.validation.constraints.NotBlank;
public record PessoaDTO (
    @NotBlank
    String nome,
    @NotBlank
    String dataDeNascimento,
    @NotBlank
    String cpf,
    @NotBlank
    String sexo,
    String matricula,
    String numeroCNH
) {
}
