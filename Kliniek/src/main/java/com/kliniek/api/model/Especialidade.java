package com.kliniek.api.model;

import org.springframework.stereotype.Service;

@Service
public class Especialidade {
    private long especialidadeid;
    private String nome;
    private String descricao;

    public Especialidade() {
    }

    public Especialidade(long especialidadeid, String nome, String descricao) {
        this.especialidadeid = especialidadeid;
        this.nome = nome;
        this.descricao = descricao;
    }

    public long getEspecialidadeid() {
        return especialidadeid;
    }

    public void setEspecialidadeid(long especialidadeid) {
        this.especialidadeid = especialidadeid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
