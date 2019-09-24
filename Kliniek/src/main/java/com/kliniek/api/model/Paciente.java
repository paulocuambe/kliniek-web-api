package com.kliniek.api.model;


import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Paciente extends Pessoa {
    private long pacienteid;
    private String profissao;
    private String estadoActual;

    public Paciente() {
    }

    public Paciente(long pessoaoid, long usuarioid, String bi, String nuit, String primeiroNome, String apelido, String email,
                    Date dataNascimento, String sexo, String endereco, String contactoPrimario, Date dataRegisto, String profissao, String estadoActual) {
        super(pessoaoid, usuarioid, bi, nuit, primeiroNome, apelido, email, dataNascimento, sexo, endereco, contactoPrimario, dataRegisto);
        this.pacienteid = pessoaoid;
        this.profissao = profissao;
        this.estadoActual = estadoActual;
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
