package com.example.sistemadenotas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "calificaciones",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"matricula_id", "momento", "tipo"}
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @Column(name = "momento", nullable = false)
    private Integer momento; // 1, 2 o 3

    @Column(name = "tipo", nullable = false)
    private String tipo; // PARCIAL_1, PARCIAL_2, TRABAJO, EXAMEN_FINAL

    @Column(name = "nota", nullable = false)
    private Double nota; // 0.0 a 5.0
}