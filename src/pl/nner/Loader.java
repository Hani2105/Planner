/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.joda.time.DateTime;
import org.joda.time.Days;
import static pl.nner.PlNner.ALTERNATIVE_DB;
import static pl.nner.PlNner.HELPMODEL;
import static pl.nner.PlNner.JOBINFO;
import static pl.nner.PlNner.MYDB_DB;
import static pl.nner.PlNner.PLANS;
import static pl.nner.PlNner.xssf;

/**
 *
 * @author krisztian_csekme1
 */
public class Loader extends javax.swing.JDialog {

    Station active_station;
    int side_number = -1;

    /**
     * Creates new form Loader
     */
    public Loader(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        TAB.setCellSelectionEnabled(true);
        ExcelAdapter myAd = new ExcelAdapter(SOURCE);
        setCenter();

    }

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

        public void table_change() {
        for (int s = 0; s < SOURCE.getRowCount(); s++) {
            if (SOURCE.getValueAt(s, 1) != null) {
                String pn = SOURCE.getValueAt(s, 1).toString();

                for (int q = 1; q < 4; q++) {
                    //a mernoki idos tablat porgetjuk
                    for (int r = 0; r < TAB.getRowCount(); r++) {
                        try {
                            if (pn.equals(TAB.getValueAt(r, 1).toString()) && (Integer.parseInt(TAB.getValueAt(r, 4).toString()) == q) && (TAB.getValueAt(r, 3).toString().toLowerCase().equals(active_station.getName().toLowerCase()))) {

                                TAB.setRowSelectionInterval(r, r);

                                double cycle = 0.0;
                                if (TAB.getValueAt(TAB.getSelectedRow(), 6) != null) {
                                    cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 6).toString());
                                } else if (TAB.getValueAt(TAB.getSelectedRow(), 7) != null) {
                                    cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 7).toString());
                                } else if (TAB.getValueAt(TAB.getSelectedRow(), 8) != null) {
                                    cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 8).toString());
                                }

                                switch (q) {
                                    case 1:
                                        SOURCE.setValueAt(cycle, s, 3);
                                        break;
                                    case 2:
                                        SOURCE.setValueAt(cycle, s, 4);
                                        break;
                                    case 3:
                                        SOURCE.setValueAt(cycle, s, 5);
                                        break;

                                }

                                //boardnumber 5
                                int boardnumber = 0;
                                int qty = 0;
                                try {
                                    boardnumber = Integer.parseInt(TAB.getValueAt(TAB.getSelectedRow(), 5).toString());
                                    qty = Integer.parseInt(SOURCE.getValueAt(s, 2).toString());

                                    if (qty % boardnumber == 0) {
                                        SOURCE.setValueAt("OK", s, 6);
                                    } else {
                                        SOURCE.setValueAt("NG", s, 6);
                                    }

                                } catch (Exception e) {

                                }

                            }

                        } catch (Exception e) {

                        }
                    }
                }

            }
        }
        for (int r = 0; r < SOURCE.getRowCount(); r++) {
            active_station.loader[0][r] = (Object) SOURCE.getValueAt(r, 0);
            active_station.loader[1][r] = (Object) SOURCE.getValueAt(r, 1);
            if (SOURCE.getValueAt(r, 2) != null) {
                active_station.loader[2][r] = (Object) SOURCE.getValueAt(r, 2).toString();
            } else {
                SOURCE.setValueAt(null, r, 2);
            }

        }

        for (int r = 0; r < SOURCE.getRowCount(); r++) {
            if (SOURCE.getValueAt(r, 1) != null) {
                String pn = SOURCE.getValueAt(r, 1).toString();
                for (int i = 0; i < PlNner.HELPMODEL.getRowCount(); i++) {
                    //0   5

                    if (PlNner.HELPMODEL.getValueAt(i, 0) != null) {
                        if (PlNner.HELPMODEL.getValueAt(i, 5) != null) {

                            if (PlNner.HELPMODEL.getValueAt(i, 0).toString().equals(pn)) {
                                SOURCE.setValueAt(PlNner.HELPMODEL.getValueAt(i, 5), r, 8);
                            } else {
                                //  SOURCE.setValueAt(null, r, 8);                                
                            }
                        }
                    }

                }

            }
        }

    }

    public void reload() {

        if (PlNner.alternative_database == 0) {
            ALT.setSelected(false);
            TAB.setModel(PlNner.PLR_DB.getDataTableModel("SELECT `cycletime_prog`.`ID` as cpID,`oraclepn`,smtprogname,`smtline` as sline,`sequence`,`boardnumber`,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 1 AND cycletime_data.active = 1 ORDER BY ID DESC LIMIT 1) as mertido,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 2 AND cycletime_data.active = 1 ORDER BY ID DESC LIMIT 1) as gyorsmeres,(SELECT value FROM cycletime_data WHERE cycletime_prog_id = cpID AND priority = 3 AND cycletime_data.active = 1 ORDER BY ID DESC LIMIT 1) as kalkulalt,IFNULL(expectedeffbyprog,COALESCE((SELECT expectedeff FROM cycletime_config WHERE smtline = sline),(SELECT expectedeff FROM cycletime_config WHERE smtline = 'ALL'))) as eff FROM `cycletime_prog` WHERE cycletime_prog.active=1 and smtline is not null group by smtprogname, sequence, smtline ORDER BY smtprogname;"));
        } else {
            ALT.setSelected(true);
            TAB.setModel(PlNner.ALTERNATIVE_DB);
        }

    }

    public void init(Station station) {

        active_station = station;

        try {
            String query = "SELECT side from stations WHERE id=" + active_station.station_id + ";";

            Object value = PlNner.MYDB_DB.getCellValue(query);

            String tooltip = "Vegye figyelembe az ültetőoldalak számát, jelen beállítás: " + value;
            SIDER.setToolTipText(tooltip);
            side_number = Integer.parseInt(value.toString());

        } catch (Exception e) {

        }

        reload();
        txtDOWNTIME.setText(Integer.toString(PlNner.DEFAULT_RUNUP_MIN));
        DefaultTableModel dm = (DefaultTableModel) SOURCE.getModel();
        int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }

        for (int i = 0; i < 100; i++) {
            dm.addRow(new Object[dm.getColumnCount()]);
        }

        for (int r = 0; r < active_station.loader[1].length; r++) {
            Object[] row = new Object[3];
            row[0] = active_station.loader[0][r];
            row[1] = active_station.loader[1][r];
            try {
                row[2] = Integer.toString((int) Double.parseDouble(active_station.loader[2][r].toString()));
            } catch (NullPointerException e) {
                row[2] = null;
            }
            SOURCE.setValueAt(row[0], r, 0);
            SOURCE.setValueAt(row[1], r, 1);
            SOURCE.setValueAt(row[2], r, 2);

        }
        table_change();

        SOURCE.getColumnModel().getColumn(6).setCellRenderer(new MyCellRendererPanelization(MyCellRendererPanelization.CENTER));
        DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
        singleclick.setClickCountToStart(1);

        for (int i = 0; i < TAB.getColumnCount(); i++) {
            TAB.setDefaultEditor(TAB.getColumnClass(i), singleclick);
        }

        TAB.setRowHeight(25);
        SOURCE.setRowHeight(25);
        reloadDB();
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
        jButton1 = new javax.swing.JButton();
        ALT = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDOWNTIME = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        SIDER = new javax.swing.JCheckBox();
        CHECK_SPLIT = new javax.swing.JCheckBox();
        SPLIT_NO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        QS2 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        SOURCE = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAB = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        QS = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setTitle("Loader");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Betölt");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ALT.setText("Alternatív");
        ALT.setToolTipText("Kapcsoló a PLR és az alternatív tábla között");
        ALT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ALTActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/refresh16.png"))); // NOI18N
        jButton3.setToolTipText("Újraolvas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Tárazás:");

        jLabel3.setText("perc.");

        SIDER.setSelected(true);
        SIDER.setText("Ültető oldal");
        SIDER.setToolTipText("Vegye figyelembe az ültetőoldalak számát");

        CHECK_SPLIT.setText("Betöltés több részletbe");

        SPLIT_NO.setText("1");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/search16.png"))); // NOI18N

        QS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                QS2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ALT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDOWNTIME, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SIDER)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CHECK_SPLIT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SPLIT_NO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(QS2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(QS2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(ALT)
                    .addComponent(jButton3)
                    .addComponent(jLabel2)
                    .addComponent(txtDOWNTIME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(SIDER)
                    .addComponent(CHECK_SPLIT)
                    .addComponent(SPLIT_NO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        SOURCE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "JOB-szám", "Partnumber", "Mennyiség", "Ciklus idő SMT1", "Ciklus idő SMT2", "Ciklus idő BE", "Panelizáció", "Betölt", "Partnumber komment", "JOB infó"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        SOURCE.setCellSelectionEnabled(true);
        SOURCE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SOURCEKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(SOURCE);

        jTabbedPane1.addTab("Loader", jScrollPane1);

        TAB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TAB);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        QS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                QSKeyReleased(evt);
            }
        });

        jLabel1.setText("Gyorskeresés:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/clear16.png"))); // NOI18N
        jButton2.setToolTipText("Szűrés törlése");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addComponent(QS, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(QS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Mérnöki idők", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(SOURCE.getModel());
        SOURCE.setRowSorter(sorter);
        sorter.setRowFilter(null);
//ahány terméket betöltünk
        for (int i = 0; i < SOURCE.getRowCount(); i++) {
            //ha a pn nem nulla
            if (SOURCE.getValueAt(i, 1) != null) {
                //ha a betölt ki van pipálva
                if (SOURCE.getValueAt(i, 7) != null) {
                    if (SOURCE.getValueAt(i, 7).equals(Boolean.TRUE)) {

                        String pn = SOURCE.getValueAt(i, 1).toString();

                        int fullqty = Integer.parseInt(SOURCE.getValueAt(i, 2).toString());

                        int splitno = 1;
                        int[] qtys;
                        //betöltés több részletben
                        if (CHECK_SPLIT.isSelected()) {

                            int hanyad;

                            try {

                                splitno = Integer.parseInt(SPLIT_NO.getText());

                            } catch (Exception e) {

                            }

                            if (fullqty % splitno == 0) {
                                hanyad = fullqty / splitno;
                                qtys = new int[splitno];
                                java.util.Arrays.fill(qtys, hanyad);

                            } else {

                                qtys = new int[1];
                                qtys[0] = fullqty;
                            }

                        } else {
                            qtys = new int[1];
                            qtys[0] = fullqty;
                        }

                        boolean ido = false;
                        int from = 1;
                        int to = 3;

                        if (SIDER.isSelected()) {
                            switch (side_number) {
                                case 0:
                                    break;
                                case 1:
                                    from = 2;
                                    to = 3;
                                    break;
                                case 2:
                                    from = 1;
                                    to = 3;
                                    break;
                                case 3:
                                    from = 2;
                                    to = 4;
                                    break;
                            }
                        }
                        //ha több részletben töltjük be
                        for (int t = 0; t < qtys.length; t++) {
                            
                            for (int s = from; s < to; s++) { //oldalak
                               //mernoki idos tablat porgetjuk
                                for (int r = 0; r < TAB.getRowCount(); r++) {
                                    try {
                                        if (pn.equals(TAB.getValueAt(r, 1).toString()) && (Integer.parseInt(TAB.getValueAt(r, 4).toString()) == s) && (TAB.getValueAt(r, 3).toString().toLowerCase().equals(active_station.getName().toLowerCase()))) {

                                            TAB.setRowSelectionInterval(r, r);
                                            //System.out.println("Betölt");
                                            //peldanyositunk egy productot
                                            Product prod = new Product(PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate);
                                            prod.setJobnumber(SOURCE.getValueAt(i, 0).toString());
                                            prod.setPartNumber(TAB.getValueAt(TAB.getSelectedRow(), 1).toString());
                                            prod.setQty(qtys[t]);
                                            prod.setSequence(s);
                                            prod.setEff(Integer.parseInt(TAB.getValueAt(TAB.getSelectedRow(), 9).toString()));
                                            prod.setPanelization(Integer.parseInt(TAB.getValueAt(TAB.getSelectedRow(), 5).toString()));

                                            try {
                                                int tr = Integer.parseInt(txtDOWNTIME.getText().replace(" ", ""));

                                                prod.setStartUpMin(tr);
                                            } catch (Exception e) {
                                                prod.setStartUpMin(PlNner.DEFAULT_RUNUP_MIN);
                                            }

                                            prod.setDownTimeMin(0);
                                            //6-7-8
                                            double cycle = 0.0;
                                            if (TAB.getValueAt(TAB.getSelectedRow(), 6) != null) {
                                                cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 6).toString());
                                            } else if (TAB.getValueAt(TAB.getSelectedRow(), 7) != null) {
                                                cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 7).toString());
                                            } else if (TAB.getValueAt(TAB.getSelectedRow(), 8) != null) {
                                                cycle = Double.parseDouble(TAB.getValueAt(TAB.getSelectedRow(), 8).toString());
                                            }

                                            if (cycle == 0) {
                                                cycle = Double.parseDouble(JOptionPane.showInputDialog(this, "<html>Figyelem nincs ciklusidő az adatbázisban a következő terméknél: " + prod.getPartnumber() + "  " + PlNner.SEQUENCES[prod.getSequence()] + "<br>Kérem adjon meg egy 0-nál nagyobb ciklusidőt!</html>", "Figyelem", JOptionPane.ERROR_MESSAGE));
                                                System.out.println();
                                            }

                                            if (prod.getPanelization() == 0) {
                                                Integer temp_b = Integer.parseInt(JOptionPane.showInputDialog(this, "<html>Figyelem nincs panelizáció az adatbázisban a következő terméknél: " + prod.getPartnumber() + "  " + PlNner.SEQUENCES[prod.getSequence()] + "<br>Kérem adjon meg egy 0-nál nagyobb panelizációt!</html>", "Figyelem", JOptionPane.ERROR_MESSAGE));
                                                prod.setPanelization(temp_b);
                                            }

                                            prod.setCycle(cycle);

                                            active_station.runcode++;
                                            prod.setID(Integer.toString(active_station.getPlan().weekdate.getYear()).substring(2) + String.format("%02d", PLANS.get(MainForm.TOP.getSelectedIndex()).getWeekIndex()) + String.format("%03d", active_station.runcode) + "#" + active_station.station_id);

                                            active_station.addProduct(prod);

                                            prod.resize();
                                            active_station.repaint();
                                            active_station.sort();
                                            active_station.recalcJobs();

                                            String query = "SELECT startdate FROM terv t "
                                                    + "where partnumber='*PN*' "
                                                    + "and stationid=% "
                                                    + "and qty_teny>0 "
                                                    + "order by startdate desc limit 1;";

                                            Object res = PlNner.MYDB_DB.getCellValue(query.replace("*PN*", prod.getPartnumber()).replace("%", Integer.toString(active_station.station_id)));

                                            if (!res.toString().equals("-1")) {
                                                try {
                                                    Object[] _tp = res.toString().split(" ");
                                                    Object[] _dt = _tp[0].toString().split("-");

                                                    DateTime reconstruct_date = new DateTime(Integer.parseInt(_dt[0].toString()), Integer.parseInt(_dt[1].toString()), Integer.parseInt(_dt[2].toString()), 1, 1, 1);
                                                    DateTime now = new DateTime();

                                                    int days_old = Days.daysBetween(reconstruct_date, now).getDays();

                                                    if (days_old >= PlNner.LAST_PRODUCED_CHECK) {
                                                        active_station.setEngineering(prod.getJobnumber(), Boolean.TRUE);
                                                        prod.setComment(Integer.toString(days_old) + " napja gyártottuk mérnöki támogatás szükséges");
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            } else {
                                                active_station.setEngineering(prod.getJobnumber(), Boolean.TRUE);
                                                prod.setComment("Ezen a soron még nem gyártottuk mérnöki támogatás szükséges");
                                            }

                                        }
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < SOURCE.getRowCount(); i++) {
            boolean exist = false;
            if (SOURCE.getValueAt(i, 0) != null) {
                String JOBNAME = SOURCE.getValueAt(i, 0).toString();
                for (int r = 0; r < PlNner.JOBINFO.getRowCount(); r++) {
                    try {
                        if (PlNner.JOBINFO.getValueAt(r, 0).toString().equals(JOBNAME)) {
                            exist = true;
                            PlNner.JOBINFO.setValueAt(SOURCE.getValueAt(i, 9), r, 1);
                        }
                    } catch (Exception e) {

                    }
                }
                if (!exist) {
                    Object[] inf = new Object[2];
                    inf[0] = JOBNAME;
                    inf[1] = SOURCE.getValueAt(i, 9);
                    PlNner.JOBINFO.addRow(inf);
                }
            }
        }

        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void SOURCEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SOURCEKeyReleased
        table_change();
    }//GEN-LAST:event_SOURCEKeyReleased

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

    public void reloadDB() {
        Runnable RUN = new Runnable() {

            @Override
            public void run() {

                HELPMODEL = MYDB_DB.getDataTableModel("SELECT * from pn_data");

                if (JOBINFO == null) {
                    JOBINFO = new DefaultTableModel(new Object[]{"JobInfo", "Comment"}, 0);
                } else {
                    for (int i = 0; i < JOBINFO.getRowCount(); i++) {

                        Object JOBNAME = JOBINFO.getValueAt(i, 0);

                        for (int r = 0; r < SOURCE.getRowCount(); r++) {
                            try {
                                if (SOURCE.getValueAt(r, 0).equals(JOBNAME)) {
                                    SOURCE.setValueAt(JOBINFO.getValueAt(i, 1), r, 9);
                                }
                            } catch (NullPointerException e) {

                            }

                        }

                    }
                }

                ALTERNATIVE_DB = xssf.getExcelData("datas.xlsx", "alternative_db");
                if (ALTERNATIVE_DB == null) {
                    ALTERNATIVE_DB = new DefaultTableModel(new Object[]{"id", "oraclepn", "smtprogramname", "smtline", "sequence", "boardnumber", "mertido", "gyorsmeres", "kalkulalt", "eff"}, 0);
                }

            }
        };

        Thread t1 = new Thread(RUN);
        t1.start();
    }


    private void ALTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ALTActionPerformed
        reloadDB();
        if (ALT.isSelected()) {
            PlNner.alternative_database = 1;
        } else {
            PlNner.alternative_database = 0;
        }
        reload();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(TAB.getModel());

        TAB.setRowSorter(sorter);
        if (QS.getText().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(QS.getText()));
        }


    }//GEN-LAST:event_ALTActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(TAB.getModel());
        QS.setText("");
        TAB.setRowSorter(sorter);
        sorter.setRowFilter(null);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        if (jTabbedPane1.getSelectedIndex() == 0) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(TAB.getModel());

            TAB.setRowSorter(sorter);
            sorter.setRowFilter(null);
        }
        table_change();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reloadDB();
        reload();
        table_change();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void QS2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QS2KeyReleased
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(SOURCE.getModel());

        SOURCE.setRowSorter(sorter);
        if (QS2.getText().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(QS2.getText()));
        }
    }//GEN-LAST:event_QS2KeyReleased

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
            java.util.logging.Logger.getLogger(Loader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Loader dialog = new Loader(new javax.swing.JFrame(), true);
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
    private javax.swing.JToggleButton ALT;
    private javax.swing.JCheckBox CHECK_SPLIT;
    private javax.swing.JTextField QS;
    private javax.swing.JTextField QS2;
    private javax.swing.JCheckBox SIDER;
    private javax.swing.JTable SOURCE;
    private javax.swing.JTextField SPLIT_NO;
    private javax.swing.JTable TAB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtDOWNTIME;
    // End of variables declaration//GEN-END:variables
}
