package com.tacomanda.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("mesero")
public class Mesero extends PersonaTacomanda implements Autenticable, Operable {

    public Mesero() {
        super();
    }

    public Mesero(String nombre, String telefono, String pin, String contrasena) {
        super(nombre, telefono, pin, contrasena);
    }

    @Override
    public String mostrarRol() {
        return "------ MESERO ------";
    }

    @Override
    public String getCodigoRol() {
        return "mesero";
    }

    @Override
    public boolean verificarPassword(String intento) {
        return coincideConHash(intento);
    }

    @Override
    public String permisos() {
        return "Puede tomar comandas, asignar mesas y enviarlas a cocina/caja.";
    }
}
