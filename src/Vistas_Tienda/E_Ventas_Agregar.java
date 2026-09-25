
package Vistas_Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class E_Ventas_Agregar extends javax.swing.JPanel {
    
private E_Ventas ventas;
private JDialog ventana;

    public E_Ventas_Agregar() {
        initComponents();
    }
public E_Ventas_Agregar(
        E_Ventas ventas,
        JDialog ventana) {

    initComponents();

    this.ventas = ventas;
    this.ventana = ventana;

    cargarProductos();
}
private void buscarProducto(String texto) {

    DefaultTableModel modelo =
            (DefaultTableModel) tablaProductos.getModel();

    modelo.setRowCount(0);

    String sql = """
        SELECT
            id_producto,
            tipo_prenda,
            descripcion,
            stock_inventario,
            precio
        FROM producto
        WHERE tipo_prenda LIKE ?
           OR descripcion LIKE ?
        ORDER BY id_producto
    """;

    try (
        Connection con = conexionLiv.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        String busqueda = "%" + texto + "%";

        ps.setString(1, busqueda);
        ps.setString(2, busqueda);

        try (ResultSet rs = ps.executeQuery()) {

            boolean encontrado = false;

            while (rs.next()) {

                encontrado = true;

                modelo.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("tipo_prenda"),
                    rs.getString("tipo_prenda"),
                    rs.getString("descripcion"),
                    rs.getInt("stock_inventario"),
                    rs.getDouble("precio"),
                    "Agregar"
                });
            }

            if (!encontrado) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontraron productos."
                );

                cargarProductos();
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al buscar producto:\n"
                + e.getMessage()
        );
    }
}



private void buscarProductoPorID(int id) {

    DefaultTableModel modelo =
            (DefaultTableModel) tablaProductos.getModel();

    modelo.setRowCount(0);

    String sql = """
        SELECT
            id_producto,
            tipo_prenda,
            descripcion,
            stock_inventario,
            precio
        FROM producto
        WHERE id_producto = ?
    """;

    try (
        Connection con = conexionLiv.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("tipo_prenda"),
                    rs.getString("tipo_prenda"),
                    rs.getString("descripcion"),
                    rs.getInt("stock_inventario"),
                    rs.getDouble("precio"),
                    "Agregar"
                });

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No existe un producto con ID " + id
                );

                cargarProductos();
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al buscar producto:\n"
                + e.getMessage()
        );
    }
}

private void cargarProductos() {

    DefaultTableModel modelo =
            (DefaultTableModel) tablaProductos.getModel();

    modelo.setRowCount(0);

    String sql = """
        SELECT
            id_producto,
            tipo_prenda,
            descripcion,
            stock_inventario,
            precio
        FROM producto
        ORDER BY id_producto
    """;

    try (
        Connection con = conexionLiv.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            modelo.addRow(new Object[]{

                rs.getInt("id_producto"),

                // PRODUCTO
                rs.getString("tipo_prenda"),

                // TIPO PRENDA
                rs.getString("tipo_prenda"),

                rs.getString("descripcion"),

                rs.getInt("stock_inventario"),

                rs.getDouble("precio"),

                "Agregar"
            });
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error al cargar productos:\n"
            + e.getMessage()
        );
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBuscarID = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();

        btnBuscarID.setText("BUSCAR ID");
        btnBuscarID.addActionListener(this::btnBuscarIDActionPerformed);

        btnBuscarProducto.setText("BUSCAR PRODUCTO");
        btnBuscarProducto.addActionListener(this::btnBuscarProductoActionPerformed);

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

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBuscarID)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)
                        .addGap(101, 101, 101))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarID)
                    .addComponent(btnBuscarProducto)
                    .addComponent(btnActualizar))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIDActionPerformed
         String dato = JOptionPane.showInputDialog(
            this,
            "Ingrese el ID del producto:"
    );

    if (dato == null) {
        return;
    }

    dato = dato.trim();

    if (dato.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Debe ingresar un ID."
        );
        return;
    }

    try {

        int id = Integer.parseInt(dato);

        buscarProductoPorID(id);

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this,
                "El ID debe ser un número."
        );
    }
    }//GEN-LAST:event_btnBuscarIDActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
            String texto = JOptionPane.showInputDialog(
            this,
            "Ingrese producto o descripción:"
    );

    if (texto == null) {
        return;
    }

    texto = texto.trim();

    if (texto.isEmpty()) {

        cargarProductos();
        return;
    }

    buscarProducto(texto);
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
         if (evt.getClickCount() != 2) {
        return;
    }

    int fila = tablaProductos.getSelectedRow();

    if (fila == -1) {
        return;
    }

    int idProducto = Integer.parseInt(
        tablaProductos.getValueAt(fila, 0).toString()
    );

    String tipoPrenda =
        tablaProductos.getValueAt(fila, 2).toString();

    String descripcion =
        tablaProductos.getValueAt(fila, 3).toString();

    int stock = Integer.parseInt(
        tablaProductos.getValueAt(fila, 4).toString()
    );

    double precio = Double.parseDouble(
        tablaProductos.getValueAt(fila, 5).toString()
    );

    if (stock <= 0) {

        JOptionPane.showMessageDialog(
            this,
            "Este producto no tiene stock disponible."
        );

        return;
    }

    ventas.agregarProducto(
        idProducto,
        tipoPrenda,
        descripcion,
        precio
    );

    ventana.dispose();
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
         cargarProductos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscarID;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
