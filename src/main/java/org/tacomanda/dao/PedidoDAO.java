package org.tacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.tacomanda.config.Conexion;
import org.tacomanda.modelo.Pedido;

public class PedidoDAO {

    public PedidoDAO() {
        super();
    }

    public boolean registrarPedido(Pedido pedido) {
        boolean registrado = false;
        String sql = "INSERT INTO pedidos (id_comanda, cambio, tipo_pago, tipo_pedido) VALUES (?,?,?,?)";

        try (
                Connection conexion = Conexion.conectar();
                PreparedStatement stm = conexion.prepareStatement(sql);
        ) {
            stm.setInt(1, pedido.getIdComanda());
            stm.setDouble(2, pedido.getCambio());
            stm.setString(3, pedido.getTipoPago());
            stm.setString(4, pedido.getTipoPedido());

            int filasAfectadas = stm.executeUpdate();
            if (filasAfectadas > 0) {
                registrado = true;
                System.out.println("Cobro registrado correctamente.");
            }
        } catch (SQLException err) {
            System.out.println("Error al registrar el pedido/cobro: " + err.getMessage());
        }

        return registrado;
    }
}
