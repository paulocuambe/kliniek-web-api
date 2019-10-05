package com.kliniek.api.model;

import org.springframework.stereotype.Service;

@Service
public class DiaSemana {
    private long diaid;
    private String designacao;

    public DiaSemana() {
    }

    public DiaSemana(long diaid, String designacao) {
        this.diaid = diaid;
        this.designacao = designacao;
    }

    public long getDiaid() {
        return diaid;
    }

    public void setDiaid(long diaid) {
        this.diaid = diaid;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }
}
