package com.tacomanda.backend.controller;

import com.tacomanda.backend.dto.LoginRequest;
import com.tacomanda.backend.dto.LoginResponse;
import com.tacomanda.backend.model.PersonaTacomanda;
import com.tacomanda.backend.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        // Manejo de errores: el número de control debe ser numérico
        // (viene como texto desde el formulario de Electron).
        Integer noControl;
        try {
            noControl = Integer.parseInt(request.getNoControl().trim());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body(Map.of("error", "El número de control debe ser numérico"));
        }

        Optional<PersonaTacomanda> encontrado = usuarioRepository.findById(noControl);

        if (encontrado.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Número de control o contraseña incorrectos"));
        }

        PersonaTacomanda persona = encontrado.get();

        // Polimorfismo real: cada subclase (Administrador/Cajero/Mesero) valida
        // su propia contraseña a través de la interfaz Autenticable.
        if (!persona.verificarPassword(request.getContrasena())) {
            return ResponseEntity.status(401).body(Map.of("error", "Número de control o contraseña incorrectos"));
        }

        // Otro punto polimórfico: permisos() responde distinto según la subclase real.
        String permisos = persona.permisos();

        return ResponseEntity.ok(new LoginResponse(persona, permisos));
    }
}
