package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.Endereco;
import br.com.alura.domain.http.AgenciaHttp;
import br.com.alura.domain.http.SituacaoCadastral;
import br.com.alura.exception.AgenciaNaoEncontradaOuInativaException;
import br.com.alura.repository.AgenciaRepository;
import br.com.alura.service.http.SituacaoCadastralHttpService;
import br.com.alura.utils.AgenciaFixture;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class AgenciaServiceTest {

    @InjectMock
    private AgenciaRepository agenciaRepository;

    @InjectMock
    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    @Inject
    private AgenciaService agenciaService;

    @BeforeEach
    public void setUp(){
        Mockito.doNothing().when(agenciaRepository).persist(Mockito.any(Agencia.class));
    }

    @Test
    public void deveNaoCadastrarQuandoClientRetornarNull(){
        Agencia agencia = AgenciaFixture.criarAgencia();

        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(null);
        Assertions.assertThrows(AgenciaNaoEncontradaOuInativaException.class, () -> agenciaService.cadastrar(agencia));
        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    public void deveNaoCadastrarQuandoClientRetornarAgenciaInativa(){
        Agencia agencia = AgenciaFixture.criarAgencia();

        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(AgenciaFixture.criarAgenciaHttp(SituacaoCadastral.INATIVO));
        Assertions.assertThrows(AgenciaNaoEncontradaOuInativaException.class, () -> agenciaService.cadastrar(agencia));
        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    public void deveCadastrarQuandoClientRetornarSituacaoCadastralAtiva(){
        Agencia agencia = AgenciaFixture.criarAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(AgenciaFixture.criarAgenciaHttp(SituacaoCadastral.ATIVO));//thenReturn não performa a operação de fato. traz uma simulação
        agenciaService.cadastrar(agencia);
        Mockito.verify(agenciaRepository).persist(agencia);
    }
}
