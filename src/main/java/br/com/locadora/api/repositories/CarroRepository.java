package br.com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.locadora.api.domain.carro.*;

import java.util.Date;
import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    @Query("SELECT c FROM Carro c WHERE c.id = :idCarro AND c.categoria = :categoria AND c.descricao = :descricao AND c.nome = :nome AND NOT EXISTS (" +
            "SELECT a FROM aluguel a WHERE a.carro = c AND a.dataEntrega < :dataFinal AND a.dataDevolucao > :dataInicial)")
    List<Carro> findCarrosAvailable(Long idCarro, Categoria categoria, String descricao, String nome, Date dataInicial, Date dataFinal);
}

