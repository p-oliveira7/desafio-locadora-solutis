package br.com.locadora.api.repositories;

import br.com.locadora.api.domain.pessoa.Funcionario;
import br.com.locadora.api.domain.pessoa.Motorista;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Funcionario> findFuncionarioByCpf(String cpf);
    Optional<Motorista> findMotoristaByCpf(String cpf);

    Optional<Pessoa> findPessoaById(Long id);

    Optional<Pessoa> findByCpf(String cpf);

    Page<Pessoa> findByUsuario(Usuario user, Pageable pageable);
}

