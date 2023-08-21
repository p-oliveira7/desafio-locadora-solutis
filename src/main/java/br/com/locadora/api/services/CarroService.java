package br.com.locadora.api.services;

import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.mappers.CarroMapper;
import br.com.locadora.api.repositories.CarroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private CarroMapper carroMapper;

    public Page<CarroResponseDTO> findAll(Pageable pageable) {
        Page<Carro> carrosPage = carroRepository.findAll(pageable);
        return carrosPage.map(carroMapper::toDto);
    }

    private Carro converterDTOparaEntidade(CarroDTO carroDTO) {
        Carro carro = new Carro(carroDTO.placa(), carroDTO.chassi(), carroDTO.cor(), carroDTO.valorDiaria(), carroDTO.categoria(), carroDTO.acessorio(), carroDTO.descricao(), carroDTO.nome(), carroDTO.imagePath());
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


    public List<Carro> listarCarrosPorModelo(String descricao){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getDescricao().equals(descricao));
        return carros;
    }


  public List<Carro> listarCarrosPorAcessorios(String acessorio){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getAcessorio().equals(acessorio));
        return carros;
        }


    public List<Carro> listarCarrosPorFabricante(String nome){
        List<Carro> carros = carroRepository.findAll();

        carros.removeIf(carro -> !carro.getNome().equals(nome));
        return carros;
    }

    }
