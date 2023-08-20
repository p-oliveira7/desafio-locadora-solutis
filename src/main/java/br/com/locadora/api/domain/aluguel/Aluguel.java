package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguro;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.pessoa.Pessoa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
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

    private BigDecimal valorTotal;

    @Embedded
    private ApoliceSeguro apoliceSeguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id") // Chave estrangeira para a tabela de Pessoas
    private Pessoa pessoa;

    private Boolean status;

    public void calcularValorTotal() {
        if (dataEntrega != null && dataDevolucao != null) {
            long diasAlugados = ChronoUnit.DAYS.between(dataEntrega.toInstant(), dataDevolucao.toInstant()) + 1;
            BigDecimal valorDiarias = carro.getValorDiaria().multiply(BigDecimal.valueOf(diasAlugados));
            if (apoliceSeguro.getValorFranquia() != null) {
                valorTotal = valorDiarias.add(apoliceSeguro.getValorFranquia());
            } else {
                valorTotal = valorDiarias;
            }
        }
    }
    public Aluguel(AluguelApoliceRequestDTO dados, Carro carro) {
        this.dataPedido = dados.dataPedido();
        this.dataEntrega = dados.dataEntrega();
        this.dataDevolucao = dados.dataDevolucao();
        this.apoliceSeguro = new ApoliceSeguro(dados.apolice());
        this.carro = carro;
    }

    public void atualizarInformacoes(AluguelApoliceRequestDTO dados) {
        if (dados.dataPedido() != null) {
            this.dataPedido = dados.dataPedido();
        }
        if (dados.dataEntrega() != null) {
            this.dataEntrega = dados.dataEntrega();
        }
        if (dados.dataDevolucao() != null) {
            this.dataDevolucao = dados.dataDevolucao();
        }
        if (dados.getApoliceSeguro() != null) {
            this.apoliceSeguro = new ApoliceSeguro(dados.getApoliceSeguro());
        }

    }
}
