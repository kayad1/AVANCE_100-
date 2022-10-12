
package MODELO_MVC;

import CONTROLADOR_MVC.Conexion_DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LOGIN_VENDEDOR {
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado;
    public static String sql;
    public static int resultado_numero = 0;
    
     public static String BuscarNombre(String usuario){
        
        String busqueda_nombre= null;
        Connection conexion = null;
        try {
            conexion = Conexion_DAO.conectar();
            String sentencia_buscar = ("SELECT nombre,apellido FROM usuarios WHERE usuario='"+usuario+"'");
            sentencia_preparada = conexion.prepareStatement(sentencia_buscar);
            resultado = sentencia_preparada.executeQuery();
            if(resultado.next()){
            String nombre = resultado.getString("nombre");
            String apellido = resultado.getString("apellido");
            busqueda_nombre = (nombre + " " + apellido);
        }
            conexion.close();
            
        } catch (Exception e){
            System.out.println(e);
            
        }
        return busqueda_nombre;
        
    }
    
    public static String buscarUsuarioRegistrado(String usuario,String contra ){
        String busqueda_usuario= null;
        Connection conexion = null;
        
        try {
            conexion = Conexion_DAO.conectar();
            String sentencia_buscar_usuario = ("SELECT nombre,usuario,contra FROM usuarios WHERE usuario ='"+usuario+"' && contra = '"+contra+"'");
            sentencia_preparada = conexion.prepareStatement(sentencia_buscar_usuario);
            resultado = sentencia_preparada.executeQuery();
            if(resultado.next()){
                busqueda_usuario = "usuario encontrado";
                
            }else {
                busqueda_usuario = "usuario no encontrado";
                
            }
            
            conexion.close();
        }catch (Exception e){
            System.out.println(e);
        }
        
        
        
        return busqueda_usuario;
              
    }
    
}
