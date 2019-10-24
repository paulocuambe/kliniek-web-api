package com.kliniek.api.controller;

import com.kliniek.api.exception.InternalServerError;
import com.kliniek.api.exception.ResourceNotFound;
import com.kliniek.api.model.Usuario;
import com.kliniek.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<?> findAll() {
        if (usuarioRepository.findAll() == null)
            throw new ResourceNotFound("Sem usuarios para mostrar.");
        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable long id) {
        if (usuarioRepository.findUsuarioById(id) != null)
            return new ResponseEntity<>(usuarioRepository.findUsuarioById(id), HttpStatus.OK);
        throw new ResourceNotFound("Nenhum usuario com id " + id + " foi encontrado.");
    }

    @GetMapping("/usuarios/username/{username}")
    public ResponseEntity<?> findUsuarioByUsername(@PathVariable String username) {
        if (usuarioRepository.findUsuarioByUsername(username) == null)
            throw new ResourceNotFound("Nenhum usuario com username " + username + " foi encontrado.");
        return new ResponseEntity<>(usuarioRepository.findUsuarioByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        System.out.println(usuario.getUsername());
        if (usuarioRepository.findUsuarioByUsername(usuario.getUsername()) != null)
            throw new InternalServerError("Ja existe um usuario com o username escolhido.");
        else if (usuarioRepository.create(usuario) > 0)
            return new ResponseEntity<>(1, HttpStatus.CREATED);
        else
            throw new InternalServerError("Ocorreu algum erro ao gravar dados do usuario.");
    }

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<?> updateSenha(@PathVariable long id, @RequestBody Usuario usuario) {
        System.out.println(usuario.getSenha());
        if (usuarioRepository.findUsuarioById(id) == null)
            throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
        else if (usuarioRepository.updateSenha(id, usuario.getSenha()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar a senha.");
    }

    @PatchMapping(value = "/usuarios/{id}/estado")
    public ResponseEntity<?> updateEstado(@PathVariable long id, @RequestBody Usuario usuario) {
        System.out.println("Estado: " + usuario.getEstado());
        if (usuarioRepository.findUsuarioById(id) == null)
            throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
        else if (usuarioRepository.updateEstado(id, usuario.getEstado()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar o estado.");
    }

    @PatchMapping("/usuarios/{id}/username")
    public ResponseEntity<?> updateUsername(@PathVariable long id, @RequestBody Usuario usuario) {
        if (usuarioRepository.findUsuarioById(id) == null)
            throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
        else if (usuarioRepository.updateUsername(id, usuario.getUsername()) > 0)
            return new ResponseEntity<>(1, HttpStatus.OK);
        throw new InternalServerError("Ocorreu algum erro ao actualizar o username.");
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (usuarioRepository.findUsuarioById(id) == null)
            throw new ResourceNotFound("Usuario com id " + id + " nao existe.");
        else if (usuarioRepository.deleteUsuario(id) == 0)
            throw new InternalServerError("Ocorreu algum erro ao eliminar usuario.");
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
