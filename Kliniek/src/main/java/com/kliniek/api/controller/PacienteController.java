package com.kliniek.api.controller;

import com.kliniek.api.model.Paciente;
import com.kliniek.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping("/pacientes")
    public ResponseEntity<?> create(@RequestBody Paciente paciente){
        return new ResponseEntity<>(pacienteRepository.create(paciente), HttpStatus.CREATED);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(pacienteRepository.findAll(), HttpStatus.OK);
    }
}
