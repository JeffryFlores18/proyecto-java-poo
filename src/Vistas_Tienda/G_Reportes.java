package Vistas_Tienda;

import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


public class G_Reportes extends javax.swing.JPanel {

    
    public G_Reportes() {
        initComponents();
       
        
    }
    
    public  void generarReporteVentas(){
        if(dcfechaDesde.getDate() == null || dcfechaHasta.getDate()==null){
             JOptionPane.showMessageDialog( this,"Seleccione ambas fechas.","Fechas incompletas",JOptionPane.WARNING_MESSAGE);
          return;
        }
        
        java.sql.Date fechaDesde = new java.sql.Date(dcfechaDesde.getDate().getTime());
        java.sql.Date fechaHasta = new java.sql.Date(dcfechaHasta.getDate().getTime());
        
        if (fechaDesde.after(fechaHasta)) {
            JOptionPane.showMessageDialog(this,"La fecha inicial no puede ser mayor que la fecha final.","Rango de fechas inválido",JOptionPane.WARNING_MESSAGE);
          return;
        }
        
        cargarResumenVentas(fechaDesde, fechaHasta);
        cargarHistorialVentas(fechaDesde, fechaHasta);
    }
    
    private void cargarResumenVentas( java.sql.Date fechaDesde, java.sql.Date fechaHasta) {

    String sql =
            "SELECT "
            + "COALESCE(SUM(total), 0) AS ingresos, "
            + "COUNT(*) AS cantidad_ventas, "
            + "COUNT(DISTINCT id_cliente) AS clientes, "
            + "COALESCE(AVG(total), 0) AS promedio "
            + "FROM venta "
            + "WHERE fecha BETWEEN ? AND ?";

    try {

        Connection con = conexionLiv.conectar();

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, fechaDesde);
        ps.setDate(2, fechaHasta);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            lblingresosTotales.setText(String.format("S/ %.2f",rs.getDouble("ingresos")) );
            lblnumeroVentas.setText(String.valueOf(rs.getInt("cantidad_ventas")));
            lblclientesAten.setText( String.valueOf(rs.getInt("clientes") ));
            lblpromedioVenta.setText(String.format( "S/ %.2f",rs.getDouble("promedio")));
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,"Error al generar el reporte: "+ e.getMessage());
    }
}
    
    private void cargarHistorialVentas( java.sql.Date fechaDesde,java.sql.Date fechaHasta) {

        DefaultTableModel modelo =(DefaultTableModel)tblhistorial.getModel();
        modelo.setRowCount(0);

        String sql =
            "SELECT "
            + "v.id_venta, "
            + "v.fecha, "
            + "v.hora, "
            + "COALESCE(c.nombre, 'Cliente general') AS cliente, "
            + "COALESCE(c.dni, '-') AS dni, "
            + "v.total "
            + "FROM venta v LEFT JOIN cliente c ON v.id_cliente = c.id_cliente "
            + "WHERE v.fecha BETWEEN ? AND ? "
            + "ORDER BY v.fecha, v.hora";

    try {

        Connection con = conexionLiv.conectar();
        PreparedStatement ps =con.prepareStatement(sql);
        ps.setDate(1, fechaDesde);
        ps.setDate(2, fechaHasta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Object[] fila = {
                rs.getInt("id_venta"),
                rs.getDate("fecha"),
                rs.getTime("hora"),
                rs.getString("cliente"),
                rs.getString("dni"),
                String.format( "S/ %.2f", rs.getDouble("total")
                )
            };

            modelo.addRow(fila);
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog( this, "Error al cargar historial: " + e.getMessage()
        );
    }
}
    
    public void fecha(){
        // Establecer la fecha "Desde" en 01/09/2026
        java.util.Calendar calDesde = java.util.Calendar.getInstance();
        calDesde.set(2026, java.util.Calendar.SEPTEMBER, 1);
        dcfechaHasta.setDate(calDesde.getTime());

       // Establecer la fecha "Hasta" en 20/09/2026 (según lo que tenías en tus apuntes)
        java.util.Calendar calHasta = java.util.Calendar.getInstance();
        calHasta.set(2026, java.util.Calendar.SEPTEMBER, 20);
        dcfechaHasta.setDate(calHasta.getTime());
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        btnIngresos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btngenerar = new javax.swing.JButton();
        dcfechaDesde = new com.toedter.calendar.JDateChooser();
        dcfechaHasta = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblingresosTotales = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblnumeroVentas = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblclientesAten = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblpromedioVenta = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblhistorial = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 252, 250));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 42, 38));
        jLabel2.setText("REPORTES");
        jLabel2.setAlignmentX(32.0F);
        jLabel2.setAlignmentY(22.0F);
        jLabel2.setMaximumSize(new java.awt.Dimension(180, 28));
        jLabel2.setMinimumSize(new java.awt.Dimension(180, 28));
        jLabel2.setPreferredSize(new java.awt.Dimension(32, 22));
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 260, 50));

        btnIngresos.setBackground(new java.awt.Color(216, 154, 132));
        btnIngresos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btnIngresos.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresos.setText("Ingresos");
        btnIngresos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btnIngresos.setFocusPainted(false);
        btnIngresos.setOpaque(true);
        btnIngresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngresosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngresosMouseExited(evt);
            }
        });
        btnIngresos.addActionListener(this::btnIngresosActionPerformed);
        add(btnIngresos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 105, 330, 42));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(123, 98, 90));
        jLabel7.setText("Gestione la informacion de sus clientes");
        jLabel7.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel7.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel7.setPreferredSize(new java.awt.Dimension(20, 42));
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 220, 30));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 248, 243));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(58, 42, 38));
        jLabel1.setText("Filtro de periodo");

        jLabel4.setForeground(new java.awt.Color(123, 98, 90));
        jLabel4.setText("Desde: ");
        jLabel4.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel4.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel4.setPreferredSize(new java.awt.Dimension(20, 42));

        jLabel5.setForeground(new java.awt.Color(123, 98, 90));
        jLabel5.setText("Hasta:");
        jLabel5.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel5.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel5.setPreferredSize(new java.awt.Dimension(20, 42));

        btngenerar.setBackground(new java.awt.Color(216, 154, 132));
        btngenerar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        btngenerar.setForeground(new java.awt.Color(255, 255, 255));
        btngenerar.setText("Generar");
        btngenerar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 154, 132), 1, true));
        btngenerar.setFocusPainted(false);
        btngenerar.setOpaque(true);
        btngenerar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngenerarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngenerarMouseExited(evt);
            }
        });
        btngenerar.addActionListener(this::btngenerarActionPerformed);

        dcfechaDesde.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255)));
        dcfechaDesde.setToolTipText("");
        dcfechaDesde.setDateFormatString("dd/MM/yyyy");

        dcfechaHasta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255)));
        dcfechaHasta.setToolTipText("");
        dcfechaHasta.setDateFormatString("dd/MM/yyyy");
        dcfechaHasta.setFocusTraversalPolicyProvider(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcfechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcfechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(btngenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btngenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dcfechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dcfechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 17, 692, 80));

        jPanel2.setBackground(new java.awt.Color(255, 231, 249));
        jPanel2.setFocusCycleRoot(true);

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(123, 98, 90));
        jLabel8.setText("INGRESO TOTALES");
        jLabel8.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel8.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel8.setPreferredSize(new java.awt.Dimension(20, 42));

        lblingresosTotales.setFont(new java.awt.Font("Georgia", 1, 19)); // NOI18N
        lblingresosTotales.setForeground(new java.awt.Color(123, 98, 90));
        lblingresosTotales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblingresosTotales.setText("S/ 0.0");
        lblingresosTotales.setMaximumSize(new java.awt.Dimension(250, 18));
        lblingresosTotales.setMinimumSize(new java.awt.Dimension(250, 18));
        lblingresosTotales.setPreferredSize(new java.awt.Dimension(20, 42));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblingresosTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblingresosTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 120, 163, 85));

        jPanel3.setBackground(new java.awt.Color(173, 214, 217));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 98, 90));
        jLabel3.setText("N° DE VENTAS");
        jLabel3.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel3.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel3.setPreferredSize(new java.awt.Dimension(20, 42));

        lblnumeroVentas.setFont(new java.awt.Font("Georgia", 1, 19)); // NOI18N
        lblnumeroVentas.setForeground(new java.awt.Color(123, 98, 90));
        lblnumeroVentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnumeroVentas.setText("0");
        lblnumeroVentas.setMaximumSize(new java.awt.Dimension(250, 18));
        lblnumeroVentas.setMinimumSize(new java.awt.Dimension(250, 18));
        lblnumeroVentas.setPreferredSize(new java.awt.Dimension(20, 42));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnumeroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblnumeroVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 163, 85));

        jPanel4.setBackground(new java.awt.Color(255, 239, 188));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(123, 98, 90));
        jLabel10.setText("CLIENTES ATENDIDOS");
        jLabel10.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel10.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel10.setPreferredSize(new java.awt.Dimension(20, 42));

        lblclientesAten.setFont(new java.awt.Font("Georgia", 1, 19)); // NOI18N
        lblclientesAten.setForeground(new java.awt.Color(123, 98, 90));
        lblclientesAten.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblclientesAten.setText("0");
        lblclientesAten.setMaximumSize(new java.awt.Dimension(250, 18));
        lblclientesAten.setMinimumSize(new java.awt.Dimension(250, 18));
        lblclientesAten.setPreferredSize(new java.awt.Dimension(20, 42));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblclientesAten, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblclientesAten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 120, 163, 85));

        jPanel5.setBackground(new java.awt.Color(204, 211, 245));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(123, 98, 90));
        jLabel12.setText("VENTA PROMEDIO");
        jLabel12.setMaximumSize(new java.awt.Dimension(250, 18));
        jLabel12.setMinimumSize(new java.awt.Dimension(250, 18));
        jLabel12.setPreferredSize(new java.awt.Dimension(20, 42));

        lblpromedioVenta.setFont(new java.awt.Font("Georgia", 1, 19)); // NOI18N
        lblpromedioVenta.setForeground(new java.awt.Color(123, 98, 90));
        lblpromedioVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblpromedioVenta.setText("0");
        lblpromedioVenta.setMaximumSize(new java.awt.Dimension(250, 18));
        lblpromedioVenta.setMinimumSize(new java.awt.Dimension(250, 18));
        lblpromedioVenta.setPreferredSize(new java.awt.Dimension(20, 42));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblpromedioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblpromedioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 120, 163, 85));

        jPanel7.setBackground(new java.awt.Color(241, 218, 206));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(58, 42, 38));
        jLabel14.setText("Historial de ventas");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 130, 20));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblhistorial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 216, 208)));
        tblhistorial.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblhistorial.setForeground(new java.awt.Color(58, 42, 38));
        tblhistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID ", "FECHA", "HORA", "CLIENTE", "DNI", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblhistorial.setFillsViewportHeight(true);
        tblhistorial.setGridColor(new java.awt.Color(232, 216, 208));
        tblhistorial.setPreferredSize(new java.awt.Dimension(375, 720));
        tblhistorial.setRowHeight(32);
        tblhistorial.setSelectionBackground(new java.awt.Color(241, 209, 196));
        tblhistorial.setSelectionForeground(new java.awt.Color(58, 42, 38));
        tblhistorial.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tblhistorial);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 660, 220));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 220, 692, 290));

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 730, 530));

        jPanel8.setBackground(new java.awt.Color(248, 221, 218));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(58, 42, 38));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenClientes/reporte.png"))); // NOI18N
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setAlignmentX(32.0F);
        jLabel6.setAlignmentY(22.0F);
        jLabel6.setMaximumSize(new java.awt.Dimension(180, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(180, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(32, 22));
        jPanel8.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 0, 70, 60));

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 30, 70, 60));
    }// </editor-fold>//GEN-END:initComponents

    private void btngenerarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarMouseEntered
        btngenerar.setBackground(new Color(199, 132, 108));
    }//GEN-LAST:event_btngenerarMouseEntered

    private void btngenerarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarMouseExited
        btngenerar.setBackground(new Color(216, 154, 132));
    }//GEN-LAST:event_btngenerarMouseExited

    private void btngenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarActionPerformed
           generarReporteVentas();
    }//GEN-LAST:event_btngenerarActionPerformed

    private void btnIngresosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresosMouseEntered

    private void btnIngresosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresosMouseExited

    private void btnIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIngresosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresos;
    private javax.swing.JButton btngenerar;
    private com.toedter.calendar.JDateChooser dcfechaDesde;
    private com.toedter.calendar.JDateChooser dcfechaHasta;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblclientesAten;
    private javax.swing.JLabel lblingresosTotales;
    private javax.swing.JLabel lblnumeroVentas;
    private javax.swing.JLabel lblpromedioVenta;
    private javax.swing.JTable tblhistorial;
    // End of variables declaration//GEN-END:variables
}
