package CONTROLADOR_MVC;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class BUSQUEDA_VENTA {

     
    Connection cn;
    
    public void cargartablaventa(JTable tabla, String cadena){
        DefaultTableModel modelo;
        String [] titulo = {"IDVENTA","CODIGO","PRODUCTO","DESCRIPCION","CANTIDAD","PRECIO","NOMBREAPELLIDO","DNI/RUC","NUMERO","CORREO"};
        modelo = new DefaultTableModel(null,titulo);
        
        String [] registros = new String[10];
        String sql = "SELECT * FROM venta WHERE CONCAT(IDVENTA,' ',CODIGO,' ',PRODUCTO,' ',DESCRIPCION,' ',CANTIDAD,' ',PRECIO,' ',NOMBREAPELLIDO,' ',NOMBREAPELLIDO,' ',DNIRUC,' ',CORREO,' ') LIKE '%"+cadena+"%'";
        Conexion_DAO con = new Conexion_DAO();
        cn= con.conectar();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                for(int i=0; i<10; i++)
                    registros[i]=rs.getString(i+1);
                modelo.addRow(registros);
                
            }
            tabla.setModel(modelo);
            
        }  
     catch (SQLException ex){
         JOptionPane.showMessageDialog(null,"ERROR: "+ ex);
    
}
    
    }
    
    
}
