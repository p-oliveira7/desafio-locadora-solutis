package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.carro.Carro;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Table(name = "alugueis")
@Entity(name = "aluguel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Calendar dataPedido;

    private Date dataEntrega;

    private Date dataDevolucao;

    @OneToOne
    private Carro carro;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true) // Pode ser ou não bidirecional
    @JoinColumn(name = "apolice_id")
    private ApoliceSeguro apoliceSeguro;

    public Aluguel(Calendar dataPedido, Date dataEntrega, Date dataDevolucao, BigDecimal valorFranquia,
                   Boolean protecaoTerceiro, Boolean protecaoCausaNatural, Boolean protecaoRoubo) {

        this.dataPedido = dataPedido;
        this.dataEntrega = dataEntrega;
        this.dataDevolucao = dataDevolucao;
    }
}
