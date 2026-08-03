package com.tacomanda.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_comanda")
public class DetalleComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_comanda", nullable = false)
    @JsonIgnore // evita el ciclo infinito Comanda -> detalles -> comanda -> ...
    private Comanda comanda;

    @ManyToOne
    @JoinColumn(name = "id_platillo", nullable = false)
    private Platillo platillo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(length = 100)
    private String nota;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public Integer getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Integer idDetalle) { this.idDetalle = idDetalle; }

    public Comanda getComanda() { return comanda; }
    public void setComanda(Comanda comanda) { this.comanda = comanda; }

    public Platillo getPlatillo() { return platillo; }
    public void setPlatillo(Platillo platillo) { this.platillo = platillo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
