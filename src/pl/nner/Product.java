/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.joda.time.DateTime;

/**
 *
 * @author krisztian_csekme1
 */
class ActionColor implements ActionListener {

    Product prod;
    Color color;

    public ActionColor(Product product, Color bg) {
        this.prod = product;
        this.color = bg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (prod.getStation()._CTRL) {
            prod.getStation().reColor(prod, color);
        } else {
            prod.setBGColor(color);
        }
    }

}

class TimeAction implements ActionListener {

    TimeStamp ts;
    Product prod;

    TimeAction(TimeStamp ts, Product prod) {
        this.ts = ts;
        this.prod = prod;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ts.addProduct(prod.getID());
    }

}

class ToolTip extends JDialog {

    Product prod;

    JPanel panel = new JPanel() {

        @Override
        public void paint(Graphics g) {
            final Graphics2D graph = (Graphics2D) g;
            graph.setFont(new Font(prod.getFont().getName(), Font.PLAIN, 10));
            graph.setColor(new Color(254, 255, 188));
            graph.fillRect(0, 0, getWidth(), getHeight());
            graph.setColor(Color.BLACK);
            graph.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            graph.drawLine(10, 25, getWidth() - 10, 25);
            graph.drawString("Jobszám: " + prod.getJobnumber(), 10, 40);
            graph.drawString("No: " + prod.getNo(), 5, 18);

            graph.drawLine(25, 42, 25, 55);

            graph.drawLine(25, 55, 40, 55);

            graph.drawLine(40, 52, 40, 58);
            graph.drawLine(40, 52, 45, 55);
            graph.drawLine(40, 58, 45, 55);
            graph.drawString(prod.getPartnumber(), 50, 60);

            int fx, fy;
            fx = 40;
            fy = 10;

            graph.fillRect(fx - 1, fy - 1, 104, 12);
            graph.setColor(Color.LIGHT_GRAY);

            graph.fillRect(fx, fy, 102, 10);
            if ((int) prod.getPercent() == 100) {
                graph.setColor(Color.green);
            } else {
                graph.setColor(Color.red);
            }
            graph.fillRect(fx + 1, fy + 1, (int) prod.getPercent(), 8);
            graph.setColor(Color.white);
            graph.drawString(Integer.toString((int) prod.getPercent()) + "%", (fx + 50) - (graph.getFontMetrics().stringWidth(Integer.toString((int) prod.getPercent()) + "%") / 2), fy + 8);
            graph.setColor(Color.black);

            graph.drawString("Szekvencia: " + PlNner.SEQUENCES[prod.getSequence()], 10, 80);
            graph.drawString("Gyártás mennyisége: " + (int) prod.getQty(), 10, 100);
            graph.drawString("Megvalósulás: " + new DecimalFormat("#").format(prod.getFactMSzak()), 10, 120);
            graph.drawString("Darabszám / Óra: " + new DecimalFormat("#.##").format(prod.getQtyPHour()), 10, 140);

//            //partnumber
//            SwingUtilities.invokeLater(new Runnable() {
//
//                @Override
//                public void run() {
//                      String query = "SELECT distinct stationid FROM terv WHERE partnumber like '%" + prod.getPartnumber() + "%';";
//            DefaultTableModel model = PlNner.MYDB_DB.getDataTableModel(query);
//
//            String res = "";
//            graph.drawString("Gyártottuk: ", 10, 160);
//            for (int i = 0; i < model.getRowCount(); i++) {
//
//                res = PlNner.MYDB_DB.getCellValue("SELECT name from stations WHERE id='" + model.getValueAt(i, 0) + "'").toString().toUpperCase();
//                graph.setColor(Color.red);
//                graph.fillOval(20 + i * 20, 165, 16, 16);
//
//                graph.setColor(Color.WHITE);
//                graph.drawString(res, 25 + i * 20, 177);
//                graph.setColor(Color.BLACK);
//                graph.drawOval(20 + i * 20, 165, 16, 16);
//
//            }
//                     
//                }
//            });
            if (prod.planner_comment != null) {
                if (prod.planner_comment.length() > 0) {
                    panel.setSize(200, 200);
                    ToolTip.this.setSize(200, 200);
                    graph.drawString("Tervezői komment: ", 10, 170);
                    graph.drawString(prod.planner_comment, 10, 185);
                    graph.drawImage(new ImageIcon(this.getClass().getResource("IMG/info16.png")).getImage(), 170, 7, this); //zoom out
                } else {
                    panel.setSize(200, 170);
                    ToolTip.this.setSize(200, 170);
                }
            } else {
                panel.setSize(200, 170);
                ToolTip.this.setSize(200, 170);
            }

            super.paint(graph);
        }

    };

