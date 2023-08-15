package br.com.locadora.api.controllers;

import br.com.locadora.api.domain.aluguel.AluguelApoliceInputDTO;
import br.com.locadora.api.domain.aluguel.AluguelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;
/*
    @PostMapping
    @Transactional
    public ResponseEntity alugar(@RequestBody @Valid AluguelApoliceInputDTO dados) {
        var dto = aluguelService.alugar(dados);
    } */
}
