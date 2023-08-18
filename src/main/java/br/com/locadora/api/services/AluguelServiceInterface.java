package br.com.locadora.api.services;

import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import jakarta.servlet.http.HttpSession;


import java.util.List;

public interface AluguelServiceInterface {

    ListarCarrinhoDTO adicionarAluguelAoCarrinho(AluguelApoliceRequestDTO dto, Usuario user, HttpSession session);

    List<ListarCarrinhoDTO> getAlugueisDoUsuario(HttpSession session);

    ListarCarrinhoDTO modificarAluguelNoCarrinho(String temporaryId, AluguelApoliceRequestDTO novoDto, HttpSession session);

    void removerAluguel(String temporaryId, HttpSession session);

    void finalizarCompra(HttpSession session);
}
