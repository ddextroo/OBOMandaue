/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package libratech.dashboard;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import libratech.auth.splash;
import libratech.design.GlassPanePopup;

/**
 *
 * @author Carocoy
 */
public class logout_dialog extends javax.swing.JPanel {

    /**
     * Creates new form logout_dialog
     */
    public logout_dialog() {
        initComponents();
        setOpaque(false);
        txt.setBackground(new Color(0, 0, 0, 0));
        txt.setOpaque(false);
        txt.setEditable(false);
        initFont();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.dispose();
        super.paintComponent(graphics);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        confirmlabel = new javax.swing.JLabel();
        txt = new javax.swing.JTextPane();
        confirm = new libratech.design.MyButtonborderless();
        cancel = new libratech.design.MyButtonborder();

        setBackground(new java.awt.Color(250, 250, 250));

        confirmlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        confirmlabel.setText("Confirm");

        txt.setText("Are you sure you want to log out of your account?");
        txt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        confirm.setForeground(new java.awt.Color(224, 224, 224));
        confirm.setText("Confirm");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        cancel.setForeground(new java.awt.Color(23, 23, 23));
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(confirmlabel))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(confirmlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
        // TODO add your handling code here:
        String filePath = "uid.txt";
        File file = new File(filePath);
        String filePath1 = "remember.txt";
        File file1 = new File(filePath1);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                if (file1.exists()) {
                    file1.delete();
                }
                home home = new home();
                home.setVisible(true);
                home.jPanel15.setBackground(Color.decode("#0E2C4A"));
                home.jPanel10.setBackground(Color.decode("#041C34"));
                home.jPanel18.setBackground(Color.decode("#041C34"));
                home.jLabel17.setFont(new Font("Poppins Regular", Font.BOLD, 16));
                home.jLabel14.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
                home.jLabel20.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
                CardLayout cardLayout = (CardLayout) home.jPanel3.getLayout();
                cardLayout.show(home.jPanel3, "book");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(logout_dialog.this);
                frame.dispose();
                home.setVisible(false);
                GlassPanePopup.closePopupLast();
                home.dispose();
            }
        } else {
            home home = new home();
            home.setVisible(true);
            home.jPanel15.setBackground(Color.decode("#0E2C4A"));
            home.jPanel10.setBackground(Color.decode("#041C34"));
            home.jPanel18.setBackground(Color.decode("#041C34"));
            home.jLabel17.setFont(new Font("Poppins Regular", Font.BOLD, 16));
            home.jLabel14.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
            home.jLabel20.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
            CardLayout cardLayout = (CardLayout) home.jPanel3.getLayout();
            cardLayout.show(home.jPanel3, "book");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(logout_dialog.this);
            frame.dispose();
            home.setVisible(false);
            GlassPanePopup.closePopupLast();
            home.dispose();
        }
        if (file1.exists()) {

        } else {
            file.delete();
            home home = new home();
            home.setVisible(true);
            home.jPanel15.setBackground(Color.decode("#0E2C4A"));
            home.jPanel10.setBackground(Color.decode("#041C34"));
            home.jPanel18.setBackground(Color.decode("#041C34"));
            home.jLabel17.setFont(new Font("Poppins Regular", Font.BOLD, 16));
            home.jLabel14.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
            home.jLabel20.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
            CardLayout cardLayout = (CardLayout) home.jPanel3.getLayout();
            cardLayout.show(home.jPanel3, "book");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(logout_dialog.this);
            frame.dispose();
            home.setVisible(false);
            GlassPanePopup.closePopupLast();
            home.dispose();
        }

    }//GEN-LAST:event_confirmActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        GlassPanePopup.closePopupLast();
    }//GEN-LAST:event_cancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private libratech.design.MyButtonborder cancel;
    private libratech.design.MyButtonborderless confirm;
    private javax.swing.JLabel confirmlabel;
    private javax.swing.JTextPane txt;
    // End of variables declaration//GEN-END:variables
    public void initFont() {
        confirmlabel.setFont(new Font("Poppins Regular", Font.BOLD, 20));
        txt.setFont(new Font("Poppins Regular", Font.PLAIN, 16));
        confirm.setFont(new Font("Poppins Regular", Font.BOLD, 12));
        cancel.setFont(new Font("Poppins Regular", Font.BOLD, 12));
    }
}
