package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class E_Ventas extends javax.swing.JPanel {

   private boolean modoEditar = false;

public E_Ventas() {
        initComponents();

    configurarTabla();

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    modelo.setRowCount(0);

    txtTotal.setText("0.00");
}
private int obtenerOCrearCliente(Connection con) throws SQLException {

    String dni =
            txtDni.getText().trim();

    String nombre =
            txtNombre.getText().trim();

    String telefono =
            txtTelefono.getText().trim();

    // Primero buscar cliente por DNI
    String sqlBuscar = """
        SELECT id_cliente
        FROM cliente
        WHERE dni = ?
    """;

    try (
        PreparedStatement ps =
                con.prepareStatement(sqlBuscar)
    ) {

        ps.setString(1, dni);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id_cliente");
            }
        }
    }

    // Si no existe, crear cliente
    String sqlInsertar = """
        INSERT INTO cliente
        (
            dni,
            nombre,
            telefono
        )
        VALUES (?, ?, ?)
    """;

    try (
        PreparedStatement ps =
                con.prepareStatement(
                        sqlInsertar,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                )
    ) {

        ps.setString(1, dni);
        ps.setString(2, nombre);
        ps.setString(3, telefono);

        ps.executeUpdate();

        try (ResultSet claves = ps.getGeneratedKeys()) {

            if (claves.next()) {
                return claves.getInt(1);
            }
        }
    }

    throw new SQLException(
            "No se pudo obtener el cliente."
    );
    
}
private void realizarVenta() {

    double total =
            Double.parseDouble(
                    txtTotal.getText()
            );

    // Temporalmente usuario 1.
    // Después podemos obtenerlo del Login.
    int idUsuario = 1;

    Connection con = null;

    try {

        con = conexionLiv.conectar();

        con.setAutoCommit(false);

        // Buscar o crear automáticamente al cliente
        int idCliente =
                obtenerOCrearCliente(con);

        String sqlVenta = """
            INSERT INTO venta
            (
                id_cliente,
                id_usuario,
                fecha,
                hora,
                total
            )
            VALUES
            (
                ?,
                ?,
                CURRENT_DATE,
                CURRENT_TIME,
                ?
            )
        """;

        PreparedStatement psVenta =
                con.prepareStatement(
                        sqlVenta,
                        java.sql.Statement.RETURN_GENERATED_KEYS
                );

        psVenta.setInt(
                1,
                idCliente
        );

        psVenta.setInt(
                2,
                idUsuario
        );

        psVenta.setDouble(
                3,
                total
        );

        psVenta.executeUpdate();

        ResultSet claves =
                psVenta.getGeneratedKeys();

        if (!claves.next()) {

            con.rollback();

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la venta."
            );

            return;
        }

        int idVenta =
                claves.getInt(1);

        guardarDetalles(
                con,
                idVenta
        );

        con.commit();

        JOptionPane.showMessageDialog(
                this,
                "Venta registrada correctamente.\n"
                + "Venta N°: "
                + idVenta
                + "\nTotal: S/ "
                + String.format("%.2f", total)
        );

        limpiarVenta();

    } catch (SQLException e) {

        try {

            if (con != null) {
                con.rollback();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(
                this,
                "Error al realizar la venta:\n"
                + e.getMessage()
        );

    } finally {

        try {

            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
private void guardarDetalles(
        Connection con,
        int idVenta)
        throws SQLException {

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    String sql = """
        INSERT INTO detalle_venta
        (
            id_venta,
            id_producto,
            cantidad,
            precio_unitario,
            subtotal
        )
        VALUES (?, ?, ?, ?, ?)
    """;

    try (
        PreparedStatement ps =
                con.prepareStatement(sql)
    ) {

        for (int i = 0;
                i < modelo.getRowCount();
                i++) {

            int idProducto =
                    Integer.parseInt(
                            modelo
                                    .getValueAt(i, 0)
                                    .toString()
                    );

            int cantidad =
                    Integer.parseInt(
                            modelo
                                    .getValueAt(i, 4)
                                    .toString()
                    );

            double precio =
                    Double.parseDouble(
                            modelo
                                    .getValueAt(i, 5)
                                    .toString()
                    );

            double subtotal =
                    Double.parseDouble(
                            modelo
                                    .getValueAt(i, 6)
                                    .toString()
                    );

            ps.setInt(
                    1,
                    idVenta
            );

            ps.setInt(
                    2,
                    idProducto
            );

            ps.setInt(
                    3,
                    cantidad
            );

            ps.setDouble(
                    4,
                    precio
            );

            ps.setDouble(
                    5,
                    subtotal
            );

            ps.addBatch();
        }

        ps.executeBatch();
    }
}
private void limpiarVenta() {

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    modelo.setRowCount(0);

    txtDni.setText("");
    txtNombre.setText("");
    txtTelefono.setText("");

    txtTotal.setText("0.00");

    modoEditar = false;
}
private void calcularTotal() {

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    double total = 0;

    for (int i = 0; i < modelo.getRowCount(); i++) {

        Object valor = modelo.getValueAt(i, 6);

        if (valor != null) {

            total += Double.parseDouble(
                    valor.toString()
            );
        }
    }

    txtTotal.setText(
            String.format("%.2f", total)
    );
}
    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID_Producto",
                    "Producto",
                    "Descripcion",
                    "Tipo prenda",
                    "Cantidad",
                    "Precio",
                    "Subtotal"
                }
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {

                // Solo cantidad puede modificarse
                // y solo después de presionar Editar
                return modoEditar && column == 4;
            }
        };

        btnContenido.setModel(modelo);

        modelo.addTableModelListener(e -> {

            if (e.getType()
                    == javax.swing.event.TableModelEvent.UPDATE
                    && e.getColumn() == 4) {

                int fila = e.getFirstRow();

                if (fila < 0 || fila >= modelo.getRowCount()) {
                    return;
                }

                try {

                    Object valorCantidad =
                            modelo.getValueAt(fila, 4);

                    Object valorPrecio =
                            modelo.getValueAt(fila, 5);

                    if (valorCantidad == null
                            || valorPrecio == null) {
                        return;
                    }

                    int cantidad = Integer.parseInt(
                            valorCantidad.toString()
                    );

                    double precio = Double.parseDouble(
                            valorPrecio.toString()
                    );

                    if (cantidad <= 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "La cantidad debe ser mayor a 0."
                        );

                        modelo.setValueAt(1, fila, 4);

                        return;
                    }

                    double subtotal =
                            cantidad * precio;

                    modelo.setValueAt(
                            subtotal,
                            fila,
                            6
                    );
                    calcularTotal();
                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Ingrese una cantidad válida."
                    );

                    modelo.setValueAt(1, fila, 4);
                }
            }
        });
    }

    public void agregarProducto(
        int idProducto,
        String tipoPrenda,
        String descripcion,
        double precio) {

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    for (int i = 0; i < modelo.getRowCount(); i++) {

        int idExistente =
                Integer.parseInt(
                        modelo.getValueAt(i, 0).toString()
                );

        if (idExistente == idProducto) {

            int cantidadActual =
                    Integer.parseInt(
                            modelo.getValueAt(i, 4).toString()
                    );

            int nuevaCantidad =
                    cantidadActual + 1;

            modelo.setValueAt(
                    nuevaCantidad,
                    i,
                    4
            );

            modelo.setValueAt(
                    nuevaCantidad * precio,
                    i,
                    6
            );

            calcularTotal();

            return;
        }
    }

    modelo.addRow(new Object[]{
        idProducto,
        tipoPrenda,
        descripcion,
        tipoPrenda,
        1,
        precio,
        precio
    });

    calcularTotal();
}    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIngresosVentas = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnContenido = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();

        jLabel2.setText("VENTAS");

        jLabel1.setText("DNI");

        jLabel3.setText("NOMBRE");

        jLabel5.setText("TELEFONO");

        btnContenido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_Producto", "Producto", "Descripcion", "Tipo prenda", "Cantidad", "Precio", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(btnContenido);

        btnAgregar.setText("Agregar Producto");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnVenta.setText("Realizar venta");
        btnVenta.addActionListener(this::btnVentaActionPerformed);

        txtTotal.setEnabled(false);

        javax.swing.GroupLayout txtIngresosVentasLayout = new javax.swing.GroupLayout(txtIngresosVentas);
        txtIngresosVentas.setLayout(txtIngresosVentasLayout);
        txtIngresosVentasLayout.setHorizontalGroup(
            txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDni, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(txtNombre)
                    .addComponent(txtTelefono))
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnEditar)
                        .addContainerGap(82, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtIngresosVentasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)
                        .addGap(143, 143, 143))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtIngresosVentasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnVenta)
                .addGap(60, 60, 60)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        txtIngresosVentasLayout.setVerticalGroup(
            txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel4)
                        .addGap(77, 77, 77)
                        .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnEditar))
                        .addGap(28, 28, 28)
                        .addComponent(btnActualizar))
                    .addGroup(txtIngresosVentasLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(txtIngresosVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVenta)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtIngresosVentas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtIngresosVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
    JDialog ventana =
                new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Agregar producto",
                        java.awt.Dialog.ModalityType.APPLICATION_MODAL
                );

        E_Ventas_Agregar panel =
                new E_Ventas_Agregar(
                        this,
                        ventana
                );

        ventana.setContentPane(panel);

        ventana.pack();

        ventana.setLocationRelativeTo(this);

        ventana.setVisible(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       int fila =
                btnContenido.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto de la tabla."
            );

            return;
        }

        modoEditar = true;

        btnContenido.editCellAt(
                fila,
                4
        );

        btnContenido.requestFocus();

        JOptionPane.showMessageDialog(
                this,
                "Ahora puede modificar la cantidad."
        );
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
    
        if (btnContenido.isEditing()) {

            btnContenido
                    .getCellEditor()
                    .stopCellEditing();
        }

        modoEditar = false;

        btnContenido.repaint();

        JOptionPane.showMessageDialog(
                this,
                "Cambios actualizados."
        );
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
         String dni =
            txtDni.getText().trim();

    String nombre =
            txtNombre.getText().trim();

    String telefono =
            txtTelefono.getText().trim();

    DefaultTableModel modelo =
            (DefaultTableModel) btnContenido.getModel();

    if (dni.isEmpty()
            || nombre.isEmpty()
            || telefono.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Complete los datos del cliente."
        );

        return;
    }

    if (modelo.getRowCount() == 0) {

        JOptionPane.showMessageDialog(
                this,
                "Debe agregar al menos un producto."
        );

        return;
    }

    double total =
            Double.parseDouble(
                    txtTotal.getText()
            );

    int respuesta =
            JOptionPane.showConfirmDialog(
                    this,
                    "Total: S/ "
                    + String.format("%.2f", total)
                    + "\n\n¿Desea realizar la venta?",
                    "Confirmar venta",
                    JOptionPane.YES_NO_OPTION
            );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    realizarVenta();            // TODO add your handling code here:
    }//GEN-LAST:event_btnVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JTable btnContenido;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtDni;
    private javax.swing.JPanel txtIngresosVentas;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
