package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Admin;
import org.tacomanda.modelo.Cajero;
import org.tacomanda.modelo.Empleado;
import org.tacomanda.modelo.Mesero;

public class EmpleadoDAO {

    public EmpleadoDAO() {
        super();
    }


    private Empleado construirEmpleado(String tipo) {
        switch (tipo) {
            case "mesero":
                return new Mesero();
            case "cajero":
                return new Cajero();
            case "admin":
                return new Admin();
            default:
                return null;
        }
    }

    public boolean registrarEmpleado(Empleado empleado) {
        boolean registrado = false;
        String sql = "INSERT INTO empleados (nombre, telefono, contrasena, tipo_empleado) VALUES (?,?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            String contrasenaHasheada = BCrypt.hashpw(empleado.getContrasena(), BCrypt.gensalt());

            stm.setString(1, empleado.getNombre());
            stm.setString(2, empleado.getTelefono());
            stm.setString(3, contrasenaHasheada);
            stm.setString(4, empleado.getTipoEmpleado());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                registrado = true;
                System.out.println("Empleado registrado correctamente en la Base de Datos.");
            }
        } catch (SQLException err) {
            System.out.println("Error al registrar empleado: " + err.getMessage());
        }

        return registrado;
    }

    public Empleado login(int noControl, String contrasena) {
        Empleado empleado = null;
        // Solo se busca por no_control: el hash de BCrypt cambia cada vez que se
        // genera (lleva una "sal" distinta), así que nunca se compara en el SQL.
        String sql = "SELECT * FROM empleados WHERE no_control = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, noControl);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String hashGuardado = rs.getString("contrasena");

                if (contrasena != null && BCrypt.checkpw(contrasena, hashGuardado)) {
                    empleado = this.construirEmpleado(rs.getString("tipo_empleado"));
                    if (empleado != null) {
                        empleado.setNoControl(rs.getInt("no_control"));
                        empleado.setNombre(rs.getString("nombre"));
                        empleado.setTelefono(rs.getString("telefono"));
                        empleado.setContrasena(hashGuardado);
                    }
                }
            }
        } catch (SQLException err) {
            System.out.println("Error al iniciar sesión: " + err.getMessage());
        }

        return empleado;
    }

    public ArrayList<Empleado> extraerEmpleados() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Empleado empleado = this.construirEmpleado(rs.getString("tipo_empleado"));
                if (empleado != null) {
                    empleado.setNoControl(rs.getInt("no_control"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setTelefono(rs.getString("telefono"));
                    empleado.setContrasena(rs.getString("contrasena"));
                    empleados.add(empleado);
                }
            }
        } catch (SQLException err) {
            System.out.println("Error al extraer empleados: " + err.getMessage());
        }

        return empleados;
    }

    public boolean actualizarEmpleado(Empleado empleado) {
        boolean actualizado = false;
        String sql = "UPDATE empleados SET nombre = ?, telefono = ?, contrasena = ? WHERE no_control = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            String contrasenaHasheada = BCrypt.hashpw(empleado.getContrasena(), BCrypt.gensalt());

            stm.setString(1, empleado.getNombre());
            stm.setString(2, empleado.getTelefono());
            stm.setString(3, contrasenaHasheada);
            stm.setInt(4, empleado.getNoControl());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                actualizado = true;
                System.out.println("Empleado actualizado con éxito.");
            } else {
                System.out.println("No se encontró ningún empleado con ese número de control.");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar empleado: " + err.getMessage());
        }

        return actualizado;
    }

    public boolean existenEmpleados() {
        boolean existen = false;
        String sql = "SELECT COUNT(*) AS total FROM empleados";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                existen = rs.getInt("total") > 0;
            }
        } catch (SQLException err) {
            System.out.println("Error al consultar empleados: " + err.getMessage());
        }

        return existen;
    }

    public boolean darBajaEmpleado(int noControl) {
        boolean eliminado = false;
        String sql = "DELETE FROM empleados WHERE no_control = ?";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, noControl);

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                eliminado = true;
                System.out.println("Empleado dado de baja correctamente.");
            } else {
                System.out.println("No se encontró ningún empleado con ese número de control.");
            }
        } catch (SQLException err) {
            System.out.println("Error al dar de baja al empleado: " + err.getMessage());
        }

        return eliminado;
    }
}
