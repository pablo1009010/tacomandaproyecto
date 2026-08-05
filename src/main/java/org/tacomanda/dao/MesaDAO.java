package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Mesa;

public class MesaDAO {

    public MesaDAO() {
        super();
    }

    public ArrayList<Mesa> extraerMesas() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM mesas ORDER BY numero";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setEstado(rs.getString("estado"));
                mesas.add(mesa);
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer mesas: " + err.getMessage());
        }

        return mesas;
    }

    public ArrayList<Mesa> extraerMesasLibres() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM mesas WHERE estado = 'libre' ORDER BY numero";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setEstado(rs.getString("estado"));
                mesas.add(mesa);
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer mesas libres: " + err.getMessage());
        }

        return mesas;
    }

    public Mesa buscarPorId(int idMesa) {
        Mesa mesa = null;
        String sql = "SELECT * FROM mesas WHERE id = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, idMesa);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                mesa = new Mesa();
                mesa.setId(rs.getInt("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setEstado(rs.getString("estado"));
            }
        } catch (SQLException err) {
            System.out.println("Error al buscar la mesa: " + err.getMessage());
        }

        return mesa;
    }

    public boolean actualizarEstado(int idMesa, String estado) {
        boolean actualizado = false;
        String sql = "UPDATE mesas SET estado = ? WHERE id = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setString(1, estado);
            stm.setInt(2, idMesa);

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                actualizado = true;
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el estado de la mesa: " + err.getMessage());
        }

        return actualizado;
    }
}
