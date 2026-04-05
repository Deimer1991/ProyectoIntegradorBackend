package com.example.sistemadenotas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "profesores")
@PrimaryKeyJoinColumn(name = "id_usuario")

public class Profesor extends Usuario {

    @Column (name = "titulo")
    private String tituloProfesional;


    @Column (name = "especializacion")
    private String especializacion;

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;


    

}
