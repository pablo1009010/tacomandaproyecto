package com.tacomanda.backend.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name = "empleados")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_empleado", discriminatorType = DiscriminatorType.STRING)
public abstract class PersonaTacomanda implements Autenticable, Operable {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_control")
    private Integer noControl;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 4)
    private String pin; 

    @Column(length = 13)
    private String telefono;

    @Column(nullable = false, length = 255)
    private String contrasena; 

    protected PersonaTacomanda() {
        super();
    }

    protected PersonaTacomanda(String nombre, String telefono, String pin, String contrasena) {
        super();
        this.setNombre(nombre);
        this.setTelefono(telefono);
        this.setPin(pin);
        this.setContrasena(contrasena);
    }

    
    public abstract String mostrarRol();
    public abstract String getCodigoRol();

    public Integer getNoControl() {
        return this.noControl;
    }

    public void setNoControl(Integer noControl) {
        if (noControl == null || noControl >= 0) {
            this.noControl = noControl;
        } else {
            System.out.println("Error: El número de control no puede ser negativo");
        }
    }

    public String getNombre() {
        if (this.nombre == null || this.nombre.isBlank()) return "";
        String[] partes = this.nombre.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String parte : partes) {
            sb.append(Character.toUpperCase(parte.charAt(0))).append(parte.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            System.out.println("Error: El nombre no puede estar vacío");
        }
    }

    public String getPin() {
        return this.pin == null ? "" : this.pin;
    }

    public void setPin(String pin) {
        if (pin != null && pin.trim().length() == 4 && pin.trim().chars().allMatch(Character::isDigit)) {
            this.pin = pin.trim();
        } else {
            System.out.println("Error: El PIN debe tener exactamente 4 dígitos");
        }
    }

    public String getTelefono() {
        if (this.telefono == null || this.telefono.isBlank()) return "";
        // formato visual: xxx-xxx-xxxx (si trae 10 dígitos)
        String limpio = this.telefono.replaceAll("\\D", "");
        if (limpio.length() == 10) {
            return limpio.substring(0, 3) + "-" + limpio.substring(3, 6) + "-" + limpio.substring(6);
        }
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && telefono.replaceAll("\\D", "").length() >= 10) {
            this.telefono = telefono.trim();
        } else {
            System.out.println("Error: El teléfono debe tener al menos 10 dígitos");
        }
    }

    
    protected String getContrasena() {
        return this.contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena != null && contrasena.length() >= 4) {
            
            this.contrasena = contrasena.startsWith("$2") ? contrasena : ENCODER.encode(contrasena);
        } else {
            System.out.println("Error: La contraseña debe tener al menos 4 caracteres");
        }
    }

    
    protected boolean coincideConHash(String intento) {
        if (intento == null || this.contrasena == null) return false;
        return ENCODER.matches(intento, this.contrasena);
    }

    @Override
    public String toString() {
        return mostrarRol() + "\n" +
                "No. de control: " + getNoControl() + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Teléfono: " + getTelefono();
    }
}
