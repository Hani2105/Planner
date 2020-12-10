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

/**
 *
 * @author krisztian_csekme1
 */
public class Plan2StreamV2 implements Serializable {

    private String name;
    private int week;
    List<station> stations = new ArrayList<>();

    // public static void addStation(Plan plan, String name, Dimension dim, Point location){
    public class station implements Serializable {

        List<pn> partnumbers = new ArrayList<>();
        List<downtime> downtimes = new ArrayList<>();
        public String name;
        public int runcode;
        public int station_id;
        public int width, height;
        public int x, y;
        public String comment;

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
        
    }

    public void load(Plan plan) {
        name = plan.getName();
        week = plan.getWeekIndex();

        for (int i = 0; i < plan.STATIONS.size(); i++) {
            station temp = new station();
            temp.name = plan.STATIONS.get(i).getName();
            temp.runcode = plan.STATIONS.get(i).runcode;
            temp.station_id = plan.STATIONS.get(i).station_id;
            temp.comment = plan.STATIONS.get(i).getComment();
            
            temp.width = plan.WINDOWS.get(i).getSize().width;
            temp.height = plan.WINDOWS.get(i).getSize().height;

            temp.x = plan.WINDOWS.get(i).getLocation().x;
            temp.y = plan.WINDOWS.get(i).getLocation().y;

            for (int p = 0; p < plan.STATIONS.get(i).PRODUCTS.size(); p++) {
                pn part_temp = new pn();
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
                temp.partnumbers.add(part_temp);
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
            station_temp.setComment(stations.get(i).comment);
            MainForm.addStation(plan, station_temp,stations.get(i).station_id , new Dimension(stations.get(i).width, stations.get(i).height), new Point(stations.get(i).x, stations.get(i).y));

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
                //station_temp.addProduct(prod);
                 
                
                station_temp.addProductWithLocation(prod, stations.get(i).partnumbers.get(p).x, stations.get(i).partnumbers.get(p).y);
                
                
                
                
                 station_temp.setSelected(true);
                 
               // station_temp.sort();
                prod.resize();
                
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

        
        
        // plan.repaint();
    }

    public int getWeekIndex() {
        return week;
    }

    public String getName() {
        return name;
    }

}
