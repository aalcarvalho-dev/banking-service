package br.com.alura.domain;

import jakarta.persistence.*;

@Entity
public class Agencia {
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