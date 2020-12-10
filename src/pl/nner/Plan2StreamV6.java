/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author krisztian_csekme1
 */
public class Plan2StreamV6 implements Serializable {

    private String name;
    private int week;
     
    List<station> stations = new ArrayList<>();
    

    // public static void addStation(Plan plan, String name, Dimension dim, Point location){
    public class station implements Serializable {

        List<pn> partnumbers = new ArrayList<>();
        List<downtime> downtimes = new ArrayList<>();
        List<timestamp> timestamps = new ArrayList<>();
        public String name;
        public int runcode;
        public int station_id;
        public int width, height;
        public int x, y;
        public String comment;
        public Object[][] loader;
        
        public ImageIcon BG_IMAGE=null;
        public int img_pos=0;
        public boolean isPlanA;
        public float products_alpha;
        public int products_onwrite_string;

        public boolean SW_STATUS_BAR;
        public boolean SW_INDICATOR;
        public boolean SW_COMMENT;
        public boolean SW_FAMILY;
        public boolean SW_JOBINFO;

        public boolean SW_JOB_NO;
        public boolean SW_PN_NO;
        public boolean SW_SEQ;
        public boolean SW_ID;

        public Color BG_COLOR;
        public Color SELECTED_COLOR;
        public Color FG_COLOR;
        public Color HALF_VISIBLE;
        public Color BG_PROGRESS;
        public Color FG_PROGRESS;
        public String[] STATION_MINUTES;

    }

    public class timestamp implements Serializable {
        
        public int x,y;
        public String product_id;
        
    }
    
    public class downtime implements Serializable {

        public Color bg;
        public Color fg;
        public String name;
        public int width, height;
        public int length;
        public int x, y;

    }

    public class pn implements Serializable {

        public String pn;
        public double qty;
        public int panel;
        public double cycle;
        public int eff;
        public String job;
        public int x, y;
        public int StartUpMin, DownMin;
        public int seq;
        public Color fg;
        public Color bg;
        public String ID;
        public String comment;
        public Boolean release;

        public Boolean freeze;
        public String planner_comment;
        public String history;
        public int original_qty;
        String[] MINUTES;
        String[] QTYS;
        String[] QTYS_TENY;
        String[] QTYS_TENY_MSZAK;
        String[] QTYS_TENY_TIME;

    }

