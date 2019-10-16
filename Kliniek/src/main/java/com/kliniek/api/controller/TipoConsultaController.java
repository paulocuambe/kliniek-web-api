package com.kliniek.api.controller;

import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.TipoConsulta;
import com.kliniek.api.repository.TipoConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TipoConsultaController {

    @Autowired
    TipoConsultaRepository tipoConsultaRepository;

    @GetMapping("/tipos-consulta")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(tipoConsultaRepository.findAllTiposConsultas(), HttpStatus.FOUND);
    }

    @GetMapping("/tipos-consulta/{id}")
    public ResponseEntity<?> findByID(@PathVariable long id) {
        if (tipoConsultaRepository.findTipoConsultaById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de consulta com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoConsultaRepository.findTipoConsultaById(id), HttpStatus.FOUND);
    }

    @PostMapping("/tipos-consulta")
    public ResponseEntity<?> create(@RequestBody TipoConsulta tipoConsulta) {
        return new ResponseEntity<>(tipoConsultaRepository.create(tipoConsulta), HttpStatus.CREATED);
    }

    @PutMapping("/tipos-consulta/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TipoConsulta tipoConsulta) {
        if (tipoConsultaRepository.findTipoConsultaById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de consulta com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoConsultaRepository.update(id, tipoConsulta), HttpStatus.OK);
    }

    @DeleteMapping("/tipos-consulta/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (tipoConsultaRepository.findTipoConsultaById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de consulta com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoConsultaRepository.indisponibilizarConsulta(id), HttpStatus.OK);
    }

}
