package com.tacomanda.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Administrador extends PersonaTacomanda implements Autenticable, Operable {

    public Administrador() {
        super();
    }

    public Administrador(String nombre, String telefono, String pin, String contrasena) {
        super(nombre, telefono, pin, contrasena);
    }

    @Override
    public String mostrarRol() {
        return "------ ADMINISTRADOR ------";
    }

    @Override
    public String getCodigoRol() {
        return "admin";
    }

    @Override
    public boolean verificarPassword(String intento) {
        return coincideConHash(intento);
    }

    @Override
    public String permisos() {
        return "Puede gestionar productos, usuarios, mesas y ver reportes generales.";
    }
}
