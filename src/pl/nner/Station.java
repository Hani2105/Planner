/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.Encoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.util.IOUtils;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.json.JSONArray;

import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
class Action implements ActionListener {

    String filename;
    Station station;

    public Action(String filename, Station station) {
        this.filename = filename;
        this.station = station;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Theme theme = (Theme) ois.readObject();

            station.setTheme(theme);

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }
    }

}

class JOB {

    public String jobname;
    public String partnumber;
    public Double _qty;

    public List<Product> prods = new ArrayList<>();

    public boolean isReleased() {
        for (int i = 0; i < prods.size(); i++) {
            if (prods.get(i).RELEASE == false) {
                return false;
            }
        }
        return true;
    }

    public int getQty(int mszak) {
        double q = 0;
        int szak = 0;
        for (int p = 0; p < prods.size(); p++) {

            if ((prods.get(p).getSequence() == 0) || (prods.get(p).getSequence() == 2)) {
                for (int c = 1; c < 168 * 60 + 1; c++) {
                    q += prods.get(p).QTYS[c - 1];

                    if (c % (PlNner.MSZAK * 60) == 0) {
                        szak++;
                        if (szak == mszak) {
                            return (int) q;
                        } else {
                            q = 0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public JOB() {

    }

    public int getQty() {

        int seq = prods.get(0).getSequence();

        int QTY = 0;

        for (int i = 0; i < prods.size(); i++) {
            if (prods.get(i).getSequence() == seq) {
                QTY += prods.get(i).getQty();
            }
        }

        return QTY;
    }

    ;
    
    DateTime getStart() {
        return prods.get(0).getStartTime();
    }

    DateTime getStop() {
        return prods.get(prods.size() - 1).getEndTime();
    }

    public boolean isThis(Product prod) {
        for (int i = 0; i < prods.size(); i++) {
            if (prod.getID().equals(prods.get(i).getID())) {
                return true;
            }
        }

        return false;

    }

}

public final class Station extends JPanel implements Serializable {

    public Object[][] loader = new Object[20][100];

    int p_r;
    int p_p;

    boolean js = false;  //JSON export

    public double[] STATION_MINUTES; //168 * 60 perc 1 heti időintervallum
    private String name;
    public int station_id;
    protected Point anchorPoint;

    public int inf_x, inf_y;
    public float products_alpha = 0.75f;
    public int products_onwrite_string = 0; //Default 0 as nothing
    public ImageIcon BG_IMAGE = null;
    public ImageIcon imghelp = new ImageIcon(this.getClass().getResource("IMG/search28.png"));
    public ImageIcon imgwarning = new ImageIcon(this.getClass().getResource("IMG/warning.png"));
    public ImageIcon imgkitted = new ImageIcon(this.getClass().getResource("IMG/kitted.png"));
    public ImageIcon imgeng = new ImageIcon(this.getClass().getResource("IMG/engineering.png"));
    public ImageIcon imgeyec = new ImageIcon(this.getClass().getResource("IMG/eyeclose16.png"));
    public ImageIcon imginf = new ImageIcon(this.getClass().getResource("IMG/inf16.png"));
    public ImageIcon imgeye = new ImageIcon(this.getClass().getResource("IMG/eye16.png"));
    public ImageIcon imgup = new ImageIcon(this.getClass().getResource("IMG/up_arrow.png"));
    public ImageIcon imgdown = new ImageIcon(this.getClass().getResource("IMG/down_arrow.png"));
    public ImageIcon imgstops = new ImageIcon(this.getClass().getResource("IMG/stop_arrow.png"));
    public ImageIcon imgsh = new ImageIcon(this.getClass().getResource("IMG/search_bg.png"));
    public int img_pos = 0;
    public boolean isPlanA;

    public boolean needToReloadThemes = true;

    //Egéresemények a TOP panelen
    public int P_MOUSE_X = 0;
    public int P_MOUSE_Y = 0;

    boolean SCROLLED = false;
    Color TOP_COLOR;
    float TOP_ALPHA;
    //Egér paraméterek
    int mouse_x;
    int mouse_y;
    boolean mouse_over;

    //Egér paraméterek
    //Kapcsolók
    public boolean SW_STATUS_BAR;
    public boolean SW_INDICATOR;
    public boolean SW_COMMENT;
    public boolean SW_FAMILY;
    public boolean SW_JOBINFO;

    public boolean SW_JOB_NO;
    public boolean SW_PN_NO;
    public boolean SW_SEQ;
    public boolean SW_ID;

    //Színezés
    public Color BG_COLOR = new Color(255, 255, 255);
    public Color SELECTED_COLOR = Color.blue;
    public Color FG_COLOR = new Color(0, 0, 0);
    public Color HALF_VISIBLE = Color.lightGray;
    public Color BG_PROGRESS = Color.lightGray;
    public Color FG_PROGRESS = Color.white;

    public int IND_HOUR; //Óra beosztás
    private int IND_HOUR_LENGTH = 10; //Óra beosztás hossza
    private int IND_DAY_LENGTH = 20; //Nap beosztás hossza

    private String[] DAY_NAMES = new String[]{"Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"};

    public JPanel TOP_PANEL;
    public List<Product> PRODUCTS = new ArrayList<>();
    public List<DownTime> DOWNTIMES = new ArrayList<>();
    public List<TimeStamp> TIMESTAMPES = new ArrayList<>();

    public List<JOB> JOBS = new ArrayList<>();

    private String TableInHtml;
    public boolean _CTRL;
    public boolean _SHIFT;
    private String comment;

    private int BORDER = 10; //Margó
    private int TOP_BORDER = 120;
    private int Z_LEVEL; //Zoom level min :5 //lépésköz tervezéshez
    private int Z_LEVEL_LAST;
    private int[] Z_INDEXES = new int[]{5, 10, 15, 20, 30, 60};
    private int Z_INDEX;
    private boolean selected;
    public boolean szamol; //Kalkuláció folyik
    public boolean teny_szamol; //Tény számolás folyik
    private int Toolbar_X = 0;
    private int Toolbar_Y = 6;

    JToolBar TB;

    private double progress;
    private String progress_text = "";
    private String progress_post_text = "";
    private int stored_start_min;
    private int stored_last_min;
    private JCheckBox TENY_CALC;
    public boolean drag = false;
    public boolean can_drag;
    public double now, max;
    public int runcode;
    public boolean export_to_mysql;
    public boolean mysql_online;

    private JPopupMenu POP;
    public JTextField SEARCHER;

    public JCheckBox AUTO_ARRANGE;

    private Thread szal;

    public void setComment(String comm) {
        comment = comm;
    }

    public String getComment() {
        return comment;
    }

    public void clearSelection() {
        for (Product elem : PRODUCTS) {
            elem.setSel(false);
            elem.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    public JOB getJob(String jname) {
        for (int i = 0; i < JOBS.size(); i++) {
            if (JOBS.get(i).jobname.equals(jname)) {
                return JOBS.get(i);
            }
        }
        return null;
    }

    public boolean isExistinLoader(String JOBNAME) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void putJobtoLoader(String jobname, String partnumber, Double qty) {

        if (!isExistinLoader(jobname)) {
            for (int r = 0; r < 100; r++) {
                if (loader[0][r] == null) {
                    System.out.println("Put");
                    loader[0][r] = jobname;
                    loader[1][r] = partnumber;
                    loader[2][r] = qty;
                    loader[3][r] = Boolean.FALSE; //kit
                    loader[4][r] = Boolean.FALSE;  //mérnöki
                    loader[5][r] = Boolean.TRUE; //passz

                    break;
                }
            }
        }
        recalcJobs();
    }

    public double getValtasokOraban() {
        int min = 0;

        for (Product prod : PRODUCTS) {
            if (prod.RELEASE) {
                min += prod.getStartUpMin();
            }
        }

        return (double) min / (double) 60;
    }

    public int getValtasokSzama() {
        int no = 0;

        for (Product prod : PRODUCTS) {
            if (prod.RELEASE) {
                if (prod.getStartUpMin() > 0) {
                    no++;
                }
            }
        }

        return no;
    }

    public double getPercentOfProducts() {

        int min = 0;

        for (Product prod : PRODUCTS) {
            if (prod.RELEASE) {
                prod.setQtyPerMin(60 / prod.getCycle() * ((double) prod.getEff() / 100) * prod.getPanelization()); //Percenkénti darabszám

                double t = (prod.getQty() / prod.getQtyPerMin());
                min += (int) t + prod.getStartUpMin();
            }
        }

        for (DownTime downtimes : DOWNTIMES) {
            min += downtimes.m;
        }

        return (double) min / (168 * 60);

    }

    public void setKitted(String JOBNAME, boolean value) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    loader[3][r] = value;
                }
            }
        }

    }

    public void setPassed(String JOBNAME, boolean value) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    loader[5][r] = value;
                }
            }
        }

    }

