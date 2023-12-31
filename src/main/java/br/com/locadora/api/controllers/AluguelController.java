package br.com.locadora.api.controllers;

import br.com.locadora.api.services.AluguelServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.CartaoCreditoDTO;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.exceptions.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("locadora")
@SecurityRequirement(name = "bearer-key")
public class AluguelController{
    private static final Logger logger = LoggerFactory.getLogger(AluguelController.class);
    @Autowired
    private AluguelServiceInterface aluguelService;

    @Operation(summary = "Adicionar um novo aluguel ao carrinho")
    @PostMapping("add")
    public ResponseEntity<ListarCarrinhoDTO> addAluguel(
            @Parameter(description = "ID do carro a ser alugado", required = true)
            @RequestParam Long idCarro,
            @RequestBody @Valid AluguelApoliceRequestDTO dto,
            @AuthenticationPrincipal Usuario user) {
        try {
            logger.info("Adicionando aluguel ao carrinho para o usuário: " + user.getEmail());
            ListarCarrinhoDTO carrinhoDTO = aluguelService.addAluguel(idCarro, dto, user);
            return ResponseEntity.ok(carrinhoDTO);
        } catch (Exception e) {
            logger.error("Erro ao adicionar aluguel ao carrinho: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar aluguéis no carrinho")
    @GetMapping("carrinho")
    public Page<ListarCarrinhoDTO> listar(@AuthenticationPrincipal Usuario user, Pageable pageable) {
        return aluguelService.listar(user, pageable);
    }

    @Operation(summary = "Atualizar um aluguel no carrinho")
    @PutMapping("atualizar")
    public ResponseEntity<ListarCarrinhoDTO> atualizarCarrinho(
            @Parameter(description = "ID do aluguel a ser atualizado", required = true)
            @RequestParam Long id,
            @RequestBody AluguelApoliceRequestDTO aluguelAtualizacaoDTO,
            @AuthenticationPrincipal Usuario user) {
        try{
        var aluguelAtualizado = aluguelService.atualizarCarrinho(user, id, aluguelAtualizacaoDTO);
        return ResponseEntity.ok(aluguelAtualizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar carrinho: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Apagar um aluguel do carrinho")
    @DeleteMapping("apagar")
    public ResponseEntity apagarItem(
            @Parameter(description = "ID do aluguel a ser removido", required = true)
            @RequestParam Long id,
            @AuthenticationPrincipal Usuario user) {
        try{
        aluguelService.apagarItem(id, user);
        return ResponseEntity.ok(new ResponseMessage("Aluguel removido com sucesso!"));
        } catch (Exception e) {
            logger.error("Erro ao remover aluguel do carrinho: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Efetuar pagamento de um aluguel")
    @PostMapping("pagamento")
    public ResponseEntity<String> pagarAluguel(
            @Parameter(description = "ID do aluguel a ser pago", required = true)
            @RequestParam Long id,
            @AuthenticationPrincipal Usuario user,
            @RequestBody CartaoCreditoDTO cartaoCreditoDTO) {
try{
        aluguelService.pagarAluguel(id, user, cartaoCreditoDTO);
        return ResponseEntity.ok("Pagamento efetuado com sucesso.");
} catch (Exception e) {
    logger.error("Erro ao efetuar pagamento do aluguel: " + e.getMessage());
    return ResponseEntity.badRequest().build();
}
    }

    @Operation(summary = "Listar aluguéis pagos")
    @GetMapping("/pagos")
    public ResponseEntity<Page<ListarCarrinhoDTO>> listarAlugueisPagos(@AuthenticationPrincipal Usuario user, Pageable pageable) {
        try{
        Page<ListarCarrinhoDTO> alugueisPagos = aluguelService.listarAlugueisPagos(user, pageable);
        return ResponseEntity.ok(alugueisPagos);
        } catch (Exception e) {
            logger.error("Erro ao listar aluguéis pagos: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

