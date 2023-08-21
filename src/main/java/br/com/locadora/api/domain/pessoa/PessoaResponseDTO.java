package br.com.locadora.api.domain.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PessoaResponseDTO(
        @NotBlank
        @NotNull
        String nome,
        @NotBlank
        String dataDeNascimento,
        @NotBlank
        @Pattern(regexp = "^([0-9]{3}\\.?){3}-?[0-9]{2}$", message = "O CPF deve estar no formato ###.###.###-## ou sem ponto e hífen")
        String cpf,
        @NotBlank
        String sexo,
        @Pattern(regexp = "\\d{6}", message = "A matrícula deve conter exatamente 6 dígitos")
        String matricula,
        @Pattern(regexp = "\\d{11}", message = "O número da CNH deve conter exatamente 11 dígitos")
        String numeroCNH){
}
