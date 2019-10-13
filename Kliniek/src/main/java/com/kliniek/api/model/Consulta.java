package com.kliniek.api.model;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class Consulta {
    private long consultaid;
    private long tipoconsultaid;
    private long medicoid;
    private long pacienteid;
    private long recepcionistaid;
    private Date data;
    private String hora;
    private String descricao;
    private String prescricao;
    private String observacao;
    private boolean urgente;

    public Consulta() {
    }

    public Consulta(long consultaid, long tipoconsultaid, long medicoid, long pacienteid, long recepcionistaid, Date data, String hora, String descricao, String prescricao,
                     String observacao, boolean urgente) {
        this.consultaid = consultaid;
        this.tipoconsultaid = tipoconsultaid;
        this.medicoid = medicoid;
        this.pacienteid = pacienteid;
        this.recepcionistaid = recepcionistaid;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.prescricao = prescricao;
        this.observacao = observacao;
        this.urgente = urgente;
    }

    public long getConsultaid() {
        return consultaid;
    }

    public void setConsultaid(long consultaid) {
        this.consultaid = consultaid;
    }

    public long getTipoconsultaid() {
        return tipoconsultaid;
    }

    public void setTipoconsultaid(long tipoconsultaid) {
        this.tipoconsultaid = tipoconsultaid;
    }

    public long getMedicoid() {
        return medicoid;
    }

    public void setMedicoid(long medicoid) {
        this.medicoid = medicoid;
    }

    public long getPacienteid() {
        return pacienteid;
    }

    public void setPacienteid(long pacienteid) {
        this.pacienteid = pacienteid;
    }

    public long getRecepcionistaid() {
        return recepcionistaid;
    }

    public void setRecepcionistaid(long recepcionistaid) {
        this.recepcionistaid = recepcionistaid;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }
}
