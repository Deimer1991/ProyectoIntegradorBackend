package com.example.sistemadenotas.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sistemadenotas.model.entity.Usuario;
import com.example.sistemadenotas.model.enums.EnvioCorreo;
import com.example.sistemadenotas.model.enums.Estado;
import com.example.sistemadenotas.model.enums.Registro;
import com.example.sistemadenotas.model.enums.Rol;
import com.example.sistemadenotas.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Usuario guardar(Usuario usuario) {

        // ✅ Valida correo duplicado SIEMPRE (antes estaba dentro del if)
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        // ✅ Valida documento duplicado SIEMPRE
        if (usuarioRepository.findByDocumento(usuario.getDocumento()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese número de documento");
        }

        // ✅ Si es el primer usuario → SUPER_ADMIN automático
        if (usuarioRepository.count() == 0) {
            usuario.setRol(Rol.SUPER_ADMIN);
            usuario.setEstado(Estado.ACTIVO);
            usuario.setRegistro(Registro.PENDIENTE);

            Usuario guardado = usuarioRepository.save(usuario);

            // ✅ Crea fila en administrativos
            entityManager.createNativeQuery(
                "INSERT INTO administrativos (id_usuario, titulo, especializacion) VALUES (:id, null, null)")
                .setParameter("id", guardado.getId())
                .executeUpdate();

            // ✅ Genera token y envía correo
            String token = UUID.randomUUID().toString();
            guardado.setTokenRegistro(token);
            guardado.setEnvioCorreo(EnvioCorreo.ENVIADO);
            usuarioRepository.save(guardado);

            // ✅ Usa el nuevo método con usuario completo
            emailService.enviarLinkFormulario(guardado, token);

            return guardado;
        }

        // ✅ Registro normal para los demás usuarios
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario asignarRol(Long id, String rol) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRol(Rol.valueOf(rol));
        usuario.setEstado(Estado.ACTIVO);
        usuarioRepository.save(usuario);

        // ✅ Limpia filas hijas anteriores para evitar inconsistencias
        entityManager.createNativeQuery("DELETE FROM estudiantes WHERE id_usuario = :id")
            .setParameter("id", id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM profesores WHERE id_usuario = :id")
            .setParameter("id", id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM administrativos WHERE id_usuario = :id")
            .setParameter("id", id).executeUpdate();

        // ✅ Inserta fila en la tabla correcta
        switch (Rol.valueOf(rol)) {
            case ESTUDIANTE -> entityManager.createNativeQuery(
                "INSERT INTO estudiantes (id_usuario, programa_id) VALUES (:id, null)")
                .setParameter("id", id).executeUpdate();
            case PROFESOR -> entityManager.createNativeQuery(
                "INSERT INTO profesores (id_usuario, titulo, especializacion) VALUES (:id, null, null)")
                .setParameter("id", id).executeUpdate();
            case ADMINISTRADOR -> entityManager.createNativeQuery(
                "INSERT INTO administrativos (id_usuario, titulo, especializacion) VALUES (:id, null, null)")
                .setParameter("id", id).executeUpdate();
            default -> throw new RuntimeException("Rol no válido para asignación: " + rol);
        }

        return usuarioRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void enviarCorreoRegistro(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = UUID.randomUUID().toString();
        usuario.setTokenRegistro(token);
        usuario.setEnvioCorreo(EnvioCorreo.ENVIADO);
        usuario.setRegistro(Registro.PENDIENTE);

        usuarioRepository.save(usuario);

        // ✅ Usa el nuevo método con usuario completo
        emailService.enviarLinkFormulario(usuario, token);
    }

    public Usuario buscarPorToken(String token) {
        return usuarioRepository.findByTokenRegistro(token)
            .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));
    }
}