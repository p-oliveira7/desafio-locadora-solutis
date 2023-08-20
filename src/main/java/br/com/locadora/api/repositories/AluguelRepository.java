package br.com.locadora.api.repositories;

import br.com.locadora.api.domain.aluguel.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    @Query("SELECT a FROM aluguel a WHERE a.carro.id = :idCarro AND a.dataEntrega < :dataFinal AND a.dataDevolucao > :dataInicial")
    List<Aluguel> findAlugueisByCarroAndRentalPeriodOverlapping(Long idCarro, Date dataInicial, Date dataFinal);

    List<Aluguel> findAlugueisByPessoaIdAndStatus(Long id, boolean b);

    Aluguel findAluguelByIdAndStatus(Long id, boolean b);
}

