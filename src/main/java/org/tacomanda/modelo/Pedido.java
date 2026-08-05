package org.tacomanda.modelo;

public class Pedido {
    private int idPedido;
    private int idComanda;
    private double cambio;
    private String tipoPago;   // efectivo, tarjeta, transferencia
    private String tipoPedido; // en_mesa, para_llevar

    public Pedido() {
        super();
    }

    public Pedido(int idComanda, double cambio, String tipoPago, String tipoPedido) {
        super();
        this.setIdComanda(idComanda);
        this.setCambio(cambio);
        this.setTipoPago(tipoPago);
        this.setTipoPedido(tipoPedido);
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(int idPedido) {
        if (idPedido >= 0) {
            this.idPedido = idPedido;
        } else {
            System.out.println("Error: Id de pedido inválido");
        }
    }

    public int getIdComanda() {
        return this.idComanda;
    }

    public void setIdComanda(int idComanda) {
        if (idComanda > 0) {
            this.idComanda = idComanda;
        } else {
            System.out.println("Error: Comanda inválida para el pedido");
        }
    }

    public double getCambio() {
        String cambioFormato = String.format(java.util.Locale.US, "%.2f", this.cambio);
        return Double.parseDouble(cambioFormato);
    }

    public void setCambio(double cambio) {
        if (cambio >= 0.0) {
            this.cambio = cambio;
        } else {
            System.out.println("Error: El cambio no puede ser negativo");
        }
    }

    public String getTipoPago() {
        return this.tipoPago != null ? this.tipoPago.toUpperCase() : "";
    }

    public void setTipoPago(String tipoPago) {
        if (tipoPago != null && !tipoPago.trim().isEmpty()) {
            this.tipoPago = tipoPago.trim();
        } else {
            System.out.println("Error: El tipo de pago no puede estar vacío");
        }
    }

    public String getTipoPedido() {
        return this.tipoPedido != null ? this.tipoPedido.toUpperCase() : "";
    }

    public void setTipoPedido(String tipoPedido) {
        if (tipoPedido != null && !tipoPedido.trim().isEmpty()) {
            this.tipoPedido = tipoPedido.trim();
        } else {
            System.out.println("Error: El tipo de pedido no puede estar vacío");
        }
    }

    @Override
    public String toString() {
        return "Pedido #" + this.getIdPedido() +
                " | Comanda: " + this.getIdComanda() +
                " | Pago: " + this.getTipoPago() +
                " | Tipo: " + this.getTipoPedido() +
                " | Cambio: $" + this.getCambio();
    }
}
