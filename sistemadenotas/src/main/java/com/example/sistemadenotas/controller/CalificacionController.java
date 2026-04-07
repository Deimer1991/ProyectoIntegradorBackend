package com.example.sistemadenotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Calificacion;
import com.example.sistemadenotas.service.CalificacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    // ✅ Listar por matrícula (estudiante ve sus notas)
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<Calificacion>> listarPorMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(calificacionService.listarPorMatricula(matriculaId));
    }

    // ✅ Listar por grupo (profesor ve notas de su grupo)
    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Calificacion>> listarPorGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(calificacionService.listarPorGrupo(grupoId));
    }

    // ✅ Registrar o actualizar nota
    @PostMapping
    public ResponseEntity<Calificacion> registrar(@RequestBody Map<String, String> body) {
        Long matriculaId = Long.parseLong(body.get("matriculaId"));
        Integer momento = Integer.parseInt(body.get("momento"));
        String tipo = body.get("tipo");
        Double nota = Double.parseDouble(body.get("nota"));
        return ResponseEntity.ok(calificacionService.registrar(matriculaId, momento, tipo, nota));
    }

    // ✅ Calcular nota final de una matrícula
    @GetMapping("/matricula/{matriculaId}/nota-final")
    public ResponseEntity<Map<String, Object>> notaFinal(@PathVariable Long matriculaId) {
        List<Calificacion> notas = calificacionService.listarPorMatricula(matriculaId);
        double m1 = calificacionService.calcularPromedioMomento(notas, 1);
        double m2 = calificacionService.calcularPromedioMomento(notas, 2);
        double m3 = calificacionService.calcularPromedioMomento(notas, 3);
        double final_ = calificacionService.calcularNotaFinal(notas);
        return ResponseEntity.ok(Map.of(
            "momento1", m1,
            "momento2", m2,
            "momento3", m3,
            "notaFinal", final_
        ));
    }
}