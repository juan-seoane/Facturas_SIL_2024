/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import modelo.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Juan Seoane
 */
public class FormularioRS extends javax.swing.JFrame {
    
    private static FormularioRS instancia = null;
    private boolean enviado = false;
    private boolean cancelado = false;
    /**
     * Creates new form FormularioRS
     */
    private FormularioRS() {
        super("Formulario de Distribuidores");
        initComponents();
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(){
//                formWindowClosing();
//            }
//        });
    }
    
    public static FormularioRS getFormulario(){
        if (instancia == null)
            instancia = new FormularioRS();
        return instancia;
    }
    
    public boolean enviado(){
//        System.out.println("Enviado!");
        return this.enviado;
    }
    
    public boolean cancelado(){
        return this.cancelado;
    }
    
    public void reset(){
        this.enviado = false;
        this.cancelado = false;
        this.dispose();
    }
    public boolean limpiarFormulario(){
        this.txtNombreRS.setText("");
        this.txtRazonRS.setText("");
        this.txtCategoriaRS.setText("");
        this.txtDireccionRS.setText("");
        this.txtCPRS.setText("");
        this.txtPoblacionRS.setText("");
        this.txtTelefonoRS.setText("");
        this.txtLetraCIFRS.setText("");
        this.txtLetraNIFRS.setText("");       
        this.txtNumeroNIFRS.setText("");
        this.txtfieldNotaRS.setText("");
        return true;
    }
    public void cambiarBotonoK(String rotulo){
            btnOKFormRS.setText(rotulo);
    }
    public boolean rellenarFormulario(RazonSocial rs){
        limpiarFormulario();      

        this.txtNombreRS.setText(rs.getNombre().toUpperCase());
        this.txtRazonRS.setText(rs.getRazon().toUpperCase());
        this.txtCategoriaRS.setText(rs.getCategoria().toUpperCase());
        this.txtDireccionRS.setText(rs.getDireccion().toUpperCase());
        this.txtCPRS.setText(rs.getCP().toUpperCase());
        this.txtPoblacionRS.setText(rs.getPoblacion().toUpperCase());
        this.txtTelefonoRS.setText(rs.getTelefono());
        NIF nif = rs.getNIF();
        this.txtLetraCIFRS.setText("");
        this.txtLetraNIFRS.setText("");       
        if (nif.isCIF())
            this.txtLetraCIFRS.setText(rs.getNIF().getLetra().toUpperCase());
        else 
            this.txtLetraNIFRS.setText(rs.getNIF().getLetra().toUpperCase());
        txtNumeroNIFRS.setText(rs.getNIF().getNumero()+"");
        txtfieldNotaRS.setText(rs.getNota().getTexto().toString());
        System.out.println("Nota : "+rs.getNota().getTexto().toString());
        return true;
    }
    
     public RazonSocial recogerRazonForm(){
         try{
        RazonSocial rs = new RazonSocial();
        rs.setNombre(this.txtNombreRS.getText().toUpperCase());
        rs.setRazon(this.txtRazonRS.getText().toUpperCase());
        rs.setCategoria(this.txtCategoriaRS.getText().toUpperCase());
        rs.setDireccion(this.txtDireccionRS.getText().toUpperCase());
        rs.setCP(this.txtCPRS.getText().toUpperCase());
        rs.setPoblacion(this.txtPoblacionRS.getText().toUpperCase());
        rs.setTelefono(this.txtTelefonoRS.getText());
        if (this.txtLetraNIFRS.getText().equals("")){
            boolean isCIF = true;
            String letra = this.txtLetraCIFRS.getText().toUpperCase();
            int numero = Integer.parseInt(txtNumeroNIFRS.getText());
            rs.setNif(new NIF( numero, letra, isCIF));
        }
        else if (this.txtLetraCIFRS.getText().equals("")){
            boolean isCIF = false;
            String letra = this.txtLetraNIFRS.getText().toUpperCase();
            int numero = Integer.parseInt(txtNumeroNIFRS.getText());
            rs.setNif(new NIF( numero, letra, isCIF));
        }
        rs.setNota(new Nota(1,txtfieldNotaRS.getText()));
        /** TODO : FALTA RECOGER LA NOTA EN EL FORM DE DISTRIBUIDORES */
        return rs;
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null,"Ha habido un error en la entrada de datos!");
         }
         return null;
    }
    
     
     public Nota getNota(){
         return new Nota(1,txtfieldNotaRS.getText());
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNumeroNIFRS = new javax.swing.JTextField();
        txtLetraNIFRS = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreRS = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefonoRS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccionRS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCPRS = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPoblacionRS = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtfieldNotaRS = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        btnOKFormRS = new javax.swing.JButton();
        btnCancelFormRS = new javax.swing.JButton();
        txtLetraCIFRS = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtRazonRS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCategoriaRS = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulario Distribuidores");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NIF :");

