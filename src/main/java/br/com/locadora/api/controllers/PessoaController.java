package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locadora/")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> pessoasDTO = pessoaService.findAll();
        return ResponseEntity.ok(pessoasDTO);
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> cadastrarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO, @AuthenticationPrincipal Usuario user) {
        pessoaService.cadastrarPessoa(pessoaDTO, user);
        return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
    }

    @PutMapping("/pessoas")
    public ResponseEntity<Object> atualizarPessoa(@RequestBody @Valid PessoaDTO pessoaAtualizada, @AuthenticationPrincipal Usuario user) {
        pessoaService.atualizarPessoa(user, pessoaAtualizada);
        return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
    }

    @DeleteMapping("/pessoas/{cpf}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable @Valid String cpf) {
        pessoaService.deletarPessoa(cpf);
        return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
    }
}
