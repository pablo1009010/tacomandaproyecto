package com.tacomanda.backend.controller;

import com.tacomanda.backend.dto.CobroRequest;
import com.tacomanda.backend.dto.ComandaCreateRequest;
import com.tacomanda.backend.dto.ItemComandaRequest;
import com.tacomanda.backend.model.*;
import com.tacomanda.backend.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comandas")
public class ComandaController {

    private final ComandaRepository comandaRepo;
    private final MesaRepository mesaRepo;
    private final PlatilloRepository platilloRepo;
    private final PedidoRepository pedidoRepo;

    public ComandaController(ComandaRepository comandaRepo, MesaRepository mesaRepo,
                              PlatilloRepository platilloRepo, PedidoRepository pedidoRepo) {
        this.comandaRepo = comandaRepo;
        this.mesaRepo = mesaRepo;
        this.platilloRepo = platilloRepo;
        this.pedidoRepo = pedidoRepo;
    }

    
    @PostMapping
    @Transactional
    public ResponseEntity<?> crear(@Valid @RequestBody ComandaCreateRequest request) {

        Optional<Mesa> mesaOpt = mesaRepo.findByNumero(request.getMesaNumero());
        if (mesaOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No existe la mesa " + request.getMesaNumero()));
        }

        Comanda comanda = new Comanda();
        comanda.setMesa(mesaOpt.get());
        comanda.setNoControl(request.getNoControl());
        comanda.setEstado("abierta");

        for (ItemComandaRequest item : request.getItems()) {
            Optional<Platillo> platilloOpt = platilloRepo.findById(item.getIdPlatillo());
            if (platilloOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "No existe el platillo con id " + item.getIdPlatillo()));
            }
            Platillo platillo = platilloOpt.get();

            DetalleComanda detalle = new DetalleComanda();
            detalle.setComanda(comanda);
            detalle.setPlatillo(platillo);
            detalle.setCantidad(item.getCantidad());
            detalle.setNota(item.getNota());
            detalle.setSubtotal(platillo.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
            comanda.getDetalles().add(detalle);
        }

        Mesa mesa = mesaOpt.get();
        mesa.setEstado("ocupada");
        mesaRepo.save(mesa);

        Comanda guardada = comandaRepo.save(comanda);
        return ResponseEntity.ok(guardada);
    }

   
    @GetMapping
    public List<Comanda> listar(@RequestParam(required = false) String estado) {
        if (estado != null) {
            return comandaRepo.findByEstadoOrderByFechaAsc(estado);
        }
        return comandaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Integer id) {
        return comandaRepo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(Map.of("error", "No existe esa comanda")));
    }

    
    @PostMapping("/{id}/cobrar")
    @Transactional
    public ResponseEntity<?> cobrar(@PathVariable Integer id, @Valid @RequestBody CobroRequest request) {
        Optional<Comanda> comandaOpt = comandaRepo.findById(id);
        if (comandaOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No existe esa comanda"));
        }
        Comanda comanda = comandaOpt.get();

        if (!"abierta".equals(comanda.getEstado())) {
            return ResponseEntity.status(409).body(Map.of("error", "Esta comanda ya fue cobrada o cancelada"));
        }

        BigDecimal total = comanda.getDetalles().stream()
                .map(DetalleComanda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (request.getMontoRecibido().compareTo(total) < 0) {
            return ResponseEntity.status(400).body(Map.of("error", "El monto recibido es menor al total de la comanda ($" + total + ")"));
        }

        Pedido pedido = new Pedido();
        pedido.setComanda(comanda);
        pedido.setTipoPago(request.getTipoPago());
        pedido.setTipoPedido(request.getTipoPedido());
        pedido.setCambio(request.getMontoRecibido().subtract(total));

        comanda.setEstado("cerrada");
        Mesa mesa = comanda.getMesa();
        mesa.setEstado("libre");
        mesaRepo.save(mesa);
        comandaRepo.save(comanda);
        Pedido guardado = pedidoRepo.save(pedido);

        return ResponseEntity.ok(Map.of(
                "pedido", guardado,
                "total", total,
                "cambio", guardado.getCambio()
        ));
    }
}
