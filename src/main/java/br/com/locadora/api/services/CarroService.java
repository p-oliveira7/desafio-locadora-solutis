package br.com.locadora.api.services;

import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.domain.pessoa.Pessoa;
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
        carroRepository.save(carro);
    }

    public void deletarCarro(Long id) {
        Carro carro = carroRepository.findById(id).orElse(null);
        if (carro != null) {
            carroRepository.delete(carro);
        } else {
            throw new EntityNotFoundException("Carro não encontrado!");
        }
    }

    public List<Carro> listarCarrosPorCategoria(Categoria categoria){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getCategoria().equals(categoria));
        return carros;
    }


    public List<Carro> listarCarrosPorModelo(ModeloCarro descricao){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getDescricao().equals(descricao));
        return carros;
    }


  public List<Carro> listarCarrosPorAcessorios(Acessorio acessorio){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getAcessorio().equals(acessorio));
        return carros;
        }


    public List<Carro> listarCarrosPorFabricante(Fabricante nome){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getNome().equals(nome));
        return carros;
    }

    }
