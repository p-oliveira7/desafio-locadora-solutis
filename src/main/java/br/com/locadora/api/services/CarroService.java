package br.com.locadora.api.services;

import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.carro.CarroDTO;
import br.com.locadora.api.domain.pessoa.Funcionario;
import br.com.locadora.api.domain.pessoa.Motorista;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.repositories.CarroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    private Carro converterDTOparaEntidade(CarroDTO carroDTO) {
        Carro carro = new Carro(carroDTO.placa(), carroDTO.chassi(), carroDTO.cor(), carroDTO.valorDiaria(), carroDTO.categoria(), carroDTO.acessorio(), carroDTO.descricao(), carroDTO.nome());
        return carro;
    }

    public void cadastrarCarro(CarroDTO carroDTO) {
        Carro carro = converterDTOparaEntidade(carroDTO);
        carroRepository.save(carro); // Persiste a entidade no banco de dados
    }

    public void deletarCarro(Long id) {
        Carro carro = carroRepository.findById(id).orElse(null);
        if (carro != null) {
            carroRepository.delete(carro);
        } else {
            throw new EntityNotFoundException("Carro não encontrado!");
        }
    }
}