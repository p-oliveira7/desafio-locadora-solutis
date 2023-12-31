package br.com.locadora.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.locadora.api.domain.carro.*;

import java.util.Date;
import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    Carro findCarroById(Long id);

    @Query("SELECT c FROM carro c WHERE c.id = :idCarro AND c.categoria = :categoria AND c.descricao = :descricao AND c.nome = :nome AND NOT EXISTS (" +
            "SELECT a FROM aluguel a WHERE a.carro = c AND a.dataEntrega < :dataFinal AND a.dataDevolucao > :dataInicial)")
    List<Carro> findCarrosAvailable(Long idCarro, Categoria categoria, String descricao, String nome, Date dataInicial, Date dataFinal);


    Page<Carro> findByDescricao(String descricao, Pageable pageable);

    Page<Carro> findByAcessorio(String acessorio, Pageable pageable);

    Page<Carro> findByCategoria(Categoria categoria, Pageable pageable);

    Page<Carro> findByNome(String nome, Pageable pageable);
}

