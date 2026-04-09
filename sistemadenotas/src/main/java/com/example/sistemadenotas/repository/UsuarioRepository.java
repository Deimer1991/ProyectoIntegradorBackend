package com.example.sistemadenotas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByTokenRegistro(String tokenRegistro);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDocumento(String documento);
    Optional<Usuario> findByTokenRecuperacion(String tokenRecuperacion);

}


