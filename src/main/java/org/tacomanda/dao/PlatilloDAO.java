package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Platillo;

public class PlatilloDAO {

    public PlatilloDAO() {
        super();
    }

    public boolean registrarPlatillo(Platillo platillo) {
        boolean registrado = false;
        String sql = "INSERT INTO platillos (nombre, categoria, descripcion, precio, activo) VALUES (?,?,?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setString(1, platillo.getNombre());
            stm.setString(2, platillo.getCategoria());
            stm.setString(3, platillo.getDescripcion());
            stm.setDouble(4, platillo.getPrecio());
            stm.setBoolean(5, platillo.isActivo());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                registrado = true;
                System.out.println("Platillo registrado correctamente en el menú.");
            }
        } catch (SQLException err) {
            System.out.println("Error al registrar platillo: " + err.getMessage());
        }

        return registrado;
    }

    public ArrayList<Platillo> extraerPlatillos() {
        ArrayList<Platillo> platillos = new ArrayList<>();
        String sql = "SELECT * FROM platillos WHERE activo = 1";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                platillos.add(this.mapearPlatillo(rs));
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer platillos: " + err.getMessage());
        }

        return platillos;
    }

    public Platillo buscarPorId(int idPlatillo) {
        Platillo platillo = null;
        String sql = "SELECT * FROM platillos WHERE id_platillo = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, idPlatillo);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                platillo = this.mapearPlatillo(rs);
            }
        } catch (SQLException err) {
            System.out.println("Error al buscar platillo: " + err.getMessage());
        }

        return platillo;
    }

    public boolean actualizarPlatillo(Platillo platillo) {
        boolean actualizado = false;
        String sql = "UPDATE platillos SET nombre = ?, categoria = ?, descripcion = ?, precio = ?, activo = ? WHERE id_platillo = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setString(1, platillo.getNombre());
            stm.setString(2, platillo.getCategoria());
            stm.setString(3, platillo.getDescripcion());
            stm.setDouble(4, platillo.getPrecio());
            stm.setBoolean(5, platillo.isActivo());
            stm.setInt(6, platillo.getIdPlatillo());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                actualizado = true;
                System.out.println("Platillo actualizado con éxito.");
            } else {
                System.out.println("No se encontró ningún platillo con ese id.");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar platillo: " + err.getMessage());
        }

        return actualizado;
    }

    public boolean darBajaPlatillo(int idPlatillo) {
        boolean eliminado = false;
        String sql = "UPDATE platillos SET activo = 0 WHERE id_platillo = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, idPlatillo);

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                eliminado = true;
                System.out.println("Platillo dado de baja (desactivado) correctamente.");
            } else {
                System.out.println("No se encontró ningún platillo con ese id.");
            }
        } catch (SQLException err) {
            System.out.println("Error al dar de baja el platillo: " + err.getMessage());
        }

        return eliminado;
    }

    private Platillo mapearPlatillo(ResultSet rs) throws SQLException {
        Platillo platillo = new Platillo();
        platillo.setIdPlatillo(rs.getInt("id_platillo"));
        platillo.setNombre(rs.getString("nombre"));
        platillo.setCategoria(rs.getString("categoria"));
        platillo.setDescripcion(rs.getString("descripcion"));
        platillo.setPrecio(rs.getDouble("precio"));
        platillo.setActivo(rs.getBoolean("activo"));
        return platillo;
    }
}
