package br.com.locadora.api.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.locadora.api.domain.pessoa.PessoaDTO;
import br.com.locadora.api.domain.pessoa.PessoaResponseDTO;
import br.com.locadora.api.domain.usuario.Usuario;
public interface PessoaServiceInterface {
    Page<PessoaResponseDTO> listarPessoas(Usuario user, Pageable pageable);

    void cadastrarPessoa(PessoaDTO pessoaDTO, Usuario user);

    void deletarPessoaPorCPF(String cpf);

    void atualizarPessoa(String cpf, Usuario user, PessoaDTO pessoaDTO);
}
