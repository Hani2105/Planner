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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author krisztian_csekme1
 */
public class MailSender extends javax.swing.JDialog {

    private Point anchorPoint;
    private DefaultTableModel cimlista;
    public Station station;
    public List<String> sign_to_kit = new ArrayList<>();

    /**
     * Creates new form MailSender
     */
    public MailSender(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setMoveAble();
        setCenter();
    }

    public void refresh() {

        String value = LIST.getSelectedItem().toString();

        Object id = PlNner.MYDB_DB.getCellValue("SELECT id FROM cimlistak WHERE nev='" + value + "'");

        cimlista = PlNner.MYDB_DB.getDataTableModel("SELECT emailid, email FROM mailescim,email WHERE cimlistaid='" + id + "' AND emailid=email.id");

        String to = "";
        for (int i = 0; i < cimlista.getRowCount(); i++) {
            //System.out.println(cimlista.getValueAt(i, 1));
            to += cimlista.getValueAt(i, 1) + ",";
        }
        if (to.length() > 0) {
            to = to.substring(0, to.length() - 1);
        }
        ADDRESSES.setText(to);

        repaint();
        revalidate();

    }

    public void init() {

        DefaultTableModel model = PlNner.MYDB_DB.getDataTableModel("SELECT nev FROM cimlistak");
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            cbm.addElement(model.getValueAt(i, 0));
        }
        LIST.setModel(cbm);
        refresh();
    }

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void setVisible(boolean value, Station stat) {
        station = stat;
        sign_to_kit.clear();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));
        init();
        if (station == null) {
            KIT_BTN.setVisible(false);
        } else {

            KIT_BTN.setVisible(true);
        }
        super.setVisible(value);
    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final MailSender handle = MailSender.this;
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        SUBJECT = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ADDRESSES = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        LIST = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        KIT_BTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MESSAGE = new javax.swing.JTextPane();

        setUndecorated(true);
        setOpacity(0.9F);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/email72.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("sansserif", 2, 18)); // NOI18N
        jLabel5.setText("Értesítő e-mail küldése...");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
            }
        });

        jLabel3.setText("Levél tárgya:");

        SUBJECT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(SUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(38, 38, 38)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Üzenet:");

        jLabel7.setText("Címzettek:");

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(318, 86));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(318, 86));
        jScrollPane3.setName(""); // NOI18N
        jScrollPane3.setPreferredSize(new java.awt.Dimension(318, 86));

        ADDRESSES.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        jScrollPane3.setViewportView(ADDRESSES);

        jLabel2.setText("Válasszon címlistát:");

        LIST.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        LIST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LISTMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LISTMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LISTMouseReleased(evt);
            }
        });
        LIST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LISTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LIST, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(LIST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/send_email.png"))); // NOI18N
        jButton1.setToolTipText("Levél küldése....");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        KIT_BTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/kitted.png"))); // NOI18N
        KIT_BTN.setText("Kitt összeállítás");
        KIT_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KIT_BTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(KIT_BTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(KIT_BTN)))
                .addContainerGap())
        );

        MESSAGE.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        MESSAGE.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(MESSAGE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        setVisible(false);
    }//GEN-LAST:event_jLabel6MouseReleased

    private void LISTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LISTMouseClicked


    }//GEN-LAST:event_LISTMouseClicked

    private void LISTMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LISTMouseReleased
        refresh();

    }//GEN-LAST:event_LISTMouseReleased

    private void LISTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LISTMouseEntered

    }//GEN-LAST:event_LISTMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String ObjButtons[] = {"Igen", "Nem"};
        int PromptResult = JOptionPane.showOptionDialog(this, "Biztos vagy benne hogy elküldöd a levelet?", "Figyelem!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {

            EMAIL mail = new EMAIL();
            mail.setFrom(PlNner.USER.mail);

            mail.setTo(ADDRESSES.getText());
            mail.setSubject(SUBJECT.getText());

            String message = "<HTML><BODY>";
            message += MESSAGE.getText() + "</BODY></HTML>";
            if (SUBJECT.getText().contains("változás")) {

                message.replace("</BODY></HTML>", "<br><br>");
                Plan pl = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
                ArrayList<Product> lista = new ArrayList<>();
                String pattern = "yyyy-MM-dd HH:mm:ss";
                org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

                message += "<table border=\"2\"><thead><tr><th>PartNumber</th><th>Job</th><th>Oldal</th><th>Qty</th><th>QtyTeny</th><th>StartTime</th><th>Komment</th><th>Mérnöki</th></tr></thead><tbody>";
                for (int i = 0; i < pl.STATIONS.size(); i++) {
                    for (int n = 0; n < pl.STATIONS.get(i).PRODUCTS.size(); n++) {
                        Product p = pl.STATIONS.get(i).PRODUCTS.get(n);
                        lista.add(p);
//                        String starttime = formatter.print(p.getStartTime());
//                        message += "<tr><td>" + p.getPartnumber() + "</td><td>" + p.getJobnumber() + "</td><td>" + p.getQty() + "</td><td>" + p.getSumTeny() + "</td><td>" + starttime + "</td><td>" + p.getComment() + "</td><td>" + p.isEngeenering() + "</td></tr>";

                    }

                }
                //tegyuk sorba a gyartasiido szerint

                Collections.sort(lista, new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return o1.getStartTime().compareTo(o2.getStartTime());
                    }
                });

                //most porgessuk be es csinaljunk html kodot
                for (int i = 0; i < lista.size(); i++) {
                    Product p = lista.get(i);
                    String starttime = formatter.print(p.getStartTime());
                    message += "<tr><td>" + p.getPartnumber() + "</td><td>" + p.getJobnumber() + "</td><td>"+p.getSequence()+"</td><td>" + p.getQty() + "</td><td>" + p.getSumTeny() + "</td><td>" + starttime + "</td><td>" + p.getComment() + "</td><td>" + p.isEngeenering() + "</td></tr>";

                }

                message += "</tbody></table></body></html>";

            }
            mail.setMessage(message.replace("\n", "").replace("\"", "'"));
            mail.send();

            for (int i = 0; i < sign_to_kit.size(); i++) {
                station.setKitted(sign_to_kit.get(i), true);
            }
            if (station != null) {
                station.getPlan().NEED_TO_SAVE = true;
                sign_to_kit.clear();
            }

            setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void LISTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LISTActionPerformed
        refresh();
    }//GEN-LAST:event_LISTActionPerformed

    private void KIT_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KIT_BTNActionPerformed
        PlNner.NKW.setVisible(true, MailSender.this.station);
    }//GEN-LAST:event_KIT_BTNActionPerformed

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
            java.util.logging.Logger.getLogger(MailSender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MailSender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MailSender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MailSender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MailSender dialog = new MailSender(new javax.swing.JFrame(), true);
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
    public javax.swing.JEditorPane ADDRESSES;
    private javax.swing.JButton KIT_BTN;
    public javax.swing.JComboBox LIST;
    public javax.swing.JTextPane MESSAGE;
    public javax.swing.JTextField SUBJECT;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
