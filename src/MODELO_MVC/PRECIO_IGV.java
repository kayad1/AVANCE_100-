
package MODELO_MVC;

import CONTROLADOR_MVC.Conexion_DAO;
import static MODELO_MVC.VENTA_CRUD.sentencia_preparada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PRECIO_IGV {
    
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
     public int guardarigv(int dni,String ps_igv, String igv,String precio_final) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_guardarigv = ("INSERT INTO igv (dni,ps_igv,igv,precio_final) VALUES (?,?,?,?)");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_guardarigv);
            sentencia_preparada.setInt(1,dni);
            sentencia_preparada.setString(2,ps_igv);
            sentencia_preparada.setString(3,igv);
            sentencia_preparada.setString(4,precio_final);
   
            
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado;
        
    
     }
     
     public int eliminarigv(int dni,String ps_igv, String igv,String precio_final) throws SQLException{
        int resultado3 = 0;
        Connection conexion = null;
        
        String sentencia_eliminarventa = ("DELETE FROM igv WHERE dni=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_eliminarventa);
            sentencia_preparada.setInt(1,dni);
         
          
            resultado3 = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado3;
     
}
}