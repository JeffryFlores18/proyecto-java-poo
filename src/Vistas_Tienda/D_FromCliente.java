
package Vistas_Tienda;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class D_FromCliente extends javax.swing.JFrame {


    private D_Clientesp ventanaClientes;
    private int idclienteEditar = 0;
    
 
    
    
    public D_FromCliente() {
        initComponents();
        
        prepararRegistro();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
 
    }

    public D_FromCliente(D_Clientesp ventanaClientes) {
        this();
        this.ventanaClientes = ventanaClientes;
        setLocationRelativeTo(ventanaClientes);
    }

    public D_FromCliente(D_Clientesp ventanaClientes, D_clientees cliente,String titulo) {
        this(ventanaClientes);
        idclienteEditar = cliente.getIDcliente();
        txtidCliente.setText(String.valueOf(cliente.getIDcliente()));
        txtdni.setText(cliente.getDni());
        txtnombre.setText(cliente.getNombre());
        txttelefono.setText(cliente.getTelefono());
        if(titulo.equals("Editar")){
            lbltituloFormulario.setText("Editar Cliente");
        }
        btnguardar.setText("Guardar cambios");
    }
    
    
    
    
    private void prepararRegistro(){
        txtidCliente.setText("Automatico");
        txtidCliente.setEditable(false);
        
        txtdni.setText("");
        txtnombre.setText("");
        txttelefono.setText("");

        btnguardar.setText("Confirmar");
    }
    
    
    private boolean validarDatos(){
        String dni = txtdni.getText().trim();
        String nombre = txtnombre.getText().trim();
        String telefono = txttelefono.getText().trim();
        
        if (dni.isEmpty() ||nombre.isEmpty() || telefono.isEmpty()) {
           JOptionPane.showMessageDialog( this,"Complete todos los campos." );
           return false;
        }
        if (!dni.matches("[0-9]{8}")) {
            JOptionPane.showMessageDialog(this, "El DNI debe contener 8 dígitos.");
            txtdni.requestFocusInWindow();
            return false;
        }
        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(this,"Ingrese nombre.");
            txtnombre.requestFocusInWindow();
            return false;
        }
        if (!telefono.matches("[0-9]{9}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 9 dígitos.");
            txttelefono.requestFocusInWindow();
            return false;
        }
       return true;
    }
    
    //Este método actualizará la tabla y cerrará el formulario. También servirá al editar
    private void finalizarGuardado(String mensaje) {

        JOptionPane.showMessageDialog(this, mensaje);
        if (ventanaClientes != null) {
            ventanaClientes.cargarClientes();
        }
        dispose();
    }
    
    private void registrarCliente (){
        if (!validarDatos()) {
            return;
        }
        String sql = "INSERT INTO cliente (dni, nombre, telefono) "+ "VALUES (?, ?, ?)";
        
        try(Connection con = conexionLiv.conectar()) {
            if (con == null) {
               JOptionPane.showMessageDialog(this,"No se pudo conectar con la base de datos." );
            return; 
            }
            
            try(PreparedStatement ps = con.prepareStatement(sql)) {
              ps.setString(1, txtdni.getText().trim());
              ps.setString(2, txtnombre.getText().trim());
              ps.setString(3, txttelefono.getText().trim());
              
              int filas = ps.executeUpdate();
                if (filas==1) {
                    finalizarGuardado("Cliente registrado correctamente.");
                }
            } 
            
        } catch (SQLException e) {
            if (e.getErrorCode()==1062) {
                JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese DNI.");
            }else{
                JOptionPane.showMessageDialog(this,"Error al registrar cliente: "+e.getMessage());
            }
        }
    }
     
    public  void editarCliente(){
        if (!validarDatos()) {
            return;
        }
        String sql = "UPDATE cliente "+"SET dni = ?,nombre = ?, telefono = ? "+"WHERE id_cliente = ?" ;
        
        try(Connection con = conexionLiv.conectar()) {
            if (con ==null) {
               JOptionPane.showMessageDialog(this, "No se pudo conectar con la base de datos");
               return;
            }
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, txtdni.getText().trim());
                ps.setString(2, txtnombre.getText().trim());
                ps.setString(3, txttelefono.getText().trim());
                ps.setInt(4,idclienteEditar);
                int filas = ps.executeUpdate();
                
                if (filas>0) {
                    finalizarGuardado("Cliente actualizado correctamente.");
                }else{
                    JOptionPane.showMessageDialog(this, "No se realizaron cambios. "+"Actualice la tabla para comprobar si el cliente todavia existe.");
                }     
            }      
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Ese DNI ya pertenece a otro cliente");
            }else{
                JOptionPane.showMessageDialog(this, "Error al editar cliente: "+e.getMessage());
            }       
        }
    }
    
    
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbltituloFormulario = new javax.swing.JLabel();
        lblsubtitulo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtidCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtdni = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txttelefono = new javax.swing.JTextField();
        btnguardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbltituloFormulario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbltituloFormulario.setForeground(new java.awt.Color(123, 98, 90));
        lbltituloFormulario.setText("REGISTRAR CLIENTE");
        lbltituloFormulario.setAlignmentX(32.0F);
        lbltituloFormulario.setAlignmentY(22.0F);
        lbltituloFormulario.setMaximumSize(new java.awt.Dimension(180, 28));
        lbltituloFormulario.setMinimumSize(new java.awt.Dimension(180, 28));
        lbltituloFormulario.setPreferredSize(new java.awt.Dimension(32, 22));
        jPanel1.add(lbltituloFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 210, 40));

        lblsubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblsubtitulo.setForeground(new java.awt.Color(123, 98, 90));
        lblsubtitulo.setText("Complete los datos del cliente");
        lblsubtitulo.setMaximumSize(new java.awt.Dimension(250, 18));
        lblsubtitulo.setMinimumSize(new java.awt.Dimension(250, 18));
        lblsubtitulo.setPreferredSize(new java.awt.Dimension(20, 42));
        jPanel1.add(lblsubtitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 220, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(58, 42, 38));
        jLabel4.setText("ID:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 20, 30));

        txtidCliente.setEditable(false);
        txtidCliente.setBackground(new java.awt.Color(255, 248, 250));
        txtidCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        jPanel1.add(txtidCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 200, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(58, 42, 38));
        jLabel3.setText(" Datos:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 120, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(58, 42, 38));
        jLabel6.setText("DNI:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 100, 40));

        txtdni.setBackground(new java.awt.Color(255, 248, 250));
        txtdni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        jPanel1.add(txtdni, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 200, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(58, 42, 38));
        jLabel7.setText("Nombre: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 100, 40));

        txtnombre.setBackground(new java.awt.Color(255, 248, 250));
        txtnombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        jPanel1.add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 200, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(58, 42, 38));
        jLabel5.setText("Telefono:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 70, 40));

        txttelefono.setBackground(new java.awt.Color(255, 248, 250));
        txttelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        jPanel1.add(txttelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 200, 30));

        btnguardar.setBackground(new java.awt.Color(216, 154, 132));
        btnguardar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnguardar.setForeground(new java.awt.Color(255, 255, 255));
        btnguardar.setText("Confirmar");
        btnguardar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnguardar.setFocusPainted(false);
        btnguardar.setOpaque(true);
        btnguardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnguardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnguardarMouseExited(evt);
            }
        });
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 140, 34));

        btnCancelar.setBackground(new java.awt.Color(255, 243, 238));
        btnCancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(184, 123, 103));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setOpaque(true);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 80, 34));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseEntered
        btnguardar.setBackground(new Color(199, 132, 108));
    }//GEN-LAST:event_btnguardarMouseEntered

    private void btnguardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseExited
        btnguardar.setBackground(new Color(216, 154, 132));
    }//GEN-LAST:event_btnguardarMouseExited

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        if (idclienteEditar==0) {
            registrarCliente();
        }else{
            editarCliente();
        }
        
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new D_FromCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblsubtitulo;
    private javax.swing.JLabel lbltituloFormulario;
    private javax.swing.JTextField txtdni;
    private javax.swing.JTextField txtidCliente;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txttelefono;
    // End of variables declaration//GEN-END:variables
}