    public ToolTip(Product pro) {
        prod = pro;
        setSize(200, 190);
        setModal(false);
        setUndecorated(true);
        setOpacity(0.80f);
        panel.setOpaque(false);
        setContentPane(panel);
    }

    public void setPosition(Point p) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        if (p.y + getHeight() + 20 > height) {
            ToolTip.this.setLocation(p.x + 20, p.y - ToolTip.this.getHeight() + 10);
        } else {
            ToolTip.this.setLocation(p.x + 20, p.y + 15);
        }

        setVisible(true);

    }

}

public final class Product extends JPanel implements Serializable {

    private JCheckBoxMenuItem TUL_KIT;
    public boolean ONMENU = false;
    public boolean RELEASE;
    public TimeStamp ts;
    public MyToolTip mytooltip;
    public boolean on_change = false;
    private int last_x;
    private ToolTip TT;
    private String _Partnumber;
    private int _Sequence; //oldal 1-első oldal 2-második oldal
    private int _Panelization; //panelizáció
    private double _Cycle;
    private int _Eff;
    private double _Qty;
    private double _Remind;
    private String _Jobnumber;
    private double _Time;
    private double _QtyPerHour;
    private double _QtyPerMin;
    private String comment;
    private Color mselected;
    private boolean mp = false;
    public int mx, my;
    private Point anchorPoint;
    private Point grabPoint;
    public double[] MINUTES = new double[168 * 60];
    public double[] QTYS = new double[168 * 60];
    public double[] QTYS_TENY = new double[168 * 60];
    public double[] QTYS_TENY_MSZAK;
    public double[] QTYS_TENY_TIME;
    private boolean selected;
    private double StartHour;     //Gyártás kezdeti órája
    private double EndHour;       //Gyártás utolsó órája
    private DateTime StartTime;    //Gyártás kezedti dátuma
    private DateTime EndTime;    //Gyártás vége dátuma
    public boolean storedProduceMin = false;
    public boolean drag = false;
    private Point last;
    public Point resLoc;
    public boolean res = false;
    public int storedFirst;
    public int storedLast;
    private String ID;
    private int StartUpMin;
    private int DownTimeMin;
    public boolean finalIndicator;
    public boolean skip; //Ez határozza meg hogy tervezzen-e a termékkel
    private Color BG = new Color(0, 0, 150);
    private Color FG = Color.white;
    private boolean modified;
    private int _No; //Gyártási sorrend
    private boolean canArrange;
    private int top;  //magasság

    public boolean freeze;
    public String history;
    public int original_qty;
    public String planner_comment;

    private JPopupMenu menu;
    private JMenu menu_time;
    private JMenu menu_color;
    public DateTime weekdate;

