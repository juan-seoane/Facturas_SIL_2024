/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import modelo.TipoGasto;
import modelo.records.Config;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author Juan Seoane
 */
public class VentanaFiltrosRS extends javax.swing.JFrame {

    /**
     * Creates new form VentanaFiltrosRS
     */
    
    private static VentanaFiltrosRS instancia = null; 
    /**
     * Creates new form VentanaFiltros
     */
    private VentanaFiltrosRS() {
        initComponents();
        cargarComboCategorias();
    }
    
    public static VentanaFiltrosRS getVentana(){
        if (instancia == null)
            instancia = new VentanaFiltrosRS();
        return instancia;
    }
    
    public boolean cargarComboCategorias(){
      for (TipoGasto tipo : Config.getConfig().getTiposGasto())  
      {
          cmbCategoriasFiltros.addItem(tipo.tipo);
      } 
      return true;
    }
    public JCheckBox getChbFiltroCategoria() {
        return chbFiltroCategoria;
    }
    public String getFiltroDist(){
        return this.txtDistribuidorFiltro.getText().toUpperCase().trim();
    }
    public JCheckBox getChbFiltroDistribuidor() {
        return chbFiltroDistribuidor;
    }

    public JComboBox getCmbCategoriasFiltros() {
        return cmbCategoriasFiltros;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnOKFiltros = new javax.swing.JButton();
        btnCancelFiltros = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        chbFiltroDistribuidor = new javax.swing.JCheckBox();
        chbFiltroCategoria = new javax.swing.JCheckBox();
        txtDistribuidorFiltro = new javax.swing.JTextField();
        cmbCategoriasFiltros = new javax.swing.JComboBox();

        btnOKFiltros.setText("OK");
        btnOKFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKFiltrosActionPerformed(evt);
            }
        });

        btnCancelFiltros.setText("CANCEL");
        btnCancelFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFiltrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnCancelFiltros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOKFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnOKFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        chbFiltroDistribuidor.setText("DISTRIBUIDOR");

        chbFiltroCategoria.setText("CATEGORÍA");

        txtDistribuidorFiltro.setFocusCycleRoot(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chbFiltroCategoria)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCategoriasFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chbFiltroDistribuidor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDistribuidorFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbFiltroDistribuidor)
                    .addComponent(txtDistribuidorFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbFiltroCategoria)
                    .addComponent(cmbCategoriasFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        txtDistribuidorFiltro.getAccessibleContext().setAccessibleParent(chbFiltroDistribuidor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFiltrosActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelFiltrosActionPerformed

    private void btnOKFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKFiltrosActionPerformed
        this.hide();
        TablaDistribuidores.setPulsado(7);
    }//GEN-LAST:event_btnOKFiltrosActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaFiltrosRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaFiltrosRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaFiltrosRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaFiltrosRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaFiltrosRS().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelFiltros;
    private javax.swing.JButton btnOKFiltros;
    private javax.swing.JCheckBox chbFiltroCategoria;
    private javax.swing.JCheckBox chbFiltroDistribuidor;
    private javax.swing.JComboBox cmbCategoriasFiltros;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtDistribuidorFiltro;
    // End of variables declaration//GEN-END:variables
}
