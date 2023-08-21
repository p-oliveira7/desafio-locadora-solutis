package br.com.locadora.api.domain.apolice;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApoliceSeguro {
    private BigDecimal valorFranquia;

    private Boolean protecaoTerceiro;

    private Boolean protecaoCausaNatural;

    private Boolean protecaoRoubo;

    public ApoliceSeguro(ApoliceSeguroRequestDTO dados) {
        this.protecaoTerceiro = dados.protecaoTerceiro();
        this.protecaoCausaNatural = dados.protecaoCausaNatural();
        this.protecaoRoubo = dados.protecaoRoubo();

    }
    public BigDecimal calcularValorFranquia(BigDecimal valorTotalAluguel) {
        BigDecimal porcentagem = BigDecimal.ZERO;

        if (protecaoTerceiro) {
            porcentagem = porcentagem.add(BigDecimal.valueOf(0.01));
        }
        if (protecaoCausaNatural) {
            porcentagem = porcentagem.add(BigDecimal.valueOf(0.02));
        }
        if (protecaoRoubo) {
            porcentagem = porcentagem.add(BigDecimal.valueOf(0.05));
        }

        BigDecimal valorFranquiaCalculado = valorTotalAluguel.multiply(porcentagem);
        this.valorFranquia = valorFranquiaCalculado;
        return valorFranquiaCalculado;
    }



    public void atualizarInformacoes(ApoliceSeguroRequestDTO dados) {
        if (dados.protecaoTerceiro() != null) {
            this.protecaoTerceiro = dados.protecaoTerceiro();
        }
        if (dados.protecaoCausaNatural() != null) {
            this.protecaoCausaNatural = dados.protecaoCausaNatural();
        }
        if (dados.protecaoRoubo() != null) {
            this.protecaoRoubo = dados.protecaoRoubo();
        }
    }
}
