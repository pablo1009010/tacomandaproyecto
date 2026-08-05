package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Retroalimentacion;

public class RetroalimentacionDAO {

    public RetroalimentacionDAO() {
        super();
    }

    public boolean registrar(Retroalimentacion retro) {
        boolean registrado = false;
        String sql = "INSERT INTO retroalimentacion_app (no_control, calificacion, comentario) VALUES (?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, retro.getNoControl());
            stm.setInt(2, retro.getCalificacion());
            stm.setString(3, retro.getComentario());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                registrado = true;
                System.out.println("¡Gracias por tu retroalimentación!");
            }
        } catch (SQLException err) {
            System.out.println("Error al registrar la retroalimentación: " + err.getMessage());
        }

        return registrado;
    }

    public ArrayList<Retroalimentacion> extraerTodas() {
        ArrayList<Retroalimentacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM retroalimentacion_app ORDER BY fecha_hora DESC";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Retroalimentacion retro = new Retroalimentacion();
                retro.setIdRetro(rs.getInt("id_retro"));
                retro.setNoControl(rs.getInt("no_control"));
                retro.setCalificacion(rs.getInt("calificacion"));
                retro.setComentario(rs.getString("comentario"));
                retro.setFecha(rs.getString("fecha_hora"));
                lista.add(retro);
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer la retroalimentación: " + err.getMessage());
        }

        return lista;
    }

    public double calcularPromedio() {
        double promedio = 0.0;
        String sql = "SELECT AVG(calificacion) AS promedio FROM retroalimentacion_app";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                promedio = rs.getDouble("promedio");
            }
        } catch (SQLException err) {
            System.out.println("Error al calcular el promedio de calificación: " + err.getMessage());
        }

        return promedio;
    }
}
