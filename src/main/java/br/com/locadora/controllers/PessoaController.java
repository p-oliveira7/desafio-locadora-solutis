package br.com.locadora.controllers;

import br.com.locadora.domain.pessoa.Pessoa;
import br.com.locadora.domain.pessoa.PessoaDTO;
import br.com.locadora.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locadora/")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public ResponseEntity findAll() {
        var pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/pessoas")
    public ResponseEntity cadastrarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO) {
        pessoaService.cadastrarPessoa(pessoaDTO);
        return ResponseEntity.ok("Cadastro feito!");
    }

    @PutMapping("/pessoas")
    public ResponseEntity atualizarPessoa(@RequestBody Pessoa pessoa) {
        pessoaService.atualizarPessoa(pessoa);
        return ResponseEntity.ok("Cadastro atualizado");
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity deletarPessoa(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.ok().build();
    }
}
