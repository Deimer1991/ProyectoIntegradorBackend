package com.example.sistemadenotas.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Profesor;
import com.example.sistemadenotas.model.enums.Registro;
import com.example.sistemadenotas.repository.ProfesorRepository;
import com.example.sistemadenotas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final UsuarioRepository usuarioRepository;

    public Profesor completarRegistro(Long id, String tituloProfesional, String especializacion) {
        Profesor profesor = profesorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        profesor.setTituloProfesional(tituloProfesional);
        profesor.setEspecializacion(especializacion);

        Profesor guardado = profesorRepository.save(profesor);

        usuarioRepository.findById(id).ifPresent(u -> {
            u.setRegistro(Registro.COMPLETO);
            u.setTokenRegistro(null);
            usuarioRepository.save(u);
        });

        return guardado;
    }

    // ✅ Obtener perfil
    public Profesor obtenerPerfil(Long id) {
        return profesorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }

    // ✅ Actualizar datos y foto
    public Profesor actualizarPerfil(Long id, Map<String, String> body) {
        Profesor profesor = profesorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        usuarioRepository.findById(id).ifPresent(u -> {
            if (body.containsKey("numeroCelular"))
                u.setNumeroCelular(body.get("numeroCelular"));
            if (body.containsKey("correo"))
                u.setCorreo(body.get("correo"));
            usuarioRepository.save(u);
        });

        if (body.containsKey("tituloProfesional"))
            profesor.setTituloProfesional(body.get("tituloProfesional"));
        if (body.containsKey("especializacion"))
            profesor.setEspecializacion(body.get("especializacion"));
        if (body.containsKey("foto"))
            profesor.setFoto(body.get("foto"));

        return profesorRepository.save(profesor);
    }
}