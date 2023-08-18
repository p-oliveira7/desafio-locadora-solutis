package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguro;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true)
    private String temporaryId;

    private Calendar dataPedido;

    private Date dataEntrega;

    private Date dataDevolucao;

    @Embedded
    private ApoliceSeguro apoliceSeguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id") // Chave estrangeira para a tabela de Pessoas
    private Pessoa pessoa;


    public Aluguel(AluguelApoliceRequestDTO dados, Carro carro) {
        this.dataPedido = dados.dataPedido();
        this.dataEntrega = dados.dataEntrega();
        this.dataDevolucao = dados.dataDevolucao();
        this.apoliceSeguro = new ApoliceSeguro(dados.apolice());
        this.carro = carro;
    }

}
