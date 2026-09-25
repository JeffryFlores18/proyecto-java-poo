package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProveedorAgregar extends javax.swing.JPanel {

    public ProveedorAgregar() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombre = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtRuc = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 247, 244));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\ruc.png")); // NOI18N
        jLabel1.setText("RUC:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 80, 41));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\razon_social.png")); // NOI18N
        jLabel2.setText("RAZON SOCIAL:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 150, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\telefono.png")); // NOI18N
        jLabel3.setText("TELEFONO:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 110, 38));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\direccion.png")); // NOI18N
        jLabel4.setText("DIRECCION:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\email.png")); // NOI18N
        jLabel6.setText("EMAIL:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 90, 30));

        jLabel7.setFont(new java.awt.Font("Maiandra GD", 1, 48)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\logo_alanastore.png")); // NOI18N
        jLabel7.setText("AGREGAR PROVEEDOR ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 20, 750, 60));

        btnGuardar.setBackground(new java.awt.Color(202, 125, 117));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\guardar.png")); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, 210, 70));

        btnCancelar.setBackground(new java.awt.Color(202, 125, 117));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\Downloads\\Iconos_AlanaStore_32x32\\cancelar.png")); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 220, 70));
        jPanel1.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 530, 40));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 530, 40));
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 530, 40));

        txtEmail.addActionListener(this::txtEmailActionPerformed);
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 530, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String ruc = txtRuc.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();

        if (ruc.isEmpty() || nombre.isEmpty() || telefono.isEmpty()
                || direccion.isEmpty() || email.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos."
            );

            return;
        }

        if (ruc.length() != 11) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "El RUC debe tener 11 dígitos."
            );

            return;
        }

        try {

            Long.parseLong(ruc);

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "El RUC solo debe contener números."
            );

            return;
        }

        if (telefono.length() != 9) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "El teléfono debe tener 9 dígitos."
            );

            return;
        }

        try {

            Long.parseLong(telefono);

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "El teléfono solo debe contener números."
            );

            return;
        }

        String sql = "INSERT INTO proveedor "
                + "(ruc, razon_social, telefono, direccion, email) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conexion = conexionLiv.conectar();

            PreparedStatement sentencia
                    = conexion.prepareStatement(sql);

            sentencia.setString(1, ruc);
            sentencia.setString(2, nombre);
            sentencia.setString(3, telefono);
            sentencia.setString(4, direccion);
            sentencia.setString(5, email);

            sentencia.executeUpdate();

            sentencia.close();
            conexion.close();

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Proveedor agregado correctamente."
            );

            
            txtRuc.setText("");
            txtNombre.setText("");
            txtTelefono.setText("");
            txtDireccion.setText("");
            txtEmail.setText("");

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        java.awt.Window ventana
                = javax.swing.SwingUtilities.getWindowAncestor(this);

        ventana.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
