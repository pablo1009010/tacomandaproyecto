package com.tacomanda.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 20)
    private String estado = "libre"; 

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
