package com.tacomanda.backend.dto;

import com.tacomanda.backend.model.PersonaTacomanda;

public class LoginResponse {
    private Integer noControl;
    private String nombre;
    private String rol;       // viene de getCodigoRol() -> polimorfico
    private String permisos;  // viene de Operable.permisos() -> polimorfico

    public LoginResponse(PersonaTacomanda persona, String permisos) {
        this.noControl = persona.getNoControl();
        this.nombre = persona.getNombre();
        this.rol = persona.getCodigoRol();
        this.permisos = permisos;
    }

    public Integer getNoControl() { return noControl; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public String getPermisos() { return permisos; }
}
