package com.kliniek.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Telefone {
    @JsonIgnore
    private long pessoaid;
    private String numero;
    private String tipo;

    public Telefone() {
    }

    public Telefone(long pessoaid, String numero, String tipo) {
        this.pessoaid = pessoaid;
        this.numero = numero;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
