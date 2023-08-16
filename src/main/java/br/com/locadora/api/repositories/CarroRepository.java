package br.com.locadora.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.locadora.api.domain.carro.*;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
}
