package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.PessoaService;
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
    public ResponseEntity<Object> findAll() {
        // TODO: usar a mesma lógica de que deve enviar a pessoaDTO
        var pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> cadastrarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO) {
        pessoaService.cadastrarPessoa(pessoaDTO);
        return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
    }

    @PutMapping("/pessoas")
    public ResponseEntity<Object> atualizarPessoa(@RequestBody PessoaDTO pessoaAtualizada) {
        pessoaService.atualizarPessoa(pessoaAtualizada);
        return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable @Valid Long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
    }
}
