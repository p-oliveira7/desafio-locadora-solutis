package br.com.locadora.api.controllers;
import br.com.locadora.api.domain.carro.CarroDTO;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locadora/")
public class CarroController {
    @Autowired
    private CarroService carroService;
    @GetMapping("/carros")
    public ResponseEntity<Object> findAll() {

        var carros = carroService.findAll();
        return ResponseEntity.ok(carros);
    }

    @PostMapping("/carros")
    public ResponseEntity<Object> cadastrarCarro(@RequestBody @Valid CarroDTO carroDTO) {
        carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.ok(new ResponseMessage("Cadastro de carro feito com sucesso!"));
    }

    @DeleteMapping("/carros/{id}")
    public ResponseEntity<Object> deletarCarro(@PathVariable @Valid Long id) {
        carroService.deletarCarro(id);
        return ResponseEntity.ok(new ResponseMessage("Carro removido com sucesso!"));
    }
}
