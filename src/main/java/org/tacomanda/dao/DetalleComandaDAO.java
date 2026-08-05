package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.DetalleComanda;

public class DetalleComandaDAO {

    public DetalleComandaDAO() {
        super();
    }

    public boolean agregarDetalle(DetalleComanda detalle) {
        boolean agregado = false;
        String sql = "INSERT INTO detalle_comanda (id_comanda, id_platillo, cantidad, nota, subtotal) VALUES (?,?,?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, detalle.getIdComanda());
            stm.setInt(2, detalle.getIdPlatillo());
            stm.setInt(3, detalle.getCantidad());
            stm.setString(4, detalle.getNota());
            stm.setDouble(5, detalle.getSubtotal());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                agregado = true;
            }
        } catch (SQLException err) {
            System.out.println("Error al agregar el platillo a la comanda: " + err.getMessage());
        }

        return agregado;
    }

    public ArrayList<DetalleComanda> extraerPorComanda(int idComanda) {
        ArrayList<DetalleComanda> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_comanda WHERE id_comanda = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, idComanda);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                DetalleComanda detalle = new DetalleComanda();
                detalle.setIdDetalle(rs.getInt("id_detalle"));
                detalle.setIdComanda(rs.getInt("id_comanda"));
                detalle.setIdPlatillo(rs.getInt("id_platillo"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setNota(rs.getString("nota"));
                detalle.setSubtotal(rs.getDouble("subtotal"));
                detalles.add(detalle);
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer el detalle de la comanda: " + err.getMessage());
        }

        return detalles;
    }

    public double calcularTotalComanda(int idComanda) {
        double total = 0.0;
        String sql = "SELECT SUM(subtotal) AS total FROM detalle_comanda WHERE id_comanda = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, idComanda);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException err) {
            System.out.println("Error al calcular el total de la comanda: " + err.getMessage());
        }

        return total;
    }
}
