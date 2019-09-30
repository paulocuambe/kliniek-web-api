package com.kliniek.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Usuario {
    private long usuarioid;
    private String username;
    private String senha;
    private String estado;
    private Date dataCriacao;

    public Usuario() {
    }

    public Usuario(long usuarioid, String username, String senha, String estado, Date dataCriacao) {
        this.usuarioid = usuarioid;
        this.username = username;
        this.senha = senha;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
    }

    public long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
