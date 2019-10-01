package com.kliniek.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class Pessoa {
    @JsonIgnore
    private long pessoaoid;
    private long usuarioid;
    private String bi;
    private String nuit;
    private String primeiroNome;
    private String apelido;
    private String email;
    private Date dataNascimento;
    private String sexo;
    private String endereco;
    private String contactoPrimario;
    private Date dataRegisto;
    private List<Telefone> telefones;

    public Pessoa() {
    }

    public Pessoa(long pessoaoid, long usuarioid, String bi, String nuit, String primeiroNome, String apelido, String email, Date dataNascimento, String sexo, String endereco,
                  String contactoPrimario, Date dataRegisto) {
        this.pessoaoid = pessoaoid;
        this.usuarioid = usuarioid;
        this.bi = bi;
        this.nuit = nuit;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.endereco = endereco;
        this.contactoPrimario = contactoPrimario;
        this.dataRegisto = dataRegisto;
    }

    public Pessoa(long pessoaoid, long usuarioid, String bi, String nuit, String primeiroNome, String apelido, String email, Date dataNascimento,
                  String sexo, String endereco, String contactoPrimario, Date dataRegisto, List<Telefone> telefones) {
        this.pessoaoid = pessoaoid;
        this.usuarioid = usuarioid;
        this.bi = bi;
        this.nuit = nuit;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.endereco = endereco;
        this.contactoPrimario = contactoPrimario;
        this.dataRegisto = dataRegisto;
        this.telefones = telefones;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public long getPessoaoid() {
        return pessoaoid;
    }

    public void setPessoaoid(long pessoaoid) {
        this.pessoaoid = pessoaoid;
    }

    public long getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(long usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContactoPrimario() {
        return contactoPrimario;
    }

    public void setContactoPrimario(String contactoPrimario) {
        this.contactoPrimario = contactoPrimario;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}
