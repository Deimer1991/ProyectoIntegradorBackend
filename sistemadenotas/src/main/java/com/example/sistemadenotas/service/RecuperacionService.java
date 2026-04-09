package com.example.sistemadenotas.service;

import com.example.sistemadenotas.model.entity.Usuario;
import com.example.sistemadenotas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecuperacionService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    // ✅ Solicitar recuperación — genera token y envía correo
    public void solicitarRecuperacion(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("No existe un usuario con ese correo"));

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setExpiracionTokenRecuperacion(LocalDateTime.now().plusHours(1));
        usuarioRepository.save(usuario);

        emailService.enviarCorreoRecuperacion(usuario, token);
    }

    // ✅ Validar token
    public boolean validarToken(String token) {
        return usuarioRepository.findByTokenRecuperacion(token)
            .map(u -> u.getExpiracionTokenRecuperacion() != null &&
                      u.getExpiracionTokenRecuperacion().isAfter(LocalDateTime.now()))
            .orElse(false);
    }

    // ✅ Cambiar contraseña con token
    public void cambiarContrasena(String token, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
            .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (usuario.getExpiracionTokenRecuperacion() == null ||
            usuario.getExpiracionTokenRecuperacion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El link ha expirado. Solicita uno nuevo.");
        }

        usuario.setContraseña(nuevaContrasena);
        usuario.setContraseñaConfirmada(nuevaContrasena);
        usuario.setTokenRecuperacion(null);
        usuario.setExpiracionTokenRecuperacion(null);
        usuarioRepository.save(usuario);
    }
}