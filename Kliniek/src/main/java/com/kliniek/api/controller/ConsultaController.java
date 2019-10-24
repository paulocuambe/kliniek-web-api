package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Consulta;
import com.kliniek.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class ConsultaController {

    @Autowired
    ConsultaRepository consultaRepository;

    @GetMapping("/consultas")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(consultaRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<?> findConsultaById(@PathVariable long id){
        if(consultaRepository.findConsultaById(id) == null)
            throw new ResourceNotFound("Nenhuma consulta com id " + id + " foi encontrado.");
        return new ResponseEntity<>(consultaRepository.findConsultaById(id), HttpStatus.FOUND);
    }

    @PostMapping("/consultas/")
    public ResponseEntity<?> marcarConsulta(@RequestBody Consulta consulta){
        return new ResponseEntity<>(consultaRepository.marcarConsulta(consulta), HttpStatus.CREATED);
    }

    @PutMapping("/consultas/{id}")
    public ResponseEntity<?> prescricaoMedica(@PathVariable long id, @RequestBody Consulta consulta){
        return new ResponseEntity<>(consultaRepository.prescricaoMedica(id, consulta), HttpStatus.OK);
    }

    @PutMapping("/consultas/pagar/{id}")
    public ResponseEntity<?> prescricaoMedica(@PathVariable long id){
        if(consultaRepository.pagarConsulta(id) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar");
        return new ResponseEntity<>(consultaRepository.pagarConsulta(id), HttpStatus.OK);
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<?> cancelarConsulta(@PathVariable long id){
        return new ResponseEntity<>(consultaRepository.cancelarConsulta(id), HttpStatus.OK);
    }
}
