package org.tacomanda.modelo;

public class Retroalimentacion {
    private int idRetro;
    private int noControl;
    private int calificacion; // 1 a 5
    private String comentario;
    private String fecha;

    public Retroalimentacion() {
        super();
    }

    public Retroalimentacion(int noControl, int calificacion, String comentario) {
        super();
        this.setNoControl(noControl);
        this.setCalificacion(calificacion);
        this.setComentario(comentario);
    }

    public int getIdRetro() {
        return this.idRetro;
    }

    public void setIdRetro(int idRetro) {
        if (idRetro >= 0) {
            this.idRetro = idRetro;
        } else {
            System.out.println("Error: Id de retroalimentación inválido");
        }
    }

    public int getNoControl() {
        return this.noControl;
    }

    public void setNoControl(int noControl) {
        if (noControl > 0) {
            this.noControl = noControl;
        } else {
            System.out.println("Error: Número de control inválido");
        }
    }

    public int getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(int calificacion) {
        if (calificacion >= 1 && calificacion <= 5) {
            this.calificacion = calificacion;
        } else {
            System.out.println("Error: La calificación debe ser entre 1 y 5");
        }
    }

    public String getComentario() {
        return this.comentario != null ? this.comentario : "";
    }

    public void setComentario(String comentario) {
        this.comentario = comentario != null ? comentario.trim() : "";
    }

    public String getFecha() {
        return this.fecha != null ? this.fecha : "";
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "No. Control: " + this.getNoControl() +
                " | Calificación: " + this.getCalificacion() + "/5" +
                " | Fecha: " + this.getFecha() + "\n" +
                "Comentario: " + this.getComentario() + "\n" +
                "=====================================";
    }
}
