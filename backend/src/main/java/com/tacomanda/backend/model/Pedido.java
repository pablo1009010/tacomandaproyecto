package com.tacomanda.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @OneToOne
    @JoinColumn(name = "id_comanda", nullable = false, unique = true)
    private Comanda comanda;

    @Column(nullable = false)
    private BigDecimal cambio = BigDecimal.ZERO;

    @Column(name = "tipo_pago", nullable = false, length = 20)
    private String tipoPago; 

    @Column(name = "tipo_pedido", nullable = false, length = 20)
    private String tipoPedido; 

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }

    public Comanda getComanda() { return comanda; }
    public void setComanda(Comanda comanda) { this.comanda = comanda; }

    public BigDecimal getCambio() { return cambio; }
    public void setCambio(BigDecimal cambio) { this.cambio = cambio; }

    public String getTipoPago() { return tipoPago; }
    public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }

    public String getTipoPedido() { return tipoPedido; }
    public void setTipoPedido(String tipoPedido) { this.tipoPedido = tipoPedido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
