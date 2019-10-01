package com.kliniek.api.model;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Medico extends Pessoa{
    private long medicoid;
    private Especialidade especialidade;
    private String carteiraProfissional;

    public Medico() {
    }

    public Medico(long pessoaoid, long usuarioid, String bi, String nuit, String primeiroNome, String apelido, String email, Date dataNascimento,
                  String sexo, String endereco, String contactoPrimario, Date dataRegisto, String carteiraProfissional) {
        super(pessoaoid, usuarioid, bi, nuit, primeiroNome, apelido, email, dataNascimento, sexo, endereco, contactoPrimario, dataRegisto);
        this.medicoid = pessoaoid;
        this.carteiraProfissional = carteiraProfissional;
    }

    public Medico(long pessoaoid, long usuarioid, String bi, String nuit, String primeiroNome, String apelido, String email, Date dataNascimento,
                  String sexo, String endereco, String contactoPrimario, Date dataRegisto, Especialidade especialidade, String carteiraProfissional) {
        super(pessoaoid, usuarioid, bi, nuit, primeiroNome, apelido, email, dataNascimento, sexo, endereco, contactoPrimario, dataRegisto);
        this.medicoid = pessoaoid;
        this.especialidade = especialidade;
        this.carteiraProfissional = carteiraProfissional;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public long getMedicoid() {
        return medicoid;
    }

    public void setMedicoid(long medicoid) {
        this.medicoid = medicoid;
    }

    public String getCarteiraProfissional() {
        return carteiraProfissional;
    }

    public void setCarteiraProfissional(String carteiraProfissional) {
        this.carteiraProfissional = carteiraProfissional;
    }
}