    public Boolean isPassed(String JOBNAME) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    if (loader[5][r] == null) {
                        return Boolean.TRUE;
                    } else {
                        return (Boolean) loader[5][r];
                    }
                }
            }
        }
        return false;
    }

    public Boolean isKitted(String JOBNAME) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    if (loader[3][r] == null) {
                        return false;
                    } else {
                        return (Boolean) loader[3][r];
                    }
                }
            }
        }
        return false;
    }

    public Boolean isEngineering(String JOBNAME) {
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[0][r].toString().equals(JOBNAME)) {
                    if (loader[4][r] == null) {
                        return false;
                    } else {
                        return (Boolean) loader[4][r];
                    }
                }
            }
        }
        return false;
    }

    public void setEngineering(String JOBNAME, Boolean value) {
        for (int i = 0; i < 100; i++) {
            if (loader[0][i] != null) {
                if (loader[0][i].toString().equals(JOBNAME)) {

                    loader[4][i] = value;

                }
            }
        }
    }

    public void recalcJobs() {
        //Létrehoz egy job tömböt, ami a jobszámokat raktározza prio sorrendbe
        /*
         loader[3][r] = Boolean.FALSE; //kit
         loader[4][r] = Boolean.FALSE;  //mérnöki
         loader[5][r] = Boolean.TRUE; //passz
         */
        for (int r = 0; r < 100; r++) {
            if (loader[0][r] != null) {
                if (loader[3][r] == null) {
                    loader[3][r] = Boolean.FALSE;
                }

                if (loader[4][r] == null) {
                    loader[4][r] = Boolean.FALSE;
                }
                if (loader[5][r] == null) {
                    loader[5][r] = Boolean.TRUE;
                }
            }
        }

        JOBS.clear();
        String jbs = "";
        for (int i = 0; i < PRODUCTS.size(); i++) {
            for (int p = 0; p < PRODUCTS.size(); p++) {
                if (PRODUCTS.get(p).getNo() == i) {
                    if (!jbs.contains(PRODUCTS.get(p).getJobnumber())) {
                        jbs += PRODUCTS.get(p).getJobnumber() + ",";
                        JOB job = new JOB();
                        job.jobname = PRODUCTS.get(p).getJobnumber();
                        job.partnumber = PRODUCTS.get(p).getPartnumber();
                        JOBS.add(job);
                    }
                }
            }
        }

        for (int i = 0; i < PRODUCTS.size(); i++) {
            for (int p = 0; p < JOBS.size(); p++) {
                if (JOBS.get(p).jobname.equals(PRODUCTS.get(i).getJobnumber())) {
                    JOBS.get(p).prods.add(PRODUCTS.get(i));
                }
            }
        }

    }

    public Theme getTheme() {
        Theme back = new Theme();
        back.BG_COLOR = BG_COLOR;
        back.BG_PROGRESS = BG_PROGRESS;
        back.FG_COLOR = FG_COLOR;
        back.FG_PROGRESS = FG_PROGRESS;
        back.HALF_VISIBLE = HALF_VISIBLE;
        back.SELECTED_COLOR = SELECTED_COLOR;
        return back;
    }

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    public void setTheme(Theme theme) {
        /*
         public Color BG_COLOR = new Color(255, 255, 255);
         public Color SELECTED_COLOR = Color.blue;
         public Color FG_COLOR = new Color(0, 0, 0);
         public Color HALF_VISIBLE = Color.lightGray;
         public Color BG_PROGRESS =Color.lightGray;
         public Color FG_PROGRESS = Color.white;
         */
        BG_COLOR = theme.BG_COLOR;
        SELECTED_COLOR = theme.SELECTED_COLOR;
        FG_COLOR = theme.FG_COLOR;
        HALF_VISIBLE = theme.HALF_VISIBLE;
        BG_PROGRESS = theme.BG_PROGRESS;
        FG_PROGRESS = theme.FG_PROGRESS;
        this.img_pos = theme.img_pos;

        if (theme.BG != null) {
            this.BG_IMAGE = theme.BG;

        } else {
            // getPlan().BG_IMAGE = null;

        }
        JPanel panel = (JPanel) Station.this.getParent();
        JScrollPane pane = (JScrollPane) Station.this.getParent().getParent().getParent();
        panel.repaint();
        pane.setBackground(BG_COLOR);
        panel.setBackground(BG_COLOR);

    }

    public void addProduct(Product prod) {

        add(prod);
        PRODUCTS.add(prod);
        prod.setLocation(_getBorder(), _getTop() + 35 * PRODUCTS.size());
        recalcJobs();

        if (prod.getJobnumber() != null) {
            if (prod.getJobnumber().length() > 0) {
                if (!isExistinLoader(prod.getJobnumber())) {

                    this.putJobtoLoader(prod.getJobnumber(), prod.getPartnumber(), prod.getQty());

                }
            }
        }

        setVSize();
        prod.resetchange();
    }

    public void reColor(Product prod, Color color) {

        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).getJobnumber().equals(prod.getJobnumber())) {
                PRODUCTS.get(i).setBGColor(color);
            }
        }

    }

    public void reColor() {
        Random rand = new Random();
        recalcJobs();
        for (int i = 0; i < JOBS.size(); i++) {

            Color col = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            for (int j = 0; j < PRODUCTS.size(); j++) {
                if (PRODUCTS.get(j).getJobnumber().equals(JOBS.get(i).jobname)) {
                    if (!_CTRL) {
                        PRODUCTS.get(j).setBGColor(col);
                    } else {
                        PRODUCTS.get(j).setBGColor(new Color(0, 0, 150));
                    }

                }
            }

        }

    }

    public void addProductWithLocation(Product prod, int x, int y) {
        prod.setLocation(x, y);
        add(prod);
        PRODUCTS.add(prod);
        recalcJobs();
        if (prod.getJobnumber() != null) {
            if (prod.getJobnumber().length() > 0) {
                if (!isExistinLoader(prod.getJobnumber())) {
                    putJobtoLoader(prod.getJobnumber(), prod.getPartnumber(), prod.getQty());
                }
            }
        }
        setVSize();
        prod.resetchange();
    }

    public double getFoglaltTermek(int szak) {
        int szum = 0;
        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).QTYS_TENY_MSZAK[szak] > 0) {
                szum++;
            }
        }
        return szum;
    }

    public double getAranyPar(int szak) {
        double szum = 0;
        for (int i = 0; i < PRODUCTS.size(); i++) {
            szum += PRODUCTS.get(i).QTYS_TENY_TIME[szak];
        }

        if (szum > 0) {
            return 1 / szum;
        } else {
            return 0;
        }
    }

    public int getNoOfShiftFromWeek() {

        int dist = Hours.hoursBetween(PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, new DateTime()).getHours();

        if (dist > 168) {
            dist = 168;
        }

        return dist / PlNner.MSZAK_REND;

    }

    public void setNeedToSave() {
        Plan plan = (Plan) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
        plan.NEED_TO_SAVE = true;
    }

    public void setPlanStatustoCalc() {
        szamol = true;
        Plan plan = (Plan) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        JTabbedPane pane = (JTabbedPane) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        String nm = plan.getName();
        for (int i = 0; i < pane.getTabCount(); i++) {
            if (pane.getComponentAt(i).getName().equals(nm)) {
                pane.setIconAt(i, new ImageIcon(this.getClass().getResource("IMG/ajax-loader.gif")));
            }
        }
        PlNner.CP.tick();
    }

    public Plan getPlan() {
        return (Plan) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }

    public void setPlanStatustoInactive() {
        szamol = false;
        Plan plan = (Plan) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        JTabbedPane pane = (JTabbedPane) getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();

        String nm = plan.getName();
        for (int i = 0; i < pane.getTabCount(); i++) {
            if (pane.getComponentAt(i).getName().equals(nm)) {
                pane.setIconAt(i, null);
            }
        }
        PlNner.CP.tick();
    }

    //megvalósulás lekérdezése
    public void calcResult_2() {
        teny_szamol = true;
        //  DefaultTableModel MODEL = PlNner.MYDB_DB.getDataTableModel("SELECT * from terv where")

        DateTime WEEK = PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate;

        DateTime T1 = WEEK;
        DateTime T2 = WEEK.plusDays(6).plusHours(12);

        if (T2.getZone().toTimeZone().inDaylightTime(T2.toDate())) {
            T2 = T2.minusHours(1);
        }

        DefaultTableModel MODEL = PlNner.MYDB_DB.getDataTableModel("SELECT * from " + PlNner.PLANTABLENAME + " WHERE stationid = '" + station_id + "' AND startdate >= '" + formatDate(T1) + "' AND startdate <= '" + formatDate(T2.plusHours(2)) + "' and active=1;");

//        for (int i = 0; i < PRODUCTS.size(); i++) {
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY, 0);
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY_MSZAK, 0);
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY_TIME, 0);
//            repaint();
//        }

        for (int i = 0; i < PRODUCTS.size(); i++) {
            progress_text = "Megvalósulás lekérdezése: [" + i + "/" + PRODUCTS.size() + "]";
            double teny = 0;
            for (int r = 0; r < MODEL.getRowCount(); r++) {
                if (MODEL.getValueAt(r, 1).toString().equals(PRODUCTS.get(i).getID())) {
                    teny += Double.parseDouble(MODEL.getValueAt(r, 8).toString());
                }
            }
            PRODUCTS.get(i).QTYS_TENY[0] = teny;
            PRODUCTS.get(i).QTYS_TENY_MSZAK[0] = teny;
            repaint();
        }
        teny_szamol = false;
        progress_text = "";
        repaint();
    }

    public void calcResult() {
        teny_szamol = true;
        DateTime WEEK = PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate;
        //megvalósulás ...
        for (int i = 0; i < PRODUCTS.size(); i++) {
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY, 0);
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY_MSZAK, 0);
//            Arrays.fill(PRODUCTS.get(i).QTYS_TENY_TIME, 0);

            progress = 0;
            progress_text = "Megvalósulás lekérdezése:";
            progress_post_text = PRODUCTS.get(i).getPartnumber() + " " + PlNner.SEQUENCES[PRODUCTS.get(i).getSequence()];
            max = 10080;
            now = 0;

            int _to = 168 / PlNner.MSZAK;

            if (TENY_CALC.isSelected()) {
                _to = getNoOfShiftFromWeek();
            }

            for (int c = 1; c < (_to * PlNner.MSZAK) * 60 + 1; c++) {

                now++;
                progress = (now / max) * 100;

                if (c % (PlNner.MSZAK_REND * 60) == 0) {

                    Object val = null;
                    if (mysql_online) {
                        val = PlNner.MYDB_DB.getCellValue("SELECT qty_teny FROM " + PlNner.PLANTABLENAME + " WHERE prod_id='" + PRODUCTS.get(i).getID()
                                + "' AND startdate='" + formatDate(WEEK.plusMinutes(c - PlNner.MSZAK_REND * 60)) + "' AND active=1;");
                    }
                    if (val == null) {
                        val = 0.0;
                    }
                    //ha nem sikerül a lekérdezés
                    if (Integer.parseInt(val.toString()) == -1) {

                        //custom title, error icon
                        JOptionPane.showMessageDialog(this,
                                "Az adatokat nem sikerült frissíteni!",
                                "Lekérdezés hiba!",
                                JOptionPane.ERROR_MESSAGE);

                    }
                    double result = Double.parseDouble(val.toString());
                    if (result > 0) {
                        repaint();
                        PRODUCTS.get(i).QTYS_TENY_MSZAK[(c / (PlNner.MSZAK_REND * 60)) - 1] = result;
                        PRODUCTS.get(i).QTYS_TENY_TIME[(c / (PlNner.MSZAK_REND * 60)) - 1] = result / PRODUCTS.get(i).getQtyPerMin(); //ennyi időt venne igénybe
                    }

                }
            }
            repaint();
        }
        teny_szamol = false;
        progress = 0;
        progress_text = "";
        progress_post_text = "";

        //megvalósulás ...
    }

    public void calc() {
        Runnable RUN = new Runnable() {

            @Override
            public void run() {
                setNeedToSave();
                setPlanStatustoCalc();
                //PlNner.MYDB_DB.setConnection();

                progress_post_text = "Csatlakozás az adatbázishoz...";
                mysql_online = PlNner.MYDB_DB.isDBAlive();
                DateTime WEEK = PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate;

                Arrays.fill(STATION_MINUTES, 1.0); //Állomások feltöltése

                for (int i = 0; i < PRODUCTS.size(); i++) {
                    //Gyártási idők beállítása
                    PRODUCTS.get(i).setQtyPHour(3600 / PRODUCTS.get(i).getCycle() * ((double) PRODUCTS.get(i).getEff() / 100) * PRODUCTS.get(i).getPanelization()); //Óránkénti darabszám
                    PRODUCTS.get(i).setQtyPerMin(60 / PRODUCTS.get(i).getCycle() * ((double) PRODUCTS.get(i).getEff() / 100) * PRODUCTS.get(i).getPanelization()); //Percenkénti darabszám
                    PRODUCTS.get(i).setTime(PRODUCTS.get(i).getQty() / PRODUCTS.get(i).getQtyPHour());
                    Arrays.fill(PRODUCTS.get(i).QTYS, 0.0);
                    Arrays.fill(PRODUCTS.get(i).QTYS_TENY, 0.0);
                    // Arrays.fill(PRODUCTS.get(i).QTYS_TENY_MSZAK, 0.0);
                    Arrays.fill(PRODUCTS.get(i).QTYS_TENY_TIME, 0.0);

                }

                if (TENY_CALC.isSelected()) {
//                    calcResult();
//                    calcResult();
                      calcResult_2();

                    for (int i = 0; i < PRODUCTS.size(); i++) {

                        for (int p = 0; p < PRODUCTS.size(); p++) {

                            if (PRODUCTS.get(p).getNo() == i) {
                                if (PRODUCTS.get(p).RELEASE) {
                                    progress = 0;
                                    progress_text = "Autómata terv igazítás...";
                                    progress_post_text = PRODUCTS.get(i).getPartnumber() + " " + PlNner.SEQUENCES[PRODUCTS.get(i).getSequence()];
                                    max = 10080;
                                    now = 0;

                                    int _to = 168 / PlNner.MSZAK;

                                    if (TENY_CALC.isSelected()) {
                                        _to = getNoOfShiftFromWeek();
                                    }

                                    for (int c = 1; c < (_to * PlNner.MSZAK) * 60 + 1; c++) {
                                        now++;
                                        progress = (now / max) * 100;
                                        if (c % (PlNner.MSZAK_REND * 60) == 0) {

                                            if (PRODUCTS.get(p).QTYS_TENY_MSZAK[(c / (PlNner.MSZAK_REND * 60)) - 1] > 0) {

                                                double arany = getAranyPar((c / (PlNner.MSZAK_REND * 60)) - 1);
                                                double lefoglalt_ido = (PRODUCTS.get(p).QTYS_TENY_TIME[(c / (PlNner.MSZAK_REND * 60)) - 1] * arany * (PlNner.MSZAK_REND * 60));
                                                int szamlalo = -1;
                                                for (int f = c - (PlNner.MSZAK_REND * 60); f < c; f++) {
                                                    if (STATION_MINUTES[f] == 1.0) {
                                                        szamlalo++;

                                                        if (szamlalo == PRODUCTS.get(p).getStartUpMin()) {
                                                            PRODUCTS.get(p).QTYS_TENY[f] = 0.001;
                                                            PRODUCTS.get(p).QTYS[f] = 0.001;
                                                        }

                                                        if (szamlalo == (int) lefoglalt_ido - 1) {
                                                            PRODUCTS.get(p).QTYS_TENY[f] = PRODUCTS.get(p).QTYS_TENY_MSZAK[(c / (PlNner.MSZAK_REND * 60)) - 1];
                                                            PRODUCTS.get(p).QTYS[f] = PRODUCTS.get(p).QTYS_TENY_MSZAK[(c / (PlNner.MSZAK_REND * 60)) - 1];
                                                            break;
                                                        }

                                                        if (szamlalo <= (int) lefoglalt_ido) {
                                                            STATION_MINUTES[f] = 0;

                                                        }

                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }//release
                        }

                    }
                }

                //tervezés
                DateTime T1 = WEEK;
                DateTime T2 = WEEK.plusDays(6).plusHours(12);

                if (export_to_mysql) {
                    PlNner.MYDB_DB.insertCommand("DELETE FROM downtimes WHERE stations_id = '" + station_id + "' AND startdate >= '" + formatDate(T1) + "' AND startdate <= '" + formatDate(T2) + "';");

                }

                for (int i = 0; i < DOWNTIMES.size(); i++) {
                    DOWNTIMES.get(i).calc();
                    if (export_to_mysql) {
                        String[] name_dt = DOWNTIMES.get(i).getName().split("_");
                        Object id_dt = PlNner.MYDB_DB.getCellValue("SELECT id from downtime_types WHERE name='" + name_dt[0] + "';");
                        PlNner.MYDB_DB.insertCommand("INSERT INTO downtimes (downtime_types_id, stations_id, startdate, stopdate) VALUE (" + id_dt + "," + station_id + ",'" + formatDate(DOWNTIMES.get(i).StartTime) + "','" + formatDate(DOWNTIMES.get(i).EndTime) + "');");
                    }

                    int first = Minutes.minutesBetween(WEEK, DOWNTIMES.get(i).StartTime).getMinutes();
                    int last = Minutes.minutesBetween(WEEK, DOWNTIMES.get(i).EndTime).getMinutes();

                    for (int T = first; T < last; T++) {
                        try {
                            STATION_MINUTES[T] = 0.0;
                        } catch (Exception e) {

                        }
                    }

                }

                if (TENY_CALC.isSelected()) {
                    for (int w = 0; w < getNoOfShiftFromWeek() * PlNner.MSZAK * 60; w++) {
                        STATION_MINUTES[w] = 0;
                    }
                }

                progress = 0;
                max = PRODUCTS.size() * 10080;
                now = 0;

                //TimeStamp-es termékek
                for (int i = 0; i < TIMESTAMPES.size(); i++) {
                    for (int x = 0; x < TIMESTAMPES.size(); x++) {
                        if (TIMESTAMPES.get(x).getNo() == i) {

                            if (TIMESTAMPES.get(x).prod != null) {
                                Product PRODUCTS1 = TIMESTAMPES.get(x).prod;
                                if (PRODUCTS1.RELEASE) {

                                    PRODUCTS1.finalIndicator = false;

                                    //Tervezés kezdete
                                    for (int T = 0; T < (168 * 60); T++) {

                                        //168 * 60 perc = 1 hét
                                        //now++;
                                        // progress = (now / max) * 100;
                                        // progress_text = "Tervezés:";
                                        //  progress_post_text = PRODUCTS.get(i).getPartnumber() + " " + PlNner.SEQUENCES[PRODUCTS.get(i).getSequence()];
                                        DateTime current = WEEK.plusMinutes(T);

                                        int mindist = 0;

                                        if (PRODUCTS1.getFact() == 0) {
                                            mindist = Minutes.minutesBetween(PRODUCTS1.getStartTime().plusMinutes(PRODUCTS1.getStartUpMin()), current).getMinutes(); //Távolság a ciklus idő és a gyártási idő között
                                        } else {
                                            mindist = Minutes.minutesBetween(PRODUCTS1.getStartTime(), current).getMinutes();
                                        }
                                        int firstcont = Minutes.minutesBetween(PRODUCTS1.getStartTime(), current).getMinutes(); //Távolság a ciklus idő és a gyártási idő között
                                        //Tárazás
                                        //  int tarazas = Minutes.minutesBetween(PRODUCTS.get(p).getStartTime().minus(PRODUCTS.get(p).getStartUpMin()), current).getMinutes();
                                        if (firstcont == 0) {
                                            if (PRODUCTS1.getFact() == 0) {
                                                int count = 0;
                                                int c2 = 0;
                                                while (c2 != PRODUCTS1.getStartUpMin()) {
                                                    if (T + count == 10080) {
                                                        break;
                                                    }
                                                    if (STATION_MINUTES[T + count] == 1.0) {
                                                        STATION_MINUTES[T + count] = 0.0;
                                                        c2++;
                                                    }
                                                    count++;
                                                }
                                            }

                                        }
                                        //Tárazás
                                        if (mindist >= 0) {
                                            if (STATION_MINUTES[T] == 1.0) {
                                                //Az állomás 100%-ban elérhető
                                                if (PRODUCTS1.getRemind() >= PRODUCTS1.getQtyPerMin()) {
                                                    //A maradék nagyobb vagy egyenlő mint a percenként legyártható mennyiség
                                                    PRODUCTS1.QTYS[T] = PRODUCTS1.getQtyPerMin(); //betervezzük a maximumot
                                                    //PRODUCTS.get(p).MINUTES[actmin]=1.0; //beírjuk a felhasznált időt
                                                    STATION_MINUTES[T] = 0.0; //elfogyasztottuk az állomás idejét
                                                } else {
                                                    //kevesebb maradt vissza a gyártásból mint 1 percnyi gyártás
                                                    PRODUCTS1.QTYS[T] = PRODUCTS1.getRemind(); //beterveztük a maradékot
                                                    // PRODUCTS.get(p).MINUTES[actmin] = PRODUCTS.get(p).getRemind() / PRODUCTS.get(p).getQtyPerMin(); //beírjuk a felhasznált időt
                                                    STATION_MINUTES[T] = STATION_MINUTES[T] - PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin(); //levonjuk az elhasznált időt
                                                }
                                            } else if (STATION_MINUTES[T] > 0) {
                                                //Az állomásban van még kapacitás de nem 100%
                                                if (PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin() >= STATION_MINUTES[T]) {
                                                    PRODUCTS1.QTYS[T] = STATION_MINUTES[T] / PRODUCTS1.getQtyPerMin();
                                                    //   PRODUCTS.get(p).MINUTES[actmin] =  PRODUCTS.get(p).MINUTES[actmin] + PRODUCTS.get(p).getRemind() / PRODUCTS.get(p).getQtyPerMin(); 
                                                    STATION_MINUTES[T] = 0.0;
                                                } else {
                                                    PRODUCTS1.QTYS[T] = PRODUCTS1.getRemind(); //beterveztük a maradékot
                                                    STATION_MINUTES[T] = STATION_MINUTES[T] - PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin();
                                                }
                                            }

                                            if (PRODUCTS1.finalIndicator == false) {
                                                if (PRODUCTS1.getRemind() == 0) {
                                                    PRODUCTS1.finalIndicator = true;
                                                    int c2 = 0;
                                                    int count = 0;
                                                    while (c2 != PRODUCTS1.getDownTimeMin()) {
                                                        if (T + count == 10080) {
                                                            break;
                                                        }
                                                        if (STATION_MINUTES[T + count] == 1.0) {
                                                            STATION_MINUTES[T + count] = 0.0;
                                                            c2++;
                                                        }
                                                        count++;
                                                    }
                                                }
                                            }

                                        }
                                    }

                                    PRODUCTS1.storedFirst = PRODUCTS1.getFirstMin();
                                    PRODUCTS1.storedLast = PRODUCTS1.getLastMin();
                                    PRODUCTS1.storedProduceMin = true;
                                    PRODUCTS1.resize();

                                }

                            }
                        }
                    }
                }
                //TimeStamp-es termékek vége

                for (int i = 0; i < PRODUCTS.size(); i++) {
                    for (Product PRODUCTS1 : PRODUCTS) {
                        if (PRODUCTS1.getNo() == i) {
                            if (PRODUCTS1.RELEASE) {

                                PRODUCTS1.finalIndicator = false;

                                //Tervezés kezdete
                                for (int T = 0; T < (168 * 60); T++) {

                                    //168 * 60 perc = 1 hét
                                    now++;
                                    progress = (now / max) * 100;
                                    progress_text = "Tervezés:";
                                    progress_post_text = PRODUCTS.get(i).getPartnumber() + " " + PlNner.SEQUENCES[PRODUCTS.get(i).getSequence()];
                                    DateTime current = WEEK.plusMinutes(T);

                                    int mindist = 0;

                                    if (PRODUCTS1.getFact() == 0) {
                                        mindist = Minutes.minutesBetween(PRODUCTS1.getStartTime().plusMinutes(PRODUCTS1.getStartUpMin()), current).getMinutes(); //Távolság a ciklus idő és a gyártási idő között
                                    } else {
                                        mindist = Minutes.minutesBetween(PRODUCTS1.getStartTime(), current).getMinutes();
                                    }
                                    int firstcont = Minutes.minutesBetween(PRODUCTS1.getStartTime(), current).getMinutes(); //Távolság a ciklus idő és a gyártási idő között
                                    //Tárazás
                                    //  int tarazas = Minutes.minutesBetween(PRODUCTS.get(p).getStartTime().minus(PRODUCTS.get(p).getStartUpMin()), current).getMinutes();
                                    if (firstcont == 0) {
                                        if (PRODUCTS1.getFact() == 0) {
                                            int count = 0;
                                            int c2 = 0;
                                            while (c2 != PRODUCTS1.getStartUpMin()) {
                                                if (T + count == 10080) {
                                                    break;
                                                }
                                                if (STATION_MINUTES[T + count] == 1.0) {
                                                    STATION_MINUTES[T + count] = 0.0;
                                                    c2++;
                                                }
                                                count++;
                                            }
                                        }

                                    }
                                    //Tárazás
                                    if (mindist >= 0) {
                                        if (STATION_MINUTES[T] == 1.0) {
                                            //Az állomás 100%-ban elérhető
                                            if (PRODUCTS1.getRemind() >= PRODUCTS1.getQtyPerMin()) {
                                                //A maradék nagyobb vagy egyenlő mint a percenként legyártható mennyiség
                                                PRODUCTS1.QTYS[T] = PRODUCTS1.getQtyPerMin(); //betervezzük a maximumot
                                                //PRODUCTS.get(p).MINUTES[actmin]=1.0; //beírjuk a felhasznált időt
                                                STATION_MINUTES[T] = 0.0; //elfogyasztottuk az állomás idejét
                                            } else {
                                                //kevesebb maradt vissza a gyártásból mint 1 percnyi gyártás
                                                PRODUCTS1.QTYS[T] = PRODUCTS1.getRemind(); //beterveztük a maradékot
                                                // PRODUCTS.get(p).MINUTES[actmin] = PRODUCTS.get(p).getRemind() / PRODUCTS.get(p).getQtyPerMin(); //beírjuk a felhasznált időt
                                                STATION_MINUTES[T] = STATION_MINUTES[T] - PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin(); //levonjuk az elhasznált időt
                                            }
                                        } else if (STATION_MINUTES[T] > 0) {
                                            //Az állomásban van még kapacitás de nem 100%
                                            if (PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin() >= STATION_MINUTES[T]) {
                                                PRODUCTS1.QTYS[T] = STATION_MINUTES[T] / PRODUCTS1.getQtyPerMin();
                                                //   PRODUCTS.get(p).MINUTES[actmin] =  PRODUCTS.get(p).MINUTES[actmin] + PRODUCTS.get(p).getRemind() / PRODUCTS.get(p).getQtyPerMin(); 
                                                STATION_MINUTES[T] = 0.0;
                                            } else {
                                                PRODUCTS1.QTYS[T] = PRODUCTS1.getRemind(); //beterveztük a maradékot
                                                STATION_MINUTES[T] = STATION_MINUTES[T] - PRODUCTS1.getRemind() / PRODUCTS1.getQtyPerMin();
                                            }
                                        }

                                        if (PRODUCTS1.finalIndicator == false) {
                                            if (PRODUCTS1.getRemind() == 0) {
                                                PRODUCTS1.finalIndicator = true;
                                                int c2 = 0;
                                                int count = 0;
                                                while (c2 != PRODUCTS1.getDownTimeMin()) {
                                                    if (T + count == 10080) {
                                                        break;
                                                    }
                                                    if (STATION_MINUTES[T + count] == 1.0) {
                                                        STATION_MINUTES[T + count] = 0.0;
                                                        c2++;
                                                    }
                                                    count++;
                                                }
                                            }
                                        }

                                    }
                                }

                                PRODUCTS1.storedFirst = PRODUCTS1.getFirstMin();
                                PRODUCTS1.storedLast = PRODUCTS1.getLastMin();
                                PRODUCTS1.storedProduceMin = true;
                                PRODUCTS1.resize();

                            }
                        }//felt vége

                    }
                }
                progress = 0;
                //tervezés vége;
                DefaultTableModel MODEL = new DefaultTableModel(PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.head, 0);
                Object[] sor = new Object[MODEL.getColumnCount()];
                Object[] sor_teny = new Object[MODEL.getColumnCount()];

                T1 = WEEK;
                T2 = WEEK.plusDays(6).plusHours(12);

                if (T2.getZone().toTimeZone().inDaylightTime(T2.toDate())) {
                    T2 = T2.minusHours(1);
                }

                if (export_to_mysql) {
                    String parancs = "UPDATE " + PlNner.PLANTABLENAME + " SET active=0 WHERE stationid = " + station_id + " AND startdate >= '" + formatDate(T1) + "' AND startdate <= '" + formatDate(T2.plusHours(2)) + "' AND qty_teny=0;";
                    //JOptionPane.showMessageDialog(null, parancs);
                    PlNner.MYDB_DB.insertCommand(parancs);

                    parancs = "UPDATE " + PlNner.PLANTABLENAME + " SET qty_terv=0 WHERE stationid = " + station_id + " AND startdate >= '" + formatDate(T1) + "' AND startdate <= '" + formatDate(T2.plusHours(2)) + "';";

                    PlNner.MYDB_DB.insertCommand(parancs);

                    PlNner.MYDB_DB.insertCommand("UPDATE " + PlNner.PLANTABLENAME + " SET mernoki=0, qty_terv=0 WHERE stationid = '" + station_id + "' AND startdate >= '" + formatDate(T1) + "' AND startdate <= '" + formatDate(T2) + "';");

                }
                for (int i = 0; i < PRODUCTS.size(); i++) {
                    for (int p = 0; p < PRODUCTS.size(); p++) {
                        if (PRODUCTS.get(p).getNo() == i) {
                            if (PRODUCTS.get(p).RELEASE) {
                                progress = 0;
                                max = 10080;
                                now = 0;
                                Arrays.fill(sor, "");
                                Arrays.fill(sor_teny, "");

                                sor[0] = (PRODUCTS.get(p).getID());
                                sor[1] = (PRODUCTS.get(p).getPartnumber());
                                sor[2] = (PlNner.SEQUENCES[PRODUCTS.get(p).getSequence()]);
                                sor[3] = (PRODUCTS.get(p).getJobnumber());
                                sor[4] = "Terv";

                                sor_teny[0] = (PRODUCTS.get(p).getID());
                                sor_teny[1] = (PRODUCTS.get(p).getPartnumber());
                                sor_teny[2] = (PlNner.SEQUENCES[PRODUCTS.get(p).getSequence()]);
                                sor_teny[3] = (PRODUCTS.get(p).getJobnumber());
                                sor_teny[4] = "Tény";

                                double db = 0.0;
                                double maradek = 0.0;
                                double maradek_teny = 0.0;
                                double teny_db = 0.0;

                                int shift = 4;

                                for (int c = 1; c < 168 * 60 + 1; c++) {
                                    now++;
                                    progress = (now / max) * 100;
                                    progress_text = "Terv elkészítése [" + i + "/" + PRODUCTS.size() + "]";
                                    progress_post_text = PRODUCTS.get(i).getPartnumber() + " " + PlNner.SEQUENCES[PRODUCTS.get(i).getSequence()];
                                    db = db + PRODUCTS.get(p).QTYS[c - 1]; //terv kigyűjtése
                                    teny_db = teny_db + PRODUCTS.get(p).QTYS_TENY[c - 1]; //tény kigyújtése

                                    if (c % 720 == 0) {

                                        shift++;

                                        if (TENY_CALC.isSelected()) {
                                            if (maradek_teny == 0.0) {
                                                maradek_teny = teny_db - (int) teny_db;
                                            } else {
                                                teny_db = teny_db + maradek_teny;
                                                maradek_teny = teny_db - (int) teny_db;
                                            }
                                        } else {
                                            Object val = null;
//                                            if (export_to_mysql) {
//                                                val = PlNner.MYDB_DB.getCellValue("SELECT `qty_teny` FROM `terv` WHERE `prod_id`='" + PRODUCTS.get(p).getID()
//                                                        + "' AND `startdate`='" + formatDate(WEEK.plusMinutes(c - PlNner.MSZAK_REND * 60)) + "';");
//                                            }
                                            if (val == null) {
                                                val = 0.0;
                                            }
                                            if (Double.parseDouble(val.toString()) > 0) {
                                                teny_db = Double.parseDouble(val.toString());
                                            }
                                        }
                                        if (maradek == 0.0) {
                                            maradek = db - (int) db;
                                        } else {
                                            db = db + maradek;
                                            maradek = db - (int) db;
                                        }

                                        sor[shift] = (int) db;
                                        sor_teny[shift] = (int) teny_db;
                                        /*
                                         DELETE from `terv` WHERE `prod_id`="1515001#5" AND `startdate`='2015-04-09 14:00:00';
                                         REPLACE INTO `terv` (prod_id,job,partnumber,stationid,startdate,qty_terv,qty_teny,comments)
                                         VALUES ("1515001#5","LFHB151001","WLTYA3AM19845ABAA/01",1, '2015-04-09 14:00:00', 50, 10,"");
                                         */
                                        if (export_to_mysql) {
                                            //A beazonosított tétel törlése

                                            //PlNner.MYDB_DB.insertCommand("DELETE from `terv` WHERE `prod_id`='" + PRODUCTS.get(p).getID() + "' AND `startdate`='" + formatDate(WEEK.plusMinutes(c - 720)) + "';");
                                            //     PlNner.MYDB_DB.insertCommand("UPDATE terv SET active=0 WHERE `prod_id`='" + PRODUCTS.get(p).getID() + "' AND `startdate`='" + formatDate(WEEK.plusMinutes(c - 720)) + "' ;");
                                            //Adatok beírása
                                            DateTime TT = WEEK.plusMinutes(c - 720);

                                            if (TT.getHourOfDay() == 19 || TT.getHourOfDay() == 7) {
                                                TT = TT.minusHours(1);
                                            }

                                            if (TT.getHourOfDay() == 17 || TT.getHourOfDay() == 5) {
                                                TT = TT.plusHours(1);
                                            }

                                            /*  
                                             String q =     "INSERT INTO `terv` (prod_id,job,partnumber,seq,stationid,startdate,qty_terv,qty_teny,startup,qty_p_hour,qty_full,comments,active,waterfall) VALUES ('"
                                             + PRODUCTS.get(p).getID() + "','"
                                             + PRODUCTS.get(p).getJobnumber() + "','"
                                             + PRODUCTS.get(p).getPartnumber() + "','"
                                             + Integer.toString(PRODUCTS.get(p).getSequence()) + "','"
                                             + Integer.toString(station_id) + "','"
                                             + formatDate(TT) + "','"
                                             + Double.toString((int) db) + "','" //terv
                                             + Double.toString((int) teny_db) + "','" //Tény
                                             + PRODUCTS.get(p).getStartUpMin() + "','"
                                             + (int) PRODUCTS.get(p).getQtyPHour() + "','"
                                             + (int) PRODUCTS.get(p).getQty() + "','"
                                             + PRODUCTS.get(p).getComment() + "',"
                                             + "1,"
                                             + Integer.toString(PRODUCTS.get(p).getNo()) + ") ON DUPLICATE KEY UPDATE "
                                             + "qty_terv=" + Double.toString((int) db)+", "
                                             + "startup=" + PRODUCTS.get(p).getStartUpMin() + ", " 
                                             + "qty_p_hour=" + (int) PRODUCTS.get(p).getQtyPHour() + ", " 
                                             + "qty_full=" + (int) PRODUCTS.get(p).getQty() + ", " 
                                             + "comments='" + PRODUCTS.get(p).getComment() + "', " 
                                             + "active=1, "
                                             + "waterfall=" + Integer.toString(PRODUCTS.get(p).getNo()) + 
                                             ";";
                                             System.out.println("\nQUERY:\n" + q + "\n\n");
                                             */
                                            int _mernoki = 0;
                                            if (PRODUCTS.get(p).isEngeenering()) {
                                                _mernoki = 1;
                                            }

                                            if (db < 1.0) {
                                                db = 0;
                                            }

                                            int a = (int) Math.round(db);

                                            db = a;

                                            if (db > 0) {

                                                PlNner.MYDB_DB.insertCommand(
                                                        "INSERT INTO " + PlNner.PLANTABLENAME + " (prod_id,job,partnumber,seq,stationid,startdate,qty_terv,qty_teny,startup,qty_p_hour,qty_full,comments,active, mernoki) VALUES ('"
                                                        + PRODUCTS.get(p).getID() + "','"
                                                        + PRODUCTS.get(p).getJobnumber() + "','"
                                                        + PRODUCTS.get(p).getPartnumber() + "','"
                                                        + Integer.toString(PRODUCTS.get(p).getSequence()) + "','"
                                                        + Integer.toString(station_id) + "','"
                                                        + formatDate(TT) + "','"
                                                        + Integer.toString((int) db) + "','" //terv
                                                        + Integer.toString((int) teny_db) + "','" //Tény
                                                        + PRODUCTS.get(p).getStartUpMin() + "','"
                                                        + (int) PRODUCTS.get(p).getQtyPHour() + "','"
                                                        + (int) PRODUCTS.get(p).getQty() + "','"
                                                        + PRODUCTS.get(p).getComment() + "',"
                                                        + "1,"
                                                        + Integer.toString(_mernoki)
                                                        + ") "
                                                        + "ON DUPLICATE KEY UPDATE "
                                                        + "qty_terv=" + Integer.toString((int) db) + ", "
                                                        + "startup=" + PRODUCTS.get(p).getStartUpMin() + ", "
                                                        + "qty_p_hour=" + (int) PRODUCTS.get(p).getQtyPHour() + ", "
                                                        + "qty_full=" + (int) PRODUCTS.get(p).getQty() + ", "
                                                        + "comments='" + PRODUCTS.get(p).getComment() + "', "
                                                        + "active=1, "
                                                        + "mernoki=" + _mernoki
                                                        + ";"
                                                );
                                            }
                                            //PlNner.MYDB_DB.insertCommand("UPDATE "+PlNner.PLANTABLENAME+" SET waterfall=" + PRODUCTS.get(p).getNo() + " WHERE `prod_id`='" + PRODUCTS.get(p).getID() + "' AND `startdate`='" + formatDate(WEEK.plusMinutes(c - 720)) + "' ;");

                                        }
                                        /*
                                         System.out.println("REPLACE INTO `terv` (prod_id,job,partnumber,stationid,startdate,qty_terv,qty_teny,comments) VALUES ('"
                                         + PRODUCTS.get(p).getID() + "','" + PRODUCTS.get(p).getJobnumber() + "','" + PRODUCTS.get(p).getPartnumber()
                                         + "','" + Integer.toString(PRODUCTS.get(p).getSequence()) + "','" + formatDate(WEEK.plusMinutes(c)) + "','"
                                         + Double.toString(db) + "','0'," + "'Comment');");
                                         */
                                        db = 0;
                                        teny_db = 0;
                                    }

                                }
                                if (export_to_mysql) {
                                    PlNner.MYDB_DB.insertCommand("UPDATE " + PlNner.PLANTABLENAME + " SET waterfall=" + PRODUCTS.get(p).getNo() + " WHERE prod_id='" + PRODUCTS.get(p).getID() + "' ;");
                                }
                                PRODUCTS.get(p).storedFirst = PRODUCTS.get(p).getFirstMin();
                                PRODUCTS.get(p).storedLast = PRODUCTS.get(p).getLastMin();
                                PRODUCTS.get(p).storedProduceMin = true;

                                MODEL.addRow(sor);
                                MODEL.addRow(sor_teny);
                            }
                        }//felt vége

                    }

                }

                progress = 0;
                progress_text = "Kész";
                progress_post_text = "";

                for (int i = 0; i < PRODUCTS.size(); i++) {
                    if (PRODUCTS.get(i).RELEASE) {
                        PRODUCTS.get(i).resize();
                        PRODUCTS.get(i).repaint();
                    }
                }

                JTable table = PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.table;
                table.setModel(MODEL);
                PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.setTheme();

                //E-mail előkészítése:
                TableInHtml = "";
                TableInHtml += "<TABLE>";
                TableInHtml += "<tr>";

                for (int c = 0; c < MODEL.getColumnCount(); c++) {
                    TableInHtml += "<th>" + MODEL.getColumnName(c) + "</th>";
                }
                TableInHtml += "</tr>";
                for (int r = 0; r < MODEL.getRowCount(); r++) {
                    TableInHtml += "<tr>";
                    for (int c = 0; c < MODEL.getColumnCount(); c++) {
                        TableInHtml += "<td>" + MODEL.getValueAt(r, c) + "</td>";
                    }
                    TableInHtml += "</tr>";
                }

                TableInHtml += "</TABLE>";

                // if (PRODUCTS.size() > 0) {
                // if (getSelectedProduct() > -1) {
                //  stored_start_min = PRODUCTS.get(getSelectedProduct()).getFirstMin() ;
                // stored_last_min = PRODUCTS.get(getSelectedProduct()).getLastMin();
                //}
                //}
                //SQLStr = "UPDATE stations SET refresh='0' WHERE id=" & StationIdCheck & ";"
                if (export_to_mysql) {
                    PlNner.MYDB_DB.insertCommand("UPDATE stations SET refresh=" + "'" + PlNner.getCurrentDateTime(new DateTime()) + "'" + " WHERE id=" + Integer.toString(station_id) + ";");

//                    if (isPlanA) {
//                        String res = JOptionPane.showInputDialog("<html>Szeretné a tervet megjelölni Plan A-nak, az-az első tervnek?<br>Ha igen írja be kis betükkel az igen szócskát:</html>", "");
//
//                        if (res.equals("igen")) {
//                            PlNner.MYDB_DB.insertCommand("UPDATE stations SET plan_a=" + "'" + WEEK.getWeekOfWeekyear() + "@1" + "'" + " WHERE id=" + Integer.toString(station_id) + ";");
//                            isPlanA = false;
//                        }
//                    }
                }

                //jsonexport
                //A_SEND_DATA.actionPerformed(null);
                export_to_mysql = false;
                //PlNner.MYDB_DB.closeConnection();
                setPlanStatustoInactive();
                PlNner.PH.tick();

            }
        };

        if (szal
                == null) {
            szal = new Thread(RUN);
            szal.start();
        } else if (szal.isAlive() == false) {
            szal = null;
            szal = new Thread(RUN);
            szal.start();
        }
    }

    public String formattedSQLDate(DateTime date) {

        return date.getYear() + "-" + String.format("%02d", date.getMonthOfYear()) + "-" + String.format("%02d", date.getDayOfMonth()) + " " + String.format("%02d", date.getHourOfDay()) + ":" + String.format("%02d", date.getMinuteOfHour());

    }

    public String formatDate(DateTime date) {
        return Integer.toString(date.getYear()) + "-" + String.format("%02d", date.getMonthOfYear()) + "-" + String.format("%02d", date.getDayOfMonth()) + " " + String.format("%02d", date.getHourOfDay()) + ":" + String.format("%02d", date.getMinuteOfHour()) + ":" + String.format("%02d", date.getSecondOfMinute());
    }

    public void sortTimeStamp() {
        int no = TIMESTAMPES.size();
        int[] wait4sort = new int[no];

        for (int i = 0; i < no; i++) {
            wait4sort[i] = TIMESTAMPES.get(i).getLocation().x;
        }

        int[] sorted = SelectionSort(wait4sort);

        for (int i = 0; i < sorted.length; i++) {
            for (int ii = 0; ii < sorted.length; ii++) {
                if (TIMESTAMPES.get(i).getLocation().x == sorted[ii]) {
                    TIMESTAMPES.get(i).setNo(ii);
                }
            }
        }

    }

    public void sort() {
        int no = PRODUCTS.size();
        int[] wait4sort = new int[no];

        for (int i = 0; i < no; i++) {
            wait4sort[i] = PRODUCTS.get(i).getLocation().y;
        }

        int[] sorted = SelectionSort(wait4sort);

        for (int i = 0; i < sorted.length; i++) {
            for (int ii = 0; ii < sorted.length; ii++) {
                if (PRODUCTS.get(i).getLocation().y == sorted[ii]) {
                    PRODUCTS.get(i).setNo(ii);
                }
            }
        }

        for (int i = 0; i < PRODUCTS.size(); i++) {
            PRODUCTS.get(i).setLocation(PRODUCTS.get(i).getLocation().x, _getTop() + 35 * (PRODUCTS.get(i).getNo() + 1));
        }
        recalcJobs();
    }

    public static int[] SelectionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }

    public int getSelectedProduct() {

        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).isSelect()) {
                return i;
            }
        }
        return -1;
    }

    private boolean isThisSearch(Product prod) {

        if (SEARCHER.getText().length() == 0) {
            return false;
        }

        if (_CTRL) {
            if (prod.iscontain(SEARCHER.getText())) {
                return true;
            }
        }

        if (prod.getCustomer().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }

        if (prod.getPOutSideCustomer().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }

        if (prod.getProjectName().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }
        if (prod.getJobInfo().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }

        if (prod.getID().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }

        if (prod.getJobnumber().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }
        if (prod.getPartnumber().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }
        if (PlNner.SEQUENCES[prod.getSequence()].toLowerCase().equals(SEARCHER.getText().toLowerCase())) {
            return true;
        }
        if (prod.getComment().toLowerCase().contains(SEARCHER.getText().toLowerCase())) {
            return true;
        }

        return false;

    }

    public void setVSize() {
        setMinimumSize(new Dimension(this.getWidth(), TOP_BORDER + 50 + 35 * PRODUCTS.size()));
        setPreferredSize(new Dimension(this.getWidth(), TOP_BORDER + 50 + 35 * PRODUCTS.size()));
        setSize(new Dimension(this.getWidth(), TOP_BORDER + 50 + 35 * PRODUCTS.size()));
        setMaximumSize(new Dimension(this.getWidth(), TOP_BORDER + 50 + 35 * PRODUCTS.size()));
        recalcJobs();
    }

    public void _Resize() {

        setMinimumSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 50));
        setPreferredSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 50));
        setSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 50));
        setMaximumSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 50));

        TOP_PANEL.setMinimumSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 40));
        TOP_PANEL.setPreferredSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 40));
        TOP_PANEL.setSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 40));
        TOP_PANEL.setMaximumSize(new Dimension((168 * Z_LEVEL) + 2 * (BORDER), TOP_BORDER + 40));

        setVSize();
        repaint();
        IND_HOUR = (Station.this.getWidth() - (_getBorder() * 2)) / 168;

        for (int i = 0; i < PRODUCTS.size(); i++) {

            PRODUCTS.get(i).setLocation(((int) ((PRODUCTS.get(i).getLocation().x - _getBorder()) * ((double) Z_LEVEL / (double) Z_LEVEL_LAST)) + _getBorder()), PRODUCTS.get(i).getLocation().y);
            PRODUCTS.get(i).resize();

        }

        //DT.setSize((jSlider1.getValue() / 60 ) * selected_station.IND_HOUR, 10);
        for (int i = 0; i < DOWNTIMES.size(); i++) {
            DOWNTIMES.get(i).setLocation(((int) ((DOWNTIMES.get(i).getLocation().x - _getBorder()) * ((double) Z_LEVEL / (double) Z_LEVEL_LAST)) + _getBorder()), 125);
            DOWNTIMES.get(i).setSize((DOWNTIMES.get(i).getLength() / 60) * IND_HOUR, 10);
        }

        for (int i = 0; i < TIMESTAMPES.size(); i++) {

            TIMESTAMPES.get(i).relocate();

// TIMESTAMPES.get(i).setSize((DOWNTIMES.get(i).getLength() / 60) * IND_HOUR, 10);
        }

    }

    ActionListener Z_in = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Z_LEVEL_LAST = Z_LEVEL;

            if (Z_INDEX < Z_INDEXES.length - 2) {
                Z_INDEX++;
                Z_LEVEL = Z_INDEXES[Z_INDEX];
            }

            _Resize();
        }
    };

    ActionListener Z_out = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            Z_LEVEL_LAST = Z_LEVEL;

            if (Z_INDEX > 0) {
                Z_INDEX--;
                Z_LEVEL = Z_INDEXES[Z_INDEX];
            }

            _Resize();
        }
    };

    ActionListener Paste = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (PlNner.MF.PROD_CLIP != null) {

                Product p = PlNner.MF.PROD_CLIP;
                p.weekdate = PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate;
                runcode++;
                p.setID(Integer.toString(new DateTime().getYear()).substring(2) + String.format("%02d", PLANS.get(MainForm.TOP.getSelectedIndex()).getWeekIndex()) + String.format("%03d", runcode) + "#" + station_id);

                addProduct(p);
                p.resize();

                if (PlNner.MF.PROD_CLIP_ENGINEER) {
                    Station.this.setEngineering(p.getJobnumber(), Boolean.TRUE);
                }

                if (PlNner.MF.PROD_CLIP_KITTED) {
                    Station.this.setKitted(p.getJobnumber(), Boolean.TRUE);
                }

                PlNner.MF.PROD_CLIP = null;
            }
        }
    };

    private boolean SCROLL_UP = false;
    private boolean SCROLL_DOWN = false;

    public void scroll_up() {
        SCROLL_UP = true;
        SCROLL_DOWN = false;
    }

    public void scroll_down() {
        SCROLL_UP = false;
        SCROLL_DOWN = true;
    }

    public void scroll_stop() {
        SCROLL_UP = false;
        SCROLL_DOWN = false;
        timer.setDelay(200);
    }
    JScrollPane PANE = null;
    public Timer timer;

    ActionListener TIMER_A = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (PANE == null) {
                    PANE = (JScrollPane) Station.this.getParent().getParent().getParent();
                }
                if (SCROLL_UP) {
                    timer.setDelay(10);
                    PANE.getVerticalScrollBar().setValue(PANE.getVerticalScrollBar().getValue() - 6);
                } else if (SCROLL_DOWN) {
                    timer.setDelay(10);
                    PANE.getVerticalScrollBar().setValue(PANE.getVerticalScrollBar().getValue() + 6);
                }

            } catch (NullPointerException ex) {

            }

        }
    };

    public void loadDefaultTheme() {

        String alap = "S:\\SiteData\\BUD1\\EMS\\planning\\[DEV_CENTER]\\PlannerFacelift\\THEMES\\Default";
        try {

            FileInputStream fis = new FileInputStream(alap);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Theme theme = (Theme) ois.readObject();

            this.setTheme(theme);

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {

        }
    }

    public void reloadThemeDir() {
        JMenuItem DARK_THEME = new JMenuItem("Sötét téma");
        JMenuItem LIGHT_THEME = new JMenuItem("Világos téma");
        DARK_THEME.addActionListener(A_SET_DARK);
        LIGHT_THEME.addActionListener(A_SET_LIGHT);
        SET_THEME.add(DARK_THEME);
        SET_THEME.add(LIGHT_THEME);

        String theme_dir = "S:\\SiteData\\BUD1\\EMS\\planning\\[DEV_CENTER]\\PlannerFacelift\\THEMES";
        File files = new File(theme_dir);
        String[] themes = files.list();

        SET_THEME.removeAll();

        for (String theme : themes) {
            JMenuItem menu = new JMenuItem(theme);
            menu.addActionListener(new Action(theme_dir + "\\" + theme, this));
            SET_THEME.add(menu);
        }

    }

    private JMenu SET_THEME;

    public Station(String station_name) {
        isPlanA = true; //Megjelölés plan_A nak
        TOP_ALPHA = 0.0f;
        TOP_COLOR = new Color(0.5f, 0.5f, 0.5f, 0.0f);
        timer = new Timer(200, TIMER_A);
        timer.start();
        szamol = false;
        SW_STATUS_BAR = true;
        SW_INDICATOR = false;
        SW_COMMENT = true;
        SW_FAMILY = false;
        SW_JOBINFO = false;
        SW_JOB_NO = true;
        SW_PN_NO = true;
        SW_SEQ = true;
        comment = "";

        can_drag = false;
        mysql_online = true;
        export_to_mysql = false;
        runcode = 0;
        STATION_MINUTES = new double[168 * 60];
        AUTO_ARRANGE = new JCheckBox("Auto rendezés");
        Arrays.fill(STATION_MINUTES, 1.0); //Feltöltjük percekkel
        name = station_name;
        Z_LEVEL = 5;
        Z_INDEX = 0;
        Z_LEVEL_LAST = 5;

        IND_HOUR = (Station.this.getWidth() - (_getBorder() * 2)) / 168;
        POP = new JPopupMenu();
        JMenu ADD_MENU = new JMenu("Hozzáad");
        SET_THEME = new JMenu("Témák");
        SET_THEME.setIcon(new ImageIcon(this.getClass().getResource("IMG/paint16.png")));
        ADD_MENU.setIcon(new ImageIcon(this.getClass().getResource("IMG/plus16.png")));
        JMenuItem I_IN = new JMenuItem("Nagyítás");
        JMenuItem I_OUT = new JMenuItem("Kicsinyítés");
        JMenuItem I_PASTE = new JMenuItem("Beillesztés");

        JMenuItem PLUS_LOADER = new JMenuItem("Loader");
        PLUS_LOADER.setIcon(new ImageIcon(this.getClass().getResource("IMG/playblue16.png")));
        PLUS_LOADER.addActionListener(A_LOADER);
        JMenuItem PLUS_DOWNTIME = new JMenuItem("Állásidő");
        PLUS_DOWNTIME.setIcon(new ImageIcon(this.getClass().getResource("IMG/clock16.png")));
        PLUS_DOWNTIME.addActionListener(A_DOWNTIME);
        ADD_MENU.add(PLUS_LOADER);
        ADD_MENU.add(PLUS_DOWNTIME);

        JMenuItem MENU_TENY = new JMenuItem("Megvalósulás lekérdezése...");
        MENU_TENY.addActionListener(B_TENY_Action);

        // JMenuItem MENU_SEND = new JMenuItem("JSON send");
        // MENU_SEND.addActionListener(A_SEND_DATA);
        JMenuItem PROP = new JMenuItem("Beállítások");
        PROP.addActionListener(A_SP);
        PROP.setIcon(new ImageIcon(this.getClass().getResource("IMG/config16.png")));

        JMenuItem INF = new JMenuItem("Információs ablak");
        INF.addActionListener(B_INFO_Action);
        INF.setIcon(new ImageIcon(this.getClass().getResource("IMG/inf16.png")));

        POP.add(ADD_MENU);
        POP.add(I_IN);
        POP.add(I_OUT);
        POP.add(I_PASTE);
        POP.add(SET_THEME);
        POP.add(MENU_TENY);
        POP.add(PROP);
        POP.add(INF);
        //  POP.add(MENU_SEND);

        I_IN.addActionListener(Z_in);
        I_OUT.addActionListener(Z_out);
        I_PASTE.addActionListener(Paste);
        AUTO_ARRANGE.setLocation(new Point((168 * 5) + 2 * (BORDER) - 150, 5));
        AUTO_ARRANGE.setSize(130, 30);
        AUTO_ARRANGE.setOpaque(true);

        TENY_CALC = new JCheckBox("auto");
        TENY_CALC.setToolTipText("Automatikus újratervezés a megvalósulással...");
        TENY_CALC.setSelected(false);
        TENY_CALC.setSize(50, 20);

        TENY_CALC.setFocusable(false);
        TENY_CALC.setRequestFocusEnabled(false);

        if (PlNner.USER.id.equals("259797") || PlNner.USER.id.equals("266684")) {
            TENY_CALC.setVisible(true);
        } else {
            TENY_CALC.setVisible(false);
        }

        SEARCHER = new JTextField();

        SEARCHER.setToolTipText("Kereső");
        SEARCHER.setSize(200, 25);

        TENY_CALC.setOpaque(false);
        setOpaque(false);
        addMouseListener();
        setMouseAction();
        setLayout(null);

        TOP_PANEL = new JPanel() {

            @Override
            public void paint(Graphics g) {
                final Graphics2D graph = (Graphics2D) g;

                Color xDf = new Color(0.9f, 0.9f, 0.9f, 0.8f);

                SEARCHER.setLocation(TOP_PANEL.getWidth() - SEARCHER.getWidth() - 5, Toolbar_Y);

                TENY_CALC.setLocation(TOP_PANEL.getWidth() - TENY_CALC.getWidth() - 5, Toolbar_Y + 60);

                int count = 0;
                int count_DAY = 0;
                int count_MSZAK = 0;

                graph.setColor(TOP_COLOR);

                graph.fillRect(0, 0, TOP_PANEL.getWidth(), TOP_PANEL.getHeight());

                if (!SCROLLED) {
                    graph.setColor(FG_COLOR);
                } else {
                    graph.setColor(xDf);
                }

                Stroke oldStroke = graph.getStroke();

                int H_POS = TOP_PANEL.getHeight() - 40;

                float[] dash = {4f, 0f, 2f};
                BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);

                int sh = 280 + Toolbar_X;
                graph.setColor(FG_PROGRESS);
                double lepes = TOP_PANEL.getWidth() / 100;

                graph.drawString(progress_text, 13, 90);

                graph.fillRect(20, 100, (int) progress * (int) lepes, 2);

                if (!SCROLLED) {
                    graph.setColor(FG_COLOR);
                } else {
                    graph.setColor(xDf);
                }

                if (SW_INDICATOR) {
                    int min = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).now).getMinutes();
                    graph.drawImage(new ImageIcon(this.getClass().getResource("IMG/ind.png")).getImage(), (int) (_getBorder() + ((double) min / 60) * IND_HOUR) - new ImageIcon(this.getClass().getResource("IMG/ind.png")).getImage().getWidth(this) / 2, 135, this);
                    graph.setColor(Color.red);
                    graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), 110, (int) (_getBorder() + ((double) min / 60) * IND_HOUR), 140);
                }

                if (!SCROLLED) {
                    graph.setColor(FG_COLOR);
                } else {
                    graph.setColor(xDf);
                }

                //vonalzó
                String rem;
                for (int c = _getBorder(); c < Station.this.getWidth() - BORDER + 1; c++) {

                    if ((c - _getBorder()) % IND_HOUR == 0) {

                        switch (count) {
                            case 0:
                                graph.drawLine(c, H_POS, c, H_POS + IND_DAY_LENGTH);
                                break;
                            case 12:
                                //12 órás műszak
                                count_MSZAK++;
                                rem = new DecimalFormat("#.##").format(getRemaindedStationHourPShift(count_MSZAK)) + "ó";
                                graph.drawString(rem, c - (IND_HOUR * 6) - graph.getFontMetrics().stringWidth(rem) / 2, H_POS - 2);
                                graph.setColor(Color.blue);
                                graph.drawLine(c, H_POS, c, H_POS + IND_HOUR_LENGTH + 5);
                                if (!SCROLLED) {
                                    graph.setColor(FG_COLOR);
                                } else {
                                    graph.setColor(xDf);
                                }

                                graph.setStroke(bs);

                                graph.setColor(new Color(179, 232, 255));
                                graph.drawLine(c, H_POS + IND_HOUR_LENGTH + 5, c, getHeight());
                                if (!SCROLLED) {
                                    graph.setColor(FG_COLOR);
                                } else {
                                    graph.setColor(xDf);
                                }
                                graph.setStroke(oldStroke);

                                break;
                            case 24:
                                count_MSZAK++;
                                rem = new DecimalFormat("#.##").format(getRemaindedStationHourPShift(count_MSZAK)) + "ó";
                                graph.drawString(rem, c - (IND_HOUR * 6) - graph.getFontMetrics().stringWidth(rem) / 2, H_POS - 2);
                                graph.drawLine(c, H_POS, c, H_POS + IND_DAY_LENGTH);

                                graph.setStroke(bs);

                                graph.setColor(new Color(179, 232, 255));
                                graph.drawLine(c, H_POS + IND_HOUR_LENGTH + 5, c, getHeight());
                                if (!SCROLLED) {
                                    graph.setColor(FG_COLOR);
                                } else {
                                    graph.setColor(xDf);
                                }
                                graph.setStroke(oldStroke);

                                graph.drawString(DAY_NAMES[count_DAY], c - (IND_HOUR * 12) - graph.getFontMetrics().stringWidth(DAY_NAMES[count_DAY]) / 2, H_POS + 30);

                                count = 0;
                                count_DAY++;
                                break;

                            default:
                                graph.drawLine(c, H_POS, c, H_POS + IND_HOUR_LENGTH);

                        }
                        count++;

                    }

                }

                //vonalzó
                for (int r = 0; r < PRODUCTS.size(); r++) {

                    for (int p = 0; p < PRODUCTS.size(); p++) {
                        if (PRODUCTS.get(p).getNo() == r) {
                            if (p > 0) {

                                graph.drawLine(_getBorder(), _getTop() + 30 + (35 * p), getWidth() - _getBorder(), _getTop() + 30 + (35 * p));

                            }
                            if (PRODUCTS.get(p).isSelect()) {

                                for (int d = 0; d < JOBS.size(); d++) {
                                    if (JOBS.get(d).isThis(PRODUCTS.get(p))) {
                                        if (!SCROLLED) {
                                            graph.setColor(new Color(0.3f, 0.3f, 0.3f, 0.4f));
                                            graph.fillRoundRect(8, 100, getWidth() - 16, 55, 12, 12);
                                        }
                                        // graph.setColor(new Color(0.6f, 0.6f, 0.0f, 0.4f));
                                        //  graph.drawRoundRect(8, 100, getWidth() - 16, 55, 12, 12);

                                        int min = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, JOBS.get(d).getStart()).getMinutes();
                                        graph.setColor(SELECTED_COLOR);
                                        graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), 100, (int) (_getBorder() + ((double) min / 60) * IND_HOUR), PRODUCTS.get(p).getLocation().y);
                                        graph.setColor(BG_COLOR);

                                        int min2 = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, JOBS.get(d).getStop()).getMinutes();

                                        graph.setColor(SELECTED_COLOR);
                                        int minutes = Minutes.minutesBetween(JOBS.get(d).getStart(), JOBS.get(d).getStop()).getMinutes();
                                        double t = (double) minutes / 60;
                                        String vl = new DecimalFormat("#.##").format(t) + "ó";

                                        graph.drawString(vl, (_getBorder() + (int) ((min / 60) * IND_HOUR) + ((((min2 - min) / 60) * IND_HOUR) / 2)) - (graph.getFontMetrics().stringWidth(vl) / 2), 110);

                                        graph.setColor(SELECTED_COLOR);
                                        graph.drawLine((int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), 100, (int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), PRODUCTS.get(p).getLocation().y);

                                        //Távolság jelző indikátor
                                        graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), 113, (int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), 113);
                                        graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), 113, (int) (_getBorder() + ((double) min / 60) * IND_HOUR) + 4, 110);
                                        graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), 113, (int) (_getBorder() + ((double) min / 60) * IND_HOUR) + 4, 116);
                                        graph.drawLine((int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), 113, (int) (_getBorder() + ((double) min2 / 60) * IND_HOUR) - 4, 110);
                                        graph.drawLine((int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), 113, (int) (_getBorder() + ((double) min2 / 60) * IND_HOUR) - 4, 116);
                                        //Távolság jelző indikátor vége

                                        graph.setColor(BG_COLOR);

                                    }
                                }

                                if (!SCROLLED) {
                                    graph.setColor(FG_COLOR);
                                } else {
                                    graph.setColor(xDf);
                                }

                            }

                            if (!SCROLLED) {
                                graph.setColor(FG_COLOR);
                            } else {
                                graph.setColor(xDf);
                            }
                        }
                    }

                }

                if (((P_MOUSE_X > _getBorder()) && (P_MOUSE_X < getWidth() - _getBorder())) && ((P_MOUSE_Y > _getTop()) && (P_MOUSE_Y < _getTop() + 10))) {
                    graph.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
                    graph.drawRoundRect(_getBorder(), _getTop(), getWidth() - (_getBorder() * 2), 10, 0, 0);
                }

                if ((PRODUCTS.size() > 0) && (getSelectedProduct() > -1)) {

                    graph.drawString("Aktuális partnumber: " + PRODUCTS.get(getSelectedProduct()).getPartnumber(), Toolbar_X + 5, 45);
                    graph.drawString("Gyártandó mennyiség: " + PRODUCTS.get(getSelectedProduct()).getQty(), Toolbar_X + 5, 60);
                    graph.drawString("Gyártás kezdete: " + PlNner.getCurrentDateTime(PRODUCTS.get(getSelectedProduct()).getStartTime()), Toolbar_X + 5, 75);
                    graph.drawString("Gyártás vége   : " + PlNner.getCurrentDateTime(PRODUCTS.get(getSelectedProduct()).getEndTime()), Toolbar_X + 5, 90);

                }

                super.paint(graph);
            }

        };
        TOP_PANEL.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    POP.show(TOP_PANEL, e.getX(), e.getY());
                } else if (((P_MOUSE_X > _getBorder()) && (P_MOUSE_X < getWidth() - _getBorder())) && ((P_MOUSE_Y > _getTop()) && (P_MOUSE_Y < _getTop() + 10))) {
                    //    TOP_PANEL.setToolTipText(getPlan().weekdate.plusMinutes((P_MOUSE_X - _getBorder()) * (60/IND_HOUR)).toString());
                    //getPlan().weekdate.plusMinutes((P_MOUSE_X  - _getBorder()) * (60/IND_HOUR))
                    if (can_drag) {

                        TimeStamp ts = new TimeStamp(Station.this, P_MOUSE_X);
                        ts.add();
                        ts.setLocation(P_MOUSE_X - 10, _getTop() - 30);
                        TIMESTAMPES.add(ts);
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouse_over = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouse_over = false;
            }
        });

        TOP_PANEL.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                P_MOUSE_X = e.getX();
                P_MOUSE_Y = e.getY();
                //  int min = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate.plusMinutes(P_MOUSE_X * (60/IND_HOUR))).getMinutes();

                if (((P_MOUSE_X > _getBorder()) && (P_MOUSE_X < getWidth() - _getBorder())) && ((P_MOUSE_Y > _getTop()) && (P_MOUSE_Y < _getTop() + 10))) {
                    TOP_PANEL.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    // TOP_PANEL.setToolTipText(getPlan().weekdate.plusMinutes((P_MOUSE_X - _getBorder()) * (60/IND_HOUR)).toString());
                } else {
                    TOP_PANEL.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    //  TOP_PANEL.setToolTipText(null);
                }

            }
        });

        _Resize();
        TOP_PANEL.setOpaque(false);
        TOP_PANEL.setLayout(null);

        TB = new JToolBar();
        TB.setFloatable(false);
        TB.setBorderPainted(false);
        TB.setOpaque(false);

        //Termék hozzáadása
        JButton B_ADD = new JButton(new ImageIcon(this.getClass().getResource("IMG/add.png")));
        B_ADD.setToolTipText("Új termék hozzáadása");
        B_ADD.addActionListener(B_ADD_Action);
        B_ADD.setOpaque(false);
        TB.add(B_ADD);
        //Gyártás optimalizálás
        JButton B_SORT = new JButton(new ImageIcon(this.getClass().getResource("IMG/sort.png")));
        B_SORT.setToolTipText("Gyártás optimalizálás...");
        B_SORT.addActionListener(B_SORT_Action);
        B_SORT.setOpaque(false);
        TB.add(B_SORT);
        //Festés
        JButton B_PAINT = new JButton(new ImageIcon(this.getClass().getResource("IMG/brush16.png")));
        B_PAINT.setToolTipText("<HTML>Automatikus szín hozzárendelés, az összetartozó jobokhoz"
                + "<BR>"
                + "a [CTRL] gomb lenyomása mellett visszáll az alap színre."
                + "</HTML>");
        B_PAINT.addActionListener(B_COLOR_Action);
        B_PAINT.setOpaque(false);
        TB.add(B_PAINT);

        //Közelítés
        JButton B_ZOOM_IN = new JButton(new ImageIcon(this.getClass().getResource("IMG/zoom_in.png")));
        B_ZOOM_IN.setToolTipText("Közelítés");
        B_ZOOM_IN.addActionListener(Z_in);
        B_ZOOM_IN.setOpaque(false);
        TB.add(B_ZOOM_IN);

        //Távolítás
        JButton B_ZOOM_OUT = new JButton(new ImageIcon(this.getClass().getResource("IMG/zoom_out.png")));
        B_ZOOM_OUT.setToolTipText("Távolítás");
        B_ZOOM_OUT.addActionListener(Z_out);
        B_ZOOM_OUT.setOpaque(false);
        TB.add(B_ZOOM_OUT);

        //Riport
        JButton B_REPORT = new JButton(new ImageIcon(this.getClass().getResource("IMG/report16.png")));
        B_REPORT.setToolTipText("Job riport és analízis");
        B_REPORT.addActionListener(B_REPORT_Action);
        B_REPORT.setOpaque(false);
        TB.add(B_REPORT);

        //E-MAIL
        JButton B_MAIL = new JButton(new ImageIcon(this.getClass().getResource("IMG/email.png")));
        B_MAIL.setToolTipText("Levél küldése");
        B_MAIL.addActionListener(B_MAIL_Action);
        B_MAIL.setOpaque(false);
        TB.add(B_MAIL);

        //Loader
        JButton A_LOAD = new JButton(new ImageIcon(this.getClass().getResource("IMG/playblue.png")));
        A_LOAD.setToolTipText("Loader...");
        A_LOAD.addActionListener(A_LOADER);
        A_LOAD.setOpaque(false);
        TB.add(A_LOAD);

        //Play
        JButton B_PLAY = new JButton(new ImageIcon(this.getClass().getResource("IMG/play.png")));
        B_PLAY.setToolTipText("Tervezés");
        B_PLAY.addActionListener(B_PLAY_Action);
        B_PLAY.setOpaque(false);
        TB.add(B_PLAY);

        //Record
        JButton B_RECORD = new JButton(new ImageIcon(this.getClass().getResource("IMG/record.png")));
        B_RECORD.setToolTipText("Terv elkészítése, és közzététele...");
        B_RECORD.addActionListener(B_RECORD_Action);
        B_RECORD.setOpaque(false);
        TB.add(B_RECORD);

        //Állásidő
        JButton B_DOWN = new JButton(new ImageIcon(this.getClass().getResource("IMG/clock16.png")));
        B_DOWN.setToolTipText("Állásidő hozzáadása...");
        B_DOWN.addActionListener(A_DOWNTIME);
        B_DOWN.setOpaque(false);
        TB.add(B_DOWN);

        //Jegyzet
        JButton B_NOTE = new JButton(new ImageIcon(this.getClass().getResource("IMG/note16.png")));
        B_NOTE.setToolTipText("Állomáshoz fűződő megjegyzések tárolása");
        B_NOTE.addActionListener(B_NOTE_Action);
        B_NOTE.setOpaque(false);
        TB.add(B_NOTE);

        TB.setOpaque(false);
        TB.setSize(340, 25);
        TB.setLocation(Toolbar_X, Toolbar_Y);
        TOP_PANEL.add(TB);

        TOP_PANEL.add(TENY_CALC);

        SEARCHER.setBorder(BorderFactory.createEmptyBorder());

        SEARCHER.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SEARCHER.setBorder(BorderFactory.createLineBorder(new Color(0.3f, 0.3f, 0.3f, 0.3f)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SEARCHER.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        TOP_PANEL.add(SEARCHER);

        add(TOP_PANEL);

    }

    @Override
    public void paint(Graphics g) {

        JScrollPane pane = (JScrollPane) this.getParent().getParent().getParent();
        TOP_PANEL.setLocation(0, pane.getVerticalScrollBar().getValue() - 5);

        if ((pane.getVerticalScrollBar().getValue()) > 0) {
            SCROLLED = true;
            TOP_ALPHA = 0.8f;
            TOP_COLOR = new Color(0.3f, 0.3f, 0.3f, TOP_ALPHA);
            TOP_PANEL.repaint();

        } else {
            SCROLLED = false;
            TOP_ALPHA = 0.0f;
            TOP_COLOR = new Color(0.3f, 0.3f, 0.3f, TOP_ALPHA);
            TOP_PANEL.repaint();
        }

        inf_x = getLocation().x;
        inf_y = pane.getVerticalScrollBar().getValue() + TOP_PANEL.getHeight();

        for (int i = 0; i < DOWNTIMES.size(); i++) {
            try {
                this.setComponentZOrder(DOWNTIMES.get(i), 0);
                DOWNTIMES.get(i).setLocation(DOWNTIMES.get(i).getLocation().x, 125 + pane.getVerticalScrollBar().getValue());
            } catch (IllegalArgumentException er) {

            }
        }

        Toolbar_X = pane.getHorizontalScrollBar().getValue();

        final Graphics2D graph = (Graphics2D) g;

        SEARCHER.setBackground(new Color(1f, 1f, 1f, 0.5f));

        graph.drawImage(imgsh.getImage(), SEARCHER.getLocation().x + 5, SEARCHER.getLocation().y - 3 + pane.getVerticalScrollBar().getValue(), this); //kalkuláció futtatása

        TB.setLocation(Toolbar_X, Toolbar_Y);

        IND_HOUR = (Station.this.getWidth() - (_getBorder() * 2)) / 168;
        int count = 0;
        int count_DAY = 0;
        int count_MSZAK = 0;

        Stroke oldStroke = graph.getStroke();
        float[] dash = {4f, 0f, 2f};
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);

        graph.setFont(new Font(graph.getFont().getName(), Font.PLAIN, 10));
        graph.setColor(new Color(0, 0, 0, 0.0f));
        graph.fillRect(0, 0, Station.this.getWidth(), Station.this.getHeight());
        // graph.drawImage(new ImageIcon(this.getClass().getResource("IMG/bg.jpg")).getImage(),0,0, this); //kalkuláció futtatása
        graph.setColor(FG_COLOR);

        for (p_r = 0; p_r < PRODUCTS.size(); p_r++) {

            for (p_p = 0; p_p < PRODUCTS.size(); p_p++) {
                if (PRODUCTS.get(p_p).getNo() == p_r) {
                    if (p_p > 0) {

                        graph.drawLine(_getBorder(), _getTop() + 30 + (35 * p_p), getWidth() - _getBorder(), _getTop() + 30 + (35 * p_p));

                    }

                    if (PRODUCTS.get(p_p).isSelect()) {

                        graph.setColor(new Color(0.3f, 0.3f, 0.3f, 0.4f));
                        graph.fillRoundRect(8, PRODUCTS.get(p_p).getLocation().y - 5, getWidth() - 16, PRODUCTS.get(p_p).getHeight() + 10, 8, 8);
                        //   graph.setColor(new Color(0.6f, 0.6f, 0.0f, 0.7f));
                        //   graph.drawRoundRect(8, TOP_PANEL.getLocation().y + TOP_PANEL.getHeight(), getWidth() - 16, (PRODUCTS.get(p).getLocation().y + PRODUCTS.get(p).getHeight()) +10 - (TOP_PANEL.getLocation().y + TOP_PANEL.getHeight()), 0, 0);

                        if (SCROLLED) {
                            //     graph.setColor(new Color(0.3f, 0.3f, 0.3f, 0.7f));
                            //     graph.fillRoundRect(8, TOP_PANEL.getLocation().y + TOP_PANEL.getHeight(), getWidth() - 16, (PRODUCTS.get(p).getLocation().y + PRODUCTS.get(p).getHeight())+10 - (TOP_PANEL.getLocation().y + TOP_PANEL.getHeight()), 0, 0);
                            //     graph.setColor(new Color(0.6f, 0.6f, 0.0f, 0.7f));
                            //     graph.drawRoundRect(8, TOP_PANEL.getLocation().y + TOP_PANEL.getHeight(), getWidth() - 16, (PRODUCTS.get(p).getLocation().y + PRODUCTS.get(p).getHeight())+10 - (TOP_PANEL.getLocation().y + TOP_PANEL.getHeight()), 0, 0);

                        }
                        //  graph.setColor(new Color(0.6f,0.6f,0.0f,0.7f));
                        //   graph.drawRoundRect(8, 100, getWidth()-16, 150,12,12);

                        for (int d = 0; d < JOBS.size(); d++) {
                            if (JOBS.get(d).isThis(PRODUCTS.get(p_p))) {

                                int min = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, JOBS.get(d).getStart()).getMinutes();
                                graph.setColor(SELECTED_COLOR);
                                graph.drawLine((int) (_getBorder() + ((double) min / 60) * IND_HOUR), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight(), (int) (_getBorder() + ((double) min / 60) * IND_HOUR), PRODUCTS.get(p_p).getLocation().y);

                                if (can_drag) {
                                    graph.drawLine((int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx - 3)), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight() - 15, (int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx)), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight() - 20);
                                    graph.drawLine((int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx + 3)), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight() - 15, (int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx)), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight() - 20);
                                    graph.drawLine((int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx)), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight() - 20, (int) (((double) PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).mx)), PRODUCTS.get(p_p).getLocation().y);
                                }
                                //graph.setColor(BG_COLOR);

                                int min2 = Minutes.minutesBetween(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate, JOBS.get(d).getStop()).getMinutes();

                                graph.setColor(SELECTED_COLOR);
                                int minutes = Minutes.minutesBetween(JOBS.get(d).getStart(), JOBS.get(d).getStop()).getMinutes();
                                double t = (double) minutes / 60;
