package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Usuario;
import com.kliniek.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<?> findAll() {
        if (usuarioRepository.findAll() != null)
            return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.FOUND);
        throw new ResourceNotFound("Sem usuarios para mostrar.");
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable long id) {
        if (usuarioRepository.findUsuarioById(id) != null)
            return new ResponseEntity<>(usuarioRepository.findUsuarioById(id), HttpStatus.OK);
        throw new ResourceNotFound("Nenhum usuario encontrado com id " + id + ".");
    }

    @GetMapping("/usuarios/username={username}")
    public ResponseEntity<?> findUsuarioByUsername(@PathVariable String username) {
        if (usuarioRepository.findUsuarioByUsername(username) != null)
            return new ResponseEntity<>(usuarioRepository.findUsuarioByUsername(username), HttpStatus.OK);
        throw new ResourceNotFound("Nenhum usuario com username " + username + " foi encontrado.");
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        if (usuarioRepository.findUsuarioByUsername(usuario.getUsername()) != null)
            throw new InternalServerError("Ja existe um usuario com o username escolhido.");
        return new ResponseEntity<>(usuarioRepository.create(usuario), HttpStatus.CREATED);
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<?> updateSenha(@PathVariable long id, @RequestBody String senha) {
        if (usuarioRepository.findUsuarioById(id) != null)
            if (usuarioRepository.updateSenha(id, senha) > 0)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                throw new InternalServerError("Ocorreu algum erro ao actualizar a senha.");
        throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
    }

    @PatchMapping("/usuarios/{id}&estado={estado}")
    public ResponseEntity<?> updateEstado(@PathVariable long id, @PathVariable String estado) {
        if (usuarioRepository.findUsuarioById(id) != null)
            if (usuarioRepository.updateEstado(id, estado) > 0)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                throw new InternalServerError("Ocorreu algum erro ao actualizar o estado.");
        throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
    }

    @PatchMapping("/usuarios/{id}&username={username}")
    public ResponseEntity<?> updateUsername(@PathVariable long id, @PathVariable String username) {
        if (usuarioRepository.findUsuarioById(id) != null)
            if (usuarioRepository.updateUsername(id, username) > 0)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                throw new InternalServerError("Ocorreu algum erro ao actualizar o username.");
        throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
    }
}
