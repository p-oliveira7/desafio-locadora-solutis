package br.com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.locadora.api.domain.carro.*;

import java.util.Date;
import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    Carro findCarroById(Long id);
    @Query("SELECT DISTINCT a.carro FROM Aluguel a WHERE a.carro.id = :idCarro AND a.dataEntrega < :dataFinal AND a.dataDevolucao > :dataInicial AND a.carro.categoria = :categoria AND a.carro.descricao = :descricao AND a.carro.nome = :nome")
    List<Carro> findCarrosAvailable(Long idCarro, Categoria categoria, String descricao, String nome, Date dataInicial, Date dataFinal);
}

