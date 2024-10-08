/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/Application.java to edit this template
 */
package org.lenguajesFP.Frontend;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.lenguajesFP.Backend.Reader;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

/**
 *
 * @author brigidoalvarado
 */
public class LexicalsWebAnalyzerApp extends javax.swing.JFrame {

    private String input;

    /**
     * Creates new form LexicalsWebAnalyzerApp
     */
    public LexicalsWebAnalyzerApp() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerjPnl = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        textAreasContainerjPnl = new javax.swing.JPanel();
        jScrllPnInput = new javax.swing.JScrollPane();
        InputjTxtAr = new javax.swing.JTextArea();
        jScrllPnlOutput = new javax.swing.JScrollPane();
        OutputjTxtAr = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("GENERAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containerjPnlLayout = new javax.swing.GroupLayout(containerjPnl);
        containerjPnl.setLayout(containerjPnlLayout);
        containerjPnlLayout.setHorizontalGroup(
            containerjPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerjPnlLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(622, Short.MAX_VALUE))
        );
        containerjPnlLayout.setVerticalGroup(
            containerjPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerjPnlLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButton1))
        );

        getContentPane().add(containerjPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 50));

        textAreasContainerjPnl.setBackground(new java.awt.Color(245, 245, 245));
        textAreasContainerjPnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        InputjTxtAr.setColumns(20);
        InputjTxtAr.setRows(5);
        InputjTxtAr.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                InputjTxtArCaretUpdate(evt);
            }
        });
        jScrllPnInput.setViewportView(InputjTxtAr);

        textAreasContainerjPnl.add(jScrllPnInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 520, 480));

        OutputjTxtAr.setEditable(false);
        OutputjTxtAr.setColumns(20);
        OutputjTxtAr.setRows(5);
        OutputjTxtAr.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                OutputjTxtArCaretUpdate(evt);
            }
        });
        jScrllPnlOutput.setViewportView(OutputjTxtAr);

        textAreasContainerjPnl.add(jScrllPnlOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 550, 480));

        getContentPane().add(textAreasContainerjPnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1130, 520));

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void InputjTxtArCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_InputjTxtArCaretUpdate
        // TODO add your handling code here
        input = InputjTxtAr.getText();
    }//GEN-LAST:event_InputjTxtArCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        try {
            Reader reader = new Reader();
            reader.readCode(input);
            String html = reader.getHtmlTokens().stream().collect(Collectors.joining());
            String css = reader.getCssTokens().stream().collect(Collectors.joining());
            String js = reader.getJsTokens().stream().collect(Collectors.joining());
            OutputjTxtAr.setText("html\n"+html +"css\n"+ css +"js\n"+ js);

        } catch (LexicalAnalyzerException e) {
            JOptionPane.showMessageDialog(this, "ANALISIS FINALIZADO", e.getMessage(), JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void OutputjTxtArCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_OutputjTxtArCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_OutputjTxtArCaretUpdate

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
            java.util.logging.Logger.getLogger(LexicalsWebAnalyzerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LexicalsWebAnalyzerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LexicalsWebAnalyzerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LexicalsWebAnalyzerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LexicalsWebAnalyzerApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea InputjTxtAr;
    private javax.swing.JTextArea OutputjTxtAr;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel containerjPnl;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrllPnInput;
    private javax.swing.JScrollPane jScrllPnlOutput;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel textAreasContainerjPnl;
    // End of variables declaration//GEN-END:variables

}
