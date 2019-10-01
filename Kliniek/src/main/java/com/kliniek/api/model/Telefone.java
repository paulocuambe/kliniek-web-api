package com.kliniek.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Telefone {
    @JsonIgnore
    private long pessoaid;
    private String numero;

    public Telefone() {
    }

    public Telefone(long pessoaid, String numero) {
        this.pessoaid = pessoaid;
        this.numero = numero;
    }

    public long getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(long pessoaid) {
        this.pessoaid = pessoaid;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
