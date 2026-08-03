package com.tacomanda.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Registro del cierre de caja: cuánto calculó el sistema, cuánto se
 * contó en físico, y una mini-encuesta de cómo estuvo el turno.
 */
@Entity
@Table(name = "encuestas_cierre")
public class EncuestaCierre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encuesta")
    private Integer idEncuesta;

    @Column(name = "no_control", nullable = false)
    private Integer noControl;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "total_sistema", nullable = false)
    private BigDecimal totalSistema;

    @Column(name = "total_contado", nullable = false)
    private BigDecimal totalContado;

    @Column(name = "diferencia", nullable = false)
    private BigDecimal diferencia;

    @Column(name = "satisfaccion_turno", nullable = false, length = 20)
    private String satisfaccionTurno; // muy_malo | malo | regular | bueno | muy_bueno

    @Column(name = "hubo_incidencias", nullable = false)
    private boolean huboIncidencias;

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;

    public Integer getIdEncuesta() { return idEncuesta; }
    public void setIdEncuesta(Integer idEncuesta) { this.idEncuesta = idEncuesta; }

    public Integer getNoControl() { return noControl; }
    public void setNoControl(Integer noControl) { this.noControl = noControl; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public BigDecimal getTotalSistema() { return totalSistema; }
    public void setTotalSistema(BigDecimal totalSistema) { this.totalSistema = totalSistema; }

    public BigDecimal getTotalContado() { return totalContado; }
    public void setTotalContado(BigDecimal totalContado) { this.totalContado = totalContado; }

    public BigDecimal getDiferencia() { return diferencia; }
    public void setDiferencia(BigDecimal diferencia) { this.diferencia = diferencia; }

    public String getSatisfaccionTurno() { return satisfaccionTurno; }
    public void setSatisfaccionTurno(String satisfaccionTurno) { this.satisfaccionTurno = satisfaccionTurno; }

    public boolean isHuboIncidencias() { return huboIncidencias; }
    public void setHuboIncidencias(boolean huboIncidencias) { this.huboIncidencias = huboIncidencias; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
