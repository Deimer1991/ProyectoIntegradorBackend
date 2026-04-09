package com.example.sistemadenotas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.service.RecuperacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recuperacion")
@RequiredArgsConstructor
public class RecuperacionController {

    private final RecuperacionService recuperacionService;

    // ✅ Solicitar recuperación
    @PostMapping("/solicitar")
    public ResponseEntity<Map<String, String>> solicitar(@RequestBody Map<String, String> body) {
        recuperacionService.solicitarRecuperacion(body.get("correo"));
        return ResponseEntity.ok(Map.of("message", "Correo de recuperación enviado"));
    }

    // ✅ Validar token
    @GetMapping("/validar/{token}")
    public ResponseEntity<Map<String, Boolean>> validar(@PathVariable String token) {
        return ResponseEntity.ok(Map.of("valido", recuperacionService.validarToken(token)));
    }

    // ✅ Cambiar contraseña
    @PostMapping("/cambiar")
    public ResponseEntity<Map<String, String>> cambiar(@RequestBody Map<String, String> body) {
        recuperacionService.cambiarContrasena(body.get("token"), body.get("nuevaContrasena"));
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
    }
}