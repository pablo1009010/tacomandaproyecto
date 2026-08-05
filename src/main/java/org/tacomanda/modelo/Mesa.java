package org.tacomanda.modelo;

public class Mesa {
    private int id;
    private int numero;
    private String estado; // libre, ocupada, reservada

    public Mesa() {
        super();
    }

    public Mesa(int numero, String estado) {
        super();
        this.setNumero(numero);
        this.setEstado(estado);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            System.out.println("Error: Id de mesa inválido");
        }
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        if (numero > 0) {
            this.numero = numero;
        } else {
            System.out.println("Error: El número de mesa debe ser mayor a 0");
        }
    }

    public String getEstado() {
        return this.estado != null ? this.estado.toUpperCase() : "";
    }

    public void setEstado(String estado) {
        if (estado != null && (estado.equalsIgnoreCase("libre")
                || estado.equalsIgnoreCase("ocupada")
                || estado.equalsIgnoreCase("reservada"))) {
            this.estado = estado.toLowerCase();
        } else {
            System.out.println("Error: Estado de mesa inválido (libre, ocupada, reservada)");
        }
    }

    @Override
    public String toString() {
        return "Mesa #" + this.getNumero() + " | Estado: " + this.getEstado();
    }
}
