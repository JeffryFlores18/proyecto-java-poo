package Vistas_Tienda;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;


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

        panelGeneral = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JPanel();
        btnVenta = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnContenido = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(800, 700));

        panelGeneral.setBackground(new java.awt.Color(255, 252, 250));

        panel1.setBackground(new java.awt.Color(255, 252, 250));

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(58, 42, 38));
        jLabel6.setText("VENTAS");
        jLabel6.setAlignmentX(32.0F);
        jLabel6.setAlignmentY(22.0F);
        jLabel6.setMaximumSize(new java.awt.Dimension(180, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(180, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(32, 22));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 263, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        panel2.setBackground(new java.awt.Color(255, 252, 250));
        panel2.setPreferredSize(new java.awt.Dimension(800, 244));
        panel2.setLayout(new java.awt.GridLayout(3, 3, 3, 0));

        jLabel1.setText("DNI");
        panel2.add(jLabel1);

        txtDni.addActionListener(this::txtDniActionPerformed);
        panel2.add(txtDni);

        btnAgregar.setBackground(new java.awt.Color(255, 243, 238));
        btnAgregar.setForeground(new java.awt.Color(184, 123, 103));
        btnAgregar.setText("Agregar Producto");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);
        panel2.add(btnAgregar);

        jLabel3.setText("NOMBRE");
        panel2.add(jLabel3);
        panel2.add(txtNombre);

        btnActualizar.setBackground(new java.awt.Color(255, 243, 238));
        btnActualizar.setForeground(new java.awt.Color(184, 123, 103));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        panel2.add(btnActualizar);

        jLabel5.setText("TELEFONO");
        panel2.add(jLabel5);
        panel2.add(txtTelefono);

        btnEditar.setBackground(new java.awt.Color(255, 243, 238));
        btnEditar.setForeground(new java.awt.Color(184, 123, 103));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);
        panel2.add(btnEditar);

        btnEliminar.setBackground(new java.awt.Color(255, 252, 250));
        btnEliminar.setPreferredSize(new java.awt.Dimension(800, 488));

        btnVenta.setBackground(new java.awt.Color(255, 243, 238));
        btnVenta.setForeground(new java.awt.Color(184, 123, 103));
        btnVenta.setText("Realizar venta");
        btnVenta.addActionListener(this::btnVentaActionPerformed);

        txtTotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Total"));
        txtTotal.setEnabled(false);

        jButton1.setBackground(new java.awt.Color(255, 243, 238));
        jButton1.setForeground(new java.awt.Color(184, 123, 103));
        jButton1.setText("Eliminar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout btnEliminarLayout = new javax.swing.GroupLayout(btnEliminar);
        btnEliminar.setLayout(btnEliminarLayout);
        btnEliminarLayout.setHorizontalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEliminarLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        btnEliminarLayout.setVerticalGroup(
            btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEliminarLayout.createSequentialGroup()
                .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnEliminarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(btnEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(btnEliminarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout panelGeneralLayout = new javax.swing.GroupLayout(panelGeneral);
        panelGeneral.setLayout(panelGeneralLayout);
        panelGeneralLayout.setHorizontalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGeneralLayout.setVerticalGroup(
            panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGeneralLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jLabel4))
                    .addGroup(panelGeneralLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            || telefono.isEmpty()
            || dni.length()!=8) {

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    int fila = btnContenido.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Seleccione un producto de la venta.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String producto = btnContenido
            .getValueAt(fila, 1)
            .toString();

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que desea quitar este producto de la venta?\n\n"
            + "Producto: " + producto,
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == JOptionPane.YES_OPTION) {

        DefaultTableModel modelo =
                (DefaultTableModel) btnContenido.getModel();

        modelo.removeRow(fila);

        calcularTotal();

        JOptionPane.showMessageDialog(
                this,
                "Producto eliminado de la venta."
        );
    }            // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JTable btnContenido;
    private javax.swing.JButton btnEditar;
    private javax.swing.JPanel btnEliminar;
    private javax.swing.JButton btnVenta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
