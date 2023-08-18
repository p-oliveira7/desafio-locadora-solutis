package br.com.locadora.api.domain.apolice;

import br.com.locadora.api.domain.aluguel.Aluguel;
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

    public ApoliceSeguro(ApoliceSeguroRequestDTO dados){
        this.valorFranquia = dados.valorFranquia();
        this.protecaoTerceiro = dados.protecaoTerceiro();
        this.protecaoCausaNatural = dados.protecaoCausaNatural();
        this.protecaoRoubo = dados.protecaoRoubo();
    }
}
