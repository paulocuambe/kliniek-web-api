package com.kliniek.api.controller;

import com.kliniek.api.model.Exame;
import com.kliniek.api.repository.ExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExameController {

    @Autowired
    ExameRepository exameRepository;

    @GetMapping("/exames")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(exameRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/exames/{id}")
    public ResponseEntity<?> findExameById(@PathVariable long id){
        return new ResponseEntity<>(exameRepository.findExameById(id), HttpStatus.FOUND);
    }

    @GetMapping("/paciente/{id}/exames")
    public ResponseEntity<?> findExamesByPacienteId(@PathVariable long id){
        return new ResponseEntity<>(exameRepository.findExamesByPacienteId(id), HttpStatus.OK);
    }

    @GetMapping("/medicos/{id}/exames")
    public ResponseEntity<?> findExamesByMedicoId(@PathVariable long id){
        return new ResponseEntity<>(exameRepository.findExamesByMedicoId(id), HttpStatus.FOUND);
    }

    @PostMapping("/exames/")
    public ResponseEntity<?> marcarExame(Exame exame){
        return new ResponseEntity<>(exameRepository.marcarExame(exame), HttpStatus.OK);
    }

    @PutMapping("/exames/{id}")
    public ResponseEntity<?> resultadoExame(@PathVariable long id, @RequestBody Exame exame){
        return new ResponseEntity<>(exameRepository.resultadoExame(id, exame), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> cancelarExame(@PathVariable long id){
        return new ResponseEntity<>(exameRepository.cancelarExame(id), HttpStatus.OK);
    }
}
