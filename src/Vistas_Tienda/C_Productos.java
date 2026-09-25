package Vistas_Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
private boolean modoEdicion = false;
    /**
     * Creates new form B_Resumen
     */
    public C_Productos() {
        initComponents();
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        
        modelo.addRow(new Object[]{""});
                modelo.addRow(new Object[]{""});
        modelo.addRow(new Object[]{""});
                        modelo.addRow(new Object[]{""});
                        mostrarProductos("TODO", "");

    }
public void mostrarProductos(String buscarPor, String valorBusqueda) {
    DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
    modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

    String sql = "SELECT * FROM producto";
    
    // Configurar la consulta según el filtro
    if (buscarPor.equals("ID")) {
        sql += " WHERE id_producto = ?";
    } else if (buscarPor.equals("PRODUCTO")) {
        sql += " WHERE tipo_prenda LIKE ? OR descripcion LIKE ?";
    }

    try {
        Connection con = conexionLiv.conectar();
        PreparedStatement ps = con.prepareStatement(sql);

        // Inyectar los valores de búsqueda
        if (buscarPor.equals("ID")) {
            ps.setInt(1, Integer.parseInt(valorBusqueda));
        } else if (buscarPor.equals("PRODUCTO")) {
            ps.setString(1, "%" + valorBusqueda + "%");
            ps.setString(2, "%" + valorBusqueda + "%");
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[7];
            fila[0] = rs.getInt("id_producto");
            fila[1] = rs.getString("tipo_prenda"); // PRODUCTO
            fila[2] = rs.getString("tipo_prenda"); // TIPO PRENDA
            fila[3] = rs.getString("descripcion"); // DESCRIPCION
            fila[4] = rs.getInt("stock_inventario"); // STOCK
            fila[5] = String.format("S/ %.2f", rs.getDouble("precio")); // PRECIO
            fila[6] = ""; // DETALLES (Se deja vacío como solicitaste)

            modelo.addRow(fila);
        }
        
        rs.close();
        ps.close();
        con.close();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnBuscarID = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnAgregarProducto = new javax.swing.JButton();

        jLabel2.setText("PRODUCTOS");

        jButton1.setText("ACTUALIZAR STOCK");

        jButton2.setText("AGREGAR NUEVO PRODUCTO");

        btnBuscarID.setText("BUSCAR ID");
        btnBuscarID.addActionListener(this::btnBuscarIDActionPerformed);

        btnBuscarProducto.setText("BUSCAR PRODUCTO");
        btnBuscarProducto.addActionListener(this::btnBuscarProductoActionPerformed);

        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        btnAgregarProducto.setText("AGREGAR NUEVO PRODUCTO");
        btnAgregarProducto.addActionListener(this::btnAgregarProductoActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(45, 45, 45)
                            .addComponent(btnAgregarProducto)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnBuscarID)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBuscarProducto)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGap(0, 21, Short.MAX_VALUE))
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
                    .addComponent(btnBuscarID)
                    .addComponent(btnBuscarProducto))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 79, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditar)
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

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked

    }//GEN-LAST:event_tablaProductosMouseClicked

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
          A_Dashboard dashboard = (A_Dashboard) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (dashboard != null) {
            dashboard.mostrarPanel(new C_Productos_Nuevo_Producto());
        }   // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIDActionPerformed
        // TODO add your handling code here:
        String idBusqueda = JOptionPane.showInputDialog(this, "Ingrese el ID del producto a buscar:");
    
    if (idBusqueda != null && !idBusqueda.trim().isEmpty()) {
        try {
            Integer.parseInt(idBusqueda.trim()); // Validar que sea un número
            mostrarProductos("ID", idBusqueda.trim());
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (idBusqueda != null && idBusqueda.trim().isEmpty()) {
        mostrarProductos("TODO", ""); // Recargar toda la tabla si se deja vacío
    }
    }//GEN-LAST:event_btnBuscarIDActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
        String nombreBusqueda = JOptionPane.showInputDialog(this, "Ingrese el nombre o descripción del producto:");
    
    if (nombreBusqueda != null && !nombreBusqueda.trim().isEmpty()) {
        mostrarProductos("PRODUCTO", nombreBusqueda.trim());
    } else if (nombreBusqueda != null && nombreBusqueda.trim().isEmpty()) {
        mostrarProductos("TODO", ""); // Recargar toda la tabla si se deja vacío
    }// TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
// Cambiamos el estado: si era false pasa a true, si era true pasa a false
    modoEdicion = !modoEdicion; 
    
    if (modoEdicion) {
        // --- MODO EDICIÓN ACTIVADO ---
        btnEditar.setText("CANCELAR EDICIÓN"); // Cambiamos el texto del botón
        btnEditar.setBackground(new java.awt.Color(255, 102, 102)); // Opcional: ponerlo rojito
        
        // Creamos un modelo nuevo que SÍ permite editar (excepto el ID)
        javax.swing.table.DefaultTableModel modeloEditable = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "PRODUCTO", "TIPO PRENDA", "DESCRIPCION", "STOCK", "PRECIO", "DETALLES"}
        ) {
            // true significa que la columna se puede escribir, false que está bloqueada
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        // Aplicamos el modelo editable a la tabla
        tablaProductos.setModel(modeloEditable);
        
    } else {
        // --- MODO EDICIÓN DESACTIVADO ---
        btnEditar.setText("EDITAR");
        btnEditar.setBackground(new java.awt.Color(255, 255, 255)); // Color normal
        
        // Creamos un modelo que bloquea absolutamente todo
        javax.swing.table.DefaultTableModel modeloBloqueado = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "PRODUCTO", "TIPO PRENDA", "DESCRIPCION", "STOCK", "PRECIO", "DETALLES"}
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // Todo bloqueado
            }
        };
        
        // Aplicamos el modelo bloqueado a la tabla
        tablaProductos.setModel(modeloBloqueado);
    }
    
    // Como cambiamos el modelo (quedó vacío), volvemos a cargar los datos de la base de datos
    mostrarProductos("TODO", "");
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarID;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
