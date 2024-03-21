/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan Seoane
 */
public class TablaCaja extends javax.swing.JFrame {

    private Vector<Vector> vectorcajas;
    private Vector columnas;
    private boolean cambiado = false;
    private int pulsado = 0;
    private int indice = 0;
    private boolean filtrosActivos = false;
    private Image icon;

    /**
     * Creates new form TablaCaja
     */
    public TablaCaja(Vector<Vector> vectorcajas, Vector columnas) {
        initComponents();
        startup();
        actualizarDatos(vectorcajas,columnas);

        ListSelectionModel row = this.tablacajas.getSelectionModel();
        row.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                filaSeleccionada();
            }
        });
    }
    
    
    private ArrayList<Image> startup(){
        
        icon = Toolkit.getDefaultToolkit().getImage("imagenes/tray.png");
        setIconImage(icon);
        ArrayList<Image> listaIconos = new ArrayList<Image>();
        
        listaIconos.add(icon);
        return listaIconos;
    }
    
    public void actualizarDatos(Vector<Vector> vector, Vector columnas){
        DefaultTableModel modelo = new DefaultTableModel(vector,columnas);
        this.tablacajas.setModel(modelo);
        this.tablacajas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        actualizarTotales(vector);
    }
    
    public void actualizarTotales(Vector<Vector> vector){
        double debe = 0.0;
        double haber = 0.0;
        int cuenta = 0;
        if (vector.size()>0)
        {
            for (Vector v : vector){
                if (!v.get(2).equals(" - "))
                    debe += Double.parseDouble(v.get(2).toString());
                if (!v.get(3).equals(" - "))
                    haber += Double.parseDouble(v.get(3).toString());
                cuenta++;
            }
        }
        Vector<Vector> total =  new Vector<Vector>();
        Vector temp = new Vector();
            temp.add(cuenta);
            temp.add(debe);
            temp.add(haber);
            temp.add(haber-debe);
        total.add(temp);
        Vector columnas2 = new Vector();
        columnas2.add("ENTRADAS");
        columnas2.add("DEBE");
        columnas2.add("HABER");
        columnas2.add("SALDO");
        DefaultTableModel modelo2 = new DefaultTableModel(total,columnas2);
        this.tablatotalescaja.setModel(modelo2);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablacajas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnNuevaCaja = new javax.swing.JButton();
        btnFiltrosCaja = new javax.swing.JButton();
        btnCerrarCajas = new javax.swing.JButton();
        btnEditarCaja = new javax.swing.JButton();
        btnBorrarCaja = new javax.swing.JButton();
        chbxFiltros = new javax.swing.JCheckBox();
        btnPDF = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablatotalescaja = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablacajas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablacajas.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tablacajas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        btnNuevaCaja.setText("NUEVA CAJA");
        btnNuevaCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCajaActionPerformed(evt);
            }
        });

        btnFiltrosCaja.setText("FILTROS");
        btnFiltrosCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrosCajaActionPerformed(evt);
            }
        });

        btnCerrarCajas.setText("CERRAR");
        btnCerrarCajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarCajasActionPerformed(evt);
            }
        });

        btnEditarCaja.setText("EDITAR");
        btnEditarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCajaActionPerformed(evt);
            }
        });

        btnBorrarCaja.setText("BORRAR");
        btnBorrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarCajaActionPerformed(evt);
            }
        });

        chbxFiltros.setText("Activar Filtros");
        chbxFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbxFiltrosActionPerformed(evt);
            }
        });

        btnPDF.setText("PDF");
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnCerrarCajas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFiltrosCaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbxFiltros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(btnPDF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrarCaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarCaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaCaja))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaCaja)
                    .addComponent(btnFiltrosCaja)
                    .addComponent(btnCerrarCajas)
                    .addComponent(btnEditarCaja)
                    .addComponent(btnBorrarCaja)
                    .addComponent(chbxFiltros)
                    .addComponent(btnPDF)))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        tablatotalescaja.setModel(new javax.swing.table.DefaultTableModel(
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
        tablatotalescaja.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(tablatotalescaja);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarCajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarCajasActionPerformed
        this.pulsado = 5;
        this.cambiado = true;
    }//GEN-LAST:event_btnCerrarCajasActionPerformed

    private void btnNuevaCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCajaActionPerformed
        this.pulsado = 1;
        this.cambiado = true;
    }//GEN-LAST:event_btnNuevaCajaActionPerformed

    private void btnFiltrosCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrosCajaActionPerformed
        this.pulsado = 6;
        this.cambiado = true;
    }//GEN-LAST:event_btnFiltrosCajaActionPerformed

    private void btnEditarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCajaActionPerformed
        this.pulsado = 2;
        this.cambiado = true;
    }//GEN-LAST:event_btnEditarCajaActionPerformed

    private void btnBorrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarCajaActionPerformed
         this.pulsado = 3;
        this.cambiado = true;
    }//GEN-LAST:event_btnBorrarCajaActionPerformed

    private void chbxFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbxFiltrosActionPerformed

            this.pulsado = 4;
            this.cambiado = true;

    }//GEN-LAST:event_chbxFiltrosActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
            this.pulsado = 7;
            this.cambiado = true;
    }//GEN-LAST:event_btnPDFActionPerformed
    
    public int filaSeleccionada() {
        ListSelectionModel selectionModel = tablacajas.getSelectionModel();
        indice = selectionModel.getLeadSelectionIndex();
        if (indice < 0) {
            indice = 0;
        }
        return indice;
//        cambiado = true;
//        JOptionPane.showMessageDialog(null,"Ha seleccionado la caja "+(indice+1));
    }
    
    public boolean cambiado() {
        return this.cambiado;
    }

    public int pulsado() {
        return this.pulsado;
    }

    public void reset() {
        this.pulsado = 0;
        this.cambiado = false;
    }

    public JCheckBox getChbxFiltros() {
        return chbxFiltros;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaCaja(new Vector<Vector>(), new Vector()).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarCaja;
    private javax.swing.JButton btnCerrarCajas;
    private javax.swing.JButton btnEditarCaja;
    private javax.swing.JButton btnFiltrosCaja;
    private javax.swing.JButton btnNuevaCaja;
    private javax.swing.JButton btnPDF;
    private javax.swing.JCheckBox chbxFiltros;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablacajas;
    private javax.swing.JTable tablatotalescaja;
    // End of variables declaration//GEN-END:variables
}
