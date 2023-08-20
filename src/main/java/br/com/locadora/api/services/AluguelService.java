package br.com.locadora.api.services;

import br.com.locadora.api.domain.aluguel.Aluguel;
import br.com.locadora.api.domain.aluguel.AluguelApoliceRequestDTO;
import br.com.locadora.api.domain.aluguel.ListarCarrinhoDTO;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.pessoa.Pessoa;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.mappers.AluguelMapper;
import br.com.locadora.api.domain.aluguel.CartaoCreditoDTO;
import br.com.locadora.api.repositories.AluguelRepository;
import br.com.locadora.api.repositories.CarroRepository;
import br.com.locadora.api.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AluguelService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private AluguelMapper aluguelMapper;

    public ListarCarrinhoDTO addAluguel(Long idCarro, AluguelApoliceRequestDTO dto, Usuario user) {
        validarParametrosAddAluguel(dto, user);

        Carro carro = buscarCarroPorId(idCarro);
        verificarDisponibilidade(idCarro, dto.dataEntrega(), dto.dataDevolucao());

        Optional<Pessoa> pessoa = pessoaRepository.findPessoaById(user.getPessoa().getId());
        Pessoa pessoaAssociada = pessoa.orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada!"));

        Aluguel novoAluguel = criarNovoAluguel(dto, carro, pessoaAssociada);
        aluguelRepository.save(novoAluguel);

        return aluguelMapper.toListarCarrinhoDTO(novoAluguel);
    }

    public List<ListarCarrinhoDTO> listarAlugueisPagos(Usuario user) {
        List<Aluguel> alugueisPagosDoUsuario = aluguelRepository.findAlugueisByPessoaIdAndStatus(user.getPessoa().getId(), true);

        return alugueisPagosDoUsuario.stream()
                .map(aluguelMapper::toListarCarrinhoDTO)
                .collect(Collectors.toList());
    }

    public List<ListarCarrinhoDTO> listar(Usuario user) {
        List<Aluguel> alugueisDoUsuario = aluguelRepository.findAlugueisByPessoaIdAndStatus(user.getPessoa().getId(), false);

        return alugueisDoUsuario.stream()
                .map(aluguelMapper::toListarCarrinhoDTO)
                .collect(Collectors.toList());
    }

    public ListarCarrinhoDTO atualizarCarrinho(Usuario user, Long id, AluguelApoliceRequestDTO aluguelAtualizacaoDTO) {
        Aluguel aluguelExistente = buscarAluguelPorIdEUsuario(id, user);
        aluguelExistente.atualizarInformacoes(aluguelAtualizacaoDTO);
        aluguelRepository.save(aluguelExistente);
        return aluguelMapper.toListarCarrinhoDTO(aluguelExistente);
    }

    public void apagarItem(Long id, Usuario user) {
        Aluguel aluguelExistente = buscarAluguelPorIdEUsuario(id, user);
        aluguelRepository.delete(aluguelExistente);
    }

    public void pagarAluguel(Long idAluguel, Usuario user, CartaoCreditoDTO cartaoCreditoDTO) {
        validarParametrosPagamento(idAluguel, user, cartaoCreditoDTO);

        Aluguel aluguelExistente = buscarAluguelPorIdEUsuario(idAluguel, user);
        BigDecimal valorAluguel = aluguelExistente.getValorTotal();

        processarPagamento(cartaoCreditoDTO, valorAluguel, aluguelExistente);
    }

    // Métodos auxiliares

    private Aluguel criarNovoAluguel(AluguelApoliceRequestDTO dto, Carro carro, Pessoa pessoaAssociada) {
        Aluguel novoAluguel = new Aluguel(dto, carro);
        novoAluguel.setPessoa(pessoaAssociada);
        novoAluguel.setStatus(false);
        novoAluguel.calcularValorTotal();
        return novoAluguel;
    }

    private void validarParametrosAddAluguel(AluguelApoliceRequestDTO dto, Usuario user) {
        if (dto == null || user == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para adicionar aluguel.");
        }
    }
    private Carro buscarCarroPorId(Long idCarro) {
        return carroRepository.findCarroById(idCarro);
    }

    private void verificarDisponibilidade(Long idCarro, Date dataInicial, Date dataFinal) {
        if (!verificaDisponibilidadeCarro(idCarro)) {
            throw new IllegalArgumentException("Carro informado não existe.");
        }

        List<Aluguel> alugueis = aluguelRepository.findAlugueisByCarroAndRentalPeriodOverlapping(idCarro, dataInicial, dataFinal);

        if (!alugueis.isEmpty()) {
            throw new IllegalArgumentException("Carro não disponível para o período selecionado.");
        }
    }

    private boolean verificaDisponibilidadeCarro(Long idCarro) {
        return carroRepository.existsById(idCarro);
    }

    private void validarParametrosPagamento(Long idAluguel, Usuario user, CartaoCreditoDTO cartaoCreditoDTO) {
        if (idAluguel == null || user == null || cartaoCreditoDTO == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para pagamento.");
        }
    }

    private Aluguel buscarAluguelPorIdEUsuario(Long idAluguel, Usuario user) {
        Aluguel aluguel = aluguelRepository.findAluguelByIdAndStatus(idAluguel, false);
        if (aluguel == null) {
            throw new EntityNotFoundException("Aluguel não encontrado.");
        }
        if (!aluguel.getPessoa().getId().equals(user.getPessoa().getId())) {
            throw new IllegalArgumentException("O aluguel não pertence ao usuário.");
        }
        return aluguel;
    }

    private void processarPagamento(CartaoCreditoDTO cartaoDTO, BigDecimal valor, Aluguel aluguel) {
            if (processarPagamentoSimulado(cartaoDTO, valor)) {
                aluguel.setStatus(true);
                aluguelRepository.save(aluguel);
                } else {throw new IllegalArgumentException("Pagamento falhou. Ocorreu um erro durante o processamento.");
            }
    }

    private boolean processarPagamentoSimulado(CartaoCreditoDTO cartaoDTO, BigDecimal valor) {
        double chancePagamentoSucesso = 0.8; // 80% de chance de sucesso
        double tempoProcessamento = 2.0; // 2 segundos de tempo de processamento simulado

        try {
            Thread.sleep((long) (tempoProcessamento * 1000)); // Simulando o tempo de processamento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Math.random() < chancePagamentoSucesso;
    }
}
