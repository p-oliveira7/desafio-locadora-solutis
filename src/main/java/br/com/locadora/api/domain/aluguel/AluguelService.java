package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguro;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.mappers.AluguelMapper;
import br.com.locadora.api.repositories.AluguelRepository;
import br.com.locadora.api.repositories.CarroRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AluguelService {
    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private AluguelMapper aluguelMapper;

    public ListarCarrinhoDTO adicionarAluguelAoCarrinho(AluguelApoliceRequestDTO dto, Usuario user, HttpSession session) {
        Carro carro = carroRepository.findCarroById(dto.idCarro());
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new CarrinhoAlugueis(user);
            session.setAttribute("carrinho", carrinho);
        }

        // Verifica se o carro já está no carrinho com as mesmas datas
        if (carrinho.contemAluguelComPlacaEData(carro.getId(), dto.dataEntrega(), dto.dataDevolucao())) {
            throw new RuntimeException("Este carro já foi adicionado ao carrinho com as mesmas datas de aluguel.");
        }
        // Id temporario do Aluguel no carrinho
        String temporaryId = UUID.randomUUID().toString();

        Aluguel novoAluguel = new Aluguel(dto, carro);
        novoAluguel.setTemporaryId(temporaryId);
        carrinho.adicionarAluguel(novoAluguel);

        // Certifique-se de retornar o ID temporario do aluguel
        return aluguelMapper.toListarCarrinhoDTO(novoAluguel);
    }
    public List<ListarCarrinhoDTO> getAlugueisDoUsuario(HttpSession session) {

        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");

        List<Aluguel> alugueisDoUsuario = carrinho.getAlugueis();

        return alugueisDoUsuario.stream()
                .map(aluguelMapper::toListarCarrinhoDTO)
                .collect(Collectors.toList());
    }
    public ListarCarrinhoDTO modificarAluguelNoCarrinho(String temporaryId, AluguelApoliceRequestDTO novoDto, HttpSession session) {
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");

        Aluguel aluguel = findAluguelByTemporaryId(temporaryId, carrinho);

        aluguel.setDataEntrega(novoDto.dataEntrega());
        aluguel.setDataDevolucao(novoDto.dataDevolucao());
        aluguel.setApoliceSeguro(new ApoliceSeguro(novoDto.apolice()));

        return aluguelMapper.toListarCarrinhoDTO(aluguel);
    }
    public void removerAluguel(String temporaryId, HttpSession session) {
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");

        Aluguel aluguel = findAluguelByTemporaryId(temporaryId, carrinho);

        carrinho.removerAluguel(aluguel);
    }
    public void finalizarCompra(HttpSession session) {
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");
        List<Aluguel> alugueisNoCarrinho = carrinho.getAlugueis();

        for (Aluguel aluguel : alugueisNoCarrinho) {
            Long idCarro = aluguel.getCarro().getId();
            Date dataInicial = aluguel.getDataEntrega();
            Date dataFinal = aluguel.getDataDevolucao();

            if (!verificaDisponibilidade(idCarro, dataInicial, dataFinal)) {
                throw new RuntimeException("O carro não está disponível para aluguel entre as datas especificadas.");
            }

            aluguelRepository.save(aluguel);
        }
    }
    // Métodos utilitarios
    private Boolean verificaDisponibilidade(Long idCarro, Date dataInicial, Date dataFinal) {
        if (!carroRepository.existsById(idCarro)) {
            throw new ValidationException("Carro informado não existe");
        }
        List<Aluguel> alugueis = aluguelRepository.findAlugueisByCarroAndRentalPeriodOverlapping(idCarro, dataInicial, dataFinal);
        return alugueis.isEmpty();
    }
    private Aluguel findAluguelByTemporaryId(String temporaryId, CarrinhoAlugueis carrinho) {
        // Nota: Código que busca o iten selecionado no carrinho.
        return carrinho.getAlugueis().stream()
                .filter(a -> a.getTemporaryId().equals(temporaryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado no carrinho"));
    }
}