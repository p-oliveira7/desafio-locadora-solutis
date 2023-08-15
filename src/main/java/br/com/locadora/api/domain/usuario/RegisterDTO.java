package br.com.locadora.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
         @NotBlank
         @Email
         String email,
         @NotBlank
         String senha
) {
}
