package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Paciente;
import com.kliniek.api.model.Telefone;
import com.kliniek.api.repository.ConsultaRepository;
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

    @Autowired
    ConsultaRepository consultaRepository;

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

    @GetMapping("/pacientes/{id}/contactos")
    public ResponseEntity<?> findAllContactos(@PathVariable long id) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        else if (pessoaRepository.findAllTelefones(id) == null)
            throw new ResourceNotFound("Nenhum contacto encontrado.");
        else
            return new ResponseEntity<>(pessoaRepository.findAllTelefones(id), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/estado={estado}")
    public ResponseEntity<?> findPacienteByEstado(@PathVariable String estado) {
        if (pacienteRepository.findPacienteByEstado(estado) == null)
            throw new ResourceNotFound("Nenhum paciente encontra-se no estado " + estado + ".");
        return new ResponseEntity<>(pacienteRepository.findPacienteByEstado(estado), HttpStatus.FOUND);
    }

    @GetMapping("/pacientes/{id}/consultas")
    public ResponseEntity<?> findAllConsultasDoPaciente(@PathVariable long id){
        return new ResponseEntity<>(consultaRepository.findAllConsultasDoPaciente(id), HttpStatus.FOUND);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<?> create(@RequestBody Paciente paciente) {
        if (pessoaRepository.findPessoaByBI(paciente.getBi()) != null) {
            throw new InternalServerError("BI duplicado. Ja existe alguem com esse BI.");
        } else if (pessoaRepository.findPessoaByEmail(paciente.getEmail()) != null) {
            throw new InternalServerError("Email duplicado. Ja existe alguem com esse email.");
        } else if (pessoaRepository.findPessoaByNuit(paciente.getNuit()) != null) {
            throw new InternalServerError("NUIT duplicado. Ja existe alguem com esse NUIT.");
        } else
            for (Telefone t : paciente.getContactos()) {
                if (pessoaRepository.findTelefone(t.getTipo(), "pessoal") != null)
                    throw new InternalServerError("Ja existe alguem usando o numero " + t.getNumero() + " como pessoal.");
            }
        if (pacienteRepository.create(paciente) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar dados do paciente.");
        return new ResponseEntity<>(1, HttpStatus.CREATED);
    }

    @PostMapping("/pacientes/{id}/contactos")
    public ResponseEntity<?> addContacto(@PathVariable long id, @RequestBody Telefone telefone) {
        if (pacienteRepository.findPacienteById(id) == null) {
            throw new InternalServerError("Nenhum paciente com id " + id + " foi encontrado.");
        } else if (pessoaRepository.findTelefone(telefone.getNumero(), telefone.getTipo()) != null) {
            throw new ResourceNotFound("Alguem ja registou esse contacto como " + telefone.getTipo());
        } else if (pessoaRepository.createTelefone(new Telefone(id, telefone.getNumero(), telefone.getTipo())) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar contacto.");
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

    @PatchMapping("/pacientes/{id}")
    public ResponseEntity<?> updateEstado(@PathVariable long id, @RequestBody Paciente paciente) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        if (pacienteRepository.updateEstado(id, paciente.getEstadoActual()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        else
            throw new InternalServerError("Ocorreu algum erro ao actualizar o estado do paciente.");
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        else if (pacienteRepository.deletePaciente(id) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar paciente.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @DeleteMapping("/pacientes/{id}/{numero}")
    public ResponseEntity<?> deleteContacto(@PathVariable long id, @PathVariable String numero) {
        if (pacienteRepository.findPacienteById(id) == null)
            throw new ResourceNotFound("Nenhum paciente com id " + id + " foi encontrado.");
        else if (pessoaRepository.deleteContacto(id, numero) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar esse contacto.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

}
