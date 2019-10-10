package com.kliniek.api.controller;

import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.TipoExame;
import com.kliniek.api.repository.TipoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TipoExameController {
    @Autowired
    TipoExameRepository tipoExameRepository;

    @GetMapping("/tipos-exame")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(tipoExameRepository.findAllTiposExames(), HttpStatus.FOUND);
    }

    @GetMapping("/tipos-exame/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        if (tipoExameRepository.findTipoExameById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de exame com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoExameRepository.findTipoExameById(id), HttpStatus.FOUND);
    }

    @PostMapping("/tipos-exame")
    public ResponseEntity<?> create(@RequestBody TipoExame tipoExame) {
        return new ResponseEntity<>(tipoExameRepository.create(tipoExame), HttpStatus.OK);
    }

    @PutMapping("/tipos-exame/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TipoExame tipoExame) {
        if (tipoExameRepository.findTipoExameById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de exame com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoExameRepository.update(id, tipoExame), HttpStatus.OK);
    }

    @DeleteMapping("/tipos-exame/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (tipoExameRepository.findTipoExameById(id) == null)
            throw new ResourceNotFound("Nenhum tipo de exame com id " + id + " foi encontrado.");
        return new ResponseEntity<>(tipoExameRepository.indisponibilizarExame(id), HttpStatus.OK);
    }
}
