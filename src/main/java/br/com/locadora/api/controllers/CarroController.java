package br.com.locadora.api.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.exceptions.ResponseMessage;
import br.com.locadora.api.services.CarroService;

import java.util.List;

@RestController
@RequestMapping("/locadora/")
public class CarroController {
    @Autowired
    private CarroService carroService;

    @Operation(summary = "Obter todos os carros")
    @GetMapping("/carros")
    public ResponseEntity<List<Carro>> findAll() {
        List<Carro> carros = carroService.findAll();
        return ResponseEntity.ok(carros);
    }

    @Operation(summary = "Listar carros por categoria")
    @GetMapping("/carros/categoria/{categoria}")
    public List<Carro> listarCarrosPorCategoria(
            @Parameter(description = "Categoria dos carros a serem listados", required = true)
            @PathVariable Categoria categoria) {
        return carroService.listarCarrosPorCategoria(categoria);
    }

    @Operation(summary = "Listar carros por fabricante")
    @GetMapping("/carros/fabricante/{nome}")
    public List<Carro> listarCarrosPorFabricante(
            @Parameter(description = "Nome do fabricante dos carros a serem listados", required = true)
            @PathVariable String nome) {
        return carroService.listarCarrosPorFabricante(nome);
    }

    @Operation(summary = "Listar carros por acessório")
    @GetMapping("/carros/acessorios/{acessorio}")
    public List<Carro> listarCarrosPorAcessorios(
            @Parameter(description = "Nome do acessório dos carros a serem listados", required = true)
            @PathVariable String acessorio) {
        return carroService.listarCarrosPorAcessorios(acessorio);
    }

    @Operation(summary = "Listar carros por modelo")
    @GetMapping("/carros/modelo/{descricao}")
    public List<Carro> listarCarrosPorModelo(
            @Parameter(description = "Descrição do modelo dos carros a serem listados", required = true)
            @PathVariable String descricao) {
        return carroService.listarCarrosPorModelo(descricao);
    }

    @Operation(summary = "Cadastrar um novo carro")
    @PostMapping("/carros")
    public ResponseEntity<ResponseMessage> cadastrarCarro(
            @RequestBody @Valid CarroDTO carroDTO) {
        carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.ok(new ResponseMessage("Cadastro de carro feito com sucesso!"));
    }

    @Operation(summary = "Deletar um carro por ID")
    @DeleteMapping("/carros/{id}")
    public ResponseEntity<ResponseMessage> deletarCarro(
            @Parameter(description = "ID do carro a ser deletado", required = true)
            @PathVariable @Valid Long id) {
        carroService.deletarCarro(id);
        return ResponseEntity.ok(new ResponseMessage("Carro removido com sucesso!"));
    }
}
