package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Medico;
import com.kliniek.api.repository.MedicoRepository;
import com.kliniek.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping("/medicos")
    public ResponseEntity<?> findAll() {
        if (medicoRepository.findAll() == null)
            throw new ResourceNotFound("Sem medicos para mostrar.");
        return new ResponseEntity<>(medicoRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/{id}")
    public ResponseEntity<?> findMedicoById(@PathVariable long id) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico de id " + id + " foi encontrado.");
        return new ResponseEntity<>(medicoRepository.findMedicoById(id), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/nome={nome}")
    public ResponseEntity<?> findMedico(@PathVariable String nome) {
        if (medicoRepository.findMedico(nome) == null)
            throw new ResourceNotFound("Nenhum medico com o nome de " + nome + " foi encontrado.");
        return new ResponseEntity<>(medicoRepository.findMedico(nome), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/carteiraprofissional={carteiraProfissional}")
    public ResponseEntity<?> findMedicByCarteiraProfissional(@PathVariable String carteiraProfissional) {
        if (medicoRepository.findMedicoByCarteiraProfissional(carteiraProfissional) == null)
            throw new ResourceNotFound("Nenhum medico com carteira profissional " + carteiraProfissional + " foi encontrado.");
        return new ResponseEntity<>(medicoRepository.findMedicoByCarteiraProfissional(carteiraProfissional), HttpStatus.FOUND);
    }

    @PostMapping("/medicos")
    public ResponseEntity<?> create(@RequestBody Medico medico) {
        if (pessoaRepository.findPessoaByBI(medico.getBi()) != null) {
            throw new InternalServerError("BI duplicado. Ja existe alguem com esse BI.");
        } else if (pessoaRepository.findPessoaByEmail(medico.getEmail()) != null) {
            throw new InternalServerError("Email duplicado. Ja existe alguem com esse email.");
        } else if (pessoaRepository.findPessoaByNuit(medico.getNuit()) != null) {
            throw new InternalServerError("NUIT duplicado. Ja existe alguem com esse NUIT.");
        } else if (medicoRepository.create(medico) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar informacoes do medico.");
        return new ResponseEntity<>(1, HttpStatus.CREATED);
    }

    @PutMapping("/medicos/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Medico medico) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + "foi encontrado.");
        else if (medicoRepository.update(id, medico) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar informacoes do medico.");
    }

    @PatchMapping("/medicos/{id}&{carteiraProfissional}")
    public ResponseEntity<?> updateCarteiraProfissional(@PathVariable long id, @PathVariable String carteiraProfissional) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + "foi encontrado.");
        else if (medicoRepository.updateCarteiraProfissional(id, carteiraProfissional) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar o numero da carteira profissional do medico.");
    }
}
