package br.com.locadora.api.controllers;

<<<<<<< Updated upstream:src/main/java/br/com/locadora/api/controllers/PessoaController.java
import br.com.locadora.api.services.PessoaService;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
=======
import br.com.locadora.domain.pessoa.Pessoa;
import br.com.locadora.domain.pessoa.PessoaDTO;
import br.com.locadora.exceptions.ResponseMessage;
import br.com.locadora.services.PessoaService;
>>>>>>> Stashed changes:src/main/java/br/com/locadora/controllers/PessoaController.java
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
        var pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Object> cadastrarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO) {
        pessoaService.cadastrarPessoa(pessoaDTO);
        return ResponseEntity.ok(new ResponseMessage("Cadastro feito com sucesso."));
    }

    @PutMapping("/pessoas")
    public ResponseEntity<Object> atualizarPessoa(@RequestBody Pessoa pessoaAtualizada) {
        pessoaService.atualizarPessoa(pessoaAtualizada);
        return ResponseEntity.ok(new ResponseMessage("Cadastro atualizado com sucesso."));
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable @Valid Long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.ok(new ResponseMessage("Pessoa deletada com sucesso."));
    }
}
