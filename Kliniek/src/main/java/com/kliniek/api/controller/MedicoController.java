package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.DiaSemana;
import com.kliniek.api.model.Disponibilidade;
import com.kliniek.api.model.Medico;
import com.kliniek.api.model.Telefone;
import com.kliniek.api.repository.DisponibilidadeRepository;
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

    @Autowired
    DisponibilidadeRepository disponibilidadeRepository;

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

    @GetMapping("/medicos/carteira={carteiraProfissional}")
    public ResponseEntity<?> findMedicByCarteiraProfissional(@PathVariable String carteiraProfissional) {
        if (medicoRepository.findMedicoByCarteiraProfissional(carteiraProfissional) == null)
            throw new ResourceNotFound("Nenhum medico com carteira profissional " + carteiraProfissional + " foi encontrado.");
        return new ResponseEntity<>(medicoRepository.findMedicoByCarteiraProfissional(carteiraProfissional), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/horarios")
    public ResponseEntity<?> getHorarioTodos() {
        return new ResponseEntity<>(disponibilidadeRepository.getHorarioTodosMedicos(), HttpStatus.FOUND);
    }

    @GetMapping("/medicos/{id}/horarios")
    public ResponseEntity<?> getHorarioMedico(@PathVariable long id) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + " foi encontrado.");
        return new ResponseEntity<>(disponibilidadeRepository.getHorarioMedico(id), HttpStatus.FOUND);
    }

    @PostMapping("/medicos")
    public ResponseEntity<?> create(@RequestBody Medico medico) {
        if (pessoaRepository.findPessoaByBI(medico.getBi()) != null) {
            throw new InternalServerError("BI duplicado. Ja existe alguem com esse BI.");
        } else if (pessoaRepository.findPessoaByEmail(medico.getEmail()) != null) {
            throw new InternalServerError("Email duplicado. Ja existe alguem com esse email.");
        } else if (pessoaRepository.findPessoaByNuit(medico.getNuit()) != null) {
            throw new InternalServerError("NUIT duplicado. Ja existe alguem com esse NUIT.");
        } else if (medicoRepository.findMedicoByCarteiraProfissional(medico.getCarteiraProfissional()) != null)
            throw new InternalServerError("Carteira Profissional duplicada. Ja existe alguem com o numero dessa carteira.");
        else
            for (Telefone t : medico.getContactos()) {
                if (pessoaRepository.findTelefone(t.getTipo(), "pessoal") != null)
                    throw new InternalServerError("Ja existe alguem usando o numero " + t.getNumero() + " como pessoal.");
            }
        if (medicoRepository.create(medico) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar informacoes do medico.");
        return new ResponseEntity<>(1, HttpStatus.CREATED);
    }

    @PostMapping("/medicos/{id}/horarios")
    public ResponseEntity<?> createHorario(@PathVariable long id, @RequestBody Disponibilidade disponibilidade) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new InternalServerError("Nenhum medico com id " + id + " foi encontrado.");
        disponibilidade.setMedicoid(id);
        return new ResponseEntity<>(disponibilidadeRepository.create(disponibilidade), HttpStatus.CREATED);
    }

    @PostMapping("/medicos/{id}/contactos")
    public ResponseEntity<?> addContacto(@PathVariable long id, @RequestBody Telefone telefone) {
        if (medicoRepository.findMedicoById(id) == null) {
            throw new InternalServerError("Nenhum medico com id " + id + " foi encontrado.");
        } else if (pessoaRepository.findTelefone(telefone.getNumero(), telefone.getTipo()) != null) {
            throw new ResourceNotFound("Alguem ja registou esse contacto como " + telefone.getTipo());
        } else if (pessoaRepository.createTelefone(new Telefone(id, telefone.getNumero(), telefone.getTipo())) == 0)
            throw new InternalServerError("Ocorreu algum erro ao gravar contacto.");
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

    @PutMapping("/medicos/{id}/horarios")
    public ResponseEntity<?> updateHorario(@PathVariable long id, @RequestBody Disponibilidade disponibilidade) {
        disponibilidade.setMedicoid(id);
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + "foi encontrado.");
        else if (disponibilidadeRepository.update(disponibilidade) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar horario.");
    }

    @PatchMapping("/medicos/{id}")
    public ResponseEntity<?> updateCarteiraProfissional(@PathVariable long id, @RequestBody Medico medico) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + " foi encontrado.");
        else if (medicoRepository.updateCarteiraProfissional(id, medico.getCarteiraProfissional()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar o numero da carteira profissional do medico.");
    }

    @DeleteMapping("/medicos/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + " foi encontrado.");
        else if (medicoRepository.deleteMedico(id) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar medico.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @DeleteMapping("/medicos/{id}/horarios")
    public ResponseEntity<?> deleteHorario(@PathVariable long id, @RequestBody DiaSemana diaSemana) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + " foi encontrado.");
        else if (disponibilidadeRepository.deleteHorario(id, diaSemana.getDiaid()) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar horario.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @DeleteMapping("/medicos/{id}/{numero}")
    public ResponseEntity<?> deleteContacto(@PathVariable long id, @PathVariable String numero) {
        if (medicoRepository.findMedicoById(id) == null)
            throw new ResourceNotFound("Nenhum medico com id " + id + " foi encontrado.");
        else if (pessoaRepository.deleteContacto(id, numero) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar esse contacto.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

}
