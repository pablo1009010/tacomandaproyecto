package com.tacomanda.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemComandaRequest {

    @NotNull(message = "Falta el id del platillo")
    private Integer idPlatillo;

    @NotNull(message = "Falta la cantidad")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private String nota;

    public Integer getIdPlatillo() { return idPlatillo; }
    public void setIdPlatillo(Integer idPlatillo) { this.idPlatillo = idPlatillo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
}
