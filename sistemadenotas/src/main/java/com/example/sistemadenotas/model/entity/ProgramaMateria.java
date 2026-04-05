package com.example.sistemadenotas.model.entity;

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
    name = "programa_materia",
    uniqueConstraints = @UniqueConstraint(columnNames = {"programa_id", "materia_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id", nullable = false)
    private ProgramaAcademico programa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
}
