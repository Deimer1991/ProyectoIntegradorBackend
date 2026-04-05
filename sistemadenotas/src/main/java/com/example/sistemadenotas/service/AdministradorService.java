package com.example.sistemadenotas.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Administrador;
import com.example.sistemadenotas.model.enums.Registro;
import com.example.sistemadenotas.repository.AdministradorRepository;
import com.example.sistemadenotas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;

    public Administrador completarRegistro(Long id, String tituloProfesional, String especializacion) {
        Administrador administrador = administradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        administrador.setTituloProfesional(tituloProfesional);
        administrador.setEspecializacion(especializacion);

        Administrador guardado = administradorRepository.save(administrador);

        usuarioRepository.findById(id).ifPresent(u -> {
            u.setRegistro(Registro.COMPLETO);
            u.setTokenRegistro(null);
            usuarioRepository.save(u);
        });

        return guardado;
    }

    // ✅ Obtener perfil
    public Administrador obtenerPerfil(Long id) {
        return administradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    // ✅ Actualizar datos y foto
    public Administrador actualizarPerfil(Long id, Map<String, String> body) {
        Administrador administrador = administradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        usuarioRepository.findById(id).ifPresent(u -> {
            if (body.containsKey("numeroCelular"))
                u.setNumeroCelular(body.get("numeroCelular"));
            if (body.containsKey("correo"))
                u.setCorreo(body.get("correo"));
            usuarioRepository.save(u);
        });

        if (body.containsKey("tituloProfesional"))
            administrador.setTituloProfesional(body.get("tituloProfesional"));
        if (body.containsKey("especializacion"))
            administrador.setEspecializacion(body.get("especializacion"));
        if (body.containsKey("foto"))
            administrador.setFoto(body.get("foto"));

        return administradorRepository.save(administrador);
    }
}
