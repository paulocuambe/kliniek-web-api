package com.kliniek.api.controller;

import com.kliniek.api.model.Paciente;
import com.kliniek.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping("/pacientes")
    public ResponseEntity<?> create(@RequestBody Paciente paciente){
        return new ResponseEntity<>(pacienteRepository.create(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Paciente paciente){
        return new ResponseEntity<>(pacienteRepository.update(id, paciente), HttpStatus.OK);
    }

    @PatchMapping("/pacientes/{id}/estado={estado}")
    public ResponseEntity<?> updateEstado(@PathVariable long id, @PathVariable String estado){
        return new ResponseEntity<>(pacienteRepository.updateEstado(id, estado), HttpStatus.OK);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(pacienteRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<?> findPacienteById(@PathVariable long id){
        return new ResponseEntity<>(pacienteRepository.findPacienteById(id), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/estado={estado}")
    public  ResponseEntity<?> findPacienteByID(@PathVariable String estado){
        return new ResponseEntity<>(pacienteRepository.findPacienteByEstado(estado), HttpStatus.FOUND);
    }

}
