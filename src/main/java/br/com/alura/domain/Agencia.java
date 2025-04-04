package br.com.alura.domain;

import jakarta.persistence.*;

@Entity
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    public Endereco getEndereco() {
        return endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Column(name = "razao_social")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNome() {
        return nome;
    }

    public Integer getId() {
        return id;
    }
}