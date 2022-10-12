/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista_MVC;

import CONTROLADOR_MVC.Conexion_DAO;
import static java.awt.SystemColor.control;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import CONTROLADOR_MVC.BSUQUEDA_PRODUCTOS;
import java.sql.DriverManager;
import CONTROLADOR_MVC.BUSQUEDA_VENTA;
import MODELO_MVC.CLIENTE_CRUD;
import MODELO_MVC.PRODUCTO_CRUD;
import MODELO_MVC.VENTA_CRUD;
import MODELO_MVC.PRECIO_IGV;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



        



public class VISTA_FORMULARIO extends javax.swing.JFrame{
    
    
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
    
    
    String[] cabecera = {"CODIGO","DESCIPCION","CANTIDAD","PRECIO UNITARIO","PRECIO TOTAL"};
    String [][] cuerpo = {};
    DefaultTableModel modLista = new DefaultTableModel(cuerpo, cabecera);
    
    
    
     
    
            
   
    

    public VISTA_FORMULARIO() {
        
        initComponents();
        calcular();
       
        this.setLocationRelativeTo(null);
        cargarTablacli();
        cargarTablapro();
        cargartabla("");
        cargarTablaventa();
        limpiarprecio();
        limpiarta();
        
        
    }
    
    public void calcular() {
        float suma= 0 ;
       
        
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
        
            float renglon,renglon2;
            renglon = Float.parseFloat(TableVenta.getValueAt(i,5).toString());
            renglon2= Float.parseFloat(TableVenta.getValueAt(i,4).toString());
            
            suma = suma+ renglon*renglon2;
            
        }
        
        
        txt_precio_total.setText(String.valueOf(suma-(suma*0.18)));
        txt_igv.setText(String.valueOf(suma*0.18));
        txt_valortotal.setText(String.valueOf(suma));
    
        
    
}
      
    
   
    
    
    CLIENTE_CRUD cliente_crud = new CLIENTE_CRUD();
    PRODUCTO_CRUD producto_crud = new PRODUCTO_CRUD();
    VENTA_CRUD venta_crud = new VENTA_CRUD();
    PRECIO_IGV precio_igv = new PRECIO_IGV();
    
    
    private void limpiarcli(){
        txtnacli.setText("");
        txtdnicli.setText("");
        txtnumerocli.setText("");
        txtcorreocli.setText("");
    }
    private void limpiarpro(){
        txtnombrepro.setText("");
        txtcodigopro.setText("");
        txtdespro.setText("");
        txtstockpro.setText("");
        txtpreciopro.setText("");
    }
    private void limpiarventa(){
        
        txtcodigoVENTA.setText("");
        txtStock.setText("");
        txtproductoVENTA.setText("");
        txtdescripcion1.setText("");
        txtcantidad.setText("");
        txtprecio.setText("");
        
    }
    private void LimpiarClientesJalados(){
        txtclientedatos.setText("");
        txtdniruc.setText("");
        txtnumero.setText("");
        txtcorreo.setText(""); 
    }
    private void limpiarboleta(){
        txtproductoventa.setText("");
        txtcodigo10.setText("");
        txtdescripcion1.setText("");
        txtcantidad1.setText("");
        txtprecio1.setText("");
        txtclientedatos1.setText("");
        txtdni2.setText("");
        txtdni1.setText("");
        txtprecio3.setText("");
    }
    private void limpiarproductos(){
        txt_mandar_codigo.setText("");
        txt_mandar_nombre.setText("");
        txt_mandar_des.setText("");
        txt_mandar_precio.setText(""); 
        txt_mandar_stock.setText("");
            
    }           
       private void limpiarprecio(){
        txt_precio_total.setText("");
        txt_igv.setText("");
        txt_valortotal.setText("");
        
    }   
       
        private void limpiarta(){
        int filas=modLista.getRowCount();
        for (int i=0; 1<filas ; i++){
            modLista.removeRow(0);
        }        
        
    }
    
    
       
       
    
    void cargartabla(String cad){
        BSUQUEDA_PRODUCTOS c = new BSUQUEDA_PRODUCTOS();
        c.cargartabla(tablabuscar, cad);
    }
      void cargartablaventa(String cad){
        BUSQUEDA_VENTA c = new BUSQUEDA_VENTA();
        c.cargartablaventa(tablabuscarventa, cad);
    }
    
     private void cargarTablaventa(){
        
        calcular();
        DefaultTableModel modeloTablaventa = (DefaultTableModel) TableVenta.getModel();
        modeloTablaventa.setRowCount(0);
        
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        
        try{
            
            Connection con = Conexion_DAO.conectar();
            ps = con.prepareStatement("SELECT IDVENTA,CODIGO,PRODUCTO,DESCRIPCION,CANTIDAD,PRECIO,NOMBREAPELLIDO,DNIRUC,NUMERO,CORREO FROM VENTA");
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while (rs.next()){
                Object[] fila = new Object [columnas];
                for(int indice = 0; indice<columnas; indice++){
                    fila[indice]=rs.getObject(indice +1);
                }
                modeloTablaventa.addRow(fila);
            }
        } catch (SQLException e){
            
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
     
   
    
  
        
    private void cargarTablacli(){
        
        
        DefaultTableModel modeloTablacli = (DefaultTableModel) tablecliente.getModel();
        modeloTablacli.setRowCount(0);
        
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        
        try{
            
            Connection con = Conexion_DAO.conectar();
            ps = con.prepareStatement("SELECT nombre_apellido, Dni ,telefono ,correo FROM agregar_cliente");
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while (rs.next()){
                Object[] fila = new Object [columnas];
                for(int indice = 0; indice<columnas; indice++){
                    fila[indice]=rs.getObject(indice +1);
                }
                modeloTablacli.addRow(fila);
            }
        } catch (SQLException e){
            
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
       private void cargarTablapro(){
        
        
        DefaultTableModel modeloTablapro = (DefaultTableModel) tablapro.getModel();
        modeloTablapro.setRowCount(0);
        
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        int columnas;
        
        try{
            
            Connection con = Conexion_DAO.conectar();
            ps = con.prepareStatement("SELECT nombre_producto,codigo,descripcion,stock,precio FROM agregar_producto");
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            columnas = rsmd.getColumnCount();
            
            while (rs.next()){
                Object[] fila = new Object [columnas];
                for(int indice = 0; indice<columnas; indice++){
                    fila[indice]=rs.getObject(indice +1);
                }
                modeloTablapro.addRow(fila);
            }
        } catch (SQLException e){
            
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        
        
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGenerarVenta1 = new javax.swing.JButton();
        btnsalir3 = new javax.swing.JButton();
        txtsuma2 = new javax.swing.JLabel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel1 = new javax.swing.JPanel();
        contenedor = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        txtclientedatos = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        txtcorreo = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        btnEliminarventa = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btn_boleta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txt_igv = new javax.swing.JLabel();
        txtdescripcion1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnregresar10 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtdniruc = new javax.swing.JTextField();
        txtnumero = new javax.swing.JTextField();
        txtproductoVENTA = new javax.swing.JTextField();
        txtcodigoVENTA = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btn_actualizar_venta = new javax.swing.JButton();
        btnregresar11 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_precio_total = new javax.swing.JLabel();
        txt_valortotal = new javax.swing.JLabel();
        btn_nueva_venta = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        txtbuscar = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        btnregresar16 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablabuscar = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnEnviarCliente1 = new javax.swing.JButton();
        txt_mandar_codigo = new javax.swing.JLabel();
        txt_mandar_nombre = new javax.swing.JLabel();
        txt_mandar_des = new javax.swing.JLabel();
        txt_mandar_stock = new javax.swing.JLabel();
        txt_mandar_precio = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        txtnombrepro = new javax.swing.JTextField();
        txtpreciopro = new javax.swing.JTextField();
        txtdespro = new javax.swing.JTextField();
        txtstockpro = new javax.swing.JTextField();
        btnactualizarpro = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablapro = new javax.swing.JTable();
        txtcodigopro = new javax.swing.JTextField();
        btnguardarpro = new javax.swing.JButton();
        btnregresar18 = new javax.swing.JButton();
        btnlimpiarcli1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtnacli = new javax.swing.JTextField();
        txtdnicli = new javax.swing.JTextField();
        txtnumerocli = new javax.swing.JTextField();
        txtcorreocli = new javax.swing.JTextField();
        btnagregarcli = new javax.swing.JButton();
        btnlimpiarcli = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablecliente = new javax.swing.JTable();
        btnmodificarcli = new javax.swing.JButton();
        btneliminarcli = new javax.swing.JButton();
        btnEnviarCliente = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtcodigo10 = new javax.swing.JTextField();
        txtcantidad1 = new javax.swing.JTextField();
        txtprecio1 = new javax.swing.JTextField();
        btnGenerarVenta2 = new javax.swing.JButton();
        txtclientedatos1 = new javax.swing.JTextField();
        txtdni1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtdni2 = new javax.swing.JTextField();
        txtprecio3 = new javax.swing.JTextField();
        btnregresar = new javax.swing.JButton();
        btnregresar1 = new javax.swing.JButton();
        txtbuscarventa = new javax.swing.JTextField();
        btnbuscar1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablabuscarventa = new javax.swing.JTable();
        txtproductoventa = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        txt_total_pagar = new javax.swing.JTextField();
        txtprecio2 = new javax.swing.JTextField();
        txtprecio4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnsalir1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        btnGenerarVenta1.setBackground(new java.awt.Color(255, 255, 255));
        btnGenerarVenta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N

        btnsalir3.setBackground(new java.awt.Color(255, 102, 102));
        btnsalir3.setText("SALIR");

        txtsuma2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtsuma2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MODULO DE PEDIDOS \"LA NACIONAL\"");
        setBackground(new java.awt.Color(51, 255, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contenedor.setBackground(new java.awt.Color(0, 0, 0));
        contenedor.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtclientedatos.setEditable(false);
        txtclientedatos.setBackground(new java.awt.Color(255, 255, 255));
        txtclientedatos.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRES Y APELLIDOS"));

        txtcantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtcantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txtcorreo.setEditable(false);
        txtcorreo.setBackground(new java.awt.Color(255, 255, 255));
        txtcorreo.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCIÓN"));

        txtprecio.setEditable(false);
        txtprecio.setBackground(new java.awt.Color(255, 255, 255));
        txtprecio.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Stock Disponible");

        txtStock.setEditable(false);
        txtStock.setBackground(new java.awt.Color(204, 255, 204));
        txtStock.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnEliminarventa.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/36_104857 (1).png"))); // NOI18N
        btnEliminarventa.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnEliminarventa.setBorderPainted(false);
        btnEliminarventa.setMaximumSize(new java.awt.Dimension(300, 300));
        btnEliminarventa.setPreferredSize(new java.awt.Dimension(100, 100));
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(153, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("ELIMINAR VENTA");

        btn_boleta.setBackground(new java.awt.Color(255, 255, 255));
        btn_boleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        btn_boleta.setText("IMPRIMIR BOLETA");
        btn_boleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_boletaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setText("IGV:");

        txt_igv.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_igv.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtdescripcion1.setEditable(false);
        txtdescripcion1.setBackground(new java.awt.Color(255, 255, 255));
        txtdescripcion1.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCIÓN"));

        TableVenta.setBackground(new java.awt.Color(255, 255, 255));
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID VENTA", "CÓDIGO", "PRODUCTO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "NOMBRE Y APELLIDO", "DNI/RUC", "NUMERO", "DIRECCIÓN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setMinWidth(0);
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(0);
            TableVenta.getColumnModel().getColumn(0).setMaxWidth(0);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(15);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(200);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(400);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(10);
            TableVenta.getColumnModel().getColumn(5).setPreferredWidth(10);
            TableVenta.getColumnModel().getColumn(6).setMinWidth(0);
            TableVenta.getColumnModel().getColumn(6).setMaxWidth(0);
            TableVenta.getColumnModel().getColumn(7).setMinWidth(0);
            TableVenta.getColumnModel().getColumn(7).setPreferredWidth(0);
            TableVenta.getColumnModel().getColumn(7).setMaxWidth(0);
            TableVenta.getColumnModel().getColumn(8).setMinWidth(0);
            TableVenta.getColumnModel().getColumn(8).setPreferredWidth(0);
            TableVenta.getColumnModel().getColumn(8).setMaxWidth(0);
            TableVenta.getColumnModel().getColumn(9).setMinWidth(0);
            TableVenta.getColumnModel().getColumn(9).setPreferredWidth(0);
            TableVenta.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setText("AGREGAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnregresar10.setBackground(new java.awt.Color(153, 255, 153));
        btnregresar10.setText("AGREGAR CLIENTE");
        btnregresar10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar10ActionPerformed(evt);
            }
        });

        txtdniruc.setEditable(false);
        txtdniruc.setBackground(new java.awt.Color(255, 255, 255));
        txtdniruc.setBorder(javax.swing.BorderFactory.createTitledBorder("DNI / RUC"));

        txtnumero.setEditable(false);
        txtnumero.setBackground(new java.awt.Color(255, 255, 255));
        txtnumero.setBorder(javax.swing.BorderFactory.createTitledBorder("NÚMERO"));

        txtproductoVENTA.setEditable(false);
        txtproductoVENTA.setBackground(new java.awt.Color(255, 255, 255));
        txtproductoVENTA.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE DEL PRODUCTO"));

        txtcodigoVENTA.setEditable(false);
        txtcodigoVENTA.setBackground(new java.awt.Color(255, 255, 255));
        txtcodigoVENTA.setBorder(javax.swing.BorderFactory.createTitledBorder("CÓDIGO"));

        jButton2.setBackground(new java.awt.Color(153, 255, 153));
        jButton2.setText("BUSCAR ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_actualizar_venta.setBackground(new java.awt.Color(255, 255, 153));
        btn_actualizar_venta.setText("ACTUALIZAR");
        btn_actualizar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_ventaActionPerformed(evt);
            }
        });

        btnregresar11.setBackground(new java.awt.Color(255, 102, 102));
        btnregresar11.setText("LIMPIAR CLIENTE");
        btnregresar11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar11ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setText("Total a Pagar:");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setText("Precio Total:");

        txt_precio_total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_precio_total.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_valortotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_valortotal.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btn_nueva_venta.setBackground(new java.awt.Color(51, 255, 255));
        btn_nueva_venta.setText("NUEVA VENTA");
        btn_nueva_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nueva_ventaActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/11_104884 (1).png"))); // NOI18N
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(574, 574, 574)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtcodigoVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnregresar10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtnumero, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                            .addComponent(txtdniruc, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(btn_nueva_venta)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_actualizar_venta)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton1))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(txtproductoVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32)
                                                .addComponent(txtdescripcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(175, 175, 175)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 100, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtclientedatos, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                                            .addComponent(txtcorreo)
                                            .addComponent(btnregresar11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(186, 186, 186)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_precio_total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_igv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_valortotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_boleta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))))
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(19, 19, 19))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdescripcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtproductoVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcodigoVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1)
                                    .addComponent(btn_actualizar_venta)
                                    .addComponent(btn_nueva_venta)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel2))))
                    .addComponent(btnEliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_precio_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_igv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_valortotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_boleta)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtclientedatos, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                    .addComponent(txtdniruc))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnumero, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                    .addComponent(txtcorreo))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnregresar11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnregresar10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        contenedor.addTab("GENERAR PEDIDO", jPanel4);

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        txtbuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtbuscar.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar platos, postres o bebidas"));
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        btnbuscar.setBackground(new java.awt.Color(255, 255, 51));
        btnbuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/11_104884 (1).png"))); // NOI18N
        btnbuscar.setText("ACTUALIZAR");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        btnregresar16.setBackground(new java.awt.Color(102, 255, 102));
        btnregresar16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnregresar16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/39_104850.png"))); // NOI18N
        btnregresar16.setText("AGREGAR PLATO O BEBIDA");
        btnregresar16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar16ActionPerformed(evt);
            }
        });

        tablabuscar.setBackground(new java.awt.Color(255, 255, 255));
        tablabuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE", "CÓDIGO", "DESCRIPCIÓN", "STOCK", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablabuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabuscarMouseClicked(evt);
            }
        });
        tablabuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablabuscarKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tablabuscar);
        if (tablabuscar.getColumnModel().getColumnCount() > 0) {
            tablabuscar.getColumnModel().getColumn(0).setPreferredWidth(200);
            tablabuscar.getColumnModel().getColumn(1).setPreferredWidth(5);
            tablabuscar.getColumnModel().getColumn(2).setPreferredWidth(500);
            tablabuscar.getColumnModel().getColumn(3).setPreferredWidth(40);
            tablabuscar.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Búsqueda de toda la carta por nombre, código o descripción");

        btnEnviarCliente1.setBackground(new java.awt.Color(102, 102, 255));
        btnEnviarCliente1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEnviarCliente1.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/10_104859.png"))); // NOI18N
        btnEnviarCliente1.setText("ENVIAR PRODUCTO");
        btnEnviarCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCliente1ActionPerformed(evt);
            }
        });

        txt_mandar_codigo.setBorder(javax.swing.BorderFactory.createTitledBorder("Código"));

        txt_mandar_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del Producto"));

        txt_mandar_des.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        txt_mandar_stock.setBorder(javax.swing.BorderFactory.createTitledBorder("Stock"));

        txt_mandar_precio.setBorder(javax.swing.BorderFactory.createTitledBorder("Precio"));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(938, Short.MAX_VALUE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1337, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(btnregresar16)
                                    .addGap(902, 902, 902)
                                    .addComponent(btnEnviarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(txt_mandar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_mandar_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_mandar_des, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_mandar_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_mandar_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addGap(21, 21, 21)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_mandar_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_mandar_codigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_mandar_des, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_mandar_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_mandar_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnregresar16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        contenedor.addTab("BUSCAR (PLATOS,POSTRES,BEBIDAS)", panel1);

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtnombrepro.setBackground(new java.awt.Color(255, 255, 255));
        txtnombrepro.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE DEL PRODUCTO"));

        txtpreciopro.setBackground(new java.awt.Color(255, 255, 255));
        txtpreciopro.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO"));
        txtpreciopro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprecioproActionPerformed(evt);
            }
        });

        txtdespro.setBackground(new java.awt.Color(255, 255, 255));
        txtdespro.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCIÓN"));

        txtstockpro.setBackground(new java.awt.Color(255, 255, 255));
        txtstockpro.setBorder(javax.swing.BorderFactory.createTitledBorder("STOCK"));

        btnactualizarpro.setBackground(new java.awt.Color(153, 153, 255));
        btnactualizarpro.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnactualizarpro.setForeground(new java.awt.Color(255, 255, 255));
        btnactualizarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/17_104874.png"))); // NOI18N
        btnactualizarpro.setText("ACTUALIZAR");
        btnactualizarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarproActionPerformed(evt);
            }
        });

        tablapro.setBackground(new java.awt.Color(255, 255, 255));
        tablapro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE Y APELLIDO", "CÓDIGO", "DESCRIPCIÓN", "STOCK", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablapro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablapro);

        txtcodigopro.setBackground(new java.awt.Color(255, 255, 255));
        txtcodigopro.setBorder(javax.swing.BorderFactory.createTitledBorder("CÓDIGO"));

        btnguardarpro.setBackground(new java.awt.Color(153, 255, 153));
        btnguardarpro.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnguardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/39_104850.png"))); // NOI18N
        btnguardarpro.setText("AGREGAR");
        btnguardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarproActionPerformed(evt);
            }
        });

        btnregresar18.setBackground(new java.awt.Color(255, 102, 102));
        btnregresar18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnregresar18.setForeground(new java.awt.Color(255, 255, 255));
        btnregresar18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/36_104857 (1).png"))); // NOI18N
        btnregresar18.setText("ELIMINAR");
        btnregresar18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar18ActionPerformed(evt);
            }
        });

        btnlimpiarcli1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnlimpiarcli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/28_104847.png"))); // NOI18N
        btnlimpiarcli1.setText("LIMPIAR");
        btnlimpiarcli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarcli1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel14.setText("Tabla de productos");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel15.setText("Tabla de productos");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtpreciopro, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcodigopro, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnombrepro, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstockpro, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdespro, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(btnguardarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(btnlimpiarcli1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                .addComponent(btnactualizarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(btnregresar18, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)))
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(txtnombrepro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcodigopro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtdespro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtstockpro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnregresar18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlimpiarcli1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(135, 135, 135))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtpreciopro, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        contenedor.addTab("AGREGAR PRODUCTOS", panel2);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        txtnacli.setBackground(new java.awt.Color(255, 255, 255));
        txtnacli.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRES Y APELLIDOS"));

        txtdnicli.setBackground(new java.awt.Color(255, 255, 255));
        txtdnicli.setBorder(javax.swing.BorderFactory.createTitledBorder("DNI / RUC"));

        txtnumerocli.setBackground(new java.awt.Color(255, 255, 255));
        txtnumerocli.setBorder(javax.swing.BorderFactory.createTitledBorder("TELÉFONO"));

        txtcorreocli.setBackground(new java.awt.Color(255, 255, 255));
        txtcorreocli.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCIÓN"));

        btnagregarcli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnagregarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/39_104850.png"))); // NOI18N
        btnagregarcli.setText("AGREGAR CLIENTE");
        btnagregarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarcliActionPerformed(evt);
            }
        });

        btnlimpiarcli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnlimpiarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/28_104847.png"))); // NOI18N
        btnlimpiarcli.setText("LIMPIAR");
        btnlimpiarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarcliActionPerformed(evt);
            }
        });

        tablecliente.setBackground(new java.awt.Color(255, 255, 255));
        tablecliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRES Y APELLIDOS", "DNI/RUC", "TELÉFONO", "DIRECCIÓN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablecliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableclienteMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablecliente);
        if (tablecliente.getColumnModel().getColumnCount() > 0) {
            tablecliente.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablecliente.getColumnModel().getColumn(1).setPreferredWidth(10);
            tablecliente.getColumnModel().getColumn(2).setPreferredWidth(10);
            tablecliente.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        btnmodificarcli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnmodificarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/17_104874.png"))); // NOI18N
        btnmodificarcli.setText("MODIFICAR");
        btnmodificarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarcliActionPerformed(evt);
            }
        });

        btneliminarcli.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btneliminarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/36_104857 (1).png"))); // NOI18N
        btneliminarcli.setText("ELIMINAR");
        btneliminarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarcliActionPerformed(evt);
            }
        });

        btnEnviarCliente.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEnviarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/10_104859.png"))); // NOI18N
        btnEnviarCliente.setText("ENVIAR CLEINTE");
        btnEnviarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarClienteActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setText("Tabla de clientes");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setText("Registro de clientes");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnagregarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnmodificarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnlimpiarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btneliminarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnEnviarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnacli, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                            .addComponent(txtnumerocli, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdnicli, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtcorreocli))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtnacli, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtdnicli, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtnumerocli, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcorreocli, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnagregarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmodificarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlimpiarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        contenedor.addTab("REGISTRO DE CLIENTES", jPanel7);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtcodigo10.setBackground(new java.awt.Color(255, 255, 255));
        txtcodigo10.setBorder(javax.swing.BorderFactory.createTitledBorder("CODIGO"));

        txtcantidad1.setBackground(new java.awt.Color(255, 255, 255));
        txtcantidad1.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD"));

        txtprecio1.setBackground(new java.awt.Color(255, 255, 255));
        txtprecio1.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO UNITARIO"));

        btnGenerarVenta2.setBackground(new java.awt.Color(255, 255, 255));
        btnGenerarVenta2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        btnGenerarVenta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVenta2ActionPerformed(evt);
            }
        });

        txtclientedatos1.setBackground(new java.awt.Color(255, 255, 255));
        txtclientedatos1.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRES Y APELLIDOS"));

        txtdni1.setBackground(new java.awt.Color(255, 255, 255));
        txtdni1.setBorder(javax.swing.BorderFactory.createTitledBorder("Telefono"));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total a Pagar:");

        txtdni2.setBackground(new java.awt.Color(255, 255, 255));
        txtdni2.setBorder(javax.swing.BorderFactory.createTitledBorder("DNI / RUC"));

        txtprecio3.setBackground(new java.awt.Color(255, 255, 255));
        txtprecio3.setBorder(javax.swing.BorderFactory.createTitledBorder("CORREO"));

        btnregresar.setText("GUARDAR");
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });

        btnregresar1.setText("REGISTRAR");
        btnregresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar1ActionPerformed(evt);
            }
        });

        txtbuscarventa.setBackground(new java.awt.Color(255, 255, 255));
        txtbuscarventa.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR VENTA"));
        txtbuscarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarventaActionPerformed(evt);
            }
        });
        txtbuscarventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarventaKeyReleased(evt);
            }
        });

        btnbuscar1.setBackground(new java.awt.Color(51, 255, 51));
        btnbuscar1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnbuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/11_104884 (1).png"))); // NOI18N
        btnbuscar1.setText("RESETEAR");
        btnbuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar1ActionPerformed(evt);
            }
        });

        tablabuscarventa.setBackground(new java.awt.Color(255, 255, 255));
        tablabuscarventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablabuscarventa.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablabuscarventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabuscarventaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tablabuscarventaMouseEntered(evt);
            }
        });
        tablabuscarventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablabuscarventaKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tablabuscarventa);

        txtproductoventa.setBackground(new java.awt.Color(255, 255, 255));
        txtproductoventa.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTO"));

        jDateChooser1.setBackground(new java.awt.Color(255, 255, 255));

        txt_total_pagar.setBackground(new java.awt.Color(255, 255, 255));
        txt_total_pagar.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL A PAGAR"));

        txtprecio2.setBackground(new java.awt.Color(255, 255, 255));
        txtprecio2.setBorder(javax.swing.BorderFactory.createTitledBorder("IGV"));

        txtprecio4.setBackground(new java.awt.Color(255, 255, 255));
        txtprecio4.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO TOTAL"));
        txtprecio4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprecio4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcodigo10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnGenerarVenta2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtproductoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnregresar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnregresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtprecio3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdni1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdni2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46))))
                    .addComponent(txtclientedatos1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(txtprecio4, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addGap(5, 5, 5)
                            .addComponent(txtprecio2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1))
                        .addComponent(txtcantidad1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(txtprecio1))))
                .addGap(39, 94, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtbuscarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnbuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtclientedatos1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtcodigo10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdni2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtproductoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdni1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtprecio3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtcantidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtprecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtprecio4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txtprecio2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(86, 86, 86))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGenerarVenta2)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnregresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnregresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(44, 44, 44))))))
        );

        contenedor.addTab("COMPROBANTE DE PAGO", jPanel3);

        jPanel1.add(contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 148, -1, 634));

        jLabel1.setBackground(new java.awt.Color(0, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/orioneat-prod_N9BKs4c8JEvC33bLB-320x320.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 6, -1, 99));

        lblnombre.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblnombre.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.add(lblnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 37, 595, 41));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setText("TRABAJADOR");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 37, -1, 41));

        btnsalir1.setBackground(new java.awt.Color(255, 102, 102));
        btnsalir1.setText("SALIR");
        btnsalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnsalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1371, 816, 151, 44));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/32_104802.png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1473, 27, 49, 51));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/frutas2.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1700, 930));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnregresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnregresar1ActionPerformed

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
        VISTA_INICIO ventana = new VISTA_INICIO();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnregresarActionPerformed

    private void btneliminarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarcliActionPerformed

        try {
            int i =   cliente_crud.eliminarcli(txtnacli.getText(),Integer.parseInt(txtdnicli.getText()),Integer.parseInt(txtnumerocli.getText()),txtcorreocli.getText());
            if (i>0){

                JOptionPane.showMessageDialog(this,"Cliente eliminado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo eliminar . Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        limpiarcli();
        cargarTablacli();
    }//GEN-LAST:event_btneliminarcliActionPerformed

    private void btnmodificarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarcliActionPerformed

        try {
            int i =   cliente_crud.updatecli(txtnacli.getText(),Integer.parseInt(txtdnicli.getText()),Integer.parseInt(txtnumerocli.getText()),txtcorreocli.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Cliente modificado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo actualizar . Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        cargarTablacli();
        limpiarcli();
    }//GEN-LAST:event_btnmodificarcliActionPerformed

    private void tableclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableclienteMouseClicked
         int seleccion = tablecliente.rowAtPoint(evt.getPoint());
        txtnacli.setText(String.valueOf(tablecliente.getValueAt(seleccion, 0)));
        txtdnicli.setText(String.valueOf(tablecliente.getValueAt(seleccion, 1)));
        txtnumerocli.setText(String.valueOf(tablecliente.getValueAt(seleccion, 2)));
        txtcorreocli.setText(String.valueOf(tablecliente.getValueAt(seleccion, 3)));
       

        cargarTablacli();
    }//GEN-LAST:event_tableclienteMouseClicked

    private void btnlimpiarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarcliActionPerformed

        limpiarcli();
        cargarTablacli();
    }//GEN-LAST:event_btnlimpiarcliActionPerformed

    private void btnagregarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarcliActionPerformed

        try {
            int i =   cliente_crud.guardarcli(txtnacli.getText(),Integer.parseInt(txtdnicli.getText()),Integer.parseInt(txtnumerocli.getText()),txtcorreocli.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Cliente registrado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo registrar. Intente mas tarde");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());

        }
        limpiarcli();
        cargarTablacli();
    }//GEN-LAST:event_btnagregarcliActionPerformed

    private void btnregresar18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar18ActionPerformed
         try {
            int i =   producto_crud.eliminarpro(txtnombrepro.getText(),Integer.parseInt(txtcodigopro.getText()),txtdespro.getText(),Integer.parseInt(txtstockpro.getText()),Integer.parseInt(txtpreciopro.getText()));
            if (i>0){

                JOptionPane.showMessageDialog(this,"Producto eliminado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo eliminar . Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        
          limpiarpro();
        cargarTablapro();
    }//GEN-LAST:event_btnregresar18ActionPerformed

    private void btnguardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarproActionPerformed

        try {
            int i =   producto_crud.guardarpro(txtnombrepro.getText(),Integer.parseInt(txtcodigopro.getText()),txtdespro.getText(),Integer.parseInt(txtstockpro.getText()),Integer.parseInt(txtpreciopro.getText()));
            if (i>0){
                JOptionPane.showMessageDialog(this,"Prodcuto agregado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo registrar el prodcuto. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        limpiarpro();
        cargarTablapro();
    }//GEN-LAST:event_btnguardarproActionPerformed

    private void tablaproMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproMouseClicked
        
        int seleccion = tablapro.rowAtPoint(evt.getPoint());
        txtnombrepro.setText(String.valueOf(tablapro.getValueAt(seleccion, 0)));
        txtcodigopro.setText(String.valueOf(tablapro.getValueAt(seleccion, 1)));
        txtdespro.setText(String.valueOf(tablapro.getValueAt(seleccion, 2)));
        txtstockpro.setText(String.valueOf(tablapro.getValueAt(seleccion, 3)));
        txtpreciopro.setText(String.valueOf(tablapro.getValueAt(seleccion, 4)));
        cargarTablapro();
        
    }//GEN-LAST:event_tablaproMouseClicked

    private void btnactualizarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarproActionPerformed

        try {
            int i =   producto_crud.updatepro(txtnombrepro.getText(),Integer.parseInt(txtcodigopro.getText()),txtdespro.getText(),Integer.parseInt(txtstockpro.getText()),Integer.parseInt(txtpreciopro.getText()));
            if (i>0){
                JOptionPane.showMessageDialog(this,"Producto actulizado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo registrar. Intente mas tarde");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());

        }
        limpiarpro();
        cargarTablapro();
    }//GEN-LAST:event_btnactualizarproActionPerformed

    private void txtprecioproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprecioproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecioproActionPerformed

    private void tablabuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabuscarKeyReleased

    }//GEN-LAST:event_tablabuscarKeyReleased

    private void tablabuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabuscarMouseClicked
        int seleccion = tablabuscar.rowAtPoint(evt.getPoint());
        txt_mandar_nombre.setText(String.valueOf(tablabuscar.getValueAt(seleccion, 0)));
        txt_mandar_codigo.setText(String.valueOf(tablabuscar.getValueAt(seleccion, 1)));
        txt_mandar_des.setText(String.valueOf(tablabuscar.getValueAt(seleccion, 2)));
        txt_mandar_stock.setText(String.valueOf(tablabuscar.getValueAt(seleccion, 3)));
        txt_mandar_precio.setText(String.valueOf(tablabuscar.getValueAt(seleccion, 4)));
        
        
         cargartabla("");
        txtbuscar.setText(null);
        
    
        
        
        
        
    }//GEN-LAST:event_tablabuscarMouseClicked

    private void btnregresar16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar16ActionPerformed

        contenedor.setSelectedIndex(2);
    }//GEN-LAST:event_btnregresar16ActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed

        cargartabla("");
        txtbuscar.setText(null);
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        cargartabla(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void btnsalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnsalir1ActionPerformed

    private void btnlimpiarcli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarcli1ActionPerformed
        limpiarpro();
    }//GEN-LAST:event_btnlimpiarcli1ActionPerformed

    private void txtbuscarventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarventaKeyReleased
        cargartablaventa(txtbuscarventa.getText());
    }//GEN-LAST:event_txtbuscarventaKeyReleased

    private void btnbuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar1ActionPerformed
        cargartablaventa("");
        txtbuscarventa.setText(null);
        
        limpiarboleta();
    }//GEN-LAST:event_btnbuscar1ActionPerformed

    private void tablabuscarventaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabuscarventaMouseClicked
      
         int seleccion = tablabuscarventa.rowAtPoint(evt.getPoint());
        txtcodigo10.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 1)));
        txtproductoventa.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 2)));
        txtcantidad1.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 4)));
        txtprecio1.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 5)));
        txtclientedatos1.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 6)));
        txtdni2.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 7)));
        txtdni1.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 8)));
        txtprecio3.setText(String.valueOf(tablabuscarventa.getValueAt(seleccion, 9)));
     
        cargarTablapro();
        








