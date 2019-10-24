package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Exame;
import com.kliniek.api.repository.ExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class ExameController {

    @Autowired
    ExameRepository exameRepository;

    @GetMapping("/exames")
    public ResponseEntity<?> findAll(){
        if(exameRepository.findAll() == null)
            throw new ResourceNotFound("Nenhum exame encontrado");
        return new ResponseEntity<>(exameRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/exames/{id}")
    public ResponseEntity<?> findExameById(@PathVariable long id){
        if(exameRepository.findExameById(id) == null)
            throw new ResourceNotFound("Nenhum exame encontrado");
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
    public ResponseEntity<?> marcarExame(@RequestBody Exame exame){
        if(exameRepository.marcarExame(exame) == 0)
            throw new InternalServerError("Nao foi possivel marcar o exame. Algum erro interno.");
        return new ResponseEntity<>(exameRepository.marcarExame(exame), HttpStatus.CREATED);
    }

    @PutMapping("/exames/{id}")
    public ResponseEntity<?> resultadoExame(@PathVariable long id, @RequestBody Exame exame){
        return new ResponseEntity<>(exameRepository.resultadoExame(id, exame), HttpStatus.OK);
    }

    @PutMapping("/exames/pagar/{id}")
    public ResponseEntity<?> pagarExame(@PathVariable long id){
        if(exameRepository.pagarExame(id) == 0)
            throw new InternalServerError("Algum erro ocorreu a pagar o exame");
        return new ResponseEntity<>(exameRepository.pagarExame(id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> cancelarExame(@PathVariable long id){
        return new ResponseEntity<>(exameRepository.cancelarExame(id), HttpStatus.OK);
    }
}
