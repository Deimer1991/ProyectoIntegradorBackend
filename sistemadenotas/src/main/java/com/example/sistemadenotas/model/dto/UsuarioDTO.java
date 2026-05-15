package com.example.sistemadenotas.model.dto;

public class UsuarioDTO {

    private Long id;
    private NombreCompletoDTO nombreCompleto;
    private String correo;
    private String programa;
    private String rol;
    private String estado;
    private String envioCorreo;
    private String registro;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nombres, String apellidos, String correo,
                      String programa, String rol, String estado,
                      String envioCorreo, String registro) {
        this.id = id;
        this.nombreCompleto = new NombreCompletoDTO(nombres, apellidos);
        this.correo = correo;
        this.programa = programa;
        this.rol = rol;
        this.estado = estado;
        this.envioCorreo = envioCorreo;
        this.registro = registro;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public NombreCompletoDTO getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(NombreCompletoDTO nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getEnvioCorreo() { return envioCorreo; }
    public void setEnvioCorreo(String envioCorreo) { this.envioCorreo = envioCorreo; }

    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }

    public static class NombreCompletoDTO {
        private String nombres;
        private String apellidos;

        public NombreCompletoDTO() {}

        public NombreCompletoDTO(String nombres, String apellidos) {
            this.nombres = nombres;
            this.apellidos = apellidos;
        }

        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }

        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    }
}
