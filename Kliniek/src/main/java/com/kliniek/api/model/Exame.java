package com.kliniek.api.model;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class Exame {
    private long exameid;
    private long tipoexameid;
    private long pacienteid;
    private long recepcionistaid;
    private Date data;
    private String hora;
    private String observacao;
    private boolean positivo;
    private boolean urgente;

    public Exame() {
    }

    public Exame(long exameid, long tipoexameid, long pacienteid, long recepcionistaid, Date data, String hora,
                 String observacao, boolean positivo, boolean urgente) {
        this.exameid = exameid;
        this.tipoexameid = tipoexameid;
        this.pacienteid = pacienteid;
        this.recepcionistaid = recepcionistaid;
        this.data = data;
        this.hora = hora;
        this.observacao = observacao;
        this.positivo = positivo;
        this.urgente = urgente;
    }

    public long getRecepcionistaid() {
        return recepcionistaid;
    }

    public void setRecepcionistaid(long recepcionistaid) {
        this.recepcionistaid = recepcionistaid;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    public long getExameid() {
        return exameid;
    }

    public void setExameid(long exameid) {
        this.exameid = exameid;
    }

    public long getTipoexameid() {
        return tipoexameid;
    }

    public void setTipoexameid(long tipoexameid) {
        this.tipoexameid = tipoexameid;
    }

    public long getPacienteid() {
        return pacienteid;
    }

    public void setPacienteid(long pacienteid) {
        this.pacienteid = pacienteid;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isPositivo() {
        return positivo;
    }

    public void setPositivo(boolean positivo) {
        this.positivo = positivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
