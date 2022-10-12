
package MODELO_MVC;
import CONTROLADOR_MVC.Conexion_DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CLIENTE_CRUD {
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
          public int guardarcli(String nombre_apellido,int Dni, int telefono,String correo) throws SQLException{
        int resultado2 = 0;
        Connection conexion = null;
        
        String sentencia_guardarcli = ("INSERT INTO agregar_cliente (nombre_apellido,Dni,telefono,correo) VALUES (?,?,?,?)");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_guardarcli);
            sentencia_preparada.setString(1,nombre_apellido);
            sentencia_preparada.setInt(2,Dni);
            sentencia_preparada.setInt(3,telefono);
            sentencia_preparada.setString(4,correo);
            
            resultado2 = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado2;
        
        
    }
    
     
     public int updatecli(String nombre_apellido,int Dni, int telefono,String correo) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_updatecli = ("UPDATE agregar_cliente SET nombre_apellido=?,Dni=?,telefono=?,correo=? where Dni=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_updatecli);
            sentencia_preparada.setString(1,nombre_apellido);
            sentencia_preparada.setInt(2,Dni);
            sentencia_preparada.setInt(3,telefono);
            sentencia_preparada.setString(4,correo);
            sentencia_preparada.setInt(5,Dni);
            
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado;
        
        
    }
     
     public int eliminarcli(String nombre_apellido,int Dni, int telefono,String correo) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_eliminarcli = ("DELETE FROM agregar_cliente WHERE nombre_apellido=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_eliminarcli);
            sentencia_preparada.setString(1,nombre_apellido);
          
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado;
        
        
    }
     
        
    
    
    
}
