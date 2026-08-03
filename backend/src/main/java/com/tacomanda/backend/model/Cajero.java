package com.tacomanda.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("cajero")
public class Cajero extends PersonaTacomanda implements Autenticable, Operable {

    public Cajero() {
        super();
    }

    public Cajero(String nombre, String telefono, String pin, String contrasena) {
        super(nombre, telefono, pin, contrasena);
    }

    @Override
    public String mostrarRol() {
        return "------ CAJERO ------";
    }

    @Override
    public String getCodigoRol() {
        return "cajero";
    }

    @Override
    public boolean verificarPassword(String intento) {
        return coincideConHash(intento);
    }

    @Override
    public String permisos() {
        return "Puede cobrar comandas, cerrar cuentas y consultar el corte de caja.";
    }
}
