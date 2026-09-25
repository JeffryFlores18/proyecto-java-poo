
package Vistas_Tienda;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexionLiv {
    
    private static final String url = "jdbc:mysql://localhost/alanastore";
    private static final String user = "root";
    private static final String pass = "";
    
    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user, pass);

            System.out.println("Conexion exitosa a ALANA STORE");

        } catch (Exception e) {

            System.out.println(
                    "Error de conexion: " + e.getMessage()
            );
        }

        return con;
    }    
    public static void main(String[] args) {
      conectar();
    }
    
}

