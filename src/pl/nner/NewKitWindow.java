/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;

/**
 *
 * @author krisztian_csekme1
 */
public class NewKitWindow extends javax.swing.JDialog {
private Station station;
private Point anchorPoint ;
    /**
     * Creates new form NewKitWindow
     */
    public NewKitWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setMoveAble();
        setCenter();
    }

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
        final NewKitWindow handle = NewKitWindow.this;
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
    
    public void init(){
           DefaultTableModel MODEL = new DefaultTableModel(new Object[]{"Jobszám", "Partnumber", "Darabszám","Gyártás indulása", "Kittelésre"},0);
           for (int i = 0; i < station.JOBS.size(); i++) {
                 
                Object[] sor = new Object[MODEL.getColumnCount()];
                sor[0] = station.JOBS.get(i).jobname;
                sor[1] = station.JOBS.get(i).partnumber;
                sor[2] = Integer.toString(station.JOBS.get(i).getQty());
                sor[3] = PlNner.getDateTimeShiftFormat(station.JOBS.get(i).getStart().minusHours(CLOCK.getSelectedIndex() + 1));
                sor[4] = Boolean.FALSE;
                if (CH_KIT.isSelected()){
                 if (!station.isKitted(station.JOBS.get(i).jobname)){
                     MODEL.addRow(sor);
                 }   
                }else{
              MODEL.addRow(sor);
                }
          }
         TAB.setModel(MODEL);
        
         TAB.getColumnModel().getColumn(4).setCellEditor(PlNner.JRW.TAB.getDefaultEditor(Boolean.class));
         TAB.getColumnModel().getColumn(4).setCellRenderer(PlNner.JRW.TAB.getDefaultRenderer(Boolean.class));
         TAB.getColumnModel().getColumn(0).setCellRenderer(new own_jTable_Cell_Format_Model_KIT(station));
         
         
         SUBJECT.setText("Need To Kit WK" + station.getPlan().weekdate.getWeekOfWeekyear() + "  - " + station.getName().toUpperCase() + "-SOR - #-RÉSZ - %".replace("%", CUSTOMER.getText()).replace("#", NO.getSelectedItem().toString()) + " - " +  PlNner.getCurrentDate(new DateTime()) );
         MESSAGE.setText("Sziasztok,\n" + "\n" + "Légyszi szedjétek az alábbi jobokat össze:\n");
        
        
    }
    
    
    public void setVisible(boolean value, Station stat){
        CUSTOMER.setText("");
     
        station = stat;
          init();
        setCenter();
        super.setVisible(value);
        
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
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        SUBJECT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MESSAGE = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAB = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CUSTOMER = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        NO = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        CLOCK = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        CH_KIT = new javax.swing.JCheckBox();

        setUndecorated(true);
        setOpacity(0.9F);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255)));

        jLabel12.setFont(new java.awt.Font("sansserif", 2, 18)); // NOI18N
        jLabel12.setText("Kit levél összeállítása:");

        jLabel1.setText("Tárgy:");

        SUBJECT.setEditable(false);
        SUBJECT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255)));

        jLabel2.setText("Szöveg:");

        MESSAGE.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255)));
        jScrollPane1.setViewportView(MESSAGE);

        jLabel3.setText("Jobok kiválasztása:");

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TAB);

        jButton1.setText("Importálás");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setText("Vevő:");

        CUSTOMER.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255)));
        CUSTOMER.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CUSTOMERKeyReleased(evt);
            }
        });

        jLabel6.setText("Sorszám:");

        NO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        NO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NOActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/save16.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/open16.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Időzítés:");

        CLOCK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        CLOCK.setSelectedIndex(7);
        CLOCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CLOCKActionPerformed(evt);
            }
        });

        jLabel9.setText("órával korábban");

        CH_KIT.setBackground(new java.awt.Color(255, 255, 255));
        CH_KIT.setSelected(true);
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel5))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(CUSTOMER, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(NO, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(SUBJECT)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CLOCK, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(CH_KIT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(SUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CUSTOMER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(NO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(CLOCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(CH_KIT))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        FileDialog fd = new FileDialog(this, "Elmentett szöveg minta betöltése", FileDialog.LOAD);
        fd.setDirectory("S:\\SiteData\\BUD1\\EMS\\planning\\[DEV_CENTER]\\PlannerFacelift\\SAMPLES");
        fd.setLocation(50, 50);
        fd.setVisible(true);
        if (fd != null) {

            try {

                //Create object of FileReader
                FileReader inputFile = new FileReader(fd.getDirectory() + "\\" + fd.getFile());

                    //Instantiate the BufferedReader Class
                    BufferedReader bufferReader = new BufferedReader(inputFile);

                    //Variable to hold the one line data
                    String line;
                    String full_text = "";
                    // Read file line by line and print on the console
                    while ((line = bufferReader.readLine()) != null) {
                        full_text += line + "\n";
                    }
                    MESSAGE.setText(full_text);
                    //Close the buffer reader
                    bufferReader.close();
                } catch (Exception e) {
                    System.out.println("Error while reading file line by line:" + e.getMessage());
                }
            }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileDialog fd = new FileDialog(this, "Szövegminta elmentése", FileDialog.SAVE);
        fd.setDirectory("S:\\SiteData\\BUD1\\EMS\\planning\\[DEV_CENTER]\\PlannerFacelift\\SAMPLES");
        fd.setLocation(50, 50);
        fd.setVisible(true);
        if (fd != null) {

            try {
                FileWriter fw = new FileWriter(fd.getDirectory() + "\\" + fd.getFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter fileOut = new PrintWriter(bw);
                    fileOut.println(MESSAGE.getText());
                    fileOut.close();

                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void NOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NOActionPerformed
        SUBJECT.setText("Need To Kit WK" + station.getPlan().weekdate.getWeekOfWeekyear() + "  - " + station.getName().toUpperCase() + "-SOR - #-RÉSZ - %".replace("%", CUSTOMER.getText()).replace("#", NO.getSelectedItem().toString()) + " - " +  PlNner.getCurrentDate(new DateTime()) );
    }//GEN-LAST:event_NOActionPerformed

    private void CUSTOMERKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CUSTOMERKeyReleased
        SUBJECT.setText("Need To Kit WK" + station.getPlan().weekdate.getWeekOfWeekyear() + "  - " + station.getName().toUpperCase() + "-SOR - #-RÉSZ - %".replace("%", CUSTOMER.getText()).replace("#", NO.getSelectedItem().toString()) + " - " +  PlNner.getCurrentDate(new DateTime()) );
    }//GEN-LAST:event_CUSTOMERKeyReleased

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        setVisible(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (CUSTOMER.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Nem adtál meg projekt nevet/vevőt!", "Figyelem!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("IMG/angry64.png")));
        }else{
            //WO	PN	Qty	Planner Comment	Line	Customer

            PlNner.MS.SUBJECT.setText( SUBJECT.getText() );
            StringBuilder SB = new StringBuilder();

            // SB.append("<HTML>");
            //SB.append("<BODY>");
            SB.append(MESSAGE.getText().replace("\n", "<br>"));
            SB.append("<TABLE border=\"1px\">");
            SB.append("<tr>");
            SB.append("<th>");
            SB.append("Wo");
            SB.append("</th>");
            SB.append("<th>");
            SB.append("PN");
            SB.append("</th>");
            SB.append("<th>");
            SB.append("QTY");
            SB.append("</th>");
            SB.append("<th>");
            SB.append("Planner Comment");
            SB.append("</th>");
            SB.append("<th>");
            SB.append("Line");
            SB.append("</th>");
            SB.append("<th>");
            SB.append("Customer");
            SB.append("</th>");
            SB.append("</tr>");

            for (int r=0; r<TAB.getRowCount(); r++){
                if (TAB.getValueAt(r, 4)==Boolean.TRUE){

                    String WO_NUMBER = TAB.getValueAt(r, 0).toString();

                    PlNner.MS.sign_to_kit.add(WO_NUMBER);

                    SB.append("<tr>");
                    SB.append("<td>");
                    SB.append(TAB.getValueAt(r, 0)); //WO
                    SB.append("</td>");
                    SB.append("<td>");
                    SB.append(TAB.getValueAt(r, 1)); //PN
                    SB.append("</td>");
                    SB.append("<td>");
                    SB.append(TAB.getValueAt(r, 2));  //QTY
                    SB.append("</td>");
                    SB.append("<td>");
                    SB.append(TAB.getValueAt(r, 3));  //Planner Comment
                    SB.append("</td>");
                    SB.append("<td>");
                    SB.append(station.getName().toUpperCase()).append("-SOR");  //Line
                    SB.append("</td>");
                    SB.append("<td>");
                    SB.append(CUSTOMER.getText());  //Customer
                    SB.append("</td>");
                    SB.append("</tr>");
                }
            }

            SB.append("</TABLE>");

            // SB.append("</BODY>");
            // SB.append("</HTML>");
            PlNner.MS.MESSAGE.setText(SB.toString());
            try{
            PlNner.MS.LIST.setSelectedItem("KIT");
            }catch (Exception e){
                
            }
            setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CLOCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CLOCKActionPerformed
       init();
    }//GEN-LAST:event_CLOCKActionPerformed

    private void CH_KITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_KITActionPerformed
       init();
    }//GEN-LAST:event_CH_KITActionPerformed

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
            java.util.logging.Logger.getLogger(NewKitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewKitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewKitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewKitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewKitWindow dialog = new NewKitWindow(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox CH_KIT;
    private javax.swing.JComboBox CLOCK;
    public javax.swing.JTextField CUSTOMER;
    private javax.swing.JTextPane MESSAGE;
    private javax.swing.JComboBox NO;
    private javax.swing.JTextField SUBJECT;
    private javax.swing.JTable TAB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
