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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/locadora/")
public class CarroController {
    private static final Logger logger = LoggerFactory.getLogger(CarroController.class);
    @Autowired
    private CarroService carroService;

    @Operation(summary = "Obter todos os carros")
    @GetMapping("/carros")
    public ResponseEntity<List<Carro>> findAll() {
        try{
        List<Carro> carros = carroService.findAll();
        return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao obter todos os carros: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por categoria")
    @GetMapping("/carros/categoria/{categoria}")
    public ResponseEntity<List<Carro>> listarCarrosPorCategoria(
            @Parameter(description = "Categoria dos carros a serem listados", required = true)
            @PathVariable Categoria categoria) {
        try {
            List<Carro> carros = carroService.listarCarrosPorCategoria(categoria);
            return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por categoria: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por fabricante")
    @GetMapping("/carros/fabricante/{nome}")
    public ResponseEntity<List<Carro>> listarCarrosPorFabricante(
            @Parameter(description = "Nome do fabricante dos carros a serem listados", required = true)
            @PathVariable String nome) {
        try {
            List<Carro> carros = carroService.listarCarrosPorFabricante(nome);
            return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por fabricante: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por acessório")
    @GetMapping("/carros/acessorios/{acessorio}")
    public ResponseEntity<List<Carro>> listarCarrosPorAcessorios(
            @Parameter(description = "Nome do acessório dos carros a serem listados", required = true)
            @PathVariable String acessorio) {
        try {
            List<Carro> carros = carroService.listarCarrosPorAcessorios(acessorio);
            return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por acessório: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por modelo")
    @GetMapping("/carros/modelo/{descricao}")
    public ResponseEntity<List<Carro>> listarCarrosPorModelo(
            @Parameter(description = "Descrição do modelo dos carros a serem listados", required = true)
            @PathVariable String descricao) {
        try {
            List<Carro> carros = carroService.listarCarrosPorModelo(descricao);
            return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por modelo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Cadastrar um novo carro")
    @PostMapping("/carros")
    public ResponseEntity<ResponseMessage> cadastrarCarro(
            @RequestBody @Valid CarroDTO carroDTO) {
        try{
        carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.ok(new ResponseMessage("Cadastro de carro feito com sucesso!"));
        } catch (Exception e) {
            logger.error("Erro ao cadastrar um novo carro: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Deletar um carro por ID")
    @DeleteMapping("/carros/{id}")
    public ResponseEntity<ResponseMessage> deletarCarro(
            @Parameter(description = "ID do carro a ser deletado", required = true)
            @PathVariable @Valid Long id) {
        try{
        carroService.deletarCarro(id);
        return ResponseEntity.ok(new ResponseMessage("Carro removido com sucesso!"));
        } catch (Exception e) {
            logger.error("Erro ao deletar carro: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
