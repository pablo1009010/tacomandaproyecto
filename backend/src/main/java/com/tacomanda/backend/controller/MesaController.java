package com.tacomanda.backend.controller;

import com.tacomanda.backend.model.Mesa;
import com.tacomanda.backend.repository.MesaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaRepository repositorio;

    public MesaController(MesaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Mesa> listar() {
        return repositorio.findAll().stream()
                .sorted(Comparator.comparing(Mesa::getNumero))
                .toList();
    }
}
