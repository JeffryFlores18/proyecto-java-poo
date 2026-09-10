package Vistas_Tienda;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author PC
 */
public class C_Productos extends javax.swing.JPanel {

    /**
     * Creates new form B_Resumen
     */
    public C_Productos() {
        initComponents();
        DefaultTableModel modelo = (DefaultTableModel) tableProductos.getModel();
        
        modelo.addRow(new Object[]{""});
                modelo.addRow(new Object[]{""});
        modelo.addRow(new Object[]{""});
                        modelo.addRow(new Object[]{""});

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductos = new javax.swing.JTable();
        btnVerDetalles = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();

        jLabel2.setText("PRODUCTOS");

        jButton1.setText("ACTUALIZAR STOCK");

        jButton2.setText("AGREGAR NUEVO PRODUCTO");

        jButton3.setText("BUSCAR ID");

        jButton4.setText("BUSCAR PRODUCTO");

        btnEditar.setText("EDITAR");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TIPO PRENDA", "POLO", "SHORT", "CAMISA", "BLUSA", "VESTIDO", "PANTALON" }));

        tableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "TIPO PRENDA", "DESCRIPCION", "STOCK", "PRECIO", "DETALLES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProductos);

        btnVerDetalles.setText("VER DETALLES");
        btnVerDetalles.addActionListener(this::btnVerDetallesActionPerformed);

        btnAgregarProducto.setText("AGREGAR NUEVO PRODUCTO");
        btnAgregarProducto.addActionListener(this::btnAgregarProductoActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(0, 21, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(30, 30, 30)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnAgregarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerDetalles)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 79, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditar)
                            .addComponent(btnVerDetalles)
                            .addComponent(btnAgregarProducto))
                        .addGap(27, 27, 27))))
        );

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

    private void tableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductosMouseClicked

    }//GEN-LAST:event_tableProductosMouseClicked

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        /*int contadorSeleccionados = 0;
        int filaSeleccionada = -1;
        int columnaCheckBox = 6;

        for (int i = 0; i < tableProductos.getRowCount(); i++) {
            Object valor = tableProductos.getValueAt(i, columnaCheckBox);

            // Verificar si el valor no es nulo y es true
            if (valor != null && (Boolean) valor) {
                contadorSeleccionados++;
                filaSeleccionada = i; // Guardar el índice de la fila elegida
            }
        }
        if (contadorSeleccionados == 1) {
            String idProducto = tableProductos.getValueAt(filaSeleccionada, 0).toString();

            A_Dashboard dashboard = (A_Dashboard) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (dashboard != null) {
                dashboard.mostrarPanel(new C_Productos_DetallesProducto());
            }
        } else if (contadorSeleccionados == 0) {
            JOptionPane.showMessageDialog(null, "Ningun producto fue seleccionado");
        } else {
            JOptionPane.showMessageDialog(null, "Se selecciono mas de un producto");
        }
        //A_Dashboard dashboard = (A_Dashboard) javax.swing.SwingUtilities.getWindowAncestor(this);
        //if (dashboard != null && tableProductos.) {
        //    dashboard.mostrarPanel(new C_Productos_DetallesProducto()); }
*/
    int filaSeleccionada = tableProductos.getSelectedRow();

    if (filaSeleccionada != -1) {
        String idProducto = tableProductos.getValueAt(filaSeleccionada, 0).toString();

        A_Dashboard dashboard = (A_Dashboard) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (dashboard != null) {
            dashboard.mostrarPanel(new C_Productos_DetallesProducto());
        }
    } else {
        JOptionPane.showMessageDialog(null,"No se selecciono algun producto de la tabla");
    }
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
          A_Dashboard dashboard = (A_Dashboard) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (dashboard != null) {
            dashboard.mostrarPanel(new C_Productos_Nuevo_Producto());
        }   // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableProductos;
    // End of variables declaration//GEN-END:variables
}
