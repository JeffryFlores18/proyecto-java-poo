package Vistas_Tienda;

import java.sql.Connection;
import java.sql.DriverManager;

public class ProveedoresConexion {

    public static Connection conectar() {

        Connection conexion = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alanastore",
                    "root",
                    ""
            );

            System.out.println("CONEXION EXITOSA");

        } catch (Exception e) {

            System.out.println("ERROR DE CONEXION: " + e.getMessage());
        }

        return conexion;
    }

    public static void main(String[] args) {
        conectar();
    }
}
