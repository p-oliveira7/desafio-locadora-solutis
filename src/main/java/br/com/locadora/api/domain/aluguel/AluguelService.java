package br.com.locadora.api.domain.aluguel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AluguelService {

    public void alugar(AluguelApoliceInputDTO dto) {
        Aluguel aluguel = new Aluguel(dto.dataPedido(),dto.dataEntrega(), dto.dataDevolucao(),
                dto.valorFranquia(), dto.protecaoTerceiro(), dto.protecaoCausaNatural(), dto.protecaoRoubo());
        // Nota: Estou considerando que a apolice é feita obrigatoriamente junto ao aluguel


    }

}
