package org.tacomanda.modelo;

public class Platillo {
    private int idPlatillo;
    private String nombre;
    private String categoria;
    private String descripcion;
    private double precio;
    private boolean activo;

    public Platillo() {
        super();
    }

    public Platillo(String nombre, String categoria, String descripcion, double precio, boolean activo) {
        super();
        this.setNombre(nombre);
        this.setCategoria(categoria);
        this.setDescripcion(descripcion);
        this.setPrecio(precio);
        this.setActivo(activo);
    }

    public int getIdPlatillo() {
        return this.idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        if (idPlatillo >= 0) {
            this.idPlatillo = idPlatillo;
        } else {
            System.out.println("Error: Id de platillo inválido");
        }
    }

    public String getNombre() {
        return this.nombre != null ? this.nombre : "";
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        } else {
            System.out.println("Error: El nombre del platillo no puede estar vacío");
        }
    }

    public String getCategoria() {
        return this.categoria != null ? this.categoria.toUpperCase() : "";
    }

    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            this.categoria = categoria.trim();
        } else {
            System.out.println("Error: La categoría no puede estar vacía");
        }
    }

    public String getDescripcion() {
        return this.descripcion != null ? this.descripcion : "";
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion.trim() : "";
    }

    public double getPrecio() {
        String precioFormato = String.format(java.util.Locale.US, "%.2f", this.precio);
        return Double.parseDouble(precioFormato);
    }

    public void setPrecio(double precio) {
        if (precio >= 0.0) {
            this.precio = precio;
        } else {
            System.out.println("Error: El precio no puede ser negativo");
        }
    }

    public boolean isActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "ID: " + this.getIdPlatillo() + "\n" +
                this.getNombre() + " [" + this.getCategoria() + "]\n" +
                "Descripción: " + this.getDescripcion() + "\n" +
                "Precio: $" + this.getPrecio() + "\n" +
                "Disponible: " + (this.isActivo() ? "Sí" : "No") + "\n" +
                "=====================================";
    }
}
