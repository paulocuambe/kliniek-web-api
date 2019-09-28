package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Paciente;
import com.kliniek.api.repository.PacienteRepository;
import com.kliniek.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping("/pacientes")
    public ResponseEntity<?> findAll() {
        if (pacienteRepository.findAll() == null)
            throw new ResourceNotFound("Sem pacientes para mostrar.");
        return new ResponseEntity<>(pacienteRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<?> findPacienteById(@PathVariable long id) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        return new ResponseEntity<>(pacienteRepository.findPacienteById(id), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/nome={nome}")
    public ResponseEntity<?> findPaciente(@PathVariable String nome) {
        if (pacienteRepository.findPaciente(nome) == null)
            throw new ResourceNotFound("Nenhum paciente com o nome de " + nome + " foi encontrado.");
        return new ResponseEntity<>(pacienteRepository.findPaciente(nome), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/estado={estado}")
    public ResponseEntity<?> findPacienteByEstado(@PathVariable String estado) {
        if (pacienteRepository.findPacienteByEstado(estado) == null)
            throw new ResourceNotFound("Nenhum paciente encontra-se no estado " + estado + ".");
        return new ResponseEntity<>(pacienteRepository.findPacienteByEstado(estado), HttpStatus.FOUND);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<?> create(@RequestBody Paciente paciente) {
        if (pessoaRepository.findPessoaByBI(paciente.getBi()) != null) {
            throw new InternalServerError("BI duplicado. Ja existe alguem com esse BI.");
        } else if (pessoaRepository.findPessoaByEmail(paciente.getEmail()) != null) {
            throw new InternalServerError("Email duplicado. Ja existe alguem com esse email.");
        } else if (pessoaRepository.findPessoaByNuit(paciente.getNuit()) != null) {
            throw new InternalServerError("NUIT duplicado. Ja existe alguem com esse NUIT.");
        } else if (pacienteRepository.create(paciente) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar dados do medico.");
        return new ResponseEntity<>(1, HttpStatus.CREATED);
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Paciente paciente) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        else if (pacienteRepository.update(id, paciente) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar paciente.");
    }

    @PatchMapping("/pacientes/{id}/")
    public ResponseEntity<?> updateEstado(@PathVariable long id, @RequestBody Paciente paciente) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        if (pacienteRepository.updateEstado(id, paciente.getEstadoActual()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        else
            throw new InternalServerError("Ocorreu algum erro ao actualizar o estado do paciente.");
    }

}
