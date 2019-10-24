package com.kliniek.api.controller;

import com.kliniek.api.repository.EspecidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class EspecialidadeController {

    @Autowired
    EspecidadeRepository especidadeRepository;

    @GetMapping("/especialidades")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(especidadeRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/especialidades/{id}")
    public ResponseEntity<?> findEspecialidadeById(@PathVariable long id){
        return new ResponseEntity<>(especidadeRepository.findEspecialidadeById(id), HttpStatus.FOUND);
    }
}
