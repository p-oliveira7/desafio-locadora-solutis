package br.com.locadora.api.controllers;
import br.com.locadora.api.services.CarroServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.exceptions.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/locadora/")
@SecurityRequirement(name = "bearer-key")
public class CarroController {
    private static final Logger logger = LoggerFactory.getLogger(CarroController.class);
    @Autowired
    private CarroServiceInterface carroService;

    @Operation(summary = "Obter todos os carros")
    @GetMapping("/carros")
    public ResponseEntity<Page<CarroResponseDTO>> findAll(Pageable pageable) {
        try{
        Page<CarroResponseDTO> carros = carroService.findAll(pageable);
        return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Erro ao obter todos os carros: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por categoria")
    @GetMapping("/carros/categoria/{categoria}")
    public ResponseEntity<Page<CarroResponseDTO>> listarCarrosPorCategoria(
            @Parameter(description = "Categoria dos carros a serem listados", required = true)
            @PathVariable Categoria categoria, Pageable pageable) {
        try {
            Page<CarroResponseDTO> carrosPage = carroService.listarCarrosPorCategoria(categoria, pageable);
            return ResponseEntity.ok(carrosPage);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por categoria: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por fabricante")
    @GetMapping("/carros/fabricante/{nome}")
    public ResponseEntity<Page<CarroResponseDTO>> listarCarrosPorFabricante(
            @Parameter(description = "Nome do fabricante dos carros a serem listados", required = true)
            @PathVariable String nome, Pageable pageable) {
        try {
            Page<CarroResponseDTO> carrosPage = carroService.listarCarrosPorFabricante(nome, pageable);
            return ResponseEntity.ok(carrosPage);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por fabricante: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por acessório")
    @GetMapping("/carros/acessorios/{acessorio}")
    public ResponseEntity<Page<CarroResponseDTO>> listarCarrosPorAcessorios(
            @Parameter(description = "Nome do acessório dos carros a serem listados", required = true)
            @PathVariable String acessorio, Pageable pageable) {
        try {
            Page<CarroResponseDTO> carrosPage = carroService.listarCarrosPorAcessorios(acessorio, pageable);
            return ResponseEntity.ok(carrosPage);
        } catch (Exception e) {
            logger.error("Erro ao listar carros por acessório: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar carros por modelo")
    @GetMapping("/carros/modelo/{descricao}")
    public ResponseEntity<Page<CarroResponseDTO>> listarCarrosPorModelo(
            @Parameter(description = "Descrição do modelo dos carros a serem listados", required = true)
            @PathVariable String descricao, Pageable pageable) {
        try {
            Page<CarroResponseDTO> carrosPage = carroService.listarCarrosPorModelo(descricao, pageable);
            return ResponseEntity.ok(carrosPage);
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
