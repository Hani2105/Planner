/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

 
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import org.joda.time.DateTime;

/**
 *
 * @author Csekme
 */
public class OpenProject extends javax.swing.JFrame {

    File[] file_for_open;

    /**
     * Creates new form OpenProject
     */
    public void OpenPlan(int index) {
        try {
            FileInputStream in = null;
            BufferedInputStream BIS = null;
            ObjectInputStream s = null;

            try {
                ZipInputStream zipin = new ZipInputStream(new FileInputStream(file_for_open[index]));
                zipin.getNextEntry();
                BIS = new BufferedInputStream(zipin);
                s = new ObjectInputStream(BIS);

                Plan2StreamV6 v6 = (Plan2StreamV6) s.readObject();
                //Plan2Stream ps = (Plan2Stream) s.readObject();
                v6.restore();
                PlNner.session.addFile(file_for_open[index].getAbsolutePath());
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount() - 1);
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());

                PlNner.PH.tick();
            } catch (ClassCastException e) {
                e.printStackTrace();
                in = new FileInputStream(file_for_open[index]);
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);

                Plan2StreamV5 ps = (Plan2StreamV5) s.readObject();
                ps.restore();
                PlNner.session.addFile(file_for_open[index].getAbsolutePath());
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
                in = new FileInputStream(file_for_open[index]);
                BIS = new BufferedInputStream(in);
                s = new ObjectInputStream(BIS);

                Plan2StreamV5 ps = (Plan2StreamV5) s.readObject();
                ps.restore();
                
                PlNner.session.addFile(file_for_open[index].getAbsolutePath());
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

