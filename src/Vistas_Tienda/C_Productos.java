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
        cargarIconoProducto();                
    }
    private void cargarIconoProducto() {

     javax.swing.ImageIcon icono =
            new javax.swing.ImageIcon(
                    getClass().getResource(
                            "/ImagenProductos/producto.png"
                    )
            );

    java.awt.Image imagen =
            icono.getImage().getScaledInstance(
                    100,
                    100,
                    java.awt.Image.SCALE_SMOOTH
            );

    lblProducto.setIcon(
            new javax.swing.ImageIcon(imagen)
    );
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
        panel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblProducto = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        panel3 = new javax.swing.JPanel();
        btnBuscarID = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        btnActualizarStock = new javax.swing.JButton();
        btnAgregarNuevoProducto = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(255, 252, 250));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(58, 42, 38));
        jLabel3.setText("PRODUCTOS");
        jLabel3.setAlignmentX(32.0F);
        jLabel3.setAlignmentY(22.0F);
        jLabel3.setMaximumSize(new java.awt.Dimension(180, 28));
        jLabel3.setMinimumSize(new java.awt.Dimension(180, 28));
        jLabel3.setPreferredSize(new java.awt.Dimension(32, 22));
        panel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 210, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(123, 98, 90));
        jLabel2.setText("Gestione la informacion de los productos");
        jLabel2.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel2.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel2.setPreferredSize(new java.awt.Dimension(20, 42));
        panel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 320, 30));

        lblProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenProductos/producto.png"))); // NOI18N
        panel1.add(lblProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 100));

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 130));

        panel2.setBackground(new java.awt.Color(255, 252, 250));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        tablaProductos.setAutoCreateRowSorter(true);
        tablaProductos.setBackground(new java.awt.Color(255, 252, 250));
        tablaProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        tablaProductos.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tablaProductos.setForeground(new java.awt.Color(58, 42, 38));
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "PRODUCTO", "TIPO PRENDA", "DESCRIPCION", "STOCK", "PRECIO", "DETALLES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setFillsViewportHeight(true);
        tablaProductos.setGridColor(new java.awt.Color(232, 216, 208));
        tablaProductos.setRowHeight(32);
        tablaProductos.setSelectionBackground(new java.awt.Color(241, 209, 196));
        tablaProductos.setSelectionForeground(new java.awt.Color(58, 42, 38));
        tablaProductos.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(tablaProductos);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 830, 450));

        panel3.setBackground(new java.awt.Color(255, 252, 250));

        btnBuscarID.setBackground(new java.awt.Color(255, 243, 238));
        btnBuscarID.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnBuscarID.setForeground(new java.awt.Color(184, 123, 103));
        btnBuscarID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/buscar.png"))); // NOI18N
        btnBuscarID.setText("Buscar ID");
        btnBuscarID.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnBuscarID.setDisabledIcon(null);
        btnBuscarID.setDisabledSelectedIcon(null);
        btnBuscarID.setFocusPainted(false);
        btnBuscarID.addActionListener(this::btnBuscarIDActionPerformed);

        btnBuscarProducto.setBackground(new java.awt.Color(255, 243, 238));
        btnBuscarProducto.setForeground(new java.awt.Color(184, 123, 103));
        btnBuscarProducto.setText("BUSCAR PRODUCTO");
        btnBuscarProducto.addActionListener(this::btnBuscarProductoActionPerformed);

        btnActualizarStock.setBackground(new java.awt.Color(255, 243, 238));
        btnActualizarStock.setForeground(new java.awt.Color(184, 123, 103));
        btnActualizarStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/actualizar.png"))); // NOI18N
        btnActualizarStock.setText("ACTUALIZAR ");
        btnActualizarStock.addActionListener(this::btnActualizarStockActionPerformed);

        btnAgregarNuevoProducto.setBackground(new java.awt.Color(255, 243, 238));
        btnAgregarNuevoProducto.setForeground(new java.awt.Color(184, 123, 103));
        btnAgregarNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenProveedor/proveedoragregar.png"))); // NOI18N
        btnAgregarNuevoProducto.setText("AGREGAR NUEVO PRODUCTO");
        btnAgregarNuevoProducto.addActionListener(this::btnAgregarNuevoProductoActionPerformed);

        btnEditar.setBackground(new java.awt.Color(255, 243, 238));
        btnEditar.setForeground(new java.awt.Color(184, 123, 103));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/lapiz17.png"))); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(255, 243, 238));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(184, 123, 103));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(btnActualizarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnAgregarNuevoProducto)
                .addGap(69, 69, 69))
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnBuscarProducto)
                .addGap(150, 150, 150)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(btnEditar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 790, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIDActionPerformed
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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaProductos.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Seleccione un producto para eliminar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int idProducto = Integer.parseInt(
            tablaProductos.getValueAt(fila, 0).toString()
    );

    String producto = tablaProductos
            .getValueAt(fila, 1)
            .toString();

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que desea eliminar el producto?\n\n"
            + "ID: " + idProducto
            + "\nProducto: " + producto,
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    String sql = "DELETE FROM producto WHERE id_producto = ?";

    try (
        Connection con = conexionLiv.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, idProducto);

        int resultado = ps.executeUpdate();

        if (resultado > 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Producto eliminado correctamente."
            );

            mostrarProductos("TODO", "");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el producto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al eliminar el producto:\n"
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarStockActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        
        modelo.addRow(new Object[]{""});
                modelo.addRow(new Object[]{""});
        modelo.addRow(new Object[]{""});
                        modelo.addRow(new Object[]{""});
                        mostrarProductos("TODO", "");
    }//GEN-LAST:event_btnActualizarStockActionPerformed

    private void btnAgregarNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoActionPerformed
       A_Dashboard dashboard =
            (A_Dashboard)
            javax.swing.SwingUtilities
                    .getWindowAncestor(this);

    if (dashboard != null) {

        dashboard.mostrarPanel(
                new C_Productos_Nuevo_Producto()
        );
    }
    }//GEN-LAST:event_btnAgregarNuevoProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarStock;
    private javax.swing.JButton btnAgregarNuevoProducto;
    private javax.swing.JButton btnBuscarID;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
