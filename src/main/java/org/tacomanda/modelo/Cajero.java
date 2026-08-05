package org.tacomanda.modelo;

public class Cajero extends Empleado implements Cobrador {

    public Cajero() {
        super();
    }

    public Cajero(String nombre, String telefono, String contrasena) {
        super(nombre, telefono, contrasena);
    }

    @Override
    public String getTipoEmpleado() {
        return "cajero";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Permisos: cobrar pedidos y cerrar comandas.");
    }

    @Override
    public void cobrarPedido() {
        System.out.println(this.getNombre() + " está cobrando un pedido.");
    }

    @Override
    public String toString() {
        return "------ CAJERO ------\n" + super.toString() + "\n=====================================";
    }
}
