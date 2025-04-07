package br.com.alura.utils;

import br.com.alura.domain.Agencia;
import br.com.alura.domain.Endereco;
import br.com.alura.domain.http.AgenciaHttp;
import br.com.alura.domain.http.SituacaoCadastral;

public class AgenciaFixture {

    public static AgenciaHttp criarAgenciaHttp(SituacaoCadastral status){
        return new AgenciaHttp("Agencia Teste","razao Teste","123", status);
    }

    public static Agencia criarAgencia(){
        Endereco endereco = new Endereco(1,"ABC","Teste","TESTE",1);
        return new Agencia(1,"Agencia Teste","razao Teste","123",endereco);
    }
}
