package com.tacomanda.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CierreCajaRequest {

    @NotNull(message = "Falta el número de control de quien cierra la caja")
    private Integer noControl;

    @NotNull(message = "Falta el total que calculó el sistema")
    private BigDecimal totalSistema;

    @NotNull(message = "Falta el total contado en físico")
    private BigDecimal totalContado;

    @NotBlank(message = "Falta indicar cómo estuvo el turno")
    private String satisfaccionTurno;

    private boolean huboIncidencias;

    private String comentarios;

    public Integer getNoControl() { return noControl; }
    public void setNoControl(Integer noControl) { this.noControl = noControl; }

    public BigDecimal getTotalSistema() { return totalSistema; }
    public void setTotalSistema(BigDecimal totalSistema) { this.totalSistema = totalSistema; }

    public BigDecimal getTotalContado() { return totalContado; }
    public void setTotalContado(BigDecimal totalContado) { this.totalContado = totalContado; }

    public String getSatisfaccionTurno() { return satisfaccionTurno; }
    public void setSatisfaccionTurno(String satisfaccionTurno) { this.satisfaccionTurno = satisfaccionTurno; }

    public boolean isHuboIncidencias() { return huboIncidencias; }
    public void setHuboIncidencias(boolean huboIncidencias) { this.huboIncidencias = huboIncidencias; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
