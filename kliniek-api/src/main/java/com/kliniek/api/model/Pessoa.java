package com.kliniek.api.model;

import java.util.Date;

public class Pessoa {
    private long pessoaid;
    private long ususarioid;
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

    public Pessoa() {
    }

    public long getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(long pessoaid) {
        this.pessoaid = pessoaid;
    }

    public long getUsusarioid() {
        return ususarioid;
    }

    public void setUsusarioid(long ususarioid) {
        this.ususarioid = ususarioid;
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
