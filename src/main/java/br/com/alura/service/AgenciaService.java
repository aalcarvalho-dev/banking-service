package br.com.alura.service;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.http.AgenciaHttp;
import br.com.alura.domain.http.SituacaoCadastral;
import br.com.alura.exception.AgenciaNaoEncontradaOuInativaException;
import br.com.alura.repository.AgenciaRepository;
import br.com.alura.service.http.SituacaoCadastralHttpService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AgenciaService {

    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    private final AgenciaRepository agenciaRepository;

    AgenciaService(AgenciaRepository agenciaRepository){
        this.agenciaRepository = agenciaRepository;
    }

    public void cadastrar(Agencia agencia){
        AgenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());
        if(agenciaHttp==null){
            Log.info("A agência " + agencia.getNome() +" não foi encontrada!");
            throw new AgenciaNaoEncontradaOuInativaException("Agencia não encontrada!");
        }
        if(agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.INATIVO)){
            Log.info("A agência " + agencia.getNome() +" não foi cadastrada!");
            throw new AgenciaNaoEncontradaOuInativaException("Agência com Status de Inativa");
        }
        if(agenciaHttp.getSituacaoCadastral().equals(SituacaoCadastral.ATIVO)){
            agenciaRepository.persist(agencia);
            Log.info("A agência " + agencia.getNome() +" foi cadastrada!");
        }

    }

    public Agencia buscarPorId(Long id){
        Log.info("Realizando busca de agência pelo id :"+id);
        return agenciaRepository.findById(id);
    }

    public void deletar(Long id){
        agenciaRepository.deleteById(id);
        Log.info("A agência com o id" + id +" foi removida!");
    }

    public void alterar(Agencia agencia) {
        agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4", agencia.getNome(), agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId());
        Log.info("A agência " + agencia.getNome() +" foi alterada!");
    }
}
