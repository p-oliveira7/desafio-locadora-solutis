package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.repositories.AluguelRepository;
import br.com.locadora.api.repositories.CarroRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AluguelService {
    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private AluguelRepository aluguelRepository;

    public AluguelResponseDTO alugar(AluguelApoliceRequestDTO dto) {
        var idCarro = dto.idCarro();
        var dataInicial = dto.dataEntrega();
        var dataFinal = dto.dataDevolucao();

        if (!verificaDisponibilidade(idCarro, dataInicial, dataFinal)) {
            throw new RuntimeException("O carro não está disponível para aluguel entre as datas especificadas.");
        }
        Carro carro = carroRepository.findCarroById(dto.idCarro());
        String placa = carro.getPlaca();

        Aluguel aluguel = new Aluguel(dto, carro);

        aluguelRepository.save(aluguel);

        return new AluguelResponseDTO(placa, dto.dataPedido(), dto.dataEntrega(), dto.dataDevolucao(),
                dto.apolice().valorFranquia(), dto.apolice().protecaoTerceiro(), dto.apolice().protecaoCausaNatural(), dto.apolice().protecaoRoubo());
    }

    private Boolean verificaDisponibilidade(Long idCarro, Date dataInicial, Date dataFinal) {
        if (!carroRepository.existsById(idCarro)) {
            throw new ValidationException("Carro informado não existe");
        }
        List<Aluguel> alugueis = aluguelRepository.findAlugueisByCarroAndRentalPeriodOverlapping(idCarro, dataInicial, dataFinal);
        return alugueis.isEmpty();
    }
}
