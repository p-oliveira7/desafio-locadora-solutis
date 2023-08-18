package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoAlugueis {

    private Usuario usuario; // Vincula o carrinho ao usuário autenticado

    private List<Aluguel> alugueis = new ArrayList<>();

    public CarrinhoAlugueis(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarAluguel(Aluguel aluguel) {
        alugueis.add(aluguel);
    }

    public void removerAluguel(Aluguel aluguel) {
        alugueis.remove(aluguel);
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public boolean contemAluguelComPlacaEData(Long id, Date dataEntrega, Date dataDevolucao) {
        return alugueis.stream().anyMatch(a ->
                a.getCarro().getId().equals(id) &&
                        a.getDataEntrega().equals(dataEntrega) &&
                        a.getDataDevolucao().equals(dataDevolucao)
        );
    }
}
