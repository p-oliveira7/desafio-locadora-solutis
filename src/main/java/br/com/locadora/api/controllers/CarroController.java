package br.com.locadora.api.controllers;
import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/carros/categoria/{categoria}")
    public List<Carro> listarCarrosPorCategoria(@PathVariable Categoria categoria){
        return carroService.listarCarrosPorCategoria(categoria);
    }

   @GetMapping("/carros/fabricante/{nome}")
    public List<Carro> listarCarrosPorFabricante(@PathVariable String nome){
        return carroService.listarCarrosPorFabricante(nome);
    }

    @GetMapping("/carros/acessorios/{acessorio}")
    public List<Carro> listarCarrosPorAcessorios(@PathVariable String acessorio){
        return carroService.listarCarrosPorAcessorios(acessorio);
    }

    @GetMapping("/carros/modelo/{descricao}")
    public List<Carro> listarCarrosPorModelo(@PathVariable String descricao){
        return carroService.listarCarrosPorModelo(descricao);
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
