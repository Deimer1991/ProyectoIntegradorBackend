package com.example.sistemadenotas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByTokenRegistro(String tokenRegistro);
    Optional<Usuario> findByTokenRecuperacion(String tokenRecuperacion);

    boolean existsByCorreo(String correo);
    boolean existsByDocumento(String documento);

}


