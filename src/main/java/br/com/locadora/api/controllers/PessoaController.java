package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locadora/")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> pessoasDTO = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoasDTO);
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> cadastrarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO) {
        try {
            pessoaService.cadastrarPessoa(pessoaDTO);
            return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }


    @PutMapping("/pessoas/{cpf}")
    public ResponseEntity<Object> atualizarPessoa(@PathVariable String cpf, @RequestBody @Valid PessoaDTO pessoaAtualizada) {
        pessoaService.atualizarPessoa(cpf, pessoaAtualizada);
        return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
    }

    @DeleteMapping("/pessoas/{cpf}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable @Valid String cpf) {
        pessoaService.deletarPessoa(cpf);
        return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
    }
}
