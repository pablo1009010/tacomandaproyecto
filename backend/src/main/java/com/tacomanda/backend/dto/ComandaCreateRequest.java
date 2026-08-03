package com.tacomanda.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ComandaCreateRequest {

    @NotNull(message = "Falta el número de mesa")
    private Integer mesaNumero;

    @NotNull(message = "Falta el número de control del mesero")
    private Integer noControl;

    @NotEmpty(message = "La comanda no puede ir vacía")
    @Valid
    private List<ItemComandaRequest> items;

    public Integer getMesaNumero() { return mesaNumero; }
    public void setMesaNumero(Integer mesaNumero) { this.mesaNumero = mesaNumero; }

    public Integer getNoControl() { return noControl; }
    public void setNoControl(Integer noControl) { this.noControl = noControl; }

    public List<ItemComandaRequest> getItems() { return items; }
    public void setItems(List<ItemComandaRequest> items) { this.items = items; }
}
