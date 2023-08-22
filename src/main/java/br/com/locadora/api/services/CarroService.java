package br.com.locadora.api.services;

import br.com.locadora.api.domain.carro.*;
import br.com.locadora.api.mappers.CarroMapper;
import br.com.locadora.api.repositories.CarroRepository;
import br.com.locadora.api.services.interfaces.CarroServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.locadora.api.domain.carro.CarroResponseDTO;
import org.springframework.stereotype.Service;


@Service
public class CarroService implements CarroServiceInterface {

    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private CarroMapper carroMapper;

    @Override
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
    @Override
    public Page<CarroResponseDTO> listarCarrosPorCategoria(Categoria categoria, Pageable pageable) {
        Page<Carro> carrosPage = carroRepository.findByCategoria(categoria, pageable);

        Page<CarroResponseDTO> carrosResponsePage = carrosPage.map(carroMapper::toDto);

        return carrosResponsePage;
    }
    @Override
    public Page<CarroResponseDTO> listarCarrosPorModelo(String descricao, Pageable pageable) {
        Page<Carro> carrosPage = carroRepository.findByDescricao(descricao, pageable);

        Page<CarroResponseDTO> carrosResponsePage = carrosPage.map(carroMapper::toDto);

        return carrosResponsePage;
    }

    @Override
    public Page<CarroResponseDTO> listarCarrosPorAcessorios(String acessorio, Pageable pageable) {
        Page<Carro> carrosPage = carroRepository.findByAcessorio(acessorio, pageable);

        Page<CarroResponseDTO> carrosResponsePage = carrosPage.map(carroMapper::toDto);

        return carrosResponsePage;
    }

    @Override
    public Page<CarroResponseDTO> listarCarrosPorFabricante(String nome, Pageable pageable) {
        Page<Carro> carrosPage = carroRepository.findByNome(nome, pageable);

        Page<CarroResponseDTO> carrosResponsePage = carrosPage.map(carroMapper::toDto);

        return carrosResponsePage;
    }

    }
