
package Vistas_Tienda;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class D_Clientesp extends javax.swing.JPanel {
   
 
    public D_Clientesp() {
        initComponents();
        this.setSize(800, 700);
        
        EncabezadoTabla();
        cargarClientes();
        
        FechaHora();
//        cargarTabla();
    }
    
    public void cargarClientes(){
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setRowCount(0);
        String sql = "SELECT id_cliente, dni, nombre, telefono "+ "FROM cliente " + "ORDER BY id_cliente";
        try {

        Connection con = conexionLiv.conectar();

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Object[] fila = {

                rs.getInt("id_cliente"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono")
            };

            modelo.addRow(fila);
        }
        lblTotalClientes.setText("Total clientes: "+modelo.getRowCount());
        rs.close();
        ps.close();
        con.close();
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: "+e.getMessage());
        }

    }
    
   private void buscarClientePorDni() {

    String dni = txtBuscarCliente.getText().trim();

    // Si no escribió un DNI, mostramos todos los clientes.
    if (dni.isEmpty() || dni.equals("Ingrese DNI...")) {
        cargarClientes();
        return;
    }

    if (!dni.matches("[0-9]{8}")) {
        JOptionPane.showMessageDialog(
                this,
                "Ingrese un DNI de 8 dígitos.",
                "DNI inválido",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String sql = "SELECT id_cliente, dni, nombre, telefono "
            + "FROM cliente WHERE dni = ?";

    try (Connection con = conexionLiv.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog( this,"No se pudo conectar con la base de datos.");
            return;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                DefaultTableModel modelo =
                        (DefaultTableModel) tblClientes.getModel();

                modelo.setRowCount(0);

                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("id_cliente"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                    });
                }

                lblTotalClientes.setText(
                        "Clientes encontrados: " + modelo.getRowCount()
                );

                if (modelo.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró un cliente con ese DNI."
                    );
                }
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog( this,"Error al buscar cliente: " + e.getMessage()
        );
    }
}
    
    public void EncabezadoTabla() {
        tblClientes.getTableHeader().setFont( new Font("Segoe UI", Font.BOLD, 13) );
        tblClientes.getTableHeader().setBackground( new Color(246, 231, 223) );
        tblClientes.getTableHeader().setForeground( new Color(58, 42, 38) );

        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(100);
         tblClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(300);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(180);
        tblClientes.getTableHeader().setReorderingAllowed(false); // no editar
    }
    
    public void FechaHora() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            java.time.LocalDate fecha = java.time.LocalDate.now();
            java.time.LocalTime hora = java.time.LocalTime.now();

            lblfecha.setText(
                    fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            lblhora.setText(
                    hora.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
        });

        timer.start();

    }
      
    private  D_clientees obtenerClienteSeleccionado(){
        int fila = tblClientes.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog( this,"Seleccione una tabla ");
            return null;
        }
        // obtiene la fila correcta incluso si la tabla esta ordenada
        int filaModelo = tblClientes.convertRowIndexToModel(fila);
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        
        int id = Integer.parseInt(modelo.getValueAt(filaModelo, 0).toString()); 
        String dni = java.util.Objects.toString(modelo.getValueAt(filaModelo, 1) ,"");
        String nombre = java.util.Objects.toString(modelo.getValueAt(filaModelo, 2) ,"");
        String telefono = java.util.Objects.toString(modelo.getValueAt(filaModelo, 3) ,"");
        
        return new D_clientees(id, dni, nombre, telefono);
    }
    
    private  void eliminarClientes(){
        D_clientees cliente = obtenerClienteSeleccionado();
        if(cliente==null){
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea eliminar a este cliente?\n\n"
            + "Nombre: " + cliente.getNombre() + "\n"
            + "DNI: " + cliente.getDni(),
            "Confirmar eliminación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (respuesta!=JOptionPane.YES_OPTION) {
            return;
        }
        String sql = "DELETE FROM cliente WHERE id_cliente = ? ";
        
        try (Connection con = conexionLiv.conectar()){
            if (con == null) {
              JOptionPane.showMessageDialog(  this, "No se pudo conectar con la base de datos." );
            return;
            }
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, cliente.getIDcliente());
                int filas = ps.executeUpdate();
                if (filas>0) {
                   JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente."); 
                }else{
                   JOptionPane.showMessageDialog(this, "Cliente ya no existe en la base de datos."); 
                }
                actualizarClientes();
            }
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog( this,"Error al eliminar cliente: " + e.getMessage());
        }
    }
    
    private void actualizarClientes() {

        txtBuscarCliente.setText("");
        txtBuscarCliente.setForeground(new java.awt.Color(58, 42, 38));

        cargarClientes();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegistrarCliente = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnsalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblTotalClientes = new javax.swing.JLabel();
        lblfecha = new javax.swing.JLabel();
        lblhora = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtBuscarCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel1.setBackground(new java.awt.Color(255, 252, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 252, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(58, 42, 38));
        jLabel1.setText("CLIENTES");
        jLabel1.setAlignmentX(32.0F);
        jLabel1.setAlignmentY(22.0F);
        jLabel1.setMaximumSize(new java.awt.Dimension(180, 28));
        jLabel1.setMinimumSize(new java.awt.Dimension(180, 28));
        jLabel1.setPreferredSize(new java.awt.Dimension(32, 22));
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 210, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(123, 98, 90));
        jLabel2.setText("Gestione la informacion de sus clientes");
        jLabel2.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel2.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel2.setPreferredSize(new java.awt.Dimension(20, 42));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 62, 220, 30));
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 27, -1, -1));

        btnRegistrarCliente.setBackground(new java.awt.Color(216, 154, 132));
        btnRegistrarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnRegistrarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCliente.setText("+ Registrar Cliente");
        btnRegistrarCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnRegistrarCliente.setFocusPainted(false);
        btnRegistrarCliente.setOpaque(true);
        btnRegistrarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarClienteMouseExited(evt);
            }
        });
        btnRegistrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarClienteActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegistrarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 145, 34));

        btnEditar.setBackground(new java.awt.Color(255, 243, 238));
        btnEditar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(184, 123, 103));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/lapiz17.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnEditar.setFocusPainted(false);
        btnEditar.setOpaque(true);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 80, 34));

        btnEliminar.setBackground(new java.awt.Color(255, 243, 238));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(184, 123, 103));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 100, 85, 34));

        btnActualizar.setBackground(new java.awt.Color(255, 243, 238));
        btnActualizar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(184, 123, 103));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(216, 154, 132)));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, 90, 34));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/iconoCliente68.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 70));

        btnsalir.setBackground(new java.awt.Color(216, 154, 132));
        btnsalir.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnsalir.setForeground(new java.awt.Color(255, 255, 255));
        btnsalir.setText("X");
        btnsalir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnsalir.setFocusPainted(false);
        btnsalir.setOpaque(true);
        btnsalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsalirMouseExited(evt);
            }
        });
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(765, 0, 40, 34));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 150));

        jPanel4.setBackground(new java.awt.Color(241, 209, 196));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTotalClientes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalClientes.setForeground(new java.awt.Color(123, 98, 90));
        lblTotalClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/usuarios.png"))); // NOI18N
        lblTotalClientes.setText("Total clientes: 0");
        jPanel4.add(lblTotalClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 12, 160, 30));

        lblfecha.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblfecha.setForeground(new java.awt.Color(123, 98, 90));
        lblfecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/calendario.png"))); // NOI18N
        lblfecha.setText("03/09/2026");
        jPanel4.add(lblfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 11, 90, 30));

        lblhora.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblhora.setForeground(new java.awt.Color(123, 98, 90));
        lblhora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/hora.png"))); // NOI18N
        lblhora.setText("16:30:45");
        jPanel4.add(lblhora, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 14, 90, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 800, 50));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        tblClientes.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblClientes.setForeground(new java.awt.Color(58, 42, 38));
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "DNI ", "NOMBRE", "TELEFONO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setFillsViewportHeight(true);
        tblClientes.setGridColor(new java.awt.Color(232, 216, 208));
        tblClientes.setRowHeight(32);
        tblClientes.setSelectionBackground(new java.awt.Color(241, 209, 196));
        tblClientes.setSelectionForeground(new java.awt.Color(58, 42, 38));
        tblClientes.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tblClientes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 720, 360));

        txtBuscarCliente.setForeground(new java.awt.Color(169, 154, 148));
        txtBuscarCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBuscarCliente.setText(" Ingrese DNI...");
        txtBuscarCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        txtBuscarCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarClienteFocusLost(evt);
            }
        });
        txtBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 230, 40));

        btnBuscarCliente.setBackground(new java.awt.Color(255, 243, 238));
        btnBuscarCliente.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnBuscarCliente.setForeground(new java.awt.Color(184, 123, 103));
        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/buscar.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnBuscarCliente.setDisabledIcon(null);
        btnBuscarCliente.setDisabledSelectedIcon(null);
        btnBuscarCliente.setFocusPainted(false);
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 100, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(58, 42, 38));
        jLabel3.setText("Buscar por DNI o RUC:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 180, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarClienteMouseEntered
        btnRegistrarCliente.setBackground(new Color(199, 132, 108));
    }//GEN-LAST:event_btnRegistrarClienteMouseEntered

    private void btnRegistrarClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarClienteMouseExited
        btnRegistrarCliente.setBackground(new Color(216, 154, 132));
    }//GEN-LAST:event_btnRegistrarClienteMouseExited

    private void btnRegistrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarClienteActionPerformed
        D_FromCliente formulario = new D_FromCliente(this);
        formulario.setVisible(true);
    }//GEN-LAST:event_btnRegistrarClienteActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        D_clientees cliente = obtenerClienteSeleccionado();
        
        if (cliente==null) {
            return;
        }
        D_FromCliente formularios = new D_FromCliente(this, cliente);
        formularios.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      eliminarClientes();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
      actualizarClientes();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnsalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsalirMouseEntered

    private void btnsalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsalirMouseExited

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnsalirActionPerformed

    private void txtBuscarClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarClienteFocusGained
        if (txtBuscarCliente.getText().equals(" Ingrese DNI...")) {
            txtBuscarCliente.setText("");
        }
         txtBuscarCliente.setForeground(new Color(58, 42, 38));
    }//GEN-LAST:event_txtBuscarClienteFocusGained

    private void txtBuscarClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarClienteFocusLost
        if (txtBuscarCliente.getText().trim().isEmpty()) {
            txtBuscarCliente.setText(" Ingrese DNI...");
        }
        txtBuscarCliente.setForeground(new Color(168,154,148));
    }//GEN-LAST:event_txtBuscarClienteFocusLost

    private void txtBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClienteActionPerformed

    }//GEN-LAST:event_txtBuscarClienteActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
       buscarClientePorDni();

    }//GEN-LAST:event_btnBuscarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegistrarCliente;
    private javax.swing.JButton btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalClientes;
    private javax.swing.JLabel lblfecha;
    private javax.swing.JLabel lblhora;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscarCliente;
    // End of variables declaration//GEN-END:variables
}
