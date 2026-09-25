package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class F_ProveedorBuscarPorRuc extends javax.swing.JPanel {

    public F_ProveedorBuscarPorRuc() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 247, 244));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 700));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 1, 36)); // NOI18N
        jLabel1.setText("BUSCAR PROVEEDOR ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(50, 20, 386, 44);

        btnBuscar.setBackground(new java.awt.Color(202, 125, 117));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\lupa_negra_32x32.png")); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        jPanel1.add(btnBuscar);
        btnBuscar.setBounds(590, 90, 130, 46);

        jLabel3.setFont(new java.awt.Font("Maiandra GD", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\icono_resultados_negro_32x32.png")); // NOI18N
        jLabel3.setText("Resultados de la busqueda: ");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(50, 160, 280, 32);
        jPanel1.add(txtRuc);
        txtRuc.setBounds(180, 90, 393, 46);

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaResultados);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(50, 200, 670, 320);

        btnSalir.setBackground(new java.awt.Color(202, 125, 117));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\icono_salir_negro_32x32.png")); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(this::btnSalirActionPerformed);
        jPanel1.add(btnSalir);
        btnSalir.setBounds(280, 570, 200, 60);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\icono_buscar.png")); // NOI18N
        jLabel4.setText("RUC:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(50, 90, 150, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String ruc = txtRuc.getText();

        if (ruc.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el RUC del proveedor."
            );

            return;
        }

        String sql = "SELECT id_proveedor, ruc, razon_social, "
                + "telefono, direccion, email "
                + "FROM proveedor "
                + "WHERE ruc = ?";

        try {

            Connection conexion = conexionLiv.conectar();

            PreparedStatement sentencia
                    = conexion.prepareStatement(sql);

            sentencia.setString(1, ruc);

            ResultSet resultado = sentencia.executeQuery();

            DefaultTableModel modelo
                    = (DefaultTableModel) tablaResultados.getModel();

            modelo.setColumnIdentifiers(new Object[]{
                "ID",
                "RUC",
                "RAZÓN SOCIAL",
                "TELÉFONO",
                "DIRECCIÓN",
                "EMAIL"
            });

            modelo.setRowCount(0);

            if (resultado.next()) {

                modelo.addRow(new Object[]{
                    resultado.getInt("id_proveedor"),
                    resultado.getString("ruc"),
                    resultado.getString("razon_social"),
                    resultado.getString("telefono"),
                    resultado.getString("direccion"),
                    resultado.getString("email")
                });

            } else {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró ningún proveedor con ese RUC."
                );
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error al buscar: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        java.awt.Window ventana
                = javax.swing.SwingUtilities.getWindowAncestor(this);

        ventana.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtRuc;
    // End of variables declaration//GEN-END:variables
}
