package CONTROLADOR_MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_DAO {

    public static String url = "jdbc:mysql://localhost/login_bd";
    public static String usuario = "root";
    public static String pass = "12345";
    public static String clase = "com.mysql.jdbc.Driver";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName(clase);
            conexion = DriverManager.getConnection(url, usuario, pass);
            System.out.println("conexion establecida");
        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(e);
        }
        return conexion;
    }
}
