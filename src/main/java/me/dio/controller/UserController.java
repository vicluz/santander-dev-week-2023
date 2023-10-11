package me.dio.controller;


import me.dio.domain.model.User;
import me.dio.service.ServicoUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ServicoUsuario servicoUsuario;

    public UserController (ServicoUsuario servicoUsuario) {
        this.servicoUsuario = servicoUsuario;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        var usuario = servicoUsuario.findById(id);
        return ResponseEntity.ok(usuario);


    }
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User usuarioParaCriar) {
        var criarUsuario = servicoUsuario.create(usuarioParaCriar);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criarUsuario.getId())
                .toUri();
        return ResponseEntity.created(location).body(criarUsuario);

    }


}
