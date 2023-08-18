package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.AluguelService;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locadora")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;

    @Autowired
    private HttpServletRequest request;

    // Adicionar item no carrinho
    @PostMapping("/add")
    @Transactional
    public ListarCarrinhoDTO adicionarAluguelAoCarrinho(@RequestBody @Valid AluguelApoliceRequestDTO dto) {
        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpSession session = request.getSession();
        return aluguelService.adicionarAluguelAoCarrinho(dto, user, session);
    }

    // Listar itens no carrinho
    @GetMapping("/meus-alugueis")
    public ResponseEntity<List<ListarCarrinhoDTO>> getMeusAlugueis() {
        HttpSession session = request.getSession();
        try {
            List<ListarCarrinhoDTO> alugueis = aluguelService.getAlugueisDoUsuario(session);
            return ResponseEntity.ok(alugueis);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Modificar um item no carrinho por meio de chave temporaria
    @PostMapping("/modificar-aluguel")
    public ResponseEntity<ListarCarrinhoDTO> modificarAluguelNoCarrinho(
            @RequestParam String temporaryId,
            @RequestBody AluguelApoliceRequestDTO novoDto) {
        HttpSession session = request.getSession();
        try {
            ListarCarrinhoDTO modCarrinho = aluguelService.modificarAluguelNoCarrinho(temporaryId, novoDto, session);
            return ResponseEntity.ok(modCarrinho);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Apaga um item do carrinho identificado por chave temporaria
    @DeleteMapping("/alugueis")
    public ResponseEntity<String> removerAluguel(@RequestParam String temporaryId) {
        HttpSession session = request.getSession();
        aluguelService.removerAluguel(temporaryId, session);
        return ResponseEntity.ok("Aluguel removido com sucesso.");
    }

    // Finalizar compra de todos os itens do carrinho
    @PostMapping("/finalizar-compra")
    @Transactional
    public ResponseEntity finalizarCompra() {
        HttpSession session = request.getSession();
        aluguelService.finalizarCompra(session);
        return ResponseEntity.ok("Compra finalizada com sucesso.");
    }
}

