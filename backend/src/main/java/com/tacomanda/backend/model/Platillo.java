package com.tacomanda.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "platillos")
public class Platillo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_platillo")
    private Integer idPlatillo;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 25)
    private String categoria;

    @Column(length = 100)
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(length = 8)
    private String emoji = "🌮";

    @Column(nullable = false)
    private boolean activo = true;

    public Integer getIdPlatillo() { return idPlatillo; }
    public void setIdPlatillo(Integer idPlatillo) { this.idPlatillo = idPlatillo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
