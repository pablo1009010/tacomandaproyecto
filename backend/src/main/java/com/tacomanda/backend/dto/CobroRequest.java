package com.tacomanda.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class CobroRequest {

    @NotBlank(message = "Falta el tipo de pago")
    private String tipoPago; 

    @NotBlank(message = "Falta el tipo de pedido")
    private String tipoPedido; 

    @NotNull(message = "Falta el monto recibido")
    @PositiveOrZero
    private BigDecimal montoRecibido;

    public String getTipoPago() { return tipoPago; }
    public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }

    public String getTipoPedido() { return tipoPedido; }
    public void setTipoPedido(String tipoPedido) { this.tipoPedido = tipoPedido; }

    public BigDecimal getMontoRecibido() { return montoRecibido; }
    public void setMontoRecibido(BigDecimal montoRecibido) { this.montoRecibido = montoRecibido; }
}
