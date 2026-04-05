package com.example.sistemadenotas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Usuario;
import com.example.sistemadenotas.repository.UsuarioRepository;
import com.example.sistemadenotas.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String contraseña = body.get("contraseña");

        // Busca el usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElse(null);

        if (usuario == null || !usuario.getContraseña().equals(contraseña)) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "Correo o contraseña incorrectos"));
        }

        if (usuario.getEstado() == null ||
            !usuario.getEstado().name().equals("ACTIVO")) {
            return ResponseEntity.status(403)
                .body(Map.of("error", "Tu cuenta no está activa aún"));
        }

        if (usuario.getRol() == null) {
            return ResponseEntity.status(403)
                .body(Map.of("error", "Tu rol aún no ha sido asignado"));
        }

        // Genera el token JWT
        String token = jwtService.generarToken(
            usuario.getId(),
            usuario.getCorreo(),
            usuario.getRol().name()
        );

        return ResponseEntity.ok(Map.of(
            "token", token,
            "rol", usuario.getRol().name(),
            "id", usuario.getId(),
            "nombres", usuario.getNombreCompleto().getNombres(),
            "apellidos", usuario.getNombreCompleto().getApellidos()
        ));
    }
}