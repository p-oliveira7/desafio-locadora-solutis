package br.com.locadora.api.domain.aluguel;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "apolices")
@Entity(name = "apolice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ApoliceSeguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorFranquia;

    private Boolean protecaoTerceiro;

    private Boolean protecaoCausaNatural;

    private Boolean protecaoRoubo;

    @OneToOne(mappedBy = "apoliceSeguro")
    private Aluguel aluguel;

}
