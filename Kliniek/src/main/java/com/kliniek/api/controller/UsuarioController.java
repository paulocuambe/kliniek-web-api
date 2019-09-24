package com.kliniek.api.controller;

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

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioRepository.create(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioRepository.update(id, usuario), HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable long id) {
        return new ResponseEntity<>(usuarioRepository.findUsuarioById(id), HttpStatus.OK);
    }

    @GetMapping("/usuarios/username={username}")
    public ResponseEntity<?> findUsuarioByUsername(@PathVariable String username) {
        return new ResponseEntity<>(usuarioRepository.findUsuarioByUsername(username), HttpStatus.OK);
    }
}
