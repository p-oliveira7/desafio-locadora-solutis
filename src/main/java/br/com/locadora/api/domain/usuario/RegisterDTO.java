package br.com.locadora.api.domain.usuario;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.support.BeanDefinitionDsl;

public record RegisterDTO(
         @NotBlank
         @Email
         String email,
         @NotBlank
         String senha,

         Role role
) {
}
