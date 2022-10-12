
package MODELO_MVC;
import CONTROLADOR_MVC.Conexion_DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PRODUCTO_CRUD {
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
    public int guardarpro(String nombre_producto,int codigo, String descripcion,int stock,int precio) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_guardarpro = ("INSERT INTO agregar_producto (nombre_producto,codigo,descripcion,stock,precio) VALUES (?,?,?,?,?)");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_guardarpro);
            sentencia_preparada.setString(1,nombre_producto);
            sentencia_preparada.setInt(2,codigo);
            sentencia_preparada.setString(3,descripcion);
            sentencia_preparada.setInt(4,stock);
            sentencia_preparada.setInt(5,precio);
            
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado;
        
        
    }
         public int eliminarpro(String nombre_producto,int codigo, String descripcion,int stock,int precio) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_eliminarpro = ("DELETE FROM agregar_producto WHERE nombre_producto=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_eliminarpro);
            sentencia_preparada.setString(1,nombre_producto);
          
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado;
        
        
    }


   public int updatepro(String nombre_producto,int codigo, String descripcion,int stock,int precio) throws SQLException{
        int resultado5 = 0;
        Connection conexion = null;
        
        String sentencia_updatepro = ("UPDATE agregar_producto SET nombre_producto=?,codigo=?,descripcion=?,stock=?,precio=? WHERE codigo=? ");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_updatepro);
            sentencia_preparada.setString(1,nombre_producto);
            sentencia_preparada.setInt(2,codigo);
            sentencia_preparada.setString(3,descripcion);
            sentencia_preparada.setInt(4,stock);
            sentencia_preparada.setInt(5,precio);
            sentencia_preparada.setInt(6,codigo);
            
            
            resultado5 = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (SQLException e){
            System.out.println(e);
        }    
        return resultado5;
    

  
  
   }
     
 }
    

