
package MODELO_MVC;

import CONTROLADOR_MVC.Conexion_DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VENTA_CRUD {
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
     public int guardarventa(int CODIGO,String PRODUCTO,String DESCRIPCION,int CANTIDAD, int PRECIO,String NOMBREAPELLIDO,int DNIRUC,int NUMERO, String CORREO) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_guardarventa = ("INSERT INTO venta (CODIGO,PRODUCTO,DESCRIPCION,CANTIDAD,PRECIO,NOMBREAPELLIDO,DNIRUC,NUMERO,CORREO) VALUES (?,?,?,?,?,?,?,?,?)");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_guardarventa);
            sentencia_preparada.setInt(1,CODIGO);
            sentencia_preparada.setString(2,PRODUCTO);
            sentencia_preparada.setString(3,DESCRIPCION);
            sentencia_preparada.setInt(4,CANTIDAD);
            sentencia_preparada.setInt(5,PRECIO);
            sentencia_preparada.setString(6,NOMBREAPELLIDO);
            sentencia_preparada.setInt(7,DNIRUC);
            sentencia_preparada.setInt(8,NUMERO);
            sentencia_preparada.setString(9,CORREO);
            
            
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado;
        
    
     }
      
        public int eliminarventa( int CODIGO,String PRODUCTO,String DESCRIPCION,int CANTIDAD, int PRECIO,String NOMBREAPELLIDO,int DNIRUC,int NUMERO, String CORREO) throws SQLException{
        int resultado3 = 0;
        Connection conexion = null;
        
        String sentencia_eliminarventa = ("DELETE FROM VENTA WHERE DNIRUC=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_eliminarventa);
            sentencia_preparada.setInt(1,DNIRUC);
         
          
            resultado3 = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado3;
        
        
    }
     public int updateventa(int CANTIDAD, String PRODUCTO) throws SQLException{
        int resultado = 0;
        Connection conexion = null;
        
        String sentencia_updateventa = ("UPDATE venta SET CANTIDAD=? where PRODUCTO=?");
        
        try {
            conexion =  Conexion_DAO.conectar();
            sentencia_preparada = conexion.prepareStatement(sentencia_updateventa);
            sentencia_preparada.setInt(1,CANTIDAD);
            sentencia_preparada.setString(2,PRODUCTO);
            resultado = sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            conexion.close();
        }
        catch (Exception e){
            System.out.println(e);
        }    
        return resultado;
        
    
}
    
}
