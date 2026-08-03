package com.tacomanda.backend.controller;

import com.tacomanda.backend.dto.CierreCajaRequest;
import com.tacomanda.backend.model.EncuestaCierre;
import com.tacomanda.backend.repository.EncuestaCierreRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cierre-caja")
public class CierreCajaController {

    private static final List<String> OPCIONES_VALIDAS =
            List.of("muy_malo", "malo", "regular", "bueno", "muy_bueno");

    private final EncuestaCierreRepository repositorio;

    public CierreCajaController(EncuestaCierreRepository repositorio) {
        this.repositorio = repositorio;
    }

    @PostMapping
    public ResponseEntity<?> registrarCierre(@Valid @RequestBody CierreCajaRequest request) {

        if (!OPCIONES_VALIDAS.contains(request.getSatisfaccionTurno())) {
            return ResponseEntity.status(400).body(Map.of(
                    "error", "satisfaccionTurno debe ser una de: " + OPCIONES_VALIDAS));
        }

        EncuestaCierre encuesta = new EncuestaCierre();
        encuesta.setNoControl(request.getNoControl());
        encuesta.setTotalSistema(request.getTotalSistema());
        encuesta.setTotalContado(request.getTotalContado());
        // La diferencia se calcula en el backend, nunca se confía en lo que mande el frontend.
        encuesta.setDiferencia(request.getTotalContado().subtract(request.getTotalSistema()));
        encuesta.setSatisfaccionTurno(request.getSatisfaccionTurno());
        encuesta.setHuboIncidencias(request.isHuboIncidencias());
        encuesta.setComentarios(request.getComentarios());

        EncuestaCierre guardada = repositorio.save(encuesta);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping
    public ResponseEntity<List<EncuestaCierre>> historial() {
        return ResponseEntity.ok(repositorio.findAllByOrderByFechaHoraDesc());
    }
}