    public Plan getPlan() {
        Plan plan = new Plan();
        
        plan.setName(getName());
       // plan.setWeekIndex(getWeekIndex());

        for (int i = 0; i < stations.size(); i++) {
            Station station_temp = new Station(stations.get(i).name);
             
            station_temp.BG_IMAGE = stations.get(i).BG_IMAGE;
            station_temp.img_pos = stations.get(i).img_pos;
            station_temp.products_alpha = stations.get(i).products_alpha;
            station_temp.products_onwrite_string = stations.get(i).products_onwrite_string;
            station_temp.isPlanA = stations.get(i).isPlanA;
            station_temp.runcode = stations.get(i).runcode;
            station_temp.station_id = stations.get(i).station_id;
            station_temp.loader = stations.get(i).loader;
            station_temp.setComment(stations.get(i).comment);

            station_temp.SW_STATUS_BAR = stations.get(i).SW_STATUS_BAR;
            station_temp.SW_INDICATOR = stations.get(i).SW_INDICATOR;
            station_temp.SW_COMMENT = stations.get(i).SW_COMMENT;
            station_temp.SW_FAMILY = stations.get(i).SW_FAMILY;
            station_temp.SW_JOBINFO = stations.get(i).SW_JOBINFO;
            station_temp.BG_COLOR = stations.get(i).BG_COLOR;
            station_temp.SELECTED_COLOR = stations.get(i).SELECTED_COLOR;
            station_temp.FG_COLOR = stations.get(i).FG_COLOR;
            station_temp.HALF_VISIBLE = stations.get(i).HALF_VISIBLE;
            station_temp.BG_PROGRESS = stations.get(i).BG_PROGRESS;
            station_temp.FG_PROGRESS = stations.get(i).FG_PROGRESS;

            for (int x = 0; x < station_temp.STATION_MINUTES.length; x++) {
                if (stations.get(i).STATION_MINUTES[x] != null) {
                    station_temp.STATION_MINUTES[x] = Double.parseDouble(stations.get(i).STATION_MINUTES[x]);
                } else {
                    station_temp.STATION_MINUTES[x] = 0;
                }
            }

            station_temp.SW_JOB_NO = stations.get(i).SW_JOB_NO;
            station_temp.SW_PN_NO = stations.get(i).SW_PN_NO;
            station_temp.SW_SEQ = stations.get(i).SW_SEQ;
            station_temp.SW_ID = stations.get(i).SW_ID;

            station_temp.station_id = stations.get(i).station_id;
            plan.STATIONS.add(station_temp);

            //MainForm.addStation(plan, station_temp, stations.get(i).station_id, new Dimension(stations.get(i).width, stations.get(i).height), new Point(stations.get(i).x, stations.get(i).y));
            for (int p = 0; p < stations.get(i).partnumbers.size(); p++) {

                Product prod = new Product(plan.weekdate);
                prod.setPartNumber(stations.get(i).partnumbers.get(p).pn);
                prod.setQty(stations.get(i).partnumbers.get(p).qty);
                prod.setPanelization(stations.get(i).partnumbers.get(p).panel);
                prod.setCycle(stations.get(i).partnumbers.get(p).cycle);
                prod.setEff(stations.get(i).partnumbers.get(p).eff);
                prod.setJobnumber(stations.get(i).partnumbers.get(p).job);
                prod.setStartUpMin(stations.get(i).partnumbers.get(p).StartUpMin);
                prod.setDownTimeMin(stations.get(i).partnumbers.get(p).DownMin);
                prod.setSequence(stations.get(i).partnumbers.get(p).seq);
                prod.setFGColor(stations.get(i).partnumbers.get(p).fg);
                prod.setBGColor(stations.get(i).partnumbers.get(p).bg);
                prod.setID(stations.get(i).partnumbers.get(p).ID);
                prod.setComment(stations.get(i).partnumbers.get(p).comment);
                prod.RELEASE = stations.get(i).partnumbers.get(p).release;
                //station_temp.addProduct(prod);

                prod.freeze = stations.get(i).partnumbers.get(p).freeze;
                prod.planner_comment = stations.get(i).partnumbers.get(p).planner_comment;
                prod.history = stations.get(i).partnumbers.get(p).history;
                prod.original_qty = stations.get(i).partnumbers.get(p).original_qty;
                for (int x = 0; x < prod.MINUTES.length; x++) {
                    if (stations.get(i).partnumbers.get(p).MINUTES[x] != null) {
                        prod.MINUTES[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).MINUTES[x]);
                    } else {
                        prod.MINUTES[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS[x] != null) {
                        prod.QTYS[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS[x]);
                    } else {
                        prod.QTYS[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS_TENY.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY[x] != null) {
                        prod.QTYS_TENY[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY[x]);
                    } else {
                        prod.QTYS_TENY[x] = 0;
                    }
                }
                for (int x = 0; x < prod.QTYS_TENY_MSZAK.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY_MSZAK[x] != null) {
                        prod.QTYS_TENY_MSZAK[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY_MSZAK[x]);
                    } else {
                        prod.QTYS_TENY_MSZAK[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS_TENY_TIME.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY_TIME[x] != null) {
                        prod.QTYS_TENY_TIME[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY_TIME[x]);
                    } else {
                        prod.QTYS_TENY_TIME[x] = 0;
                    }
                }

                station_temp.addProductWithLocation(prod, stations.get(i).partnumbers.get(p).x, stations.get(i).partnumbers.get(p).y);

                station_temp.setSelected(true);

                // station_temp.sort();
                prod.resize();

            }
            
            for (int t = 0; t<stations.get(i).timestamps.size(); t++){
            TimeStamp time = new TimeStamp(station_temp,stations.get(i).timestamps.get(t).x);
            time.product_id = stations.get(i).timestamps.get(t).product_id;
            time.setLocation(stations.get(i).timestamps.get(t).x, stations.get(i).timestamps.get(t).y);
            time.add();
            time.relocate();
             }
            
            for (int p = 0; p < stations.get(i).downtimes.size(); p++) {
                DownTime dt = new DownTime(stations.get(i).downtimes.get(p).bg, stations.get(i).downtimes.get(p).fg, plan.weekdate);
                dt.BG = stations.get(i).downtimes.get(p).bg;
                dt.FG = stations.get(i).downtimes.get(p).fg;
                dt.setName(stations.get(i).downtimes.get(p).name);
                dt.setSize(stations.get(i).downtimes.get(p).width, stations.get(i).downtimes.get(p).height);
                dt.setLength(stations.get(i).downtimes.get(p).length);
                dt.setLocation(stations.get(i).downtimes.get(p).x, 125);

                dt.calc();
                station_temp.DOWNTIMES.add(dt);
                station_temp.add(dt);

            }
            station_temp.sort();
            station_temp.repaint();
        }

        return plan;
    }

    public void load(Plan plan) {
        name = plan.getName();
        week = plan.getWeekIndex();
         
        for (int i = 0; i < plan.STATIONS.size(); i++) {
            station temp = new station();
            temp.BG_IMAGE = plan.STATIONS.get(i).BG_IMAGE;
            temp.img_pos = plan.STATIONS.get(i).img_pos;
            temp.isPlanA = plan.STATIONS.get(i).isPlanA;
            temp.products_alpha = plan.STATIONS.get(i).products_alpha;
            temp.products_onwrite_string = plan.STATIONS.get(i).products_onwrite_string;
            temp.loader = plan.STATIONS.get(i).loader;
            temp.name = plan.STATIONS.get(i).getName();
            temp.runcode = plan.STATIONS.get(i).runcode;
            temp.station_id = plan.STATIONS.get(i).station_id;
            temp.comment = plan.STATIONS.get(i).getComment();
           
            temp.width = plan.WINDOWS.get(i).getSize().width;
            temp.height = plan.WINDOWS.get(i).getSize().height;

            temp.x = plan.WINDOWS.get(i).getLocation().x;
            temp.y = plan.WINDOWS.get(i).getLocation().y;

            temp.SW_STATUS_BAR = plan.STATIONS.get(i).SW_STATUS_BAR;
            temp.SW_INDICATOR = plan.STATIONS.get(i).SW_INDICATOR;
            temp.SW_COMMENT = plan.STATIONS.get(i).SW_COMMENT;
            temp.SW_FAMILY = plan.STATIONS.get(i).SW_FAMILY;
            temp.SW_JOBINFO = plan.STATIONS.get(i).SW_JOBINFO;

            temp.BG_COLOR = plan.STATIONS.get(i).BG_COLOR;
            temp.SELECTED_COLOR = plan.STATIONS.get(i).SELECTED_COLOR;
            temp.FG_COLOR = plan.STATIONS.get(i).FG_COLOR;
            temp.HALF_VISIBLE = plan.STATIONS.get(i).HALF_VISIBLE;
            temp.BG_PROGRESS = plan.STATIONS.get(i).BG_PROGRESS;
            temp.FG_PROGRESS = plan.STATIONS.get(i).FG_PROGRESS;

            temp.STATION_MINUTES = new String[plan.STATIONS.get(i).STATION_MINUTES.length];

            for (int x = 0; x < temp.STATION_MINUTES.length; x++) {
                if (plan.STATIONS.get(i).STATION_MINUTES[x] != 0) {
                    temp.STATION_MINUTES[x] = Double.toString(plan.STATIONS.get(i).STATION_MINUTES[x]);
                } else {
                    temp.STATION_MINUTES[x] = null;
                }
            }

            temp.SW_JOB_NO = plan.STATIONS.get(i).SW_JOB_NO;
            temp.SW_PN_NO = plan.STATIONS.get(i).SW_PN_NO;
            temp.SW_SEQ = plan.STATIONS.get(i).SW_SEQ;
            temp.SW_ID = plan.STATIONS.get(i).SW_ID;

            for (int p = 0; p < plan.STATIONS.get(i).PRODUCTS.size(); p++) {
                pn part_temp = new pn();
                part_temp.release = plan.STATIONS.get(i).PRODUCTS.get(p).RELEASE;
                part_temp.pn = plan.STATIONS.get(i).PRODUCTS.get(p).getPartnumber();
                part_temp.job = plan.STATIONS.get(i).PRODUCTS.get(p).getJobnumber();
                part_temp.cycle = plan.STATIONS.get(i).PRODUCTS.get(p).getCycle();
                part_temp.eff = plan.STATIONS.get(i).PRODUCTS.get(p).getEff();
                part_temp.panel = plan.STATIONS.get(i).PRODUCTS.get(p).getPanelization();
                part_temp.qty = plan.STATIONS.get(i).PRODUCTS.get(p).getQty();
                part_temp.x = plan.STATIONS.get(i).PRODUCTS.get(p).getLocation().x;
                part_temp.y = plan.STATIONS.get(i).PRODUCTS.get(p).getLocation().y;
                part_temp.StartUpMin = plan.STATIONS.get(i).PRODUCTS.get(p).getStartUpMin();
                part_temp.DownMin = plan.STATIONS.get(i).PRODUCTS.get(p).getDownTimeMin();
                part_temp.seq = plan.STATIONS.get(i).PRODUCTS.get(p).getSequence();
                part_temp.fg = plan.STATIONS.get(i).PRODUCTS.get(p).getFGColor();
                part_temp.bg = plan.STATIONS.get(i).PRODUCTS.get(p).getBGColor();
                part_temp.ID = plan.STATIONS.get(i).PRODUCTS.get(p).getID();
                part_temp.comment = plan.STATIONS.get(i).PRODUCTS.get(p).getComment();

                part_temp.freeze = plan.STATIONS.get(i).PRODUCTS.get(p).freeze;
                part_temp.planner_comment = plan.STATIONS.get(i).PRODUCTS.get(p).planner_comment;
                part_temp.history = plan.STATIONS.get(i).PRODUCTS.get(p).history;
                part_temp.original_qty = plan.STATIONS.get(i).PRODUCTS.get(p).original_qty;

                part_temp.MINUTES = new String[plan.STATIONS.get(i).PRODUCTS.get(p).MINUTES.length];

                for (int x = 0; x < part_temp.MINUTES.length; x++) {
                    if (plan.STATIONS.get(i).PRODUCTS.get(p).MINUTES[x] != 0) {
                        part_temp.MINUTES[x] = Double.toString(plan.STATIONS.get(i).PRODUCTS.get(p).MINUTES[x]);
                    } else {
                        part_temp.MINUTES[x] = null;
                    }
                }

                part_temp.QTYS = new String[plan.STATIONS.get(i).PRODUCTS.get(p).QTYS.length];

                for (int x = 0; x < part_temp.QTYS.length; x++) {
                    if (plan.STATIONS.get(i).PRODUCTS.get(p).QTYS[x] != 0) {
                        part_temp.QTYS[x] = Double.toString(plan.STATIONS.get(i).PRODUCTS.get(p).QTYS[x]);
                    } else {
                        part_temp.QTYS[x] = null;
                    }
                }
                part_temp.QTYS_TENY = new String[plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY.length];
                for (int x = 0; x < part_temp.QTYS_TENY.length; x++) {
                    if (plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY[x] != 0) {
                        part_temp.QTYS_TENY[x] = Double.toString(plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY[x]);
                    } else {
                        part_temp.QTYS_TENY[x] = null;
                    }
                }
                part_temp.QTYS_TENY_MSZAK = new String[plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_MSZAK.length];
                for (int x = 0; x < part_temp.QTYS_TENY_MSZAK.length; x++) {
                    if (plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_MSZAK[x] != 0) {
                        part_temp.QTYS_TENY_MSZAK[x] = Double.toString(plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_MSZAK[x]);
                    } else {
                        part_temp.QTYS_TENY_MSZAK[x] = null;
                    }
                }
                part_temp.QTYS_TENY_TIME = new String[plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_TIME.length];
                for (int x = 0; x < part_temp.QTYS_TENY_TIME.length; x++) {
                    if (plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_TIME[x] != 0) {
                        part_temp.QTYS_TENY_TIME[x] = Double.toString(plan.STATIONS.get(i).PRODUCTS.get(p).QTYS_TENY_TIME[x]);
                    } else {
                        part_temp.QTYS_TENY_TIME[x] = null;
                    }
                }

                temp.partnumbers.add(part_temp);
            }
            
            
            for (int t=0; t<plan.STATIONS.get(i).TIMESTAMPES.size(); t++){
                timestamp time = new timestamp();
                time.product_id = plan.STATIONS.get(i).TIMESTAMPES.get(t).product_id;
                time.x = plan.STATIONS.get(i).TIMESTAMPES.get(t).getLocation().x;
                time.y = plan.STATIONS.get(i).TIMESTAMPES.get(t).getLocation().y;
                
                temp.timestamps.add(time);
            }
            
            

            for (int p = 0; p < plan.STATIONS.get(i).DOWNTIMES.size(); p++) {
                downtime dt = new downtime();
                dt.bg = plan.STATIONS.get(i).DOWNTIMES.get(p).BG;
                dt.fg = plan.STATIONS.get(i).DOWNTIMES.get(p).FG;
                dt.height = plan.STATIONS.get(i).DOWNTIMES.get(p).getHeight();
                dt.length = plan.STATIONS.get(i).DOWNTIMES.get(p).getLength();
                dt.name = plan.STATIONS.get(i).DOWNTIMES.get(p).getName();
                dt.width = plan.STATIONS.get(i).DOWNTIMES.get(p).getWidth();
                dt.x = plan.STATIONS.get(i).DOWNTIMES.get(p).getLocation().x;
                dt.y = plan.STATIONS.get(i).DOWNTIMES.get(p).getLocation().y;
                temp.downtimes.add(dt);
            }

            stations.add(temp);
        }

    }

    public void restore() {
        Plan plan = new Plan();
         
        MainForm.newPlan(plan, getName(), getWeekIndex());

        for (int i = 0; i < stations.size(); i++) {
            Station station_temp = new Station(stations.get(i).name);
            station_temp.runcode = stations.get(i).runcode;
            station_temp.station_id = stations.get(i).station_id;
            station_temp.loader = stations.get(i).loader;
            station_temp.setComment(stations.get(i).comment);
            station_temp.BG_IMAGE = stations.get(i).BG_IMAGE;
            station_temp.isPlanA = stations.get(i).isPlanA;
            station_temp.img_pos = stations.get(i).img_pos;
            station_temp.products_alpha = stations.get(i).products_alpha;
            station_temp.products_onwrite_string = stations.get(i).products_onwrite_string;
            station_temp.SW_STATUS_BAR = stations.get(i).SW_STATUS_BAR;
            station_temp.SW_INDICATOR = stations.get(i).SW_INDICATOR;
            station_temp.SW_COMMENT = stations.get(i).SW_COMMENT;
            station_temp.SW_FAMILY = stations.get(i).SW_FAMILY;
            station_temp.SW_JOBINFO = stations.get(i).SW_JOBINFO;
            station_temp.BG_COLOR = stations.get(i).BG_COLOR;
            station_temp.SELECTED_COLOR = stations.get(i).SELECTED_COLOR;
            station_temp.FG_COLOR = stations.get(i).FG_COLOR;
            station_temp.HALF_VISIBLE = stations.get(i).HALF_VISIBLE;
            station_temp.BG_PROGRESS = stations.get(i).BG_PROGRESS;
            station_temp.FG_PROGRESS = stations.get(i).FG_PROGRESS;

            for (int x = 0; x < station_temp.STATION_MINUTES.length; x++) {
                if (stations.get(i).STATION_MINUTES[x] != null) {
                    station_temp.STATION_MINUTES[x] = Double.parseDouble(stations.get(i).STATION_MINUTES[x]);
                } else {
                    station_temp.STATION_MINUTES[x] = 0;
                }
            }

            station_temp.SW_JOB_NO = stations.get(i).SW_JOB_NO;
            station_temp.SW_PN_NO = stations.get(i).SW_PN_NO;
            station_temp.SW_SEQ = stations.get(i).SW_SEQ;
            station_temp.SW_ID = stations.get(i).SW_ID;

            MainForm.addStation(plan, station_temp, stations.get(i).station_id, new Dimension(stations.get(i).width, stations.get(i).height), new Point(stations.get(i).x, stations.get(i).y));

            for (int p = 0; p < stations.get(i).partnumbers.size(); p++) {

                Product prod = new Product(plan.weekdate);
                prod.setPartNumber(stations.get(i).partnumbers.get(p).pn);
                prod.setQty(stations.get(i).partnumbers.get(p).qty);
                prod.setPanelization(stations.get(i).partnumbers.get(p).panel);
                prod.setCycle(stations.get(i).partnumbers.get(p).cycle);
                prod.setEff(stations.get(i).partnumbers.get(p).eff);
                prod.setJobnumber(stations.get(i).partnumbers.get(p).job);
                prod.setStartUpMin(stations.get(i).partnumbers.get(p).StartUpMin);
                prod.setDownTimeMin(stations.get(i).partnumbers.get(p).DownMin);
                prod.setSequence(stations.get(i).partnumbers.get(p).seq);
                prod.setFGColor(stations.get(i).partnumbers.get(p).fg);
                prod.setBGColor(stations.get(i).partnumbers.get(p).bg);
                prod.setID(stations.get(i).partnumbers.get(p).ID);
                prod.setComment(stations.get(i).partnumbers.get(p).comment);
                prod.RELEASE = stations.get(i).partnumbers.get(p).release;

                prod.freeze = stations.get(i).partnumbers.get(p).freeze;
                prod.planner_comment = stations.get(i).partnumbers.get(p).planner_comment;
                prod.history = stations.get(i).partnumbers.get(p).history;
                prod.original_qty = stations.get(i).partnumbers.get(p).original_qty;

                for (int x = 0; x < prod.MINUTES.length; x++) {
                    if (stations.get(i).partnumbers.get(p).MINUTES[x] != null) {
                        prod.MINUTES[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).MINUTES[x]);
                    } else {
                        prod.MINUTES[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS[x] != null) {
                        prod.QTYS[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS[x]);
                    } else {
                        prod.QTYS[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS_TENY.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY[x] != null) {
                        prod.QTYS_TENY[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY[x]);
                    } else {
                        prod.QTYS_TENY[x] = 0;
                    }
                }
                for (int x = 0; x < prod.QTYS_TENY_MSZAK.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY_MSZAK[x] != null) {
                        prod.QTYS_TENY_MSZAK[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY_MSZAK[x]);
                    } else {
                        prod.QTYS_TENY_MSZAK[x] = 0;
                    }
                }

                for (int x = 0; x < prod.QTYS_TENY_TIME.length; x++) {
                    if (stations.get(i).partnumbers.get(p).QTYS_TENY_TIME[x] != null) {
                        prod.QTYS_TENY_TIME[x] = Double.parseDouble(stations.get(i).partnumbers.get(p).QTYS_TENY_TIME[x]);
                    } else {
                        prod.QTYS_TENY_TIME[x] = 0;
                    }
                }
                
                

                //station_temp.addProduct(prod);
                station_temp.addProductWithLocation(prod, stations.get(i).partnumbers.get(p).x, stations.get(i).partnumbers.get(p).y);

                station_temp.setSelected(true);

                // station_temp.sort();
                prod.resize();

            }
            
            for (int t = 0; t<stations.get(i).timestamps.size(); t++){
            TimeStamp time = new TimeStamp(station_temp,stations.get(i).timestamps.get(t).x);
            time.product_id = stations.get(i).timestamps.get(t).product_id;
            time.setLocation(stations.get(i).timestamps.get(t).x, stations.get(i).timestamps.get(t).y);
            time.add();
            time.relocate();
            station_temp.TIMESTAMPES.add(time);
            time.repaint();
            time.init();
            }
            
            

            for (int p = 0; p < stations.get(i).downtimes.size(); p++) {
                DownTime dt = new DownTime(stations.get(i).downtimes.get(p).bg, stations.get(i).downtimes.get(p).fg, plan.weekdate);
                dt.BG = stations.get(i).downtimes.get(p).bg;
                dt.FG = stations.get(i).downtimes.get(p).fg;
                dt.setName(stations.get(i).downtimes.get(p).name);
                dt.setSize(stations.get(i).downtimes.get(p).width, stations.get(i).downtimes.get(p).height);
                dt.setLength(stations.get(i).downtimes.get(p).length);
                dt.setLocation(stations.get(i).downtimes.get(p).x, 125);

                
                station_temp.DOWNTIMES.add(dt);
                station_temp.add(dt);
                dt.calc();
            }
            station_temp.sort();
            station_temp.inicializate();
            station_temp.repaint();

        }

        // plan.repaint();
    }

    public int getWeekIndex() {
        return week;
    }

    public String getName() {
        return name;
    }

}
