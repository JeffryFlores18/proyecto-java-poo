package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class Proveedores extends javax.swing.JPanel {

    public Proveedores() {
        initComponents();
        cargarProveedores();
    }

    public void cargarProveedores() {

        DefaultTableModel modelo = (DefaultTableModel) tablaProveedores.getModel();

        modelo.setColumnIdentifiers(new Object[]{
            "ID",
            "RUC",
            "RAZÓN SOCIAL",
            "TELÉFONO",
            "DIRECCIÓN",
            "EMAIL"
        });

        modelo.setRowCount(0);

        String sql = "SELECT id_proveedor, ruc, razon_social, telefono, direccion, email FROM proveedor";

        try {

            Connection conexion = conexionLiv.conectar();

            PreparedStatement sentencia = conexion.prepareStatement(sql);

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {

                modelo.addRow(new Object[]{
                    resultado.getInt("id_proveedor"),
                    resultado.getString("ruc"),
                    resultado.getString("razon_social"),
                    resultado.getString("telefono"),
                    resultado.getString("direccion"),
                    resultado.getString("email")
                });
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (Exception e) {

            System.out.println("Error al cargar proveedores: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        javax.swing.JFrame ventana = new javax.swing.JFrame("Proveedores");

        ventana.setContentPane(new Proveedores());

        ventana.setSize(800, 700);

        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAgregarProveedor = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 247, 244));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 1, 36)); // NOI18N
        jLabel1.setText("PROVEEDORES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, 29));

        btnAgregarProveedor.setBackground(new java.awt.Color(202, 125, 117));
        btnAgregarProveedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregarProveedor.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\proveedor\\agregar.png")); // NOI18N
        btnAgregarProveedor.setText("AGREGAR PROVEEDOR");
        btnAgregarProveedor.setFocusPainted(false);
        btnAgregarProveedor.addActionListener(this::btnAgregarProveedorActionPerformed);
        jPanel1.add(btnAgregarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, 48));

        btnBuscar.setBackground(new java.awt.Color(202, 125, 117));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\proveedor\\buscar.png")); // NOI18N
        btnBuscar.setText("BUSCAR POR RUC");
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 180, 48));

        btnEditar.setBackground(new java.awt.Color(202, 125, 117));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\proveedor\\icono_editar_negro_32x32.png")); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(this::btnEditarActionPerformed);
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 120, 48));

        btnEliminar.setBackground(new java.awt.Color(202, 125, 117));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon("D:\\Users\\Usuario\\Documents\\NetBeansProjects\\POO-2026-I\\proyecto-java-poo\\src\\ImagenProveedor\\proveedor\\eliminar.png")); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 130, 48));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(700, 600));

        tablaProveedores.setForeground(new java.awt.Color(55, 40, 35));
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaProveedores);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 680, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProveedorActionPerformed
        javax.swing.JFrame ventana
                = new javax.swing.JFrame("Agregar Proveedor");

        ventana.setContentPane(new ProveedorAgregar());

        ventana.setSize(800, 700);

        ventana.setLocationRelativeTo(null);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {

                cargarProveedores();
            }
        });

        ventana.setVisible(true);
    }//GEN-LAST:event_btnAgregarProveedorActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        javax.swing.JFrame ventana = new javax.swing.JFrame("Buscar Proveedor");

        ventana.setContentPane(new ProveedorBuscarPorRuc());

        ventana.setSize(800, 700);

        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        javax.swing.JFrame ventana = new javax.swing.JFrame("Editar Proveedor");

        ventana.setContentPane(new ProveedorEditar());

        ventana.setSize(800, 700);

        ventana.setLocationRelativeTo(null);

        ventana.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaProveedores.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Seleccione un proveedor.");
            return;
        }

        int id = Integer.parseInt(
                tablaProveedores.getValueAt(fila, 0).toString()
        );

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este proveedor?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

            String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";

            try {

                Connection conexion = conexionLiv.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);

                sentencia.setInt(1, id);
                sentencia.executeUpdate();

                sentencia.close();
                conexion.close();

                cargarProveedores();

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Proveedor eliminado correctamente.");

            } catch (Exception e) {

                javax.swing.JOptionPane.showMessageDialog(this,
                        "Error al eliminar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProveedores;
    // End of variables declaration//GEN-END:variables
}
