package org.tacomanda.modelo;

public class DetalleComanda {
    private int idDetalle;
    private int idComanda;
    private int idPlatillo;
    private int cantidad;
    private String nota;
    private double subtotal;

    public DetalleComanda() {
        super();
    }

    public DetalleComanda(int idComanda, int idPlatillo, int cantidad, String nota, double subtotal) {
        super();
        this.setIdComanda(idComanda);
        this.setIdPlatillo(idPlatillo);
        this.setCantidad(cantidad);
        this.setNota(nota);
        this.setSubtotal(subtotal);
    }

    public int getIdDetalle() {
        return this.idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        if (idDetalle >= 0) {
            this.idDetalle = idDetalle;
        } else {
            System.out.println("Error: Id de detalle inválido");
        }
    }

    public int getIdComanda() {
        return this.idComanda;
    }

    public void setIdComanda(int idComanda) {
        if (idComanda > 0) {
            this.idComanda = idComanda;
        } else {
            System.out.println("Error: Comanda inválida para el detalle");
        }
    }

    public int getIdPlatillo() {
        return this.idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        if (idPlatillo > 0) {
            this.idPlatillo = idPlatillo;
        } else {
            System.out.println("Error: Platillo inválido para el detalle");
        }
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        } else {
            System.out.println("Error: La cantidad debe ser mayor a 0");
        }
    }

    public String getNota() {
        return this.nota != null ? this.nota : "";
    }

    public void setNota(String nota) {
        this.nota = (nota != null) ? nota.trim() : "";
    }

    public double getSubtotal() {
        String subtotalFormato = String.format(java.util.Locale.US, "%.2f", this.subtotal);
        return Double.parseDouble(subtotalFormato);
    }

    public void setSubtotal(double subtotal) {
        if (subtotal >= 0.0) {
            this.subtotal = subtotal;
        } else {
            System.out.println("Error: El subtotal no puede ser negativo");
        }
    }

    @Override
    public String toString() {
        return "  - Platillo #" + this.getIdPlatillo() +
                " | Cantidad: " + this.getCantidad() +
                " | Nota: " + this.getNota() +
                " | Subtotal: $" + this.getSubtotal();
    }
}
