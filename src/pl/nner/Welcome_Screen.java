/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultListModel;
import org.joda.time.DateTime;

/**
 *
 * @author krisztian_csekme1
 */
public class Welcome_Screen extends javax.swing.JDialog {

    /**
     * Creates new form Welcome_Screen
     */
    private Point anchorPoint;

    public Welcome_Screen(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();
        setMoveAble();
    }

    @Override
    public void setVisible(boolean value) {
        try {
            if (PlNner.SW_QTY_AUTO_CORRECT == 1) {
                WARNING_AUTO.setVisible(true);
                WARNING_AUTO_CH.setVisible(true);
            } else {
                WARNING_AUTO.setVisible(false);
                WARNING_AUTO_CH.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println("az elso try dobja");
            e.printStackTrace();

        }
        try {
            LBL_WELCOME.setText(LBL_WELCOME.getText().replace("%", PlNner.USER.name));
            LBL_DATE.setText(LBL_DATE.getText().replace("%", PlNner.getCurrentDate(new DateTime())));
            LBL_VER.setText(LBL_VER.getText().replace("%", PlNner.VERSION));
        } catch (Exception e) {
            System.out.println("a második try dobja");
            e.printStackTrace();
        }
        try {
            DefaultListModel MODEL = new DefaultListModel();
            System.out.println("sessionméret " + PlNner.session.files.size());
            String[] tmp = null;
            for (int i = 0; i < PlNner.session.files.size(); i++) {
                try {
                    tmp = PlNner.session.files.get(i).split("\\\\");
                } catch (Exception e) {
                    System.out.println("ez dobja");
                    e.printStackTrace();
                }
                try {
                    MODEL.addElement(tmp[tmp.length - 1]);
                } catch (Exception e) {
                    System.out.println("model.add dobja");
                    e.printStackTrace();
                }
            }

            LIST.setModel(MODEL);
        } catch (Exception e) {
            System.out.println("a 3. try dobja");
            e.printStackTrace();
        }
        try {
            super.setVisible(value);
        } catch (Exception e) {
            System.out.println("a 4. try dobja");
            e.printStackTrace();
        }
    }

    public void OpenPlan(File file) {
        try {
            FileInputStream in = null;
            BufferedInputStream BIS = null;
            ObjectInputStream s = null;

            try {
                ZipInputStream zipin = new ZipInputStream(new FileInputStream(file));
                zipin.getNextEntry();
                BIS = new BufferedInputStream(zipin);
                s = new ObjectInputStream(BIS);

                Plan2StreamV6 v6 = (Plan2StreamV6) s.readObject();
                //Plan2Stream ps = (Plan2Stream) s.readObject();
                v6.restore();
                PlNner.session.addFile(file.getAbsolutePath());
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());

                PlNner.PH.tick();
            } catch (ClassCastException e) {

                in = new FileInputStream(file);
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);

                Plan2StreamV5 ps = (Plan2StreamV5) s.readObject();
                ps.restore();
                PlNner.session.addFile(file.getAbsolutePath());
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
                PlNner.PH.tick();
            }
        } catch (ClassCastException e) {
            System.out.println("Nem támogatott formátum");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSerializableException er) {

        } catch (IOException ex) {
            try {
                FileInputStream in = null;
                BufferedInputStream BIS = null;
                ObjectInputStream s = null;
                in = new FileInputStream(file);
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);

                Plan2StreamV5 ps = (Plan2StreamV5) s.readObject();
                ps.restore();
                PlNner.session.addFile(file.getAbsolutePath());
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
                PlNner.PH.tick();
            } catch (FileNotFoundException ex1) {
                Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ClassNotFoundException ex1) {
                Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public void OpenPlan(File file) {
        try {
            FileInputStream in = null;
            in = new FileInputStream(file);
            BufferedInputStream BIS = new BufferedInputStream(in);

            ObjectInputStream s = null;

            s = new ObjectInputStream(BIS);
            try {
                Plan2StreamV6 v6 = (Plan2StreamV6) s.readObject();
                //Plan2Stream ps = (Plan2Stream) s.readObject();
                v6.restore();

                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());

                PlNner.PH.tick();
            } catch (ClassCastException e) {

                
                
               // in.close();
                in = new FileInputStream(file);
             //   s.close();
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);
                
                
               
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);

                Plan2StreamV5 ps = (Plan2StreamV5) s.readObject();
                ps.restore();

                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
                PlNner.PH.tick();
            }
        } catch (ClassCastException e) {
            System.out.println("Nem támogatott formátum");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSerializableException er) {

        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final Welcome_Screen handle = Welcome_Screen.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);

                handle.setLocation(me.getX() - anchorPoint.x, me.getY() - anchorPoint.y);

            }

        });
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
        LBL_WELCOME = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        LBL_VER = new javax.swing.JLabel();
        LBL_DATE = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LIST = new javax.swing.JList();
        REOPEN = new javax.swing.JCheckBox();
        WARNING_AUTO = new javax.swing.JLabel();
        WARNING_AUTO_CH = new javax.swing.JCheckBox();

        setUndecorated(true);
        setOpacity(0.9F);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));

        LBL_WELCOME.setFont(new java.awt.Font("Arial Narrow", 2, 18)); // NOI18N
        LBL_WELCOME.setText("Üdvözöllek %!");

        jButton1.setText("Bezár");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        LBL_VER.setText("Program verzió: %");

        LBL_DATE.setText("Dátum: %");

        jLabel5.setText("Utoljára megnyitott terveid:");

        jScrollPane1.setViewportView(LIST);

        REOPEN.setBackground(new java.awt.Color(255, 255, 255));
        REOPEN.setSelected(true);
        REOPEN.setText("Tervek autómatikus megnyitása");

        WARNING_AUTO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/warning.png"))); // NOI18N
        WARNING_AUTO.setText("Figyelem, a tervezősegéd autómatikus jobcsökkentés funkciója be van kapcsolva!");

        WARNING_AUTO_CH.setBackground(new java.awt.Color(255, 255, 255));
        WARNING_AUTO_CH.setSelected(true);
        WARNING_AUTO_CH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WARNING_AUTO_CHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBL_VER)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LBL_WELCOME)
                        .addGap(18, 465, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(REOPEN)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(WARNING_AUTO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(WARNING_AUTO_CH)))
                        .addGap(0, 58, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LBL_DATE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(LBL_WELCOME)
                .addGap(64, 64, 64)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(REOPEN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(WARNING_AUTO_CH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(WARNING_AUTO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(LBL_DATE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(LBL_VER))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (REOPEN.isSelected()) {

            if (LIST.getSelectedIndex() > -1) {
                for (int i = 0; i < LIST.getModel().getSize(); i++) {

                    if (LIST.isSelectedIndex(i)) {
                        OpenPlan(new File(PlNner.session.files.get(i)));
                        PlNner.session.temp.add(PlNner.session.files.get(i));
                    }

                }

            } else {
                for (int ii = 0; ii < LIST.getModel().getSize(); ii++) {

                    OpenPlan(new File(PlNner.session.files.get(ii)));
                    PlNner.session.temp.add(PlNner.session.files.get(ii));

                }

            }

        } else {

            PlNner.session.files.clear();

        }

        PlNner.session.files.clear();

        for (int i = 0; i < PlNner.session.temp.size(); i++) {
            PlNner.session.files.add(PlNner.session.temp.get(i));
        }
        PlNner.session.temp.clear();

        setVisible(false);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void WARNING_AUTO_CHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WARNING_AUTO_CHActionPerformed
        if (WARNING_AUTO_CH.isSelected()) {
            PlNner.SW_QTY_AUTO_CORRECT = 1;
        } else {
            PlNner.SW_QTY_AUTO_CORRECT = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_WARNING_AUTO_CHActionPerformed

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
            java.util.logging.Logger.getLogger(Welcome_Screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Welcome_Screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Welcome_Screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Welcome_Screen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Welcome_Screen dialog = new Welcome_Screen(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBL_DATE;
    private javax.swing.JLabel LBL_VER;
    private javax.swing.JLabel LBL_WELCOME;
    private javax.swing.JList LIST;
    private javax.swing.JCheckBox REOPEN;
    private javax.swing.JLabel WARNING_AUTO;
    private javax.swing.JCheckBox WARNING_AUTO_CH;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
