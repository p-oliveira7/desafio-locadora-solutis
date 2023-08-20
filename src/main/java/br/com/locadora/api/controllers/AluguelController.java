package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.CartaoCreditoDTO;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.AluguelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("locadora")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;

    @PostMapping
    @RequestMapping("add")
    public ListarCarrinhoDTO addAluguel(@RequestParam Long idCarro,
            @RequestBody @Valid AluguelApoliceRequestDTO dto,
            @AuthenticationPrincipal Usuario user){
        return aluguelService.addAluguel(idCarro, dto, user);
    }

    @GetMapping
    @RequestMapping("carrinho")
    public List<ListarCarrinhoDTO> listar(@AuthenticationPrincipal Usuario user){
        return aluguelService.listar(user);
    }

    @PutMapping
    @RequestMapping("atualizar")
    public ResponseEntity<ListarCarrinhoDTO> atualizarCarrinho(
            @RequestParam Long id,
            @RequestBody AluguelApoliceRequestDTO aluguelAtualizacaoDTO,
            @AuthenticationPrincipal Usuario user) {
          var aluguelAtalizado =aluguelService.atualizarCarrinho(user, id, aluguelAtualizacaoDTO);
            return ResponseEntity.ok(aluguelAtalizado);
    }
    @DeleteMapping
    @RequestMapping("apagar")
    public ResponseEntity apagarItem(@RequestParam Long id,
                                     @AuthenticationPrincipal Usuario user){
        aluguelService.apagarItem(id, user);
        return ResponseEntity.ok(new ResponseMessage("Aluguel removido com sucesso!"));
    }
    @PostMapping("pagamento")
    public ResponseEntity<String> pagarAluguel(
            @RequestParam Long id,
            @AuthenticationPrincipal Usuario user,
            @RequestBody CartaoCreditoDTO cartaoCreditoDTO) {

        aluguelService.pagarAluguel(id, user, cartaoCreditoDTO);
            return ResponseEntity.ok("Pagamento efetuado com sucesso.");
    }
    @GetMapping("/pagos")
    public ResponseEntity<List<ListarCarrinhoDTO>> listarAlugueisPagos(@AuthenticationPrincipal Usuario user) {
        List<ListarCarrinhoDTO> alugueisPagos = aluguelService.listarAlugueisPagos(user);
        return ResponseEntity.ok(alugueisPagos);
    }
}