//                                String vl = new DecimalFormat("#.##").format(t) + "ó";
                                // graph.drawString(vl, (int) (_getBorder() + ((double) (min + ((min2 - min) / 2) - graph.getFontMetrics().stringWidth(vl)) / 60) * IND_HOUR), 110);

                                graph.drawLine((int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), TOP_PANEL.getLocation().y + TOP_PANEL.getHeight(), (int) (_getBorder() + ((double) min2 / 60) * IND_HOUR), PRODUCTS.get(p_p).getLocation().y);

                            }
                        }

                        //graph.setColor(FG_COLOR);
                    } else {
                        graph.setColor(HALF_VISIBLE);
                    }

                    PRODUCTS.get(p_p).setAlpha(products_alpha);
                    PRODUCTS.get(p_p).setVisible(true);

                    if (isThisSearch(PRODUCTS.get(p_p))) {
                        if (SEARCHER.getText().length() > 0) {
                            graph.drawImage(imghelp.getImage(), Station.this.getWidth() - Station.this._getBorder() - 40, Station.this._getTop() + 32 + (35 * (p_r)), Station.this); //kalkuláció futtatása
                            PRODUCTS.get(p_p).setVisible(true);
                            // PRODUCTS.get(p).repaint();
                        }
                    } else if (SEARCHER.getText().length() > 0) {
                        PRODUCTS.get(p_p).setVisible(false);
                        //  PRODUCTS.get(p).repaint();
                    }

                    if (PRODUCTS.get(p_p).planner_comment != null) {
                        if (PRODUCTS.get(p_p).planner_comment.length() > 0) {
                            graph.drawImage(imginf.getImage(), getWidth() - _getBorder() - 60 - 40, _getTop() + 34 + (35 * (p_r)), this);
                        }
                    }

                    if (PRODUCTS.get(p_p).RELEASE) {
                        graph.drawImage(imgeye.getImage(), getWidth() - _getBorder() - 60, _getTop() + 32 + (35 * (p_r)), this); //kalkuláció futtatása

                    } else {
                        graph.drawImage(imgeyec.getImage(), getWidth() - _getBorder() - 60, _getTop() + 32 + (35 * (p_r)), this); //kalkuláció futtatása

                    }

                    if (PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).getWidth() > getWidth() - _getBorder()) {
                        if (PRODUCTS.get(p_p).getLocation().x - 20 > getWidth() - 30) {
                            graph.drawImage(imgwarning.getImage(), getWidth() - 35, _getTop() + 32 + (35 * (p_r)), this); //kalkuláció futtatása
                        } else {
                            graph.drawImage(imgwarning.getImage(), PRODUCTS.get(p_p).getLocation().x - 35, _getTop() + 32 + (35 * (p_r)), this); //kalkuláció futtatása

                        }
                    }

                    if (isKitted(PRODUCTS.get(p_p).getJobnumber())) {
                        graph.drawImage(imgkitted.getImage(), getWidth() - _getBorder() - 60 - 20, _getTop() + 32 + (35 * (p_r)), this); //kalkuláció futtatása

                    }

                    if (isEngineering(PRODUCTS.get(p_p).getJobnumber())) {
                        graph.drawImage(imgeng.getImage(), PRODUCTS.get(p_p).getLocation().x + PRODUCTS.get(p_p).getWidth() + 5, _getTop() + 35 + (35 * (p_r)), this); //kalkuláció futtatása

                    }

                    String JOB = "";
                    String ID = "";
                    String PN = "";
                    String SEQ = "";

                    if (SW_JOB_NO) {
                        JOB = "  JOB: " + PRODUCTS.get(p_p).getJobnumber();
                    }
                    if (SW_ID) {
                        ID = "  ID: " + PRODUCTS.get(p_p).getID();
                    }
                    if (SW_PN_NO) {
                        PN = "  PN : " + PRODUCTS.get(p_p).getPartnumber();
                    }
                    if (SW_SEQ) {
                        SEQ = "  SEQ: " + PlNner.SEQUENCES[PRODUCTS.get(p_p).getSequence()];
                    }

                    graph.drawString(JOB + ID, _getBorder(), _getTop() + 16 + (35 * (p_r + 1)));
                    graph.drawString(PN + SEQ, _getBorder(), _getTop() + 28 + (35 * (p_r + 1)));

                    if (SW_COMMENT) {
                        graph.drawString("Komment : " + PRODUCTS.get(p_p).getComment(), getWidth() - (_getBorder() + graph.getFontMetrics().stringWidth("Komment: " + PRODUCTS.get(p_p).getComment())), _getTop() + 28 + (35 * (p_r + 1)));
                    }

                    graph.setFont(new Font(graph.getFont().getName(), Font.PLAIN, 13));
                    if (SW_FAMILY) {
                        graph.drawString(PRODUCTS.get(p_p).getPOutSideCustomer() + "   " + PRODUCTS.get(p_p).getProjectName(), getWidth() / 2, _getTop() + 9 + (35 * (p_r + 1)));
                        graph.drawLine(getWidth() / 2, _getTop() + 11 + (35 * (p_r + 1)), getWidth() / 2 + graph.getFontMetrics().stringWidth(PRODUCTS.get(p_p).getPOutSideCustomer() + "   " + PRODUCTS.get(p_p).getProjectName()), _getTop() + 11 + (35 * (p_r + 1)));
                    }

                    if (SW_JOBINFO) {
                        graph.drawString(PRODUCTS.get(p_p).getJobInfo(), getWidth() / 2, _getTop() + 25 + (35 * (p_r + 1)));
                    }
                    graph.setFont(new Font(graph.getFont().getName(), Font.PLAIN, 10));
                    //Progress bar
                    if (SW_STATUS_BAR) {
                        int X_ = getWidth() - 220;
                        graph.setColor(BG_PROGRESS);

                        graph.fillRect(X_, _getTop() + 7 + (35 * (p_r + 1)), 102, 10);
                        if ((int) PRODUCTS.get(p_p).getPercent() == 100) {
                            graph.setColor(Color.green);
                        } else {
                            graph.setColor(Color.red);
                        }

                        if (PRODUCTS.get(p_p).getQty() < PRODUCTS.get(p_p).getSumTenyMSzak()) {
                            graph.setColor(Color.blue);
                        }

                        graph.fillRect(X_ + 1, _getTop() + 7 + (35 * (p_r + 1)) + 1, (int) PRODUCTS.get(p_p).getPercent(), 8);
                        graph.setColor(FG_PROGRESS);
                        graph.drawString(Integer.toString((int) PRODUCTS.get(p_p).getPercent()) + "%", (X_ + 50) - (graph.getFontMetrics().stringWidth(Integer.toString((int) PRODUCTS.get(p_p).getPercent()) + "%") / 2), _getTop() + 15 + (35 * (p_r + 1)));
                    }

                    //Progress bar
                    graph.setColor(FG_COLOR);

                    if (PRODUCTS.get(p_p).isSelect()) {
                        //mini tooltip
                        double value = (double) (PRODUCTS.get(p_p).getQty() / PRODUCTS.get(p_p).getQtyPHour()) + (double) ((1 / 60) * (double) (PRODUCTS.get(p_p).getStartUpMin()));
                        String mess = new DecimalFormat("##.#").format(value) + " óra";
                        int x_op = PRODUCTS.get(p_p).getLocation().x - (graph.getFontMetrics().stringWidth(mess) + 10);
                        int y_op = PRODUCTS.get(p_p).getLocation().y + 12;

                        if (x_op < 0) {
                            x_op = PRODUCTS.get(p_p).getLocation().x + (graph.getFontMetrics().stringWidth(mess) + 10);
                        }

                        graph.setColor(new Color(0, 0, 0, 0.5f));
                        //   graph.drawLine(x_op, y_op, x_op -(  graph.getFontMetrics().stringWidth(mess) + 10),y_op );
                        graph.fillRoundRect(x_op - 5, y_op - 10, graph.getFontMetrics().stringWidth(mess) + 10, 12, 3, 3);
                        graph.setColor(Color.white);
                        graph.drawString(mess, x_op, y_op);
                        graph.setColor(FG_COLOR);
                        //mini tooltip
                    }

                }
            }

        }
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (SCROLL_UP) {
            graph.drawImage(imgup.getImage(), (getWidth() / 2) - 75, (dim.height / 2) - 75 + pane.getVerticalScrollBar().getValue() - 200, this); //kalkuláció futtatása

        } else if (SCROLL_DOWN) {
            graph.drawImage(imgdown.getImage(), (getWidth() / 2) - 75, (dim.height / 2) + 75 + pane.getVerticalScrollBar().getValue() - 200, this); //kalkuláció futtatása

        } else if (!SCROLL_DOWN && !SCROLL_UP && drag) {
            graph.drawImage(imgstops.getImage(), (getWidth() / 2) - 75, (dim.height / 2) + pane.getVerticalScrollBar().getValue() - 200, this); //kalkuláció futtatása

        }

        super.paint(graph);
    }

    public double getRemaindedStationHourPShift(int shift) {
        double back = 0.0;
        for (int i = ((shift - 1) * 12) * 60; i < shift * 12 * 60; i++) {
            back += STATION_MINUTES[i];
        }
        return back / 60;

    }

    public void setSelected(boolean sel) {

        selected = sel;
    }

    public boolean getSelected() {
        return selected;
    }

    public String getName() {
        return name;
    }

    public int _getBorder() {
        return BORDER;
    }

    public int _getTop() {
        return TOP_BORDER;
    }

    public int getZ_Level() {
        return Z_LEVEL;
    }

    public void deleteProductWithoutConfirmation() {

        Component[] comps = getComponents();
        for (Component cmp : comps) {
            try {
                Product pr = (Product) cmp;
                if (pr.equals(PRODUCTS.get(getSelectedProduct()))) {
                    remove(cmp);
                }
            } catch (ClassCastException ex) {

            }
        }

        PRODUCTS.remove(getSelectedProduct());
        sort();
        setVSize();
        repaint();

    }

    public void deleteProduct() {
        int res = JOptionPane.showConfirmDialog(null, "Biztos vagy benne hogy törölni szeretnéd a " + PRODUCTS.get(getSelectedProduct()).getPartnumber(), "Figyelem!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (res == JOptionPane.YES_OPTION) {
            Component[] comps = getComponents();
            for (Component cmp : comps) {
                try {
                    Product pr = (Product) cmp;
                    if (pr.equals(PRODUCTS.get(getSelectedProduct()))) {
                        remove(cmp);
                    }
                } catch (ClassCastException ex) {

                }
            }

            PRODUCTS.remove(getSelectedProduct());
            sort();
            setVSize();
            repaint();
        }

    }

    public void sendMail() {

        String message = "<html><head>TERV<style type=\"\"text/css\"\">table { width: 100%;  background-color: #f1f1c1;}</style><link rel=\"\"StyleSheet\"\" href=\"\"http://balch.org/hdoc/excel.css\"\" TYPE=\"\"text/css\"\"></head><body>";
        message += TableInHtml + "</body></html>";

        EMAIL mail = new EMAIL();
        mail.setFrom("krisztian.csekme@sanmina.com");
        mail.setTo("krisztian.csekme1@sanmina.com");
        mail.setSubject("Terv 2.0");
        mail.setMessage(message);
        mail.send();
    }

    public void optimizing(int index) {

        sort();
        String[] list = new String[PRODUCTS.size()];
        for (int i = 0; i < list.length; i++) {
            for (int p = 0; p < PRODUCTS.size(); p++) {
                if (PRODUCTS.get(p).getNo() == i) {
                    list[i] = Integer.toString(i) + ", [" + PRODUCTS.get(p).getJobnumber() + "] -> " + PRODUCTS.get(p).getPartnumber() + " - " + PlNner.SEQUENCES[PRODUCTS.get(p).getSequence()];
                }
            }
        }

        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).RELEASE) {
                PRODUCTS.get(i).storedProduceMin = false;
                PRODUCTS.get(i).resize();
            }
        }
        int last = 0;
        for (int i = 0; i < PRODUCTS.size(); i++) {
            for (int p = 0; p < PRODUCTS.size(); p++) {
                if (PRODUCTS.get(p).getNo() == i) {
                    if (PRODUCTS.get(p).RELEASE) {

                        if (i >= index) {
                            if (index == i) {
                                last = PRODUCTS.get(p).getSize().width + PRODUCTS.get(p).getLocation().x;
                            } else {
                                PRODUCTS.get(p).setLocation(last, PRODUCTS.get(p).getLocation().y);
                                last = PRODUCTS.get(p).getSize().width + PRODUCTS.get(p).getLocation().x;
                            }
                        }

                    }
                }

            }
        }
        for (int i = 0; i < PRODUCTS.size(); i++) {
            if (PRODUCTS.get(i).RELEASE) {
                PRODUCTS.get(i).storedProduceMin = false;
                PRODUCTS.get(i).resize();
            }
        }

    }

    public void optimizing() {
        int index = 0;
        sort();
        String[] list = new String[PRODUCTS.size()];
        for (int i = 0; i < list.length; i++) {
            for (int p = 0; p < PRODUCTS.size(); p++) {
                if (PRODUCTS.get(p).getNo() == i) {
                    list[i] = Integer.toString(i) + ", [" + PRODUCTS.get(p).getJobnumber() + "] -> " + PRODUCTS.get(p).getPartnumber() + " - " + PlNner.SEQUENCES[PRODUCTS.get(p).getSequence()];
                }
            }
        }

        Object res = JOptionPane.showInputDialog(PlNner.MF, "Kérem válassza ki az optimalizáció kezdetét:", "Optimalizáció...", JOptionPane.QUESTION_MESSAGE, null, list, "");

        if (res != null) {

            String t = res.toString();
            String[] tmp = t.split(",");
            index = Integer.parseInt(tmp[0]);

            for (int i = 0; i < PRODUCTS.size(); i++) {
                if (PRODUCTS.get(i).RELEASE) {
                    PRODUCTS.get(i).storedProduceMin = false;
                    PRODUCTS.get(i).resize();
                }
            }
            int last = 0;
            for (int i = 0; i < PRODUCTS.size(); i++) {
                for (int p = 0; p < PRODUCTS.size(); p++) {
                    if (PRODUCTS.get(p).getNo() == i) {
                        if (PRODUCTS.get(p).RELEASE) {

                            if (i >= index) {
                                if (index == i) {
                                    last = PRODUCTS.get(p).getSize().width + PRODUCTS.get(p).getLocation().x;
                                } else {
                                    PRODUCTS.get(p).setLocation(last, PRODUCTS.get(p).getLocation().y);
                                    last = PRODUCTS.get(p).getSize().width + PRODUCTS.get(p).getLocation().x;
                                }
                            }

                        }
                    }

                }
            }
            for (int i = 0; i < PRODUCTS.size(); i++) {
                if (PRODUCTS.get(i).RELEASE) {
                    PRODUCTS.get(i).storedProduceMin = false;
                    PRODUCTS.get(i).resize();
                }
            }
        }
    }

    private void setMouseAction() {
        @SuppressWarnings("unused")
        final Station handle = Station.this;
        handle.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PLANS.get(MainForm.TOP.getSelectedIndex()).clearSelection();
                handle.selected = true;

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    POP.show(handle, e.getX(), e.getY());
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Station handle = Station.this;

                // System.out.println(handle.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().toString());
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        handle.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                if (e.getWheelRotation() < 0) {
                    if (can_drag) {
                        Z_in.actionPerformed(null);
                    }
                } else if (can_drag) {
                    Z_out.actionPerformed(null);
                }

            }
        });

    }

    private void addMouseListener() {
        final Station handle = Station.this;
        handle.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //Egér mozgatás
                clearSelection();

            }
        }
        );
    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final Station handle = Station.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();

            }

            @Override
            public void mouseDragged(MouseEvent me) {
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                handle.setLocation(me.getX() - anchorPoint.x, me.getY() - anchorPoint.y);
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

        });
    }

    ActionListener B_REPORT_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            PlNner.JRW.init(Station.this);

            PlNner.JRW.setVisible(true);

        }
    };

    ActionListener B_RECORD_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            int rs = JOptionPane.showConfirmDialog(PlNner.MF, "Biztos benne hogy szeretné a tervet közzétenni?", "Figyelem!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (rs == JOptionPane.YES_OPTION) {
                export_to_mysql = true;
                calc();

            }
        }
    };

    ActionListener B_PLAY_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            calc();
        }
    };

    ActionListener B_COLOR_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            reColor();

        }
    };

    ActionListener B_ADD_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            PlNner.NEW_PROD.init();
            PlNner.NEW_PROD.setVisible(true);
        }
    };

    ActionListener B_NOTE_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            PlNner.NOTE.setVisible(Station.this);
        }
    };

    public static String convertStreamToString(InputStream is) {
        if (is == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(2048); // Define a size if you have an idea of it.
        char[] read = new char[128]; // Your buffer size.
        try (InputStreamReader ir = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            for (int i; -1 != (i = ir.read(read)); sb.append(read, 0, i));
        } catch (Throwable t) {
        }
        return sb.toString();
    }

    ActionListener A_SEND_DATA = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            boolean send = false;
            ArrayList<Object[]> l_ = new ArrayList<>();

            for (int i = 0; i < PRODUCTS.size(); i++) {
                for (int p = 0; p < PRODUCTS.size(); p++) {
                    if (PRODUCTS.get(p).getNo() == i) {
                        if (PRODUCTS.get(p).RELEASE) {
                            if (PRODUCTS.get(p).on_change) {
                                send = true;
                            }
                            if (send) {
                                Object[] r_ = new Object[5];

                                r_[0] = formattedSQLDate(PRODUCTS.get(p).getStartTime());
                                r_[1] = formattedSQLDate(PRODUCTS.get(p).getEndTime());
                                r_[2] = getName();
                                r_[3] = "gyártás";
                                if (isEngineering(PRODUCTS.get(p).getJobnumber())) {
                                    r_[4] = "mérnöki";
                                } else {
                                    r_[4] = "normál gyártás";
                                }

                                l_.add(r_);
                            }
                        }

                    }
                }
            }

            for (int i = 0; i < DOWNTIMES.size(); i++) {

                Object[] r_ = new Object[5];
                r_[0] = formattedSQLDate(DOWNTIMES.get(i).StartTime);
                r_[1] = formattedSQLDate(DOWNTIMES.get(i).EndTime);
                r_[2] = getName();
                r_[3] = DOWNTIMES.get(i).getName();
                l_.add(r_);
            }

            JSONArray json = new JSONArray(l_);

            try {
//                URL url = new URL("http://143.116.140.117/api/se_gw.php?plan=" + json.toString());
//
//                URLConnection myURLConnection = url.openConnection();
//                myURLConnection.connect();

                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("http://143.116.140.120/api/se_gw.php");
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("set", json.toString()));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                //Execute and get the response.
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    System.out.println(convertStreamToString(instream));

                    try {

                    } finally {
                        instream.close();
                    }
                }

            } catch (MalformedURLException ex) {
                System.out.println(ex.getMessage().toString());
            } catch (IOException ex) {
                Logger.getLogger(Station.class.getName()).log(Level.SEVERE, null, ex);
            }

            //   System.out.println("\nJSON:\n--------------------");
            //   System.out.println("http://143.116.140.117/api/se_gw.php?plan=" + json.toString());
            //   System.out.println("\n-----------------");
        }
    };

    ActionListener A_LOADER = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            PlNner.LOADER.init(Station.this);
            PlNner.LOADER.setVisible(true);

        }
    };

    ActionListener A_DOWNTIME = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            PlNner.DOWN_TIME_WINDOW.init(Station.this);
            PlNner.DOWN_TIME_WINDOW.setVisible(true);

        }
    };

    ActionListener A_SET_LIGHT = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            BG_COLOR = Color.WHITE;
            FG_COLOR = Color.BLACK;
            HALF_VISIBLE = Color.LIGHT_GRAY;
            SELECTED_COLOR = Color.BLUE;
            BG_PROGRESS = Color.lightGray;
            FG_PROGRESS = Color.white;
            TENY_CALC.setForeground(FG_COLOR);
            JPanel panel = (JPanel) Station.this.getParent();
            JScrollPane pane = (JScrollPane) Station.this.getParent().getParent().getParent();
            pane.setBackground(BG_COLOR);
            panel.setBackground(BG_COLOR);
            repaint();

        }
    };

    ActionListener A_SET_DARK = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            BG_COLOR = new Color(150, 150, 150);
            FG_COLOR = new Color(255, 255, 255);
            HALF_VISIBLE = new Color(255, 255, 153);
            SELECTED_COLOR = new Color(255, 255, 26);
            BG_PROGRESS = Color.lightGray;
            FG_PROGRESS = Color.white;
            TENY_CALC.setForeground(FG_COLOR);
            JPanel panel = (JPanel) Station.this.getParent();
            JScrollPane pane = (JScrollPane) Station.this.getParent().getParent().getParent();
            pane.setBackground(BG_COLOR);
            panel.setBackground(BG_COLOR);
            repaint();

        }
    };

    ActionListener A_SP = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            PlNner.SProp.setVisible(Station.this);
        }
    };

    public void inicializate() {
        TENY_CALC.setForeground(FG_COLOR);
        JPanel panel = (JPanel) Station.this.getParent();
        JScrollPane pane = (JScrollPane) Station.this.getParent().getParent().getParent();
        pane.setBackground(BG_COLOR);
        panel.setBackground(BG_COLOR);
        repaint();
    }

    ActionListener B_TENY_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            Runnable RN = new Runnable() {

                @Override
                public void run() {
                    calcResult_2();
                }
            };

            if (!teny_szamol) {
                Thread t = new Thread(RN);
                t.start();
            }

        }
    };
    ActionListener B_INFO_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            PlNner.SIB.init(Station.this);
            PlNner.SIB.setVisible(true);
        }
    };

    ActionListener B_MAIL_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            PlNner.MS.SUBJECT.setText("Terv változás történt a(z) " + PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getName() + " tervben");
            PlNner.MS.MESSAGE.setText("Figyelem változás történt az alábbi tervben: " + PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getName() + "!");
            PlNner.MS.setVisible(true, Station.this);

        }
    };

    ActionListener B_SORT_Action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            sort();
            optimizing();
            //calc();
        }
    };

}
