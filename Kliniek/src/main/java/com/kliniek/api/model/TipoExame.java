package com.kliniek.api.model;

import org.springframework.stereotype.Service;

@Service
public class TipoExame {
    private long tipoid;
    private String designacao;
    private String descricao;
    private double preco;
    private boolean disponivel;

    public TipoExame() {
    }

    public TipoExame(long tipoid, String designacao, String descricao, double preco, boolean disponivel) {
        this.tipoid = tipoid;
        this.designacao = designacao;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    public long getTipoid() {
        return tipoid;
    }

    public void setTipoid(long tipoid) {
        this.tipoid = tipoid;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
