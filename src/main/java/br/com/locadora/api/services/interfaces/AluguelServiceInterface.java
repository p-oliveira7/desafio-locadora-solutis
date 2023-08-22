package br.com.locadora.api.services.interfaces;
import br.com.locadora.api.domain.aluguel.CartaoCreditoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
public interface AluguelServiceInterface {

    ListarCarrinhoDTO addAluguel(Long idCarro, AluguelApoliceRequestDTO dto, Usuario user);

    Page<ListarCarrinhoDTO> listarAlugueisPagos(Usuario user, Pageable pageable);

    Page<ListarCarrinhoDTO> listar(Usuario user, Pageable pageable);

    ListarCarrinhoDTO atualizarCarrinho(Usuario user, Long id, AluguelApoliceRequestDTO aluguelAtualizacaoDTO);

    void apagarItem(Long id, Usuario user);

    void pagarAluguel(Long idAluguel, Usuario user, CartaoCreditoDTO cartaoCreditoDTO);

}
