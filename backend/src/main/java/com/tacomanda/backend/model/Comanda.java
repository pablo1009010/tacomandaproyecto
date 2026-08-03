package com.tacomanda.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comandas")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private Integer idComanda;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @Column(name = "no_control", nullable = false)
    private Integer noControl; // empleado (mesero) que abrió la comanda

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String estado = "abierta"; // abierta | cerrada | cancelada

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleComanda> detalles = new ArrayList<>();

    public Integer getIdComanda() { return idComanda; }
    public void setIdComanda(Integer idComanda) { this.idComanda = idComanda; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public Integer getNoControl() { return noControl; }
    public void setNoControl(Integer noControl) { this.noControl = noControl; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<DetalleComanda> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleComanda> detalles) { this.detalles = detalles; }
}
