<<<<<<< Updated upstream:src/main/java/br/com/locadora/api/domain/pessoa/PessoaDTO.java
package br.com.locadora.api.domain.pessoa;
=======
package br.com.locadora.domain.pessoa;
import jakarta.validation.constraints.NotBlank;
>>>>>>> Stashed changes:src/main/java/br/com/locadora/domain/pessoa/PessoaDTO.java
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
