package com.kliniek.api.model;

public class Paciente  extends Pessoa{
    private long pacienteid;
    private String profissao;
    private String estadoActual;

    public Paciente() {
    }

    public long getPacienteid() {
         return pacienteid;
    }

    public void setPacienteid(long pacienteid) {
        this.pacienteid = pacienteid;
    }

     public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
}
