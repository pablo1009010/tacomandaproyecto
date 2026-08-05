package org.tacomanda.modelo;

public class Mesero extends Empleado implements Atendible {

    public Mesero() {
        super();
    }

    public Mesero(String nombre, String telefono, String contrasena) {
        super(nombre, telefono, contrasena);
    }

    @Override
    public String getTipoEmpleado() {
        return "mesero";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Permisos: tomar comandas y consultar el estado de las mesas.");
    }

    @Override
    public void tomarComanda() {
        System.out.println(this.getNombre() + " está tomando una nueva comanda.");
    }

    @Override
    public String toString() {
        return "------ MESERO ------\n" + super.toString() + "\n=====================================";
    }
}
