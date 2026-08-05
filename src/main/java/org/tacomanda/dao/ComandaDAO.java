package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Comanda;

public class ComandaDAO {

    public ComandaDAO() {
        super();
    }


    public int crearComanda(Comanda comanda) {
        int idGenerado = -1;
        String sql = "INSERT INTO comandas (mesa_id, no_control, estado) VALUES (?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stm.setInt(1, comanda.getMesaId());
            stm.setInt(2, comanda.getNoControl());
            stm.setString(3, comanda.getEstado().toLowerCase());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet keys = stm.getGeneratedKeys();
                if (keys.next()) {
                    idGenerado = keys.getInt(1);
                }
            }
        } catch (SQLException err) {
            System.out.println("Error al crear la comanda: " + err.getMessage());
        }

        return idGenerado;
    }

    public ArrayList<Comanda> extraerComandasPorEstado(String estado) {
        ArrayList<Comanda> comandas = new ArrayList<>();
        String sql = "SELECT * FROM comandas WHERE estado = ? ORDER BY fecha";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setString(1, estado);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                comandas.add(this.mapearComanda(rs));
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer comandas: " + err.getMessage());
        }

        return comandas;
    }

    public ArrayList<Comanda> extraerTodas() {
        ArrayList<Comanda> comandas = new ArrayList<>();
        String sql = "SELECT * FROM comandas ORDER BY fecha DESC";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                comandas.add(this.mapearComanda(rs));
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer comandas: " + err.getMessage());
        }

        return comandas;
    }

    public boolean actualizarEstado(int idComanda, String estado) {
        boolean actualizado = false;
        String sql = "UPDATE comandas SET estado = ? WHERE id_comanda = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setString(1, estado);
            stm.setInt(2, idComanda);

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                actualizado = true;
                System.out.println("Estado de la comanda actualizado a: " + estado.toUpperCase());
            } else {
                System.out.println("No se encontró ninguna comanda con ese id.");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el estado de la comanda: " + err.getMessage());
        }

        return actualizado;
    }

    private Comanda mapearComanda(ResultSet rs) throws SQLException {
        Comanda comanda = new Comanda();
        comanda.setIdComanda(rs.getInt("id_comanda"));
        comanda.setMesaId(rs.getInt("mesa_id"));
        comanda.setNoControl(rs.getInt("no_control"));
        comanda.setFecha(rs.getString("fecha"));
        comanda.setEstado(rs.getString("estado"));
        return comanda;
    }
}
