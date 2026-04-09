package com.example.sistemadenotas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void enviarLinkFormulario(Usuario usuario, String token) {
        String rol = usuario.getRol().name();
        String nombres = usuario.getNombreCompleto().getNombres();
        String apellidos = usuario.getNombreCompleto().getApellidos();
        String correo = usuario.getCorreo();

        String ruta = switch (rol) {
            case "ESTUDIANTE"    -> "/completar/estudiante/";
            case "PROFESOR"      -> "/completar/profesor/";
            case "ADMINISTRADOR" -> "/completar/administrador/";
            case "SUPER_ADMIN"   -> "/completar/administrador/";
            default              -> "/completar/";
        };

        String link = frontendUrl + ruta + token;

        // ✅ Asunto personalizado por rol
        String asunto = switch (rol) {
            case "ESTUDIANTE"    -> "Bienvenido al Sistema de Notas — Completa tu registro como Estudiante";
            case "PROFESOR"      -> "Bienvenido al Sistema de Notas — Completa tu registro como Profesor";
            case "ADMINISTRADOR" -> "Bienvenido al Sistema de Notas — Completa tu registro como Administrador";
            case "SUPER_ADMIN"   -> "Bienvenido al Sistema de Notas — Configura tu cuenta de Super Administrador";
            default              -> "Completa tu registro — Sistema de Notas";
        };

        // ✅ Cuerpo personalizado por rol con datos personales
        String rolLegible = switch (rol) {
            case "ESTUDIANTE"    -> "estudiante";
            case "PROFESOR"      -> "profesor";
            case "ADMINISTRADOR" -> "administrador";
            case "SUPER_ADMIN"   -> "super administrador";
            default              -> "usuario";
        };

        String instrucciones = switch (rol) {
            case "ESTUDIANTE" -> """
                Al completar tu registro podrás:
                  - Consultar tus materias y notas por semestre
                  - Ver tu horario académico
                  - Actualizar tu información personal y foto de perfil
                """;
            case "PROFESOR" -> """
                Al completar tu registro podrás:
                  - Gestionar las materias que dictas
                  - Ver tus grupos y estudiantes
                  - Registrar y actualizar notas
                  - Actualizar tu información personal y foto de perfil
                """;
            case "ADMINISTRADOR", "SUPER_ADMIN" -> """
                Al completar tu registro podrás:
                  - Gestionar todos los usuarios del sistema
                  - Asignar roles y activar cuentas
                  - Supervisar el estado general de la plataforma
                  - Actualizar tu información personal y foto de perfil
                """;
            default -> "";
        };

        String cuerpo = String.format("""
            Hola %s %s,

            Tu cuenta ha sido activada en el Sistema de Notas con el rol de %s.

            Para completar tu registro y acceder a la plataforma, haz clic en el siguiente enlace:

            %s

            %s
            Este enlace es personal e intransferible. Si no solicitaste este registro, ignora este correo.

            Atentamente,
            El equipo del Sistema de Notas
            """,
            nombres, apellidos, rolLegible, link, instrucciones
        );

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }

    public void enviarCorreoRecuperacion(Usuario usuario, String token) {
    String nombres = usuario.getNombreCompleto().getNombres();
    String apellidos = usuario.getNombreCompleto().getApellidos();
    String link = frontendUrl + "/recuperar-contrasena/" + token;

    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(usuario.getCorreo());
    mensaje.setSubject("Recuperación de contraseña — Sistema de Notas");
    mensaje.setText(String.format("""
        Hola %s %s,

        Recibimos una solicitud para restablecer la contraseña de tu cuenta.

        Haz clic en el siguiente enlace para crear una nueva contraseña:

        %s

        Este enlace es válido por 1 hora. Si no solicitaste este cambio, ignora este correo.

        Atentamente,
        El equipo del Sistema de Notas
        """, nombres, apellidos, link));

    mailSender.send(mensaje);
}
}