package br.com.alura.domain;

import jakarta.persistence.*;

@Entity
public class Agencia {

    public Agencia(Integer id, String nome, String razaoSocial, String cnpj, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public Agencia(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(name = "razao_social")
    private String razaoSocial;
    private String cnpj;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Endereco getEndereco() {
        return endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

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