package com.kliniek.api.endpoint;

import com.kliniek.api.model.Paciente;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PacienteEndpoint {

    @RequestMapping("/pacientes")
    public Paciente getAllPaciente(){
        Paciente p = new Paciente();
        p.setPacienteid(1);
        p.setProfissao("Estudante");
        p.setEstadoActual("Grave");
        p.setPrimeiroNome("Paulo Amosse");
        p.setApelido("Cuambe");
        p.setDataNascimento(new Date());
        p.setEndereco("Bairro Kumbeza");

        return p;
    }
}
