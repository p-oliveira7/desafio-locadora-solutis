package br.com.locadora.api.services.interfaces;

import br.com.locadora.api.domain.carro.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarroServiceInterface {

    Page<CarroResponseDTO> findAll(Pageable pageable);

    Page<CarroResponseDTO> listarCarrosPorCategoria(Categoria categoria, Pageable pageable);

    Page<CarroResponseDTO> listarCarrosPorModelo(String descricao, Pageable pageable);

    Page<CarroResponseDTO> listarCarrosPorAcessorios(String acessorio, Pageable pageable);

    Page<CarroResponseDTO> listarCarrosPorFabricante(String nome, Pageable pageable);

}
