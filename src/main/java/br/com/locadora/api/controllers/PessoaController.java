package br.com.locadora.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.PessoaService;

import java.util.List;

@RestController
@RequestMapping("/locadora/")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Obter todas as pessoas")
    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> pessoasDTO = pessoaService.findAll();
        return ResponseEntity.ok(pessoasDTO);
    }

    @Operation(summary = "Cadastrar uma nova pessoa")
    @PostMapping("/pessoas")
    public ResponseEntity<ResponseMessage> cadastrarPessoa(
            @RequestBody @Valid PessoaDTO pessoaDTO,
            @AuthenticationPrincipal Usuario user) {
        pessoaService.cadastrarPessoa(pessoaDTO, user);
        return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
    }

    @Operation(summary = "Atualizar uma pessoa")
    @PutMapping("/pessoas")
    public ResponseEntity<ResponseMessage> atualizarPessoa(
            @RequestBody @Valid PessoaDTO pessoaAtualizada,
            @AuthenticationPrincipal Usuario user) {
        pessoaService.atualizarPessoa(user, pessoaAtualizada);
        return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
    }

    @Operation(summary = "Deletar uma pessoa por CPF")
    @DeleteMapping("/pessoas/{cpf}")
    public ResponseEntity<ResponseMessage> deletarPessoa(
            @Parameter(description = "CPF da pessoa a ser deletada", required = true)
            @PathVariable @Valid String cpf) {
        pessoaService.deletarPessoa(cpf);
        return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
    }
}
