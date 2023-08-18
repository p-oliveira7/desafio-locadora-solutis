package br.com.locadora.api.domain.aluguel;

import br.com.locadora.api.domain.apolice.ApoliceSeguro;
import br.com.locadora.api.domain.carro.Carro;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.repositories.AluguelRepository;
import br.com.locadora.api.repositories.CarroRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AluguelService {
    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private CarrinhoAlugueis carrinhoAlugueis;
    @Autowired
    private HttpSession httpSession;

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

        // Certifique-se de retornar o ID do aluguel
        return new ListarCarrinhoDTO(novoAluguel.getTemporaryId(), carro.getPlaca(), dto.dataPedido(), dto.dataEntrega(), dto.dataDevolucao(),
                dto.apolice().valorFranquia(), dto.apolice().protecaoTerceiro(), dto.apolice().protecaoCausaNatural(), dto.apolice().protecaoRoubo());
    }
    public ListarCarrinhoDTO modificarAluguelNoCarrinho(String temporaryId, AluguelApoliceRequestDTO novoDto) {
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) httpSession.getAttribute("carrinho");

        Aluguel aluguel = carrinho.getAlugueis().stream()
                .filter(a -> a.getTemporaryId().equals(temporaryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado no carrinho"));

        aluguel.setDataEntrega(novoDto.dataEntrega());
        aluguel.setDataDevolucao(novoDto.dataDevolucao());
        aluguel.setApoliceSeguro(new ApoliceSeguro(novoDto.apolice()));

        return new ListarCarrinhoDTO(aluguel.getTemporaryId(),aluguel.getCarro().getPlaca(), aluguel.getDataPedido(),
                aluguel.getDataEntrega(), aluguel.getDataDevolucao(), aluguel.getApoliceSeguro().getValorFranquia(),
                aluguel.getApoliceSeguro().getProtecaoTerceiro(), aluguel.getApoliceSeguro().getProtecaoCausaNatural(),
                aluguel.getApoliceSeguro().getProtecaoRoubo());
    }
    public List<ListarCarrinhoDTO> getAlugueisDoUsuario(HttpSession session) {

        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");
        if (carrinho == null) {
            return new ArrayList<>();
        }

        List<Aluguel> alugueisDoUsuario = carrinho.getAlugueis();

        List<ListarCarrinhoDTO> alugueisResponse = new ArrayList<>();
        for (Aluguel aluguel : alugueisDoUsuario) {
            alugueisResponse.add(new ListarCarrinhoDTO(aluguel.getTemporaryId(), aluguel.getCarro().getPlaca(), aluguel.getDataPedido(), aluguel.getDataEntrega(),
                    aluguel.getDataDevolucao(), aluguel.getApoliceSeguro().getValorFranquia(), aluguel.getApoliceSeguro().getProtecaoTerceiro(),
                    aluguel.getApoliceSeguro().getProtecaoCausaNatural(), aluguel.getApoliceSeguro().getProtecaoRoubo()));
        }

        return alugueisResponse;
    }

    public AluguelResponseDTO alugar(AluguelApoliceRequestDTO dto) {
        var idCarro = dto.idCarro();
        var dataInicial = dto.dataEntrega();
        var dataFinal = dto.dataDevolucao();

        if (!verificaDisponibilidade(idCarro, dataInicial, dataFinal)) {
            throw new RuntimeException("O carro não está disponível para aluguel entre as datas especificadas.");
        }
        Carro carro = carroRepository.findCarroById(dto.idCarro());
        String placa = carro.getPlaca();

        Aluguel aluguel = new Aluguel(dto, carro);

        aluguelRepository.save(aluguel);

        return new AluguelResponseDTO(placa, dto.dataPedido(), dto.dataEntrega(), dto.dataDevolucao(),
                dto.apolice().valorFranquia(), dto.apolice().protecaoTerceiro(), dto.apolice().protecaoCausaNatural(), dto.apolice().protecaoRoubo());
    }

    private Boolean verificaDisponibilidade(Long idCarro, Date dataInicial, Date dataFinal) {
        if (!carroRepository.existsById(idCarro)) {
            throw new ValidationException("Carro informado não existe");
        }
        List<Aluguel> alugueis = aluguelRepository.findAlugueisByCarroAndRentalPeriodOverlapping(idCarro, dataInicial, dataFinal);
        return alugueis.isEmpty();
    }

    public void finalizarCompra() {
        List<Aluguel> alugueisNoCarrinho = carrinhoAlugueis.getAlugueis();

        for (Aluguel aluguel : alugueisNoCarrinho) {
            if (verificaDisponibilidade(aluguel.getCarro().getId(), aluguel.getDataEntrega(), aluguel.getDataDevolucao())) {
                aluguelRepository.save(aluguel);
            } else {
                throw new RuntimeException("O carro não está disponível para aluguel entre as datas especificadas.");
            }
        }
    }
    public void removerAluguel(String temporaryId, HttpSession session) {
        CarrinhoAlugueis carrinho = (CarrinhoAlugueis) session.getAttribute("carrinho");

        Aluguel aluguel = carrinho.getAlugueis().stream()
                .filter(a -> a.getTemporaryId().equals(temporaryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado no carrinho"));

        carrinho.removerAluguel(aluguel);
    }
}