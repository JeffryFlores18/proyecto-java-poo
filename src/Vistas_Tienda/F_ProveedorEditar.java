package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class F_ProveedorEditar extends javax.swing.JPanel {

    public F_ProveedorEditar() {
        initComponents();
    }

    public F_ProveedorEditar(String id, String ruc, String nombre,
            String telefono, String direccion, String email) {

        initComponents();

        txtID.setText(id);
        txtID.setEditable(false);

        txtRuc.setText(ruc);
        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtDireccion.setText(direccion);
        txtEmail.setText(email);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 247, 244));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 247, 244));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Maiandra GD", 1, 36)); // NOI18N
        jLabel2.setText("EDITAR PROVEEDOR ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("ID:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 40, -1));
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 377, 35));
        jPanel1.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, 260, 35));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 640, 35));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 310, 35));
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 290, 35));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, 300, 35));

        btnActualizar.setBackground(new java.awt.Color(202, 125, 117));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\icono_actualizar_negro_32x32.png")); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 190, 60));

        btnCancelar.setBackground(new java.awt.Color(202, 125, 117));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\icono_cancelar_negro_32x32.png")); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 580, 190, 60));

        jLabel4.setText("RUC:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 50, -1));

        jLabel5.setText("NOMBRE:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 70, -1));

        jLabel6.setText("TELF:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 40, -1));

        jLabel7.setText("DIRECCION:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 80, 30));

        jLabel8.setText("EMAIL:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, -1, -1));

        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 730, 130));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("PROVEEDOR SELECCIONADO ");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 730, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("INFORMACION DEL PROVEEDOR");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 730, 40));

        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 730, 270));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 12, 790, 690));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        String id = txtID.getText();
        String ruc = txtRuc.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();

        if (id.isEmpty() || ruc.isEmpty() || nombre.isEmpty()
                || telefono.isEmpty() || direccion.isEmpty()
                || email.isEmpty()) {

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

        String sql = "UPDATE proveedor SET "
                + "ruc = ?, "
                + "razon_social = ?, "
                + "telefono = ?, "
                + "direccion = ?, "
                + "email = ? "
                + "WHERE id_proveedor = ?";

        try {

            Connection conexion = conexionLiv.conectar();

            PreparedStatement sentencia
                    = conexion.prepareStatement(sql);

            sentencia.setString(1, ruc);
            sentencia.setString(2, nombre);
            sentencia.setString(3, telefono);
            sentencia.setString(4, direccion);
            sentencia.setString(5, email);
            sentencia.setInt(6, Integer.parseInt(id));

            int resultado = sentencia.executeUpdate();

            sentencia.close();
            conexion.close();

            if (resultado > 0) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Proveedor actualizado correctamente."
                );

                java.awt.Window ventana
                        = javax.swing.SwingUtilities.getWindowAncestor(this);

                ventana.dispose();

            } else {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el proveedor."
                );
            }

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        java.awt.Window ventana
                = javax.swing.SwingUtilities.getWindowAncestor(this);

        ventana.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
