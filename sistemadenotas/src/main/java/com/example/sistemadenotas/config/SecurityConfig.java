package com.example.sistemadenotas.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorizedHandler())
                .accessDeniedHandler(forbiddenHandler()))
            .authorizeHttpRequests(auth -> auth

                // ── Endpoints públicos ──
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuarios/token/**").permitAll()
                .requestMatchers("/api/recuperacion/**").permitAll()

                // ── Público: completar registro (llegan por email sin JWT) ──
                .requestMatchers(HttpMethod.POST, "/api/estudiantes").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/profesores").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/administradores").permitAll()

                // ── Público: lista activos para formulario de registro ──
                .requestMatchers(HttpMethod.GET, "/api/programas/activos").permitAll()

                // ── Sólo ADMINISTRADOR / SUPER_ADMIN (escritura) ──
                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}/rol").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/usuarios/{id}/enviar-correo").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/programas/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/programas/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/programas/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/materias/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/materias/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/materias/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/grupos/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/grupos/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/grupos/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/matriculas/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/matriculas/**").hasAnyRole("ADMINISTRADOR", "SUPER_ADMIN")

                // ── Autenticado (cualquier rol) ──
                .requestMatchers("/api/programas/**").authenticated()
                .requestMatchers("/api/materias/**").authenticated()
                .requestMatchers("/api/grupos/**").authenticated()
                .requestMatchers("/api/matriculas/**").authenticated()
                .requestMatchers("/api/estudiantes/**").authenticated()
                .requestMatchers("/api/profesores/**").authenticated()
                .requestMatchers("/api/administradores/**").authenticated()
                .requestMatchers("/api/calificaciones/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174", "http://localhost:5175"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token no proporcionado o inválido\"}");
        };
    }

    @Bean
    public AccessDeniedHandler forbiddenHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"No tienes permisos para este recurso\"}");
        };
    }
}
