package com.kliniek.api.model;

import org.springframework.stereotype.Service;

@Service
public class Periodo {
    private long periodoid;
    private String designacao;
    private String horaInicio;
    private String horaFim;

    public Periodo() {
    }

    public Periodo(long periodoid, String designacao, String horaInicio, String horaFim) {
        this.periodoid = periodoid;
        this.designacao = designacao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public long getPeriodoid() {
        return periodoid;
    }

    public void setPeriodoid(long periodoid) {
        this.periodoid = periodoid;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }
}