        txtNumeroNIFRS.setText("123456789");

        txtLetraNIFRS.setText("A");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre :");

        txtNombreRS.setText("NOMBRE DE LA EMPRESA");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Teléfono:");

        txtTelefonoRS.setText("900-000000");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Direccion:");

        txtDireccionRS.setText("calle edificio portal puerta piso letra");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("C.Postal :");

        txtCPRS.setText("32000");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Población :");

        txtPoblacionRS.setText("POBLACION");

        jScrollPane1.setViewportView(txtfieldNotaRS);

        jLabel7.setText("NOTA :");

        btnOKFormRS.setText("OK");
        btnOKFormRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKFormRSActionPerformed(evt);
            }
        });

        btnCancelFormRS.setText("CANCELAR");
        btnCancelFormRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFormRSActionPerformed(evt);
            }
        });

        txtLetraCIFRS.setText("A");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Razon :");

        txtRazonRS.setText("NOMBRE COMPLETO DE LA RAZON SOCIAL S.L.U.");

        jLabel9.setText("CATEGORIA :");

        txtCategoriaRS.setText("COMPRAS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(41, 41, 41))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtLetraCIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(6, 6, 6)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(32, 32, 32)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNumeroNIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtLetraNIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtCategoriaRS)
                                    .addComponent(txtTelefonoRS))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCPRS, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPoblacionRS))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDireccionRS))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreRS)
                                    .addComponent(txtRazonRS)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelFormRS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOKFormRS, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumeroNIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLetraNIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLetraCIFRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefonoRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtRazonRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDireccionRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtCategoriaRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtCPRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txtPoblacionRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOKFormRS)
                    .addComponent(btnCancelFormRS)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKFormRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKFormRSActionPerformed
        this.enviado = true;
    }//GEN-LAST:event_btnOKFormRSActionPerformed

    private void btnCancelFormRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFormRSActionPerformed
        this.cancelado = true;
    }//GEN-LAST:event_btnCancelFormRSActionPerformed
    
    public int formWindowClosing(){
        this.reset();
        this.dispose();
        return 0;
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
            java.util.logging.Logger.getLogger(FormularioRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioRS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioRS().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelFormRS;
    private javax.swing.JButton btnOKFormRS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCPRS;
    private javax.swing.JTextField txtCategoriaRS;
    private javax.swing.JTextField txtDireccionRS;
    private javax.swing.JTextField txtLetraCIFRS;
    private javax.swing.JTextField txtLetraNIFRS;
    private javax.swing.JTextField txtNombreRS;
    private javax.swing.JTextField txtNumeroNIFRS;
    private javax.swing.JTextField txtPoblacionRS;
    private javax.swing.JTextField txtRazonRS;
    private javax.swing.JTextField txtTelefonoRS;
    private javax.swing.JTextPane txtfieldNotaRS;
    // End of variables declaration//GEN-END:variables
}