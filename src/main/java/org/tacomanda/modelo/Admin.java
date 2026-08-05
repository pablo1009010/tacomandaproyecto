package org.tacomanda.modelo;

public class Admin extends Empleado implements Atendible, Cobrador, Administrador {

    public Admin() {
        super();
    }

    public Admin(String nombre, String telefono, String contrasena) {
        super(nombre, telefono, contrasena);
    }

    @Override
    public String getTipoEmpleado() {
        return "admin";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Permisos: control total del sistema (empleados, platillos, mesas y reportes).");
    }

    @Override
    public void tomarComanda() {
        System.out.println(this.getNombre() + " (admin) está tomando una comanda.");
    }

    @Override
    public void cobrarPedido() {
        System.out.println(this.getNombre() + " (admin) está cobrando un pedido.");
    }

    @Override
    public void administrarSistema() {
        System.out.println(this.getNombre() + " está administrando el sistema.");
    }

    @Override
    public String toString() {
        return "------ ADMIN ------\n" + super.toString() + "\n=====================================";
    }
}
