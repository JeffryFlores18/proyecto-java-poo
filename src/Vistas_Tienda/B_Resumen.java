package Vistas_Tienda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class B_Resumen extends javax.swing.JPanel {

    public B_Resumen() {
        initComponents();
        actualizarFechaActual();

        // Bloquear edicion
        txtIngresos.setEditable(false);
        txtVentasHoy.setEditable(false);
        txtCantidadProductos.setEditable(false);

        // Ejecutar la carga de datos de la base de datos de forma segura
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cargarDatosDashboard();
            }
        });

    }

    public void cargarDatosDashboard() {
        try {

            Connection con = conexionLiv.conectar();

            // =====================================================
            // FECHA ACTUAL DE JAVA
            // =====================================================
            java.time.LocalDate hoy
                    = java.time.LocalDate.now();

            java.sql.Date fechaHoy
                    = java.sql.Date.valueOf(hoy);

            // Obtener lunes y domingo de la semana actual
            java.time.LocalDate lunes
                    = hoy.with(
                            java.time.DayOfWeek.MONDAY
                    );

            java.time.LocalDate domingo
                    = hoy.with(
                            java.time.DayOfWeek.SUNDAY
                    );

            java.sql.Date fechaLunes
                    = java.sql.Date.valueOf(lunes);

            java.sql.Date fechaDomingo
                    = java.sql.Date.valueOf(domingo);

            // =====================================================
            // 1. INGRESOS DE HOY
            // =====================================================
            String sqlIngresos = """
            SELECT COALESCE(SUM(total), 0)
            FROM venta
            WHERE fecha = ?
        """;

            PreparedStatement ps1
                    = con.prepareStatement(sqlIngresos);

            ps1.setDate(1, fechaHoy);

            ResultSet rs1
                    = ps1.executeQuery();

            if (rs1.next()) {

                txtIngresos.setText(
                        String.format(
                                "S/ %.2f",
                                rs1.getDouble(1)
                        )
                );
            }

            // =====================================================
            // 2. CANTIDAD DE VENTAS DE HOY
            // =====================================================
            String sqlVentas = """
            SELECT COUNT(*)
            FROM venta
            WHERE fecha = ?
        """;

            PreparedStatement ps2
                    = con.prepareStatement(sqlVentas);

            ps2.setDate(1, fechaHoy);

            ResultSet rs2
                    = ps2.executeQuery();

            if (rs2.next()) {

                txtVentasHoy.setText(
                        String.valueOf(
                                rs2.getInt(1)
                        )
                );
            }

            // =====================================================
            // 3. STOCK TOTAL DE PRODUCTOS
            // =====================================================
            String sqlProductos = """
            SELECT COALESCE(
                SUM(stock_inventario),
                0
            )
            FROM producto
        """;

            PreparedStatement ps3
                    = con.prepareStatement(sqlProductos);

            ResultSet rs3
                    = ps3.executeQuery();

            if (rs3.next()) {

                txtCantidadProductos.setText(
                        String.valueOf(
                                rs3.getInt(1)
                        )
                );
            }

            // =====================================================
            // 4. VENTAS DE LA SEMANA ACTUAL
            // =====================================================
            int[] ventasSemana
                    = new int[]{
                        0, 0, 0, 0, 0, 0, 0
                    };

            String sqlGrafico = """
            SELECT
                WEEKDAY(fecha) AS dia_semana,
                COUNT(*) AS cantidad_ventas
            FROM venta
            WHERE fecha BETWEEN ? AND ?
            GROUP BY WEEKDAY(fecha)
            ORDER BY WEEKDAY(fecha)
        """;

            PreparedStatement ps4
                    = con.prepareStatement(sqlGrafico);

            ps4.setDate(
                    1,
                    fechaLunes
            );

            ps4.setDate(
                    2,
                    fechaDomingo
            );

            ResultSet rs4
                    = ps4.executeQuery();

            while (rs4.next()) {

                int indiceDia
                        = rs4.getInt(
                                "dia_semana"
                        );

                int cantidadVentas
                        = rs4.getInt(
                                "cantidad_ventas"
                        );

                // 0 = Lunes
                // 1 = Martes
                // 2 = Miércoles
                // 3 = Jueves
                // 4 = Viernes
                // 5 = Sábado
                // 6 = Domingo
                if (indiceDia >= 0
                        && indiceDia < 7) {

                    ventasSemana[indiceDia]
                            = cantidadVentas;
                }
            }

            // =====================================================
            // CERRAR CONSULTAS
            // =====================================================
            rs1.close();
            ps1.close();

            rs2.close();
            ps2.close();

            rs3.close();
            ps3.close();

            rs4.close();
            ps4.close();

            con.close();

            // =====================================================
            // 5. DIBUJAR GRÁFICO
            // =====================================================
            PanelGrafico.removeAll();

            PanelGrafico.setLayout(
                    new BorderLayout()
            );

            LienzoGrafico miGrafico
                    = new LienzoGrafico(
                            ventasSemana
                    );

            miGrafico.setPreferredSize(
                    new java.awt.Dimension(
                            PanelGrafico.getWidth(),
                            350
                    )
            );

            PanelGrafico.add(
                    miGrafico,
                    BorderLayout.CENTER
            );

            PanelGrafico.revalidate();
            PanelGrafico.repaint();

        } catch (Exception e) {

            System.out.println(
                    "Error al cargar Dashboard: "
                    + e.getMessage()
            );

            e.printStackTrace();
        }

    }

    private void actualizarFechaActual() {
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("d 'de' MMMM", new java.util.Locale("es", "ES"));
        String fechaTexto = fechaActual.format(formato);
        lblFecha.setText("📅 Hoy, " + fechaTexto);
    }

    class LienzoGrafico extends JPanel {

        private int[] ventasDiarias;

        public LienzoGrafico(int[] ventasDiarias) {
            this.ventasDiarias = ventasDiarias;
            this.setOpaque(false); // Permite ver el fondo original de tu panel
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Suavizado de bordes para que los textos y líneas se vean en alta calidad
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int margenIzquierdo = 40;
            int margenInferior = 30;
            int numBarras = ventasDiarias.length; // 7 días

            int anchoTotalBarra = (panelWidth - margenIzquierdo) / numBarras;
            int anchoBarra = anchoTotalBarra / 2;

            Color colorBarra = new Color(0, 102, 153);
            Color colorTexto = Color.DARK_GRAY;

            // Dibujar Ejes
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(margenIzquierdo, 10, margenIzquierdo, panelHeight - margenInferior); // Eje Y
            g2.drawLine(margenIzquierdo, panelHeight - margenInferior, panelWidth - 10, panelHeight - margenInferior); // Eje X

            // Encontrar el valor máximo para escalar la altura de las barras
            int maxVentas = 1;
            for (int v : ventasDiarias) {
                if (v > maxVentas) {
                    maxVentas = v;
                }
            }
            int escalaY = maxVentas + 2;

            // Dibujar barras y etiquetas
            String[] dias = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};

            for (int i = 0; i < numBarras; i++) {
                int valorVenta = ventasDiarias[i];

                int alturaBarra = (panelHeight - margenInferior - 20) * valorVenta / escalaY;
                int xPos = margenIzquierdo + (i * anchoTotalBarra) + (anchoTotalBarra - anchoBarra) / 2;
                int yPos = (panelHeight - margenInferior) - alturaBarra;

                // Rellenar la barra
                g2.setColor(colorBarra);
                g2.fillRect(xPos, yPos, anchoBarra, alturaBarra);

                // Borde de la barra
                g2.setColor(Color.BLACK);
                g2.drawRect(xPos, yPos, anchoBarra, alturaBarra);

                // Texto del día abajo
                g2.setColor(colorTexto);
                g2.drawString(dias[i], xPos - 2, panelHeight - margenInferior + 15);

                // Valor numérico arriba de la barra
                g2.drawString(String.valueOf(valorVenta), xPos + (anchoBarra / 3), yPos - 5);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCantidadProductos = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIngresos = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtVentasHoy = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        PanelGrafico = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 236, 233));

        jLabel2.setForeground(new java.awt.Color(123, 98, 90));
        jLabel2.setText("PANEL DE ADMINISTRACION");

        jLabel3.setFont(new java.awt.Font("Georgia", 0, 24)); // NOI18N
        jLabel3.setText("RESUMEN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel4.setText("CANTIDAD DE PRODUCTOS");

        txtCantidadProductos.addActionListener(this::txtCantidadProductosActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(txtCantidadProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtCantidadProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel1.setText("INGRESOS DE HOY");

        txtIngresos.addActionListener(this::txtIngresosActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel5.setText("VENTAS DE HOY");

        txtVentasHoy.addActionListener(this::txtVentasHoyActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtVentasHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtVentasHoy)
                .addGap(39, 39, 39))
        );

        PanelGrafico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout PanelGraficoLayout = new javax.swing.GroupLayout(PanelGrafico);
        PanelGrafico.setLayout(PanelGraficoLayout);
        PanelGraficoLayout.setHorizontalGroup(
            PanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelGraficoLayout.setVerticalGroup(
            PanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        jLabel6.setText("VENTAS DE LA SEMANA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(PanelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProductosActionPerformed


    }//GEN-LAST:event_txtCantidadProductosActionPerformed

    private void txtVentasHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentasHoyActionPerformed


    }//GEN-LAST:event_txtVentasHoyActionPerformed

    private void txtIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIngresosActionPerformed

    }//GEN-LAST:event_txtIngresosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelGrafico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTextField txtCantidadProductos;
    private javax.swing.JTextField txtIngresos;
    private javax.swing.JTextField txtVentasHoy;
    // End of variables declaration//GEN-END:variables
}
