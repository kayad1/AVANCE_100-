
package MODELO_MVC;

import CONTROLADOR_MVC.Conexion_DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class REGISTRO_VENDEDOR {
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
     public int guardar(String nombre,String apellido, String cargo,String usuario,String contra) throws SQLException{
        int resultado1 = 0;
        Connection conexion = null;
        
        String sentencia_guardar = ("INSERT INTO usuarios (nombre,apellido,cargo,usuario,contra) VALUES (?,?,?,?,?)");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setString(1,nombre);
            sentencia_preparada.setString(2,apellido);
            sentencia_preparada.setString(3,cargo);
            sentencia_preparada.setString(4,usuario);
            sentencia_preparada.setString(5,contra);
            resultado1 = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado1;
        
        
    }
    
    
}
