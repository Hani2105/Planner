/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;



import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;
import static pl.nner.PlNner.list_napok;

/**
 *
 * @author krisztian_csekme1
 */
public class JobRiportWindow extends javax.swing.JDialog {

    Station station;

    /**
     * Creates new form JobRiportWindow
     */
    public JobRiportWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        TAB.addKeyListener(new ClipboardKeyAdapter(TAB));
        setCenter();
      
        
       
    }

   
    
    
    
    
    
    
    
    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void calc() {
        station.recalcJobs();
        //Jobszám", "Partnumber", "Darabszám", "Gyártás indulása"
        String fejlec = "Jobszám,Partnumber,Darabszám,Gyártás indulása,Kittelve,";
        String[] szak = new String[]{"Dé", "Du", "Éjj"};

        int szak_i = 0;
        int nap_i = 0;

        for (int i = 0; i < 168; i++) {

            if (i % 12 == 0) {

                fejlec += list_napok[nap_i] + " - " + szak[szak_i] + ",";
                szak_i++;
                if (szak_i == 2) {
                    szak_i = 0;
                    nap_i++;
                }
            }

        }
        fejlec = fejlec.substring(0, fejlec.length() - 1);
        DefaultTableModel MODEL = new DefaultTableModel(fejlec.split(","), 0);

        for (int i = 0; i < station.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!station.isKitted(station.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    Object[] sor = new Object[MODEL.getColumnCount()];
                    sor[0] = station.JOBS.get(i).jobname;
                    sor[1] = station.JOBS.get(i).partnumber;
                    sor[2] = Integer.toString(station.JOBS.get(i).getQty());
                    sor[3] = PlNner.getDateTimeShiftFormat(station.JOBS.get(i).getStart());

                    if (!station.isExistinLoader(station.JOBS.get(i).jobname)) {
                        station.putJobtoLoader(station.JOBS.get(i).jobname, station.JOBS.get(i).partnumber, (double)station.JOBS.get(i).getQty());
                    }
                    sor[4] = station.isKitted(station.JOBS.get(i).jobname);
                    for (int m = 5; m < MODEL.getColumnCount(); m++) {

                        sor[m] = Integer.toString(station.JOBS.get(i).getQty(m - 3));
                        if (sor[m].equals("0")) {
                            sor[m] = "";
                        }
                    }
                    MODEL.addRow(sor);
                }
            }
        }
        PlNner.JRW.TAB.setModel(MODEL);
        PlNner.JRW.TAB.getColumnModel().getColumn(4).setCellEditor(PlNner.JRW.TAB.getDefaultEditor(Boolean.class));
        PlNner.JRW.TAB.getColumnModel().getColumn(4).setCellRenderer(PlNner.JRW.TAB.getDefaultRenderer(Boolean.class));
       
        
        try {
            URL url = new URL("http://143.116.140.114/szaif/lm/main.php?line=A1_Pre&xsize=1250&ysize=400&source=plrdb&prop=txt");
            
             URLConnection connection = url.openConnection();
             
            
            
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

    public void init(Station stat) {
        station = stat;
        calc();

        //HBSF1517247,TAB,TAB,LFHB2207710-SMT,TAB,TAB,1792,TAB,Released,TAB,22-Apr-2015 00:00:00,*DN
        String[] H = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String D = String.format("%02d", new DateTime().getDayOfMonth()) + "-" + H[new DateTime().getMonthOfYear() - 1] + "-" + Integer.toString(new DateTime().getYear()) + " 00:00:00";
        String manual_sor = "JOB#TAB#TAB#PN#TAB#TAB#QTY#TAB#Released#TAB#DATE#*DN";
        String iface_sor = "JOB#*ML(298,197)#PN#*ML(263,292)#QTY#*ML(674,293)#DATE#*ML(545,366)#*ML(479,425)#SFDC#TAB#Y#TAB#*ML(721,100)#*ML(312,240)#Released#\\^{S}#*DN";
        String iface_sor_push = "JOB#*ML(298,197)#PN#*ML(263,292)#QTY#*ML(674,293)#DATE#*ML(52,362)#*ML(354,495)#PUSH#*ML(545,366)#*ML(479,425)#SFDC#TAB#Y#TAB#*ML(721,100)#*ML(312,240)#Released#\\^{S}#*DN";

        //JOB#*ML(298,197)#PN#*ML(263,292)#QTY#*ML(674,293)#DATE#*ML(52,362)#*ML(354,495)#PUSH#*ML(312,240)#Released#\^{S}#*DN
        String push_sor = "JOB#*ML(298,197)#PN#*ML(263,292)#QTY#*ML(674,293)#DATE#*ML(52,362)#*ML(354,495)#PUSH#*ML(312,240)#Released#\\^{S}#*DN";
        
        //HBSF151192	*ML(687,170)	\{F11}	LFHB1722379	\^{F11}	*ML(329,240)	*ML(328,314)	*ML(366,317)	*ML(236,492)	*ML(431,691)	\^{S}	\{F11}	LFPSTALE-SAC405-OM338T	\^{F11}	*ML(329,240)	*ML(328,314)	*ML(366,317)	*ML(284,470)	*ML(431,691)	\^{S}	*ML(259,139)

        String cimkepaszta = "JOB#*ML(687,170)#\\{F11}#LFHB1722379#\\^{F11}#*ML(329,240)#*ML(328,314)#*ML(366,317)#*ML(236,492)#*ML(431,691)#\\^{S}#\\{F11}#LFPSTALE-SAC405-OM338T#\\^{F11}#*ML(329,240)#*ML(328,314)#*ML(366,317)#*ML(284,470)#*ML(431,691)#\\^{S}#*ML(259,139)";        
        String cimkepasztalaptop = "JOB#*ML(687,170)#\\{F11}#LFHB1722379#\\^{F11}#*ML(329,240)#*ML(328,314)#*ML(366,317)#*ML(395,465)#*ML(575,669)#\\^{S}#\\{F11}#LFPSTALE-SAC405-OM338T#\\^{F11}#*ML(329,240)#*ML(328,314)#*ML(366,317)#*ML(428,448)#*ML(575,669)#\\^{S}#*ML(259,139)";
        
        DefaultTableModel MODEL_PUSH = new DefaultTableModel(0, 14);
        for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_PUSH.addRow(push_sor.replace("JOB", stat.JOBS.get(i).jobname).replace("PN", stat.JOBS.get(i).partnumber).replace("QTY", Integer.toString(stat.JOBS.get(i).getQty())).replace("DATE", D).split("#"));
                }
            }
        }
        
        
        
        
        
        DefaultTableModel MODEL_MAN = new DefaultTableModel(0, 12);
        for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_MAN.addRow(manual_sor.replace("JOB", stat.JOBS.get(i).jobname).replace("PN", stat.JOBS.get(i).partnumber).replace("QTY", Integer.toString(stat.JOBS.get(i).getQty())).replace("DATE", D).split("#"));
                }
            }
        }
        DefaultTableModel MODEL_IFACE = new DefaultTableModel(0, 18);
        for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_IFACE.addRow(iface_sor.replace("JOB", stat.JOBS.get(i).jobname).replace("PN", stat.JOBS.get(i).partnumber).replace("QTY", Integer.toString(stat.JOBS.get(i).getQty())).replace("DATE", D).split("#"));
                }
            }
        }
        DefaultTableModel MODEL_IFACE_PUSH = new DefaultTableModel(0, 21);
        for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_IFACE_PUSH.addRow(iface_sor_push.replace("JOB", stat.JOBS.get(i).jobname).replace("PN", stat.JOBS.get(i).partnumber).replace("QTY", Integer.toString(stat.JOBS.get(i).getQty())).replace("DATE", D).split("#"));
                }
            }
        }
        
        DefaultTableModel MODEL_CIMKEPASZTA = new DefaultTableModel(0, 21);
       for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_CIMKEPASZTA.addRow(cimkepaszta.replace("JOB", stat.JOBS.get(i).jobname).split("#"));
                }
            }
        }
          DefaultTableModel MODEL_CIMKEPASZTA_LAPTOP = new DefaultTableModel(0, 21);
       for (int i = 0; i < stat.JOBS.size(); i++) {
            if (station.JOBS.get(i).isReleased() || CHECK_REL.isSelected()) {
                if (!stat.isKitted(stat.JOBS.get(i).jobname) || !CH_KIT.isSelected()) {
                    MODEL_CIMKEPASZTA_LAPTOP.addRow(cimkepasztalaptop.replace("JOB", stat.JOBS.get(i).jobname).split("#"));
                }
            }
        }
        
        PlNner.JRW.TAB_PUSH.setModel(MODEL_PUSH);
        PlNner.JRW.TAB_MANUAL.setModel(MODEL_MAN);
        PlNner.JRW.TAB_IFACE.setModel(MODEL_IFACE);
        PlNner.JRW.TAB_IFACE_PUSH.setModel(MODEL_IFACE_PUSH);
        PlNner.JRW.TAB_CIMKE_PASZTA.setModel(MODEL_CIMKEPASZTA);
         PlNner.JRW.TAB_CIMKE_PASZTA_LAPTOP.setModel(MODEL_CIMKEPASZTA_LAPTOP);
        

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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAB = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAB_MANUAL = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        TAB_PUSH = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TAB_IFACE = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        TAB_IFACE_PUSH = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        TAB_CIMKE_PASZTA = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        TAB_CIMKE_PASZTA_LAPTOP = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        CHECK_REL = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        CH_KIT = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        TAB.setModel(new javax.swing.table.DefaultTableModel(
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
        TAB.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TAB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TAB);

        jTabbedPane1.addTab("Jobok", jScrollPane1);

        TAB_MANUAL.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(TAB_MANUAL);

        jTabbedPane1.addTab("Makró - Manuál", jScrollPane2);

        TAB_PUSH.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(TAB_PUSH);

        jTabbedPane1.addTab("Makró - Push", new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/new-24.png")), jScrollPane8); // NOI18N

        TAB_IFACE.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(TAB_IFACE);

        jTabbedPane1.addTab("Makró - Interface", jScrollPane3);

        TAB_IFACE_PUSH.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(TAB_IFACE_PUSH);

        jTabbedPane1.addTab("Makró - Interface Push", jScrollPane4);

        TAB_CIMKE_PASZTA.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(TAB_CIMKE_PASZTA);

        jTabbedPane1.addTab("Makró - Címke, paszta", jScrollPane5);

        TAB_CIMKE_PASZTA_LAPTOP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(TAB_CIMKE_PASZTA_LAPTOP);

        jTabbedPane1.addTab("Makró - Címke, paszta - Laptop", jScrollPane6);
        jTabbedPane1.addTab("Line Monitor", jScrollPane7);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        CHECK_REL.setBackground(new java.awt.Color(255, 255, 255));
        CHECK_REL.setText("unreleased");
        CHECK_REL.setToolTipText("Számoljon a gyártásból kivett, unreleased jobokkal is...");
        CHECK_REL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CHECK_RELActionPerformed(evt);
            }
        });

        jButton1.setText("Rendben");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        CH_KIT.setBackground(new java.awt.Color(255, 255, 255));
        CH_KIT.setText("Kittelt jobok elrejtése");
        CH_KIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_KITActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CHECK_REL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_KIT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CHECK_REL)
                    .addComponent(jButton1)
                    .addComponent(CH_KIT))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Jobok");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TABMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABMouseClicked

    }//GEN-LAST:event_TABMouseClicked

    private void CH_KITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_KITActionPerformed
        init(station);
    }//GEN-LAST:event_CH_KITActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (int r = 0; r < TAB.getRowCount(); r++) {

            String jobname = TAB.getValueAt(r, 0).toString();

            for (int rr = 0; rr < 100; rr++) {
                if (station.loader[0][rr] != null) {
                    if (station.loader[0][rr].toString().equals(jobname)) {
                        station.loader[3][rr] = TAB.getValueAt(r, 4);
                    }
                }
            }

        }

        PlNner.PH.tick();
        station.getPlan().NEED_TO_SAVE = true;
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CHECK_RELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CHECK_RELActionPerformed
        calc();
    }//GEN-LAST:event_CHECK_RELActionPerformed

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
            java.util.logging.Logger.getLogger(JobRiportWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JobRiportWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JobRiportWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JobRiportWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JobRiportWindow dialog = new JobRiportWindow(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox CHECK_REL;
    private javax.swing.JCheckBox CH_KIT;
    public javax.swing.JTable TAB;
    private javax.swing.JTable TAB_CIMKE_PASZTA;
    private javax.swing.JTable TAB_CIMKE_PASZTA_LAPTOP;
    private javax.swing.JTable TAB_IFACE;
    private javax.swing.JTable TAB_IFACE_PUSH;
    private javax.swing.JTable TAB_MANUAL;
    private javax.swing.JTable TAB_PUSH;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
