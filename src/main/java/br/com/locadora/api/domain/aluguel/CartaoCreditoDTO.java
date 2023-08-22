package br.com.locadora.api.domain.aluguel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CartaoCreditoDTO {
    @NotBlank(message = "Número do cartão é obrigatório")
    @Size(min = 16, max = 16, message = "Número do cartão deve conter 16 dígitos")
    @Pattern(regexp = "\\d+", message = "Número do cartão deve conter apenas dígitos")
    private String numero;

    @NotBlank(message = "Data de expiração é obrigatória")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/(\\d{2})$", message = "Formato da data de expiração deve ser MM/YY")
    private String expiracao;

    @NotBlank(message = "CVV é obrigatório")
    @Size(min = 3, max = 4, message = "CVV deve conter entre 3 e 4 dígitos")
    @Pattern(regexp = "\\d+", message = "CVV deve conter apenas dígitos")
    private String cvv;

    public CartaoCreditoDTO(String numero, String expiracao, String cvv) {
        this.numero = numero;
        this.expiracao = expiracao;
        this.cvv = cvv;
    }

    public String getNumero() {
        return numero;
    }

    public String getExpiracao() {
        return expiracao;
    }

    public String getCvv() {
        return cvv;
    }
}