    ActionListener on_change_action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (last_x != Product.this.getLocation().x) {

                on_change = true;
            } else {
                on_change = false;
            }

        }
    };

    public void setPlannerComment(String comm) {
        planner_comment = comm;
    }

    public String getPlannerComment() {
        return planner_comment;
    }

    public String getProjectName() {
        String re = "";

        for (int r = 0; r < PlNner.HELPMODEL.getRowCount(); r++) {
            if (PlNner.HELPMODEL.getValueAt(r, 0) != null) {
                if (PlNner.HELPMODEL.getValueAt(r, 0).toString().equals(_Partnumber)) {
                    if (PlNner.HELPMODEL.getValueAt(r, 2) != null) {
                        return PlNner.HELPMODEL.getValueAt(r, 2).toString();
                    }
                }
            }
        }
        return re;
    }

    public void setAlpha(float val) {

        Color t = getBGColor();
        double r = (double) t.getRed() / 255;
        double g = (double) t.getGreen() / 255;
        double b = (double) t.getBlue() / 255;

        setBGColor(new Color((float) r, (float) g, (float) b, val));

    }

    public void setTimeStamp(TimeStamp ts) {
        this.ts = ts;
    }

    public TimeStamp getTimeStamp() {
        return this.ts;
    }

    public String getJobInfo() {
        String re = "";

        for (int r = 0; r < PlNner.JOBINFO.getRowCount(); r++) {
            if (PlNner.JOBINFO.getValueAt(r, 0).toString().equals(_Jobnumber)) {
                if (PlNner.JOBINFO.getValueAt(r, 0) != null) {
                    if (PlNner.JOBINFO.getValueAt(r, 1) != null) {
                        return PlNner.JOBINFO.getValueAt(r, 1).toString();
                    }
                }
            }
        }
        return re;
    }

    public boolean isKitted() {

        return getStation().isKitted(getJobnumber());

    }

    public boolean isEngeenering() {
        return getStation().isEngineering(getJobnumber());
    }

    public String getPOutSideCustomer() {
        String re = "";

        for (int r = 0; r < PlNner.HELPMODEL.getRowCount(); r++) {
            if (PlNner.HELPMODEL.getValueAt(r, 0) != null) {
                if (PlNner.HELPMODEL.getValueAt(r, 0).toString().equals(_Partnumber)) {
                    if (PlNner.HELPMODEL.getValueAt(r, 2) != null) {
                        return PlNner.HELPMODEL.getValueAt(r, 2).toString();
                    }
                }
            }
        }
        return re;
    }

    public String getCustomer() {
        String re = "";

        for (int r = 0; r < PlNner.HELPMODEL.getRowCount(); r++) {
            if (PlNner.HELPMODEL.getValueAt(r, 0) != null) {
                if (PlNner.HELPMODEL.getValueAt(r, 0).toString().equals(_Partnumber)) {
                    if (PlNner.HELPMODEL.getValueAt(r, 4) != null) {
                        return PlNner.HELPMODEL.getValueAt(r, 4).toString();
                    }
                }
            }
        }
        return re;
    }

    public void setComment(String comm) {
        comment = comm;
    }

    public String getComment() {
        return comment;
    }

    public Color getFGColor() {
        return FG;
    }

    public Color getBGColor() {
        return BG;
    }

    public void setFGColor(Color color) {
        FG = color;
    }

    public void setBGColor(Color color) {
        BG = color;
    }

    public int getStartUpMin() {
        return StartUpMin;
    }

    public int getDownTimeMin() {
        return DownTimeMin;
    }

    public void setStartUpMin(int min) {
        StartUpMin = min;
    }

    public void setDownTimeMin(int min) {
        DownTimeMin = min;
    }

    public int getNo() {
        return _No;
    }

    public void setID(String id) {
        ID = id;
    }

    public String getID() {
        return ID;
    }

    public int getLastMin() {
        for (int i = QTYS.length - 1; i > 0; i--) {
            if (QTYS[i] > 0.0) {

                return i;
            }
        }

        return 0;
    }

    public int getFirstMin() {
        for (int i = 0; i < QTYS.length; i++) {
            if (QTYS[i] > 0.0) {

                return i;
            }
        }

        return 0;
    }

    public void setQtyPerMin(double min) {
        _QtyPerMin = min;
    }

    public double getQtyPerMin() {
        return _QtyPerMin;
    }

    public void setStartTime(DateTime start) {
        StartTime = start;
    }

    public DateTime getStartTime() {
        return StartTime;
    }

    public void setEndTime(DateTime end) {
        EndTime = end;
    }

    public DateTime getEndTime() {
        return EndTime;
    }

    public void setTime(double time) {
        _Time = time;
    }

    public double getTime() {
        return _Time;
    }

    public void setQtyPHour(double qtph) {
        _QtyPerHour = qtph;
    }

    public double getQtyPHour() {
        return _QtyPerHour;
    }

    public void setNo(int number) {
        _No = number;
    }

    public double getStartHour() {
        return StartHour;
    }

    public double getEndHour() {
        return EndHour;
    }

    public boolean isSelect() {
        return selected;
    }

    public void setSel(boolean sel) {
        selected = sel;

    }

    public void setJobnumber(String job) {
        _Jobnumber = job;
    }

    public String getJobnumber() {
        return _Jobnumber;
    }

    public void setRemind(double Remind) {
        _Remind = Remind;
    }

    double getSumTenyMSzak() {
        double q = 0.0;
        for (int i = 0; i < QTYS_TENY_MSZAK.length; i++) {
            q += QTYS_TENY_MSZAK[i];
        }
        return q;
    }

    double getSumTeny() {
        double q = 0.0;
        for (int i = 0; i < QTYS_TENY.length; i++) {
            q += QTYS_TENY[i];
        }
        return q;
    }

    public double getPercent() {
        double teny = 0.0;
        for (int i = 0; i < QTYS_TENY_MSZAK.length; i++) {
            teny += QTYS_TENY_MSZAK[i];
        }
        double res = teny / (double) getQty() * 100;

        if (res > 100) {
            res = 100;
        }

        return res;
    }

    public double getRemind() {
        double rem = 0.0;
        for (int i = 0; i < QTYS.length; i++) {
            rem += QTYS[i];
        }
        if ((_Qty - rem) < 0) {
            return 0;
        }

        return _Qty - rem;
    }

    public double getFact() {
        double f = 0.0;
        for (int i = 0; i < QTYS_TENY.length; i++) {
            f += QTYS_TENY[i];
        }

        return f;
    }

    public double getFactMSzak() {
        double f = 0.0;
        for (int i = 0; i < QTYS_TENY_MSZAK.length; i++) {
            f += QTYS_TENY_MSZAK[i];
        }

        return f;
    }

    public void setQty(double Qty) {
        _Qty = Qty;

    }

    public double getQty() {
        return _Qty;
    }

    public void setPartNumber(String part) {
        _Partnumber = part;
    }

    public String getPartnumber() {
        return _Partnumber;
    }

    public void setSequence(int seq) {
        _Sequence = seq;
    }

    public int getSequence() {
        return _Sequence;
    }

    public void setCycle(double cyc) {
        _Cycle = cyc;

    }

    public boolean iscontain(String component) {

        for (int r = 0; r < PlNner.BOMMODEL.getRowCount(); r++) {
            try {
                if (PlNner.BOMMODEL.getValueAt(r, 0).toString().equals(this.getPartnumber())) {
                    if (PlNner.BOMMODEL.getValueAt(r, 1).toString().equals(component)) {
                        if (Integer.parseInt(PlNner.BOMMODEL.getValueAt(r, 7).toString()) > 0) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }

        return false;

    }

    public double getCycle() {
        return _Cycle;
    }

    public void setEff(int eff) {
        _Eff = eff;
    }

    public int getEff() {
        return _Eff;
    }

    public void setPanelization(int pan) {
        _Panelization = pan;
    }

    public int getPanelization() {
        return _Panelization;
    }

    public Station getStation() {
        return (Station) Product.this.getParent();
    }

    public void resize() {
        if (storedProduceMin == false) {    //storedProduceMin==false
            Station st = (Station) Product.this.getParent();
            double m = _Qty / (60 / _Cycle * ((double) _Eff / 100) * _Panelization) + StartUpMin + DownTimeMin; //Standard gyártási hossz
            setSize((int) (m / (60 / st.IND_HOUR)), 25);
            double full_pos_start = (((double) getLocation().x - (double) st._getBorder()))
                    / st.IND_HOUR;
            int hour_start = (int) full_pos_start;
            double min_start = 60 * (full_pos_start - hour_start);
            StartTime = weekdate.plusHours(hour_start).plusMinutes((int) min_start);
            EndTime = StartTime.plusMinutes((int) m);

        } else if (!drag) {
            Station st = (Station) Product.this.getParent();
            int m = storedLast - storedFirst;
            setSize((int) ((m + StartUpMin + DownTimeMin) / (60 / st.IND_HOUR)), 25);
            StartTime = weekdate.plusMinutes(storedFirst).minusMinutes(StartUpMin);
            EndTime = weekdate.plusMinutes(storedLast).plusMinutes(DownTimeMin);
            if (MainForm.TOP.getSelectedIndex() > -1) {
                setLocation(st._getBorder() + ((storedFirst - StartUpMin) / (60 / st.IND_HOUR)), getLocation().y);
            }
        }

        if (getTimeStamp() != null) {
            getTimeStamp().setLocation(Product.this.getLocation().x - 10, getTimeStamp().getLocation().y);
            getTimeStamp().relocate();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graph = (Graphics2D) g;

        //setAlpha(getStation().products_alpha);
        graph.setColor(BG);
        if (!RELEASE) {
            graph.setColor(Color.BLACK);
        }

        if (canArrange) {
            graph.setColor(new Color(150, 0, 0));

        } else if (selected) {

            graph.setColor(mselected);
        }

        graph.fillRect(0, 0, Product.this.getWidth(), Product.this.getHeight());

        if (mp) {
            graph.setColor(new Color(0.5f, 0.5f, 0.5f, 0.7f));
            graph.fillRect(0, 0, mx, getHeight());
            graph.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
            graph.drawLine(mx, 3, mx, getHeight() - 3);
        }

        graph.setColor(FG);
        if (!RELEASE) {
            graph.setColor(Color.red);
        }

        switch (getStation().products_onwrite_string) {
            case 0:
                break;
            case 1:
                graph.drawString(_Partnumber, 10, 17);
                break;
            case 2:
                graph.drawString(_Jobnumber, 10, 17);

                break;

        }

        //graph.drawString("#" + _No + ".  " + _Partnumber, 10, 17);
        if (mp) {
            //  graph.drawImage(new ImageIcon(this.getClass().getResource("IMG/point.png")).getImage(), mx - 120, my - 120, null);
        }

        super.paint(graph);

    }

    private void addMouseAction() {
        final Product handle = Product.this;
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                selectThis();

                handle.setRequestFocusEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

                top = handle.getLocation().y;
                last = e.getLocationOnScreen();
                // if ((e.getX() > getWidth() - 3) && (e.getX() < getWidth())) {
                //     res = true;

                //  }
                resLoc = e.getPoint();

                e.translatePoint(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
                grabPoint = e.getPoint();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Station station = (Station) Product.this.getParent();
                station.scroll_stop();

                res = false;

                mytooltip.setVisible(false);
                storedProduceMin = false;
                if (canArrange == true) {
                    //PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).action.actionPerformed(null);
                    canArrange = false;
                } else {
                    handle.setLocation(handle.getLocation().x, top);

                    //PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).action.actionPerformed(null);
                }
                station.clearSelection();
                if (e.isPopupTrigger()) {
                    selectThis();

                    menu_time.removeAll();

                    for (int i = 0; i < getStation().TIMESTAMPES.size(); i++) {
                        for (int x = 0; x < getStation().TIMESTAMPES.size(); x++) {
                            if (getStation().TIMESTAMPES.get(x).getNo() == i) {
                                if (getStation().TIMESTAMPES.get(x).prod == null) {
                                    JMenuItem m = new JMenuItem(PlNner.getCurrentDateTime(getStation().TIMESTAMPES.get(x).time));
                                    m.addActionListener(new TimeAction(getStation().TIMESTAMPES.get(x), Product.this));
                                    menu_time.add(m);
                                }
                            }
                        }
                    }
                    ONMENU = true;
                    TUL_KIT.setSelected(Product.this.getStation().isKitted(Product.this.getJobnumber()));
                    menu.show(handle, e.getX() - 10, e.getY() - 10);

                }
                drag = false;
                station.drag = false;
                station.sort();
                //handle.resize();

                station.repaint();
                PlNner.PH.tick();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                ONMENU = false;

                getStation().timer.setDelay(10);
                selectThis();
                setQtyPHour(3600 / getCycle() * ((double) getEff() / 100) * getPanelization()); //Óránkénti darabszám
                setQtyPerMin(60 / getCycle() * ((double) getEff() / 100) * getPanelization()); //Percenkénti darabszám
                setTime(getQty() / getQtyPHour());
                mx = e.getX();
                my = e.getY();
                mp = true;
                mselected = Product.this.getBGColor();
                handle.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                getStation().timer.setDelay(200);
                mp = false;
                mytooltip.setVisible(false);
                TT.setVisible(false);
                if (!selected) {
                    setBorder(null);
                }
            }
        });
    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final Product handle = Product.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                anchorPoint = e.getPoint();
                mx = e.getX();
                my = e.getY();
                double m = _Qty / (60 / _Cycle * ((double) _Eff / 100) * _Panelization) + StartUpMin + DownTimeMin;
                m = _Qty / (m / (60 / getStation().IND_HOUR));

                if ((e.getX() > getWidth() - 3) && (e.getX() < getWidth())) {

                } else if (getStation().can_drag) {
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                DateTime time = getStation().getPlan().weekdate.plusMinutes((Product.this.getLocation().x + mx - getStation()._getBorder()) * (60 / getStation().IND_HOUR));

                if (getStation().can_drag) {
                    mytooltip.update(new Point(e.getXOnScreen(), e.getYOnScreen()), "Teljes gyártás: " + _Qty + "<br>Gyártás a jelölt időpontig:" + Integer.toString((int) (m * mx)) + "<br>" + "Jelölt időpont: " + PlNner.getCurrentDateTime(time), MyToolTip.IGAZITAS.BALRA);
                } else if (!ONMENU) {
                    TT.setPosition(new Point(e.getXOnScreen(), e.getYOnScreen()));
                }

            }

            @Override
            public void mouseDragged(MouseEvent me) {

                //  if (res) {
                // setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                //   try {
                //       _Qty += (me.getXOnScreen() - last.getX());
                //        System.out.println(me.getXOnScreen() - last.getX());
                //    } catch (NullPointerException e) {
                //    }
                //    last = me.getLocationOnScreen();
                //  mytooltip.update(new Point(me.getXOnScreen(), me.getYOnScreen()), Integer.toString((int) _Qty), MyToolTip.IGAZITAS.BALRA);
                //    resize();
                //    resLoc = me.getPoint();
                //  } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                Station station = (Station) Product.this.getParent();

                if (station.can_drag) {
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    station.setNeedToSave();
                    station.drag = true;
                    drag = true;
                    selectThis();
                    me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);

                    if (Math.abs(me.getY() - grabPoint.getY()) > 40) {
                        canArrange = true;
                    } else {
                        canArrange = false;
                    }

                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                    if (Math.abs(me.getYOnScreen() - (dim.height / 2)) > 140) {

                        if (me.getYOnScreen() < (dim.height / 2)) {
                            getStation().scroll_up();

                        } else {
                            getStation().scroll_down();
                        }

                    } else {
                        getStation().scroll_stop();
                    }

                    storedProduceMin = false;

                    handle.setLocation(me.getX() - anchorPoint.x, me.getY() - anchorPoint.y);

                    station.repaint();
                    handle.resize();
                    handle.repaint();
                    station.recalcJobs();
                    station.repaint();
                    //  }
                }

            }

        });

    }

    public void selectThis() {
        try {
            Station station = (Station) Product.this.getParent();
            station.clearSelection();
            setSel(true);

        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    ActionListener A_SORT_TO_ME = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Station station = (Station) Product.this.getParent();
            station.optimizing(Product.this.getNo());
            station.setNeedToSave();
        }
    };

    ActionListener A_MAN_T = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            PlNner.LS.init(Product.this);
            PlNner.LS.setVisible(true);
        }
    };

    ActionListener A_DEL = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Station station = (Station) Product.this.getParent();
            station.setNeedToSave();
            station.deleteProduct();

        }
    };

    ActionListener A_PROP = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Station station = (Station) Product.this.getParent();
            Product p = station.PRODUCTS.get(station.getSelectedProduct());
            //  Product p = PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).PRODUCTS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).getSelectedProduct());

            PlNner.PROP.init(Product.this);
            PlNner.PROP.setVisible(true);

        }
    };

    ActionListener A_CUT = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Station station = (Station) Product.this.getParent();
            station.setNeedToSave();
            Product p = station.PRODUCTS.get(station.getSelectedProduct());
            //Product p = PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).PRODUCTS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).getSelectedProduct());

            PlNner.SP.init(Product.this);
            PlNner.SP.setVisible(true);
        }
    };

    ActionListener A_COPY_TO_CLIP = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            Station st = (Station) getParent();
            st.setNeedToSave();
            /*
             prod.setJobnumber(TEXT_JOB.getText());
             prod.setPartNumber(TEXT_PN.getText());
             prod.setSequence(Integer.parseInt(TEXT_SEQ.getText()));
             prod.setQty(Integer.parseInt(TEXT_QTY.getText()));
             prod.setPanelization(Integer.parseInt(TEXT_PAN.getText()));
             prod.setCycle(cycle);
             prod.setEff(Integer.parseInt(TEXT_EFF.getText()));
             prod.setStartUpMin(Integer.parseInt(TEXT_STARTUP_MIN.getText()));
             prod.setDownTimeMin(Integer.parseInt(TEXT_DOWNTIME_MIN.getText()));
             Station station = PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PlNner.PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation());
             station.runcode++;
             prod.setID(Integer.toString(new DateTime().getYear()).substring(2) + String.format("%02d", PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getWeekIndex()) +String.format("%03d", station.runcode) + "#" + station.station_id );
             station.addProduct(prod);
             prod.resize();
             */
            PlNner.MF.PROD_CLIP = new Product(Product.this.weekdate);

            PlNner.MF.PROD_CLIP.setJobnumber(Product.this.getJobnumber());
            PlNner.MF.PROD_CLIP.setPartNumber(Product.this.getPartnumber());
            PlNner.MF.PROD_CLIP.setSequence(Product.this.getSequence());
            PlNner.MF.PROD_CLIP.setQty(Product.this.getQty());
            PlNner.MF.PROD_CLIP.setPanelization(Product.this.getPanelization());
            PlNner.MF.PROD_CLIP.setCycle(Product.this.getCycle());
            PlNner.MF.PROD_CLIP.setEff(Product.this.getEff());
            PlNner.MF.PROD_CLIP.setStartUpMin(Product.this.getStartUpMin());
            PlNner.MF.PROD_CLIP.setDownTimeMin(Product.this.getDownTimeMin());
            PlNner.MF.PROD_CLIP.setBGColor(Product.this.getBGColor());
            PlNner.MF.PROD_CLIP.setFGColor(Product.this.getFGColor());

            PlNner.MF.PROD_CLIP_KITTED = Product.this.getStation().isKitted(Product.this.getJobnumber());
            PlNner.MF.PROD_CLIP_ENGINEER = Product.this.getStation().isEngineering(Product.this.getJobnumber());

        }
    };

    ActionListener A_CUT_TO_CLIP = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Station st = (Station) getParent();
            st.setNeedToSave();
            Arrays.fill(QTYS, 0.0);
            Arrays.fill(QTYS_TENY, 0.0);
            Arrays.fill(QTYS_TENY_MSZAK, 0.0);
            Arrays.fill(QTYS_TENY_TIME, 0.0);

            PlNner.MF.PROD_CLIP = Product.this;

            PlNner.MF.PROD_CLIP_KITTED = Product.this.getStation().isKitted(Product.this.getJobnumber());
            PlNner.MF.PROD_CLIP_ENGINEER = Product.this.getStation().isEngineering(Product.this.getJobnumber());

            st.deleteProductWithoutConfirmation();

        }
    };

    public void resetchange() {
        last_x = Product.this.getLocation().x;
    }

    ActionListener A_KIT = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Product.this.getStation().setKitted(Product.this.getJobnumber(), !Product.this.getStation().isKitted(Product.this.getJobnumber()));
        }
    };

    public Product(DateTime week) {

        // Timer t_ins = new Timer(100, on_change_action);
        //t_ins.start();
        RELEASE = true;
        modified = false;
        TT = new ToolTip(this);
        mytooltip = new MyToolTip(300, 50);
        comment = "";
        weekdate = week;
        setOpaque(false);
        setMoveAble();
        addMouseAction();

        selected = false;
        canArrange = false;
        QTYS_TENY_MSZAK = new double[168 / PlNner.MSZAK_REND];
        QTYS_TENY_TIME = new double[168 / PlNner.MSZAK_REND];

        menu = new JPopupMenu();

        JMenuItem TUL = new JMenuItem("Törlés");
        JMenuItem TUL_CUT = new JMenuItem("Szétbontás");
        JMenuItem TUL_CUTTOCLIP = new JMenuItem("Kivág");
        JMenuItem TUL_COPYTOCLIP = new JMenuItem("Másol");
        JMenuItem TUL_OPT_TO_ME = new JMenuItem("Igazítás ide...");

        TUL_KIT = new JCheckBoxMenuItem("Kittelve");

        menu_time = new JMenu("Időbélyeghez rendelés");
        menu_color = new JMenu("Színezés");

        Color red = Color.red;
        JMenuItem ired = new JMenuItem("Piros");
        ired.setIcon(new OvalIcon(16, 16, red));
        ired.addActionListener(new ActionColor(this, red));
        menu_color.add(ired);

        Color cyan = Color.CYAN;
        JMenuItem icyan = new JMenuItem("Cián");
        icyan.setIcon(new OvalIcon(16, 16, cyan));
        icyan.addActionListener(new ActionColor(this, cyan));
        menu_color.add(icyan);

        Color pink = Color.PINK;
        JMenuItem ipink = new JMenuItem("Rózsaszín");
        ipink.setIcon(new OvalIcon(16, 16, pink));
        ipink.addActionListener(new ActionColor(this, pink));
        menu_color.add(ipink);

        Color magenta = Color.MAGENTA;
        JMenuItem imagenta = new JMenuItem("Magenta");
        imagenta.setIcon(new OvalIcon(16, 16, magenta));
        imagenta.addActionListener(new ActionColor(this, magenta));
        menu_color.add(imagenta);

        Color orange = Color.ORANGE;
        JMenuItem iorange = new JMenuItem("Narancssárga");
        iorange.setIcon(new OvalIcon(16, 16, orange));
        iorange.addActionListener(new ActionColor(this, orange));
        menu_color.add(iorange);

        Color alap = new Color(0, 0, 150);
        JMenuItem ialap = new JMenuItem("Alap kék");
        ialap.setIcon(new OvalIcon(16, 16, alap));
        ialap.addActionListener(new ActionColor(this, alap));
        menu_color.add(ialap);

        Color feher = Color.white;
        JMenuItem ifeher = new JMenuItem("Fehér");
        ifeher.setIcon(new OvalIcon(16, 16, feher));
        ifeher.addActionListener(new ActionColor(this, feher));
        menu_color.add(ifeher);

        Color gray = Color.gray;
        JMenuItem igray = new JMenuItem("Szürke");
        igray.setIcon(new OvalIcon(16, 16, gray));
        igray.addActionListener(new ActionColor(this, gray));
        menu_color.add(igray);

        Color zold = Color.green;
        JMenuItem izold = new JMenuItem("Zöld");
        izold.setIcon(new OvalIcon(16, 16, zold));
        izold.addActionListener(new ActionColor(this, zold));
        menu_color.add(izold);

        menu_color.setIcon(new ImageIcon(this.getClass().getResource("IMG/painter.png")));

        JMenuItem TUL_MAN_T = new JMenuItem("Manuális gyártási hossz");

        TUL_MAN_T.setIcon(new ImageIcon(this.getClass().getResource("IMG/clock12-2.png")));
        TUL_OPT_TO_ME.setIcon(new ImageIcon(this.getClass().getResource("IMG/stock_sort16.png")));
        TUL_CUT.setIcon(new ImageIcon(this.getClass().getResource("IMG/cut_16.png")));
        JMenuItem TUL_PROP = new JMenuItem("Tulajdonságok");

        TUL_PROP.setIcon(new ImageIcon(this.getClass().getResource("IMG/gear16.png")));
        TUL.addActionListener(A_DEL);
        TUL_PROP.addActionListener(A_PROP);
        TUL_MAN_T.addActionListener(A_MAN_T);
        TUL_CUT.addActionListener(A_CUT);
        TUL_CUTTOCLIP.addActionListener(A_CUT_TO_CLIP);
        TUL_COPYTOCLIP.addActionListener(A_COPY_TO_CLIP);
        TUL_OPT_TO_ME.addActionListener(A_SORT_TO_ME);
        TUL_KIT.addActionListener(A_KIT);

        //stock_sort16
        menu.add(TUL);
        menu.add(TUL_CUT);
        menu.add(TUL_PROP);
        menu.add(menu_color);
        menu.add(TUL_MAN_T);
        menu.add(TUL_OPT_TO_ME);
        menu.add(TUL_KIT);
        menu.add(menu_time);
        menu.add(TUL_CUTTOCLIP);
        menu.add(TUL_COPYTOCLIP);

        Arrays.fill(MINUTES, 0.0);

    }

}
