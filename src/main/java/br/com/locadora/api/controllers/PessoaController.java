package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.pessoa.PessoaResponseDTO;
import br.com.locadora.api.services.PessoaServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.impl.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/locadora/")
@SecurityRequirement(name = "bearer-key")
public class PessoaController {
    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);
    @Autowired
    private PessoaServiceInterface pessoaService;

    @Operation(summary = "Obter todas as pessoas do usuário")
    @GetMapping("/pessoas")
    public ResponseEntity<Page<PessoaResponseDTO>> findAll(@AuthenticationPrincipal Usuario user, Pageable pageable) {
        try {
            Page<PessoaResponseDTO> pessoasDTOPage = pessoaService.listarPessoas(user, pageable);
            return ResponseEntity.ok(pessoasDTOPage);
        } catch (Exception e) {
            logger.error("Erro ao obter todas as pessoas: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /*@Operation(summary = "Obter todas as pessoas")
    @GetMapping("/pessoas")
    public ResponseEntity<Page<PessoaResponseDTO>> findAll(Pageable pageable) {
        try {
            Page<PessoaResponseDTO> pessoasDTOPage = pessoaService.listarPessoas(pageable);
            return ResponseEntity.ok(pessoasDTOPage);
        } catch (Exception e) {
            logger.error("Erro ao obter todas as pessoas: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }*/

    @Operation(summary = "Cadastrar uma nova pessoa")
    @PostMapping("/pessoas")
    public ResponseEntity<ResponseMessage> cadastrarPessoa(
            @RequestBody @Valid PessoaDTO pessoaDTO,
            @AuthenticationPrincipal Usuario user) {
        try {
            pessoaService.cadastrarPessoa(pessoaDTO, user);
            return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
        } catch (Exception e) {
            logger.error("Erro ao cadastrar nova pessoa: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessage("Erro ao cadastrar pessoa."));
        }
    }

    @Operation(summary = "Atualizar uma pessoa")
    @PutMapping("/pessoas/{cpf}")
    public ResponseEntity<ResponseMessage> atualizarPessoa(
            @RequestBody @Valid PessoaDTO pessoaAtualizada,
            @PathVariable String cpf,
            @AuthenticationPrincipal Usuario user) {
        try {
            pessoaService.atualizarPessoa(cpf, user, pessoaAtualizada);
            return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
        } catch (Exception e) {
            logger.error("Erro ao atualizar informações da pessoa: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessage("Erro ao atualizar pessoa."));
        }
    }

    @Operation(summary = "Deletar uma pessoa por CPF")
    @DeleteMapping("/pessoas/{cpf}")
    public ResponseEntity<ResponseMessage> deletarPessoaPorCPF(
            @Parameter(description = "CPF da pessoa a ser deletada", required = true)
            @PathVariable String cpf) {
        try {
            pessoaService.deletarPessoaPorCPF(cpf);
            return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
        } catch (Exception e) {
            logger.error("Erro ao deletar pessoa: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseMessage("Erro ao deletar pessoa."));
        }
    }


}
