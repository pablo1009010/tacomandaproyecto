package org.tacomanda.modelo;

public class Comanda {
    private int idComanda;
    private int mesaId;
    private int noControl;
    private String fecha;
    private String estado; // abierta, en_preparacion, lista, cerrada, cancelada

    public Comanda() {
        super();
    }

    public Comanda(int mesaId, int noControl) {
        super();
        this.setMesaId(mesaId);
        this.setNoControl(noControl);
        this.setEstado("abierta");
    }

    public int getIdComanda() {
        return this.idComanda;
    }

    public void setIdComanda(int idComanda) {
        if (idComanda >= 0) {
            this.idComanda = idComanda;
        } else {
            System.out.println("Error: Id de comanda inválido");
        }
    }

    public int getMesaId() {
        return this.mesaId;
    }

    public void setMesaId(int mesaId) {
        if (mesaId > 0) {
            this.mesaId = mesaId;
        } else {
            System.out.println("Error: Mesa inválida para la comanda");
        }
    }

    public int getNoControl() {
        return this.noControl;
    }

    public void setNoControl(int noControl) {
        if (noControl > 0) {
            this.noControl = noControl;
        } else {
            System.out.println("Error: Número de control de empleado inválido");
        }
    }

    public String getFecha() {
        return this.fecha != null ? this.fecha : "";
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return this.estado != null ? this.estado.toUpperCase() : "";
    }

    public void setEstado(String estado) {
        if (estado != null && (estado.equalsIgnoreCase("abierta")
                || estado.equalsIgnoreCase("en_preparacion")
                || estado.equalsIgnoreCase("lista")
                || estado.equalsIgnoreCase("cerrada")
                || estado.equalsIgnoreCase("cancelada"))) {
            this.estado = estado.toLowerCase();
        } else {
            System.out.println("Error: Estado de comanda inválido");
        }
    }

    @Override
    public String toString() {
        return "Comanda #" + this.getIdComanda() +
                " | Mesa: " + this.getMesaId() +
                " | Atendió (no. control): " + this.getNoControl() +
                " | Fecha: " + this.getFecha() +
                " | Estado: " + this.getEstado();
    }
}
