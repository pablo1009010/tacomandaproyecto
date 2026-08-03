package com.tacomanda.backend.config;

import com.tacomanda.backend.model.Administrador;
import com.tacomanda.backend.model.Cajero;
import com.tacomanda.backend.model.Mesero;
import com.tacomanda.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Se ejecuta una sola vez al arrancar el backend. Si la tabla empleados
 * está vacía, crea una cuenta real de cada subclase. El constructor de
 * PersonaTacomanda ya se encarga de hashear la contraseña con BCrypt.
 *
 *   no_control 1 -> admin  / contraseña admin123  / pin 1111
 *   no_control 2 -> mesero / contraseña mesero123 / pin 2222
 *   no_control 3 -> cajero / contraseña cajero123 / pin 3333
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataSeeder(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return; // ya hay empleados, no duplicar
        }

        usuarioRepository.save(new Administrador("Administrador", "5555550001", "1111", "admin123"));
        usuarioRepository.save(new Mesero("Mesero de prueba", "5555550002", "2222", "mesero123"));
        usuarioRepository.save(new Cajero("Cajero de prueba", "5555550003", "3333", "cajero123"));

        System.out.println(">>> Empleados de prueba creados:");
        System.out.println("    no_control 1 -> admin  / admin123");
        System.out.println("    no_control 2 -> mesero / mesero123");
        System.out.println("    no_control 3 -> cajero / cajero123");
    }
}
