package com.example.sistemadenotas.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.sistemadenotas.model.embeddable.NombreCompleto;
import com.example.sistemadenotas.model.enums.EnvioCorreo;
import com.example.sistemadenotas.model.enums.Estado;
import com.example.sistemadenotas.model.enums.Registro;
import com.example.sistemadenotas.model.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "usuarios")
@Setter
@Inheritance(strategy= InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaModificacion;

    @Embedded
    private NombreCompleto nombreCompleto;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "documento")
    private String documento;

    @Column(name = "correo")
    private String correo;

    @Column(name = "numero_celular")
    private String numeroCelular;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "Confirmacion_contraseña")
    private String contraseñaConfirmada;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "envio_correo")
    private EnvioCorreo envioCorreo;

    @Column(name = "token_registro", unique = true)
    private String tokenRegistro; 

    @Enumerated(EnumType.STRING)
    @Column(name = "registro")
    private Registro registro;

    

}
