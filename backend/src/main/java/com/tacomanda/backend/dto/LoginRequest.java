package com.tacomanda.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El número de control es obligatorio")
    private String noControl; // llega como texto; el controller lo valida/convierte

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    public String getNoControl() { return noControl; }
    public void setNoControl(String noControl) { this.noControl = noControl; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