// try {
            //int fila = tablabuscarventa.getSelectedRow();
            //int id = Integer.parseInt(tablabuscarventa.getValueAt(fila, 0).toString());

            /*PreparedStatement ps;
            ResultSet rs;

            Connection con = Conexion_DAO.conectar();
            ps = con.prepareStatement("SELECT CODIGO,PRODUCTO,DESCRIPCION,CANTIDAD,PRECIO,NOMBREAPELLIDO,DNIRUC, NUMERO,CORREO * FROM venta");
            
            //ps.setInt(5, id);
            rs = ps.executeQuery();

            while(rs.next()){
            
            txtcodigo10.setText(rs.getString("CODIGO"));
            txtcodigo1.setText(rs.getString("PRODUCTO"));
            txtdescripcion1.setText(rs.getString("DESCRIPCION"));
            txtcantidad1.setText(rs.getString("CANTIDAD"));
            txtprecio1.setText(rs.getString("PRECIO"));
            txtclientedatos1.setText(rs.getString("NOMBREAPELLIDO"));
            txtdni2.setText(rs.getString("DNIRUC"));
            txtdni1.setText(rs.getString("NUMERO"));
            txtprecio3.setText(rs.getString("CORREO"));


            }
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());

        }*/
        cargarTablapro();
    }//GEN-LAST:event_tablabuscarventaMouseClicked

    private void tablabuscarventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabuscarventaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabuscarventaKeyReleased

    private void txtbuscarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarventaActionPerformed

    private void btnEnviarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarClienteActionPerformed
      
                
                txtclientedatos.setText(txtnacli.getText());
                txtdniruc.setText(txtdnicli.getText());
                txtnumero.setText(txtnumerocli.getText());
                txtcorreo.setText(txtcorreocli.getText());
                
               
                contenedor.setSelectedIndex(0);
                limpiarcli();
        
        
    }//GEN-LAST:event_btnEnviarClienteActionPerformed

    private void btnEnviarCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCliente1ActionPerformed
                
        
        
                txtcodigoVENTA.setText(txt_mandar_codigo.getText());
                txtproductoVENTA.setText(txt_mandar_nombre.getText());
                txtdescripcion1.setText(txt_mandar_des.getText());
                txtprecio.setText(txt_mandar_precio.getText());
                txtStock.setText(txt_mandar_stock.getText());
                
               
                contenedor.setSelectedIndex(0);
                limpiarproductos();
        

    }//GEN-LAST:event_btnEnviarCliente1ActionPerformed

    private void tablabuscarventaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabuscarventaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabuscarventaMouseEntered

    private void txtprecio4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprecio4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecio4ActionPerformed

    private void btnGenerarVenta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVenta2ActionPerformed
    
    }//GEN-LAST:event_btnGenerarVenta2ActionPerformed

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void btn_nueva_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nueva_ventaActionPerformed
      
        limpiarta();
        LimpiarClientesJalados();
        limpiarventa();
       
     
        limpiarprecio();
    }//GEN-LAST:event_btn_nueva_ventaActionPerformed

    private void btnregresar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar11ActionPerformed
        LimpiarClientesJalados();
    }//GEN-LAST:event_btnregresar11ActionPerformed

    private void btn_actualizar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_ventaActionPerformed
        
        try {
            int i =   venta_crud.updateventa(Integer.parseInt(txtcantidad.getText()),txtproductoVENTA.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Pedido actulizada con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo actualizar el pedido. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        cargarTablaventa();
        limpiarventa();
        calcular();

    }//GEN-LAST:event_btn_actualizar_ventaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        contenedor.setSelectedIndex(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnregresar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar10ActionPerformed
        contenedor.setSelectedIndex(3);
    }//GEN-LAST:event_btnregresar10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            int i =   venta_crud.guardarventa(Integer.parseInt(txtcodigoVENTA.getText()),txtproductoVENTA.getText(),txtdescripcion1.getText(),Integer.parseInt(txtcantidad.getText()),Integer.parseInt(txtprecio.getText()),txtclientedatos.getText(),Integer.parseInt(txtdniruc.getText()),Integer.parseInt(txtnumero.getText()),txtcorreo.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Pedido registrado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo registrar el pedido. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        cargarTablaventa();
        limpiarventa();
        calcular();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TableVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentaMouseClicked

        int seleccion = TableVenta.rowAtPoint(evt.getPoint());
        txtcodigoVENTA.setText(String.valueOf(TableVenta.getValueAt(seleccion, 1)));
        txtproductoVENTA.setText(String.valueOf(TableVenta.getValueAt(seleccion, 2)));
        txtdescripcion1.setText(String.valueOf(TableVenta.getValueAt(seleccion, 3)));
        txtcantidad.setText(String.valueOf(TableVenta.getValueAt(seleccion, 4)));
        txtprecio.setText(String.valueOf(TableVenta.getValueAt(seleccion, 5)));
        txtclientedatos.setText(String.valueOf(TableVenta.getValueAt(seleccion, 6)));
        txtdniruc.setText(String.valueOf(TableVenta.getValueAt(seleccion, 7)));
        txtnumero.setText(String.valueOf(TableVenta.getValueAt(seleccion, 8)));
        txtcorreo.setText(String.valueOf(TableVenta.getValueAt(seleccion, 9)));

        cargarTablapro();
    }//GEN-LAST:event_TableVentaMouseClicked

    private void btn_boletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_boletaActionPerformed
       
         try {
         int i =   precio_igv.guardarigv(Integer.parseInt(txtdniruc.getText()),txt_precio_total.getText(),txt_igv.getText(),txt_valortotal.getText());

            if (i>0){
                JOptionPane.showMessageDialog(this,"Boleta registrada con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo registrar la boleta. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
         
         
        Conexion con = new Conexion();
        Connection conn = Conexion_DAO.conectar();
        
        JasperReport reporte = null;
        String path = "src\\APP\\Reportes.jasper";
        
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
        JasperPrint jprint = JasperFillManager.fillReport(reporte, null, conn);
        JasperViewer view = new JasperViewer(jprint,false);
        
        view.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(VISTA_FORMULARIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
  
        
        
    }//GEN-LAST:event_btn_boletaActionPerformed

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed
        try {
            int i =   venta_crud.eliminarventa(Integer.parseInt(txtcodigoVENTA.getText()),txtproductoVENTA.getText(),txtdescripcion1.getText(),Integer.parseInt(txtcantidad.getText()),Integer.parseInt(txtprecio.getText()),txtclientedatos.getText(),Integer.parseInt(txtdniruc.getText()),Integer.parseInt(txtnumero.getText()),txtcorreo.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Pedido eliminado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo eliminar el pedido. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        
            try {
            int i =  precio_igv.eliminarigv(Integer.parseInt(txtdniruc.getText()),txt_precio_total.getText(),txt_igv.getText(),txt_valortotal.getText());
            if (i>0){
                JOptionPane.showMessageDialog(this,"Pedido eliminado con exito");
            }
            else {
                JOptionPane.showMessageDialog(this,"No se pudo eliminar el pedido. Intente mas tarde");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        cargarTablaventa();
        limpiarventa();
        calcular();
    }//GEN-LAST:event_btnEliminarventaActionPerformed

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VISTA_FORMULARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VISTA_FORMULARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VISTA_FORMULARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VISTA_FORMULARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VISTA_FORMULARIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableVenta;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnEnviarCliente;
    private javax.swing.JButton btnEnviarCliente1;
    private javax.swing.JButton btnGenerarVenta1;
    private javax.swing.JButton btnGenerarVenta2;
    private javax.swing.JButton btn_actualizar_venta;
    private javax.swing.JButton btn_boleta;
    private javax.swing.JButton btn_nueva_venta;
    private javax.swing.JButton btnactualizarpro;
    private javax.swing.JButton btnagregarcli;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btnbuscar1;
    private javax.swing.JButton btneliminarcli;
    private javax.swing.JButton btnguardarpro;
    private javax.swing.JButton btnlimpiarcli;
    private javax.swing.JButton btnlimpiarcli1;
    private javax.swing.JButton btnmodificarcli;
    private javax.swing.JButton btnregresar;
    private javax.swing.JButton btnregresar1;
    private javax.swing.JButton btnregresar10;
    private javax.swing.JButton btnregresar11;
    private javax.swing.JButton btnregresar16;
    private javax.swing.JButton btnregresar18;
    private javax.swing.JButton btnsalir1;
    private javax.swing.JButton btnsalir3;
    public javax.swing.JTabbedPane contenedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    public javax.swing.JLabel lblnombre;
    public javax.swing.JPanel panel1;
    public javax.swing.JPanel panel2;
    public javax.swing.JTable tablabuscar;
    public javax.swing.JTable tablabuscarventa;
    public javax.swing.JTable tablapro;
    public javax.swing.JTable tablecliente;
    private javax.swing.JTextField txtStock;
    private javax.swing.JLabel txt_igv;
    private javax.swing.JLabel txt_mandar_codigo;
    private javax.swing.JLabel txt_mandar_des;
    private javax.swing.JLabel txt_mandar_nombre;
    private javax.swing.JLabel txt_mandar_precio;
    private javax.swing.JLabel txt_mandar_stock;
    private javax.swing.JLabel txt_precio_total;
    private javax.swing.JTextField txt_total_pagar;
    private javax.swing.JLabel txt_valortotal;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtbuscarventa;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcantidad1;
    private javax.swing.JTextField txtclientedatos;
    private javax.swing.JTextField txtclientedatos1;
    private javax.swing.JTextField txtcodigo10;
    private javax.swing.JTextField txtcodigoVENTA;
    private javax.swing.JTextField txtcodigopro;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtcorreocli;
    private javax.swing.JTextField txtdescripcion1;
    private javax.swing.JTextField txtdespro;
    private javax.swing.JTextField txtdni1;
    private javax.swing.JTextField txtdni2;
    private javax.swing.JTextField txtdnicli;
    private javax.swing.JTextField txtdniruc;
    private javax.swing.JTextField txtnacli;
    private javax.swing.JTextField txtnombrepro;
    private javax.swing.JTextField txtnumero;
    private javax.swing.JTextField txtnumerocli;
    private javax.swing.JTextField txtprecio;
    private javax.swing.JTextField txtprecio1;
    private javax.swing.JTextField txtprecio2;
    private javax.swing.JTextField txtprecio3;
    private javax.swing.JTextField txtprecio4;
    private javax.swing.JTextField txtpreciopro;
    private javax.swing.JTextField txtproductoVENTA;
    private javax.swing.JTextField txtproductoventa;
    private javax.swing.JTextField txtstockpro;
    private javax.swing.JLabel txtsuma2;
    // End of variables declaration//GEN-END:variables

 
}
