package com.kliniek.api.model;

public class Telefone {
    private long pessoaid;
    private String numeroTelefone;

    public long getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(long pessoaid) {
        this.pessoaid = pessoaid;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }
}
