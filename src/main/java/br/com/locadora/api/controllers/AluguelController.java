package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.AluguelService;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @PostMapping("/alugar")
    @Transactional
    public ResponseEntity alugar(@RequestBody @Valid AluguelApoliceRequestDTO dados) {
        var dto = aluguelService.alugar(dados);

        return ResponseEntity.ok(dto);
    }
    @PostMapping("/add")
    @Transactional
    public ListarCarrinhoDTO adicionarAluguelAoCarrinho(@RequestBody @Valid AluguelApoliceRequestDTO dto) {
        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpSession session = request.getSession();
        return aluguelService.adicionarAluguelAoCarrinho(dto, user, session);
    }
    @GetMapping("/meus-alugueis")
    public ResponseEntity<List<ListarCarrinhoDTO>> getMeusAlugueis() {
        HttpSession session = request.getSession();
        try {
            List<ListarCarrinhoDTO> alugueis = aluguelService.getAlugueisDoUsuario(session);
            return ResponseEntity.ok(alugueis);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/finalizar-compra")
    @Transactional
    public ResponseEntity finalizarCompra() {
        aluguelService.finalizarCompra();
        return ResponseEntity.ok("Compra finalizada com sucesso.");
    }
    @PostMapping("/modificar-aluguel")
    public ResponseEntity<ListarCarrinhoDTO> modificarAluguelNoCarrinho(
            @RequestParam String temporaryId,
            @RequestBody AluguelApoliceRequestDTO novoDto) {
        try {
            ListarCarrinhoDTO modCarrinho = aluguelService.modificarAluguelNoCarrinho(temporaryId, novoDto);
            return ResponseEntity.ok(modCarrinho);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/alugueis")
    public ResponseEntity<String> removerAluguel(@PathVariable String temporaryId) {
        HttpSession session = request.getSession();
        aluguelService.removerAluguel(temporaryId, session);
        return ResponseEntity.ok("Aluguel removido com sucesso.");
    }
}

