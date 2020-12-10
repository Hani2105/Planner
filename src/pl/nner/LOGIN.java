/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;

/**
 *
 * @author krisztian_csekme1
 */
public class LOGIN extends javax.swing.JFrame {

    private Point anchorPoint;
    private Object PASS_KEY;

    /**
     * Creates new form LOGIN
     */
    public LOGIN() {
        initComponents();

        setCenter();
        setSize(641, 420);
        GETPASS.setVisible(false);
        VER.setText(VER.getText().replace("%", PlNner.VERSION));
        setTitle("Pl@nner Login");
        setIconImage(new ImageIcon(this.getClass().getResource("IMG/lock_closed.png")).getImage()); //zoom out);
        setMoveAble();

        if (new DateTime().getMonthOfYear() == 12) {
            SANTA.setVisible(true);
        } else {
            SANTA.setVisible(false);
        }

    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final LOGIN handle = LOGIN.this;
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

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void gainPass() {
        if (ID.getText().length() == 6) {
            PASS_KEY = PlNner.MYDB_DB.getCellValue("SELECT pass from perm WHERE id='" + ID.getText() + "';");

        }
    }

    public void setPICS() {
        if (ID.getText().length() == 6) {
            Image img = PlNner.getPICS(ID.getText()).getImage();
            Image dimg = img.getScaledInstance(SCREEN.getWidth(), SCREEN.getHeight(), Image.SCALE_SMOOTH);
            SCREEN.setIcon(new ImageIcon(dimg));

        } else {
            SCREEN.setIcon(null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        SANTA = new javax.swing.JLabel();
        GETPASS = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        VER = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        PASS = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SCREEN = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setAlwaysOnTop(true);
        setUndecorated(true);
        setOpacity(0.9F);
        setResizable(false);

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(640, 430));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(640, 430));
        jLayeredPane1.setName(""); // NOI18N

        SANTA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/santa.png"))); // NOI18N
        jLayeredPane1.add(SANTA);
        SANTA.setBounds(330, 40, 110, 140);

        GETPASS.setForeground(new java.awt.Color(255, 153, 51));
        GETPASS.setText("jelszó kérése...");
        GETPASS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GETPASS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GETPASSMouseClicked(evt);
            }
        });
        jLayeredPane1.add(GETPASS);
        GETPASS.setBounds(390, 250, 90, 20);

        jButton1.setText("Belépés");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1);
        jButton1.setBounds(530, 380, 100, 28);

        VER.setFont(new java.awt.Font("sansserif", 0, 9)); // NOI18N
        VER.setForeground(new java.awt.Color(255, 255, 255));
        VER.setText("Pl@nner program v%");
        jLayeredPane1.add(VER);
        VER.setBounds(10, 390, 520, 16);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close16.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jLayeredPane1.add(jLabel4);
        jLabel4.setBounds(610, 10, 0, 0);

        ID.setBorder(null);
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });
        ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                IDKeyReleased(evt);
            }
        });
        jLayeredPane1.add(ID);
        ID.setBounds(270, 250, 110, 20);

        PASS.setBorder(null);
        PASS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PASSKeyTyped(evt);
            }
        });
        jLayeredPane1.add(PASS);
        PASS.setBounds(270, 280, 110, 20);

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Jelszó:");
        jLayeredPane1.add(jLabel3);
        jLabel3.setBounds(210, 280, 40, 20);

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dolgozói szám:");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(170, 250, 90, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/login_bg_border3_top.png"))); // NOI18N
        jLayeredPane1.add(jLabel5);
        jLabel5.setBounds(0, -5, 640, 430);

        SCREEN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLayeredPane1.add(SCREEN);
        SCREEN.setBounds(260, 110, 130, 130);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/login_bg_border3.jpg"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jLabel2.setMaximumSize(new java.awt.Dimension(642, 420));
        jLabel2.setMinimumSize(new java.awt.Dimension(642, 420));
        jLabel2.setPreferredSize(new java.awt.Dimension(642, 420));
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(0, 0, 640, 420);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void IDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IDKeyReleased
        setPICS();

    }//GEN-LAST:event_IDKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        gainPass();
        if (PASS_KEY != null) {
            if (PASS_KEY.toString().equals(PASS.getText())) {

                PlNner.USER = new user();
                PlNner.USER.id = ID.getText();
                PlNner.USER.pass = PASS.getText();
                PlNner.USER.level = Integer.parseInt(PlNner.MYDB_DB.getCellValue("SELECT level from perm WHERE id='" + ID.getText() + "';").toString());
                PlNner.USER.mail = PlNner.MYDB_DB.getCellValue("SELECT email from perm WHERE id='" + ID.getText() + "';").toString();
                System.out.println("user mail = " + PlNner.USER.mail);
                PlNner.USER.pics = PlNner.MYDB_DB.getCellValue("SELECT pics from perm WHERE id='" + ID.getText() + "';");
                System.out.println("user pics = " + PlNner.USER.pics);
                PlNner.USER.name = PlNner.MYDB_DB.getCellValue("SELECT name from perm WHERE id='" + ID.getText() + "';").toString();
                System.out.println("user name = " + PlNner.USER.name);
                PlNner.can = true;
                LOGIN.this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Helytelen jelszót adott meg!", "Figyelem!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("IMG/angry64.png")));
                GETPASS.setVisible(true);
                PASS_KEY = null;
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void GETPASSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GETPASSMouseClicked
        Object res = PlNner.MYDB_DB.getCellValue("SELECT email from perm WHERE id='" + ID.getText() + "';");

        if (res.equals(-1)) {
            JOptionPane.showMessageDialog(this, "Ezzel az ID-val nem találtam felhasználót! Biztos benne hogy van jogosultsága a programhoz?", "Figyelem!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("IMG/ooo64.png")));
        } else {
            EMAIL mail = new EMAIL();
            mail.setTo(res.toString());
            mail.setFrom("planner@sanmina.com");
            mail.setSubject("Elfelejtett jelszó");
            res = PlNner.MYDB_DB.getCellValue("SELECT pass from perm WHERE id='" + ID.getText() + "';");
            mail.setMessage("Az ön elfelejtett jelszava: " + res.toString());
            mail.send();
            JOptionPane.showMessageDialog(this, "Hamarosan megérkezik a postaládájába az elfelejtett jelszava!", "Figyelem!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("IMG/ok64.png")));

        }


    }//GEN-LAST:event_GETPASSMouseClicked

    private void PASSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PASSKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_PASSKeyTyped

    private void IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDActionPerformed

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
            java.util.logging.Logger.getLogger(LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LOGIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GETPASS;
    private javax.swing.JTextField ID;
    private javax.swing.JPasswordField PASS;
    private javax.swing.JLabel SANTA;
    private javax.swing.JLabel SCREEN;
    private javax.swing.JLabel VER;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}