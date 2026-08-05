package org.tacomanda.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/tacomanda_2?useSSL=false&serverTimezone=America/Mexico_City";
    private static final String USER = "root";
    private static final String PASSWORD = "ggxd12.com";

    public Conexion() {
        super();
    }

    public static Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException err) {
            System.out.println("Error al conectar con MySQL: " + err.getMessage());
        }

        return conexion;
    }
}
