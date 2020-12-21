/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import org.joda.time.Hours;

/**
 *
 * @author krisztian_csekme1
 */
public class PlanHelper extends javax.swing.JDialog {

    private Point anchorPoint;

    public Thread scanning;
    public Random RND = new Random();
    public Timer timer;
    public DefaultTableModel ENG_MODEL;

    ActionListener MESS = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int no = PlNner.EW.MODEL.getRowCount();
            if (no > 1) {

                int row = RND.nextInt(no);

                //4 ID szám
                //5 észrevétel
                //6 javaslat
                PlNner.MSG.IDS.setText(PlNner.EW.MODEL.getValueAt(row, 4).toString());
                PlNner.MSG.LBL_REMARK.setText(PlNner.EW.MODEL.getValueAt(row, 5).toString());
                PlNner.MSG.LBL_PROPOSAL.setText(PlNner.EW.MODEL.getValueAt(row, 6).toString());
                PlNner.MSG.setVisible(true);

            } else if (no == 1) {
                PlNner.MSG.IDS.setText(PlNner.EW.MODEL.getValueAt(0, 4).toString());
                PlNner.MSG.LBL_REMARK.setText(PlNner.EW.MODEL.getValueAt(0, 5).toString());
                PlNner.MSG.LBL_PROPOSAL.setText(PlNner.EW.MODEL.getValueAt(0, 6).toString());
                PlNner.MSG.setVisible(true);

            } else {

            }

        }
    };

    Runnable RUN = new Runnable() {

        @Override
        public void run() {

            PlNner.EW.init();
            Working(true);
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(0);
            model = PlNner.PLR_DB.getDataTableModel("SELECT `cycletime_prog`.`ID` as cpID,`oraclepn`,smtprogname,`smtline` as sline,`sequence`,`boardnumber`,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 1 ORDER BY ID DESC LIMIT 1) as mertido,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 2 ORDER BY ID DESC LIMIT 1) as gyorsmeres,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 3 ORDER BY ID DESC LIMIT 1) as kalkulalt,IFNULL(expectedeffbyprog,COALESCE((SELECT expectedeff FROM cycletime_config WHERE smtline = sline),(SELECT expectedeff FROM cycletime_config WHERE smtline = 'ALL'))) as eff FROM `cycletime_prog` ORDER BY smtprogname;");
            if (model.getRowCount() == 0) {
                return;
            }

            ENG_MODEL = PlNner.PLR_DB.getDataTableModel("SELECT `cycletime_prog`.`ID` as cpID,`oraclepn`,smtprogname,`smtline` as sline,`sequence`,`boardnumber`,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 1 ORDER BY ID DESC LIMIT 1) as mertido,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 2 ORDER BY ID DESC LIMIT 1) as gyorsmeres,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 3 ORDER BY ID DESC LIMIT 1) as kalkulalt,IFNULL(expectedeffbyprog,COALESCE((SELECT expectedeff FROM cycletime_config WHERE smtline = sline),(SELECT expectedeff FROM cycletime_config WHERE smtline = 'ALL'))) as eff FROM `cycletime_prog` ORDER BY smtprogname;");
            for (int p = 0; p < PlNner.PLANS.size(); p++) {
                Plan plan = PlNner.PLANS.get(p);

                for (int s = 0; s < plan.STATIONS.size(); s++) {
                    Station station = plan.STATIONS.get(s);

                    //new Object[]{"Terv","Állomás","JOB","Partnumber","Érintett ID-k","Észrevétel","Javaslat"
                    for (int t = 0; t < station.PRODUCTS.size(); t++) {
                        Product prod = station.PRODUCTS.get(t);

                        if (station.isPassed(prod.getJobnumber())) {

                            boolean EXIST = false;
                            for (int r = 0; r < ENG_MODEL.getRowCount(); r++) {
                                try {
                                    if (((ENG_MODEL.getValueAt(r, 1).toString().equals(prod.getPartnumber()))) && ((ENG_MODEL.getValueAt(r, 3).toString().toLowerCase().equals(station.getName().toLowerCase())))) {
                                        EXIST = true;
                                    }
                                } catch (Exception ee) {

                                }
                            }

                            if (!EXIST) {
                                if (PlNner.SW_PARTNUMBER_PLR == 1) {
                                    Object[] sor = new Object[7];
                                    sor[0] = plan.getName();
                                    sor[1] = station.getName();
                                    sor[2] = prod.getJobnumber();
                                    sor[3] = prod.getPartnumber();
                                    sor[4] = prod.getID();
                                    sor[5] = "Hiányzik a PLR adatbázisban erre a termékre és erre a sorra az adatok!";
                                    sor[6] = "Maradéktalanúl értesítse a mérnökséget";
                                    PlNner.EW.MODEL.addRow(sor);
                                }
                            }

                            if ((PlNner.SW_QTY_AUTO_CORRECT == 1) && (station.getJob(prod.getJobnumber()).getStop().getMillis() < new DateTime().getMillis())) {
                                if ((prod.getFactMSzak() > 0) && (prod.getFactMSzak() < prod.getQty())) {
                                    prod.setQty(prod.getFactMSzak());
                                }
                            }

                            if ((PlNner.SW_NOW_AND_FUTURE == 0 || (station.getJob(prod.getJobnumber()).getStop().getMillis() > new DateTime().getMillis()))) {

                                //panelizációs ellenőrzés
                                if (PlNner.SW_PAN == 1) {
                                    if (prod.getQty() % prod.getPanelization() != 0) {
                                        Object[] sor = new Object[7];
                                        sor[0] = plan.getName();
                                        sor[1] = station.getName();
                                        sor[2] = prod.getJobnumber();
                                        sor[3] = prod.getPartnumber();
                                        sor[4] = prod.getID();
                                        sor[5] = "Panelizációs probléma!";
                                        sor[6] = "Változtatni kell a gyártás mennyiségen, hogy osztható legyen a Panelizációval";
                                        PlNner.EW.MODEL.addRow(sor);
                                    }
                                }
                                //panelizációs ellenőrzés
                                if (PlNner.SW_ENGINEER == 1) {
                                    if (prod.isEngeenering()) {
                                        if ((station.getJob(prod.getJobnumber()).getStart().getHourOfDay() < PlNner.VAL_FROM || (station.getJob(prod.getJobnumber()).getStart().getHourOfDay() > PlNner.VAL_TO))) {
                                            Object[] sor = new Object[7];
                                            sor[0] = plan.getName();
                                            sor[1] = station.getName();
                                            sor[2] = prod.getJobnumber();
                                            sor[3] = prod.getPartnumber();
                                            sor[4] = prod.getID();
                                            sor[5] = "Mérnöki gyártás rossz időpontba esik!";
                                            sor[6] = "Változtatni kellene a prioritáson vagy az elötte lévő terméket megbontani!";
                                            PlNner.EW.MODEL.addRow(sor);
                                        }
                                    }
                                }

                                try {
                                    if ((new DateTime().getMillis() < station.getJob(prod.getJobnumber()).getStart().getMillis()) && (station.isKitted(prod.getJobnumber()) == false)) {
                                        if ((Hours.hoursBetween(new DateTime(), station.getJob(prod.getJobnumber()).getStart()).getHours() >= 0) && (Hours.hoursBetween(new DateTime(), station.getJob(prod.getJobnumber()).getStart()).getHours() <= 24)) {

                                            Object[] sor = new Object[7];
                                            sor[0] = plan.getName();
                                            sor[1] = station.getName();
                                            sor[2] = prod.getJobnumber();
                                            sor[3] = prod.getPartnumber();
                                            sor[4] = prod.getID();
                                            sor[5] = "Ez a job nincs még kittelve!!!";
                                            sor[6] = "Mihamarább küld le a raktárnak szedésre!";
                                            PlNner.EW.MODEL.addRow(sor);

                                        }
                                    }

                                } catch (NullPointerException e) {

                                }
                            }
                        }
                    }
                }

            }

            Working(false);
            if (PlNner.EW.MODEL.getRowCount() > 0) {
                BTN.setText(Integer.toString(PlNner.EW.MODEL.getRowCount()) + " eset");
            } else {
                BTN.setText(null);
            }

        }
    };

    public void Working(boolean work) {
        if (work) {
            FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_64.gif")));
        } else {
            FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_stop.gif")));
        }
    }

    /**
     * Creates new form PlanHelper
     */
    public PlanHelper(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setMoveAble();
        timer = new Timer(PlNner.ErrorMsgDelay, MESS);

        timer.start();

    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final PlanHelper handle = PlanHelper.this;
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        FACE = new javax.swing.JLabel();
        BTN = new javax.swing.JLabel();

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/clock16.png"))); // NOI18N
        jMenuItem1.setText("45 perc szundi");
        jMenuItem1.setActionCommand("ff");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);
        jMenuItem1.getAccessibleContext().setAccessibleName("jPpp");

        setUndecorated(true);
        setOpacity(0.9F);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255)));
        jPanel1.setPreferredSize(new java.awt.Dimension(180, 229));

        jLabel3.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N
        jLabel3.setText("Tervező segéd");

        FACE.setBackground(new java.awt.Color(153, 153, 255));
        FACE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/anim_s_stop.gif"))); // NOI18N
        FACE.setComponentPopupMenu(jPopupMenu1);
        FACE.setMaximumSize(new java.awt.Dimension(120, 120));
        FACE.setMinimumSize(new java.awt.Dimension(120, 120));
        FACE.setPreferredSize(new java.awt.Dimension(120, 120));
        FACE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FACEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                FACEMouseExited(evt);
            }
        });

        BTN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BTNMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BTNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BTNMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FACE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(BTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FACE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BTN, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNMouseEntered
        BTN.setBorder(BorderFactory.createLineBorder(Color.black));
        FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_64.gif")));
    }//GEN-LAST:event_BTNMouseEntered

    private void BTNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNMouseExited
        BTN.setBorder(null);
        FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_stop.gif")));
    }//GEN-LAST:event_BTNMouseExited

    private void BTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNMouseClicked
        PlNner.EW.setVisible(true);
    }//GEN-LAST:event_BTNMouseClicked

    private void FACEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FACEMouseEntered
        FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_64.gif")));
    }//GEN-LAST:event_FACEMouseEntered

    private void FACEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FACEMouseExited
        FACE.setIcon(new ImageIcon(this.getClass().getResource("IMG/anim_s_stop.gif")));
    }//GEN-LAST:event_FACEMouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.out.println("Tervező segéd elaltatva 45 percre...");
        timer.setDelay(270000);
        timer.setInitialDelay(270000);
        PlNner.ErrorMsgDelay = 270000;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void tick() {
        if (scanning == null) {
            scanning = new Thread(RUN);
            scanning.start();
        } else {
            scanning = new Thread(RUN);
            scanning.start();
        }

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
            java.util.logging.Logger.getLogger(PlanHelper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlanHelper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlanHelper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlanHelper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PlanHelper dialog = new PlanHelper(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel BTN;
    private javax.swing.JLabel FACE;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables
}
