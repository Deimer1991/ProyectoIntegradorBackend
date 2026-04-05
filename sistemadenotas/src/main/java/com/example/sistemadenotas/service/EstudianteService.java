package com.example.sistemadenotas.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Estudiante;
import com.example.sistemadenotas.model.entity.ProgramaAcademico;
import com.example.sistemadenotas.model.enums.Registro;
import com.example.sistemadenotas.repository.EstudianteRepository;
import com.example.sistemadenotas.repository.ProgramaAcademicoRepository;
import com.example.sistemadenotas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProgramaAcademicoRepository programaRepository;

    public Estudiante completarRegistro(Long id, Long programaId) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        ProgramaAcademico programa = programaRepository.findById(programaId)
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        estudiante.setPrograma(programa);

        Estudiante guardado = estudianteRepository.save(estudiante);

        usuarioRepository.findById(id).ifPresent(u -> {
            u.setRegistro(Registro.COMPLETO);
            u.setTokenRegistro(null);
            usuarioRepository.save(u);
        });

        return guardado;
    }

    public Estudiante obtenerPerfil(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante actualizarPerfil(Long id, Map<String, String> body) {
        Estudiante estudiante = estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        usuarioRepository.findById(id).ifPresent(u -> {
            if (body.containsKey("numeroCelular"))
                u.setNumeroCelular(body.get("numeroCelular"));
            if (body.containsKey("correo"))
                u.setCorreo(body.get("correo"));
            usuarioRepository.save(u);
        });

        // ✅ Actualiza programa si viene en el body
        if (body.containsKey("programaId")) {
            Long programaId = Long.parseLong(body.get("programaId"));
            programaRepository.findById(programaId)
                .ifPresent(estudiante::setPrograma);
        }

        if (body.containsKey("foto"))
            estudiante.setFoto(body.get("foto"));

        return estudianteRepository.save(estudiante);
    }
}