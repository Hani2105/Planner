/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.joda.time.DateTime;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
public class NewProduct extends javax.swing.JDialog {

    private int pre; //előválasztott számítási módszer

    /**
     * Creates new form NewProduct
     */
    public NewProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();

    }

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void init() {
        TAB.setModel(PlNner.PLR_DB.getDataTableModel("SELECT `cycletime_prog`.`ID` as cpID,`oraclepn`,smtprogname,`smtline` as sline,`sequence`,`boardnumber`,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 1 ORDER BY ID DESC LIMIT 1) as mertido,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 2 ORDER BY ID DESC LIMIT 1) as gyorsmeres,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 3 ORDER BY ID DESC LIMIT 1) as kalkulalt,IFNULL(expectedeffbyprog,COALESCE((SELECT expectedeff FROM cycletime_config WHERE smtline = sline),(SELECT expectedeff FROM cycletime_config WHERE smtline = 'ALL'))) as eff FROM `cycletime_prog` ORDER BY smtprogname;"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TopPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        QS = new javax.swing.JTextField();
        jScroll4TAB = new javax.swing.JScrollPane();
        TAB = new javax.swing.JTable();
        BottomPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TEXT_PN = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TEXT_PAN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TEXT_MEASURED = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TEXT_QUICK = new javax.swing.JTextField();
        TEXT_CALC = new javax.swing.JTextField();
        GO = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TEXT_EFF = new javax.swing.JTextField();
        TEXT_QTY = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TEXT_JOB = new javax.swing.JTextField();
        COMBO_MES = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        TEXT_STARTUP_MIN = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TEXT_DOWNTIME_MIN = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        TEXT_SEQ = new javax.swing.JTextField();

        setTitle("Új termék hozzáadása");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        TopPanel.setMaximumSize(new java.awt.Dimension(32767, 60));
        TopPanel.setMinimumSize(new java.awt.Dimension(0, 60));
        TopPanel.setPreferredSize(new java.awt.Dimension(664, 60));

        jLabel1.setText("Termék keresés:");

        QS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                QSKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout TopPanelLayout = new javax.swing.GroupLayout(TopPanel);
        TopPanel.setLayout(TopPanelLayout);
        TopPanelLayout.setHorizontalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QS, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(530, Short.MAX_VALUE))
        );
        TopPanelLayout.setVerticalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(QS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(TopPanel);

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
        TAB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TAB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TABMouseReleased(evt);
            }
        });
        jScroll4TAB.setViewportView(TAB);

        getContentPane().add(jScroll4TAB);

        BottomPanel.setMaximumSize(new java.awt.Dimension(32767, 180));
        BottomPanel.setMinimumSize(new java.awt.Dimension(0, 180));
        BottomPanel.setName(""); // NOI18N

        jLabel2.setText("Kiválasztott termék:");

        jLabel3.setText("Partnumber:");

        jLabel4.setText("Panelizáció:");

        TEXT_PAN.setEditable(false);
        TEXT_PAN.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setText("Mért idő:");

        TEXT_MEASURED.setEditable(false);
        TEXT_MEASURED.setBackground(new java.awt.Color(153, 255, 153));

        jLabel6.setText("Gyorsmért idő:");

        jLabel7.setText("Kalkulált idő:");

        TEXT_QUICK.setEditable(false);
        TEXT_QUICK.setBackground(new java.awt.Color(102, 204, 255));

        TEXT_CALC.setEditable(false);
        TEXT_CALC.setBackground(new java.awt.Color(255, 153, 153));

        GO.setText("Gyártásra");
        GO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GOActionPerformed(evt);
            }
        });

        jLabel8.setText("Gyártandó mennyiség:");

        jLabel9.setText("Hatékonyság:");

        TEXT_EFF.setEditable(false);
        TEXT_EFF.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setText("Job-szám:");

        COMBO_MES.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mért idő", "Gyorsmért idő", "Kalkulált idő" }));
        COMBO_MES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMBO_MESActionPerformed(evt);
            }
        });

        jLabel11.setText("Számítás:");

        jLabel12.setText("Betárazási idő (perc):");

        TEXT_STARTUP_MIN.setText("30");

        jLabel13.setText("Kitárazási idő (perc):");

        TEXT_DOWNTIME_MIN.setText("30");

        jLabel14.setText("Szekvencia:");

        TEXT_SEQ.setEditable(false);
        TEXT_SEQ.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout BottomPanelLayout = new javax.swing.GroupLayout(BottomPanel);
        BottomPanel.setLayout(BottomPanelLayout);
        BottomPanelLayout.setHorizontalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BottomPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(BottomPanelLayout.createSequentialGroup()
                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(TEXT_PN, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addGroup(BottomPanelLayout.createSequentialGroup()
                                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(TEXT_PAN, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TEXT_SEQ)))
                            .addComponent(TEXT_EFF, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BottomPanelLayout.createSequentialGroup()
                                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(TEXT_CALC, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TEXT_QUICK, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(GO))
                            .addGroup(BottomPanelLayout.createSequentialGroup()
                                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(TEXT_QTY, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(BottomPanelLayout.createSequentialGroup()
                                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addComponent(TEXT_MEASURED, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(TEXT_JOB, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel11)
                                            .addComponent(COMBO_MES, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(TEXT_STARTUP_MIN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(TEXT_DOWNTIME_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 170, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        BottomPanelLayout.setVerticalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEXT_PN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_MEASURED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_QTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_STARTUP_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEXT_PAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_QUICK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_JOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_DOWNTIME_MIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEXT_SEQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEXT_CALC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GO)
                    .addComponent(TEXT_EFF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(COMBO_MES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(BottomPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void QSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QSKeyReleased
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(TAB.getModel());

        TAB.setRowSorter(sorter);
        if (QS.getText().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(QS.getText()));
        }
    }//GEN-LAST:event_QSKeyReleased

    public void fill() {
        if (TAB.getSelectedRow() > -1) {

            TEXT_PN.setText((String) TAB.getValueAt(TAB.getSelectedRow(), 1));

            TEXT_EFF.setText((String) TAB.getValueAt(TAB.getSelectedRow(), 9));

            TEXT_PAN.setText((String) TAB.getValueAt(TAB.getSelectedRow(), 5));
            TEXT_SEQ.setText((String) TAB.getValueAt(TAB.getSelectedRow(), 4));
            
            
            TEXT_DOWNTIME_MIN.setText("30");
            TEXT_STARTUP_MIN.setText("30");

            DecimalFormat df = new DecimalFormat("#0.00");
            try {
                TEXT_MEASURED.setText(df.format(Double.parseDouble((String) TAB.getValueAt(TAB.getSelectedRow(), 6)))); //#1
            } catch (NullPointerException e) {

            }
            try {
                TEXT_QUICK.setText(df.format(Double.parseDouble((String) TAB.getValueAt(TAB.getSelectedRow(), 7)))); //#2
            } catch (NullPointerException e) {

            }
            try {
                TEXT_CALC.setText(df.format(Double.parseDouble((String) TAB.getValueAt(TAB.getSelectedRow(), 8)))); //#3
            } catch (NullPointerException e) {

            }
            if (TEXT_MEASURED.getText().length() > 0) {
                COMBO_MES.setSelectedIndex(0);
                pre = 0;
            } else if (TEXT_QUICK.getText().length() > 0) {
                COMBO_MES.setSelectedIndex(1);
                pre = 1;
            } else if (TEXT_CALC.getText().length() > 0) {
                COMBO_MES.setSelectedIndex(2);
                pre = 2;
            } else {
                JOptionPane.showMessageDialog(null, "Nincs ciklusidő! Haladéktalanul vegye fel a kapcsolatot a mérnökséggel!", "Figyelem", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void TABMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABMouseClicked

       
    }//GEN-LAST:event_TABMouseClicked

    private void COMBO_MESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMBO_MESActionPerformed

        if ((COMBO_MES.getSelectedIndex() == 0) && (TEXT_MEASURED.getText().length() == 0)) {
            JOptionPane.showMessageDialog(null, "Nincsen mért adat!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    COMBO_MES.setSelectedIndex(pre);
                }
            });

        } else if ((COMBO_MES.getSelectedIndex() == 1) && (TEXT_QUICK.getText().length() == 0)) {
            JOptionPane.showMessageDialog(null, "Nincsen gyorsmért adat!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    COMBO_MES.setSelectedIndex(pre);
                }
            });

        } else if ((COMBO_MES.getSelectedIndex() == 2) && (TEXT_CALC.getText().length() == 0)) {
            JOptionPane.showMessageDialog(null, "Nincsen kalkulált adat!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    COMBO_MES.setSelectedIndex(pre);
                }
            });

        } else {
            pre = COMBO_MES.getSelectedIndex();
        }


    }//GEN-LAST:event_COMBO_MESActionPerformed

    public void clearTextBoxes() {
        TEXT_MEASURED.setText(null);
        TEXT_QUICK.setText(null);
        TEXT_CALC.setText(null);
        TEXT_PN.setText(null);
        TEXT_QTY.setText(null);
        TEXT_PAN.setText(null);
        TEXT_EFF.setText(null);
        TEXT_STARTUP_MIN.setText(null);
        TEXT_DOWNTIME_MIN.setText(null);
        TEXT_SEQ.setText(null);
        TEXT_JOB.setText(null);
    }


    private void GOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GOActionPerformed
        double cycle = 0.0;

        switch (COMBO_MES.getSelectedIndex()) {
            case 0:
                cycle = Double.parseDouble(TEXT_MEASURED.getText());
                break;
            case 1:
                cycle = Double.parseDouble(TEXT_QUICK.getText());
                break;
            case 2:
                cycle = Double.parseDouble(TEXT_CALC.getText());
                break;
        }

        if (cycle > 0) {

            Product prod = new Product(PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate);
            if (TEXT_JOB.getText()==null){
                TEXT_JOB.setText("Nincs megadva");
            }
            prod.setJobnumber(TEXT_JOB.getText());
            prod.setPartNumber(TEXT_PN.getText());
            prod.setSequence(Integer.parseInt(TEXT_SEQ.getText()));
            prod.setQty(Integer.parseInt(TEXT_QTY.getText()));
            prod.setPanelization(Integer.parseInt(TEXT_PAN.getText()));
            prod.setCycle(cycle);
            prod.setEff(Integer.parseInt(TEXT_EFF.getText()));
            prod.setStartUpMin(Integer.parseInt(TEXT_STARTUP_MIN.getText()));
            prod.setDownTimeMin(Integer.parseInt(TEXT_DOWNTIME_MIN.getText()));
            Station station = PLANS.get(MainForm.TOP.getSelectedIndex()).STATIONS.get(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getSelectedStation());
            station.runcode++;
            prod.setID(Integer.toString(station.getPlan().weekdate.getYear()).substring(2) + String.format("%02d", PLANS.get(MainForm.TOP.getSelectedIndex()).getWeekIndex()) +String.format("%03d", station.runcode) + "#" + station.station_id );
            station.addProduct(prod);
            prod.resize();

            //PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).action.actionPerformed(null);
            clearTextBoxes();
            setVisible(false);

        } else {
            JOptionPane.showConfirmDialog(null, "Nincs ciklusidő! Vegye fel a kapcsolatot a mérnökséggel.", "Figyelem!", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_GOActionPerformed

    private void TABMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABMouseReleased
       clearTextBoxes();
       fill();
    }//GEN-LAST:event_TABMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BottomPanel;
    private javax.swing.JComboBox COMBO_MES;
    public javax.swing.JButton GO;
    private javax.swing.JTextField QS;
    public javax.swing.JTable TAB;
    private javax.swing.JTextField TEXT_CALC;
    private javax.swing.JTextField TEXT_DOWNTIME_MIN;
    private javax.swing.JTextField TEXT_EFF;
    public javax.swing.JTextField TEXT_JOB;
    private javax.swing.JTextField TEXT_MEASURED;
    private javax.swing.JTextField TEXT_PAN;
    private javax.swing.JTextField TEXT_PN;
    public javax.swing.JTextField TEXT_QTY;
    private javax.swing.JTextField TEXT_QUICK;
    private javax.swing.JTextField TEXT_SEQ;
    private javax.swing.JTextField TEXT_STARTUP_MIN;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScroll4TAB;
    // End of variables declaration//GEN-END:variables
}
