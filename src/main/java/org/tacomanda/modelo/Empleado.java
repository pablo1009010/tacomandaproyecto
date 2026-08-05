package org.tacomanda.modelo;

/**
 * Clase abstracta que representa a cualquier empleado de TacoManda.
 * Sirve como base para Mesero, Cajero y Admin (herencia).
 */
public abstract class Empleado {
    private int noControl;
    private String nombre;
    private String telefono;
    private String contrasena;

    public Empleado() {
        super();
    }

    public Empleado(String nombre, String telefono, String contrasena) {
        super();
        this.setNombre(nombre);
        this.setTelefono(telefono);
        this.setContrasena(contrasena);
    }

    // ---- Métodos abstractos (polimorfismo) ----
    public abstract String getTipoEmpleado();
    public abstract void mostrarPermisos();

    // ---- Getters y Setters con validaciones ----
    public int getNoControl() {
        return this.noControl;
    }

    public void setNoControl(int noControl) {
        if (noControl >= 0) {
            this.noControl = noControl;
        } else {
            System.out.println("Error: Número de control inválido");
        }
    }

    public String getNombre() {
        if (this.nombre != null && !this.nombre.isBlank()) {
            return this.nombre.toUpperCase();
        } else {
            System.out.println("El nombre es requerido");
            return "";
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            System.out.println("Error: El nombre no puede estar vacío");
        }
    }

    public String getTelefono() {
        return this.telefono != null ? this.telefono : "";
    }

    public void setTelefono(String telefono) {
        if (telefono != null && telefono.trim().length() >= 10 && telefono.trim().length() <= 13) {
            this.telefono = telefono.trim();
        } else {
            System.out.println("Error: Teléfono inválido (debe tener entre 10 y 13 caracteres)");
        }
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena != null && contrasena.length() >= 4) {
            this.contrasena = contrasena;
        } else {
            System.out.println("Error: La contraseña debe tener al menos 4 caracteres");
        }
    }

    @Override
    public String toString() {
        return "No. Control: " + this.getNoControl() + "\n" +
                "Nombre: " + this.getNombre() + "\n" +
                "Teléfono: " + this.getTelefono() + "\n" +
                "Tipo: " + this.getTipoEmpleado();
    }
}
