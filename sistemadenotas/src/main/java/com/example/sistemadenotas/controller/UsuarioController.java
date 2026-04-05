package com.example.sistemadenotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Usuario;
import com.example.sistemadenotas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // ✅ Asigna rol → estado cambia a ACTIVO automáticamente
    @PutMapping("/{id}/rol")
    public ResponseEntity<Usuario> asignarRol(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(usuarioService.asignarRol(id, body.get("rol")));
    }

    // ✅ Envía correo con link para completar registro
    @PostMapping("/{id}/enviar-correo")
    public ResponseEntity<Void> enviarCorreo(@PathVariable Long id) {
        usuarioService.enviarCorreoRegistro(id);
        return ResponseEntity.ok().build();
    }

    // ✅ Busca usuario por token (el frontend lo llama al abrir el link)
    @GetMapping("/token/{token}")
    public ResponseEntity<Usuario> buscarPorToken(@PathVariable String token) {
        return ResponseEntity.ok(usuarioService.buscarPorToken(token));
    }
}
