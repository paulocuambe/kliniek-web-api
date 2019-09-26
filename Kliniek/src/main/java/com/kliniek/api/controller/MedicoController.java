package com.kliniek.api.controller;

import com.kliniek.api.model.Medico;
import com.kliniek.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @GetMapping("/medicos")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(medicoRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/nome={nome}")
    public ResponseEntity<?> findMedico(@PathVariable String nome){
        return new ResponseEntity<>(medicoRepository.findMedico(nome), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/{id}")
    public ResponseEntity<?> findMedicById(@PathVariable long id){
        return new ResponseEntity<>(medicoRepository.findMedicoById(id), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/carteiraprofissional={carteiraProfissional}")
    public ResponseEntity<?> findMedicByCarteiraProfissional(@PathVariable String carteiraProfissional){
        return new ResponseEntity<>(medicoRepository.findMedicoByCarteiraProfissional(carteiraProfissional), HttpStatus.FOUND);
    }

    @PostMapping("/medicos")
    public ResponseEntity<?> create(@RequestBody Medico medico){
        return new ResponseEntity<>(medicoRepository.create(medico), HttpStatus.CREATED);
    }

    @PutMapping("/medicos/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Medico medico){
        return new ResponseEntity<>(medicoRepository.update(id, medico), HttpStatus.OK);
    }

    @PatchMapping("/medicos/{id}&{carteiraProfissional}")
    public ResponseEntity<?> updateCarteiraProfissional(@PathVariable long id, @PathVariable String carteiraProfissional){
        return new ResponseEntity<>(medicoRepository.updateCarteiraProfissional(id, carteiraProfissional), HttpStatus.OK);
    }
}
