package com.kliniek.api.model;

import org.springframework.stereotype.Service;

@Service
public class Disponibilidade {
    private long medicoid;
    private DiaSemana diaSemana;
    private Periodo periodo;

    public Disponibilidade() {
    }

    public Disponibilidade(DiaSemana diaSemana, Periodo periodo, long medicoid) {
        this.diaSemana = diaSemana;
        this.periodo = periodo;
        this.medicoid = medicoid;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public long getMedicoid() {
        return medicoid;
    }

    public void setMedicoid(long medicoid) {
        this.medicoid = medicoid;
    }
}
