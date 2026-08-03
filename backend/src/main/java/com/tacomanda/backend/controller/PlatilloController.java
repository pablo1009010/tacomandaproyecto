package com.tacomanda.backend.controller;

import com.tacomanda.backend.dto.PlatilloRequest;
import com.tacomanda.backend.model.Platillo;
import com.tacomanda.backend.repository.PlatilloRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/platillos")
public class PlatilloController {

    private final PlatilloRepository repositorio;

    public PlatilloController(PlatilloRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Platillo> listar() {
        return repositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PlatilloRequest request) {
        Platillo p = new Platillo();
        copiarDatos(request, p);
        return ResponseEntity.ok(repositorio.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody PlatilloRequest request) {
        Optional<Platillo> existente = repositorio.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No existe un platillo con ese id"));
        }
        Platillo p = existente.get();
        copiarDatos(request, p);
        return ResponseEntity.ok(repositorio.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (!repositorio.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "No existe un platillo con ese id"));
        }
        try {
            repositorio.deleteById(id);
            return ResponseEntity.ok(Map.of("eliminado", true));
        } catch (Exception e) {
            // Si el platillo ya está referenciado en detalle_comanda, MySQL rechaza el borrado (FK).
            return ResponseEntity.status(409).body(Map.of(
                    "error", "No se puede eliminar: este platillo ya tiene comandas asociadas. Desactívalo en vez de borrarlo."));
        }
    }

    private void copiarDatos(PlatilloRequest request, Platillo p) {
        p.setNombre(request.getNombre());
        p.setCategoria(request.getCategoria());
        p.setDescripcion(request.getDescripcion());
        p.setPrecio(request.getPrecio());
        p.setEmoji(request.getEmoji() != null && !request.getEmoji().isBlank() ? request.getEmoji() : "🌮");
        p.setActivo(request.isActivo());
    }
}