    public void PreOpen() {
        Runnable RUN = new Runnable() {

            @Override
            public void run() {

                try {
                    FileInputStream fis = null;
                    BufferedInputStream BIS = null;
                    ObjectInputStream s = null;

                    ZipInputStream zipin = new ZipInputStream(new FileInputStream(file_for_open[0]));
                    zipin.getNextEntry();
                    BIS = new BufferedInputStream(zipin);
                    s = new ObjectInputStream(BIS);

                    LastModified.setText("Utolsó módosítás: " + PlNner.getCurrentDateTime(new DateTime(file_for_open[0].lastModified())));
                    FILE_LAB.setText(file_for_open[0].getAbsolutePath());

                    try {
                        Plan2StreamV6 v6 = (Plan2StreamV6) s.readObject();
                        STATIONS_LAB.setText("Tervben szereplő állomások száma: " + v6.stations.size() + " db");
                        int no = 0;
                        for (int i = 0; i < v6.stations.size(); i++) {
                            no += v6.stations.get(i).partnumbers.size();
                        }

                        String tooltiptext = "<html>";
                        for (int i = 0; i < v6.stations.size(); i++) {
                            tooltiptext += "Állomás: " + v6.stations.get(i).name + "<br>";
                            for (int p = 0; p < v6.stations.get(i).partnumbers.size(); p++) {
                                if (!tooltiptext.contains(v6.stations.get(i).partnumbers.get(p).job)) {
                                    tooltiptext += v6.stations.get(i).partnumbers.get(p).job + "\t" + v6.stations.get(i).partnumbers.get(p).pn + "<br>";
                                }
                            }
                        }
                        tooltiptext += "</html>";
                        INFO.setToolTipText(tooltiptext);

                        PROD_LAB.setText("Tervben szereplő termékek/oldalak száma: " + Integer.toString(no) + " db");
                        FORMATUM.setText("Terv formátuma: legújabb");
                        INFO.setVisible(true);
                        zipin.close();

                        s.close();

                    } catch (ClassCastException e) {

                        fis = new FileInputStream(file_for_open[0]);

                        BIS = new BufferedInputStream(fis);
                        s = new ObjectInputStream(BIS);

                        Plan2StreamV5 v5 = (Plan2StreamV5) s.readObject();

                        STATIONS_LAB.setText("Tervben szereplő állomások száma: " + v5.stations.size() + " db");
                        int no = 0;
                        for (int i = 0; i < v5.stations.size(); i++) {
                            no += v5.stations.get(i).partnumbers.size();
                        }
                        PROD_LAB.setText("Tervben szereplő termékek/oldalak száma: " + Integer.toString(no) + " db");
                        FORMATUM.setText("Terv formátuma: elavult");

                        String tooltiptext = "<html>";
                        for (int i = 0; i < v5.stations.size(); i++) {
                            tooltiptext += "Állomás: " + v5.stations.get(i).name + "<br>";
                            for (int p = 0; p < v5.stations.get(i).partnumbers.size(); p++) {
                                if (!tooltiptext.contains(v5.stations.get(i).partnumbers.get(p).job)) {
                                    tooltiptext += v5.stations.get(i).partnumbers.get(p).job + "\t" + v5.stations.get(i).partnumbers.get(p).pn + "<br>";
                                }
                            }
                        }
                        tooltiptext += "</html>";
                        INFO.setToolTipText(tooltiptext);

                        INFO.setVisible(true);
                    }
                } catch (FileNotFoundException ex) {

                } catch (NotSerializableException er) {

                } catch (ClassCastException e) {
                    STATIONS_LAB.setText("Tervben szereplő állomások száma:  - db");
                    PROD_LAB.setText("Tervben szereplő termékek/oldalak száma: - db");
                    FORMATUM.setText("Terv formátuma: nem támogatott");
                    INFO.setToolTipText(null);
                    INFO.setVisible(false);
                } catch (IOException ex) {
                    try {
                        FileInputStream fis = null;
                        BufferedInputStream BIS = null;
                        ObjectInputStream s = null;
                        fis = new FileInputStream(file_for_open[0]);

                        BIS = new BufferedInputStream(fis);
                        s = new ObjectInputStream(BIS);

                        Plan2StreamV5 v5 = (Plan2StreamV5) s.readObject();

                        STATIONS_LAB.setText("Tervben szereplő állomások száma: " + v5.stations.size() + " db");
                        int no = 0;
                        for (int i = 0; i < v5.stations.size(); i++) {
                            no += v5.stations.get(i).partnumbers.size();
                        }
                        PROD_LAB.setText("Tervben szereplő termékek/oldalak száma: " + Integer.toString(no) + " db");
                        FORMATUM.setText("Terv formátuma: elavult");

                        String tooltiptext = "<html>";
                        for (int i = 0; i < v5.stations.size(); i++) {
                            tooltiptext += "Állomás: " + v5.stations.get(i).name + "<br>";
                            for (int p = 0; p < v5.stations.get(i).partnumbers.size(); p++) {
                                if (!tooltiptext.contains(v5.stations.get(i).partnumbers.get(p).job)) {
                                    tooltiptext += v5.stations.get(i).partnumbers.get(p).job + "\t" + v5.stations.get(i).partnumbers.get(p).pn + "<br>";
                                }
                            }
                        }
                        tooltiptext += "</html>";
                        INFO.setToolTipText(tooltiptext);

                        INFO.setVisible(true);
                    } catch (FileNotFoundException ex1) {
                        Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IOException ex1) {
                        Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (ClassNotFoundException ex1) {
                        Logger.getLogger(OpenProject.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                } catch (ClassNotFoundException ex) {

                }

            }
        };

        new Thread(RUN).start();

    }

    public OpenProject(java.awt.Frame parent, boolean modal) {

        initComponents();
        setIconImage(new ImageIcon(this.getClass().getResource("IMG/down64.png")).getImage());
        setCenter();
    }

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void setVisible(String dir) {
        FileTreeModel model = new FileTreeModel(new File(dir));
        //FileDirectory model = new FileDirectory();
        TREE.setModel(model);

        TREE.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {

                TreePath[] nodes = TREE.getSelectionPaths();
                if (nodes != null) {
                    file_for_open = new File[nodes.length];
                    for (int i = 0; i < nodes.length; i++) {
                        file_for_open[i] = (File) nodes[i].getLastPathComponent();
                    }

                }
            }
        });
        INFO.setVisible(false);
        INFO.setToolTipText(null);
        setVisible(true);

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
        TREE = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        LastModified = new javax.swing.JLabel();
        STATIONS_LAB = new javax.swing.JLabel();
        PROD_LAB = new javax.swing.JLabel();
        FORMATUM = new javax.swing.JLabel();
        INFO = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        FILE_LAB = new javax.swing.JLabel();

        setTitle("Projekt megnyitása...");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TREE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TREEMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TREEMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TREE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel1.setText("Projektek:");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/projects_folder48.png"))); // NOI18N

        jButton1.setText("Megnyitás");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 1, true), "Projekt leírás:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), java.awt.Color.blue)); // NOI18N
        jPanel2.setForeground(new java.awt.Color(51, 51, 255));

        LastModified.setText("Utolsó módosítás:");

        STATIONS_LAB.setText("Tervben szereplő állomások száma: ");

        PROD_LAB.setText("Tervben szereplő termékek/oldalak száma:");

        FORMATUM.setText("Terv formátuma:");

        INFO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/info16.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LastModified)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(STATIONS_LAB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(INFO))
                    .addComponent(PROD_LAB)
                    .addComponent(FORMATUM))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LastModified)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(STATIONS_LAB)
                    .addComponent(INFO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PROD_LAB)
                .addGap(33, 33, 33)
                .addComponent(FORMATUM)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/browse16.png"))); // NOI18N
        jButton2.setToolTipText("Megnyitás egyéb helyről...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        FILE_LAB.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        FILE_LAB.setMaximumSize(new java.awt.Dimension(354, 17));
        FILE_LAB.setMinimumSize(new java.awt.Dimension(354, 17));
        FILE_LAB.setPreferredSize(new java.awt.Dimension(354, 17));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(FILE_LAB, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FILE_LAB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
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

    private void TREEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TREEMouseClicked

    }//GEN-LAST:event_TREEMouseClicked

    private void TREEMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TREEMouseReleased
        if (file_for_open != null) {
            if (file_for_open.length == 1) {
                PreOpen();

            } else {
                LastModified.setText("Utolsó módosítás: ");
                STATIONS_LAB.setText("Tervben szereplő állomások száma: ");
                PROD_LAB.setText("Tervben szereplő termékek/oldalak száma:   ");
                FORMATUM.setText("Terv formátuma: ");
                INFO.setVisible(false);
                INFO.setToolTipText(null);

            }
        }
    }//GEN-LAST:event_TREEMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Runnable RUN = new Runnable() {

            @Override
            public void run() {

                for (int i = 0; i < file_for_open.length; i++) {
                    if (file_for_open[i].isFile()) {
                        // OpenProject.this.setState(JFrame.ICONIFIED);
                        OpenPlan(i);

                    }
                }

                OpenProject.this.setVisible(false);

            }
        };

        Thread szal = new Thread(RUN);
        szal.start();

        //  setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileDialog fd = new FileDialog(this, "Projekt betöltése", FileDialog.LOAD);
        fd.setDirectory(PlNner.DIR);
        fd.setLocation(50, 50);
        fd.setVisible(true);
        if (fd.getFile() != null) {
            file_for_open = new File[1];
            file_for_open[0] = new File(fd.getDirectory() + "\\" + fd.getFile());
            PreOpen();
            TREE.setSelectionInterval(-1, -1);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(OpenProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpenProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpenProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpenProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OpenProject dialog = new OpenProject(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel FILE_LAB;
    private javax.swing.JLabel FORMATUM;
    private javax.swing.JLabel INFO;
    private javax.swing.JLabel LastModified;
    private javax.swing.JLabel PROD_LAB;
    private javax.swing.JLabel STATIONS_LAB;
    private javax.swing.JTree TREE;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
