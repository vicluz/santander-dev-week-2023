package me.dio.domain.model;

import java.util.List;

import jakarta.persistence.*;

@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    private Account conta;

    @OneToOne(cascade = CascadeType.ALL)
    private Card cartao;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "card_number")
    private String cardNumber;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Feature> features;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<News> news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Account getConta() {
        return conta;
    }

    public void setConta(Account conta) {
        this.conta = conta;
    }

    public Card getCartao() {
        return cartao;
    }

    public void setCartao(Card cartao) {
        this.cartao = cartao;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}