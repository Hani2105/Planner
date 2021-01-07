/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.joda.time.format.DateTimeFormat;
import sun.net.www.content.text.plain;

/**
 *
 * @author gabor_hanacsek
 */
public class ErrorLogger extends Thread {

    String message;
    PlNner.errorlogger e;
    Exception ex;

    public ErrorLogger(PlNner.errorlogger e) {
        this.e = e;

    }

    public ErrorLogger(PlNner.errorlogger e, Exception ex) {
        this.e = e;
        this.ex = ex;

    }

    @Override
    public void run() {
        //melyik tab
        Plan pl = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
        //EMAIL mail = new EMAIL();
        Levelkuldes l = null;
        switch (e) {
            //ha mentjuk a terveket
            case mentes:
                //szedjuk ki a terveket
                ArrayList<Product> lista = new ArrayList<>();
                String pattern = "yyyy-MM-dd HH:mm:ss";
                org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

                message = "<html><body><table border=\"2\"><thead><tr><th>PartNumber</th><th>Job</th><th>Oldal</th><th>Qty</th><th>QtyTeny</th><th>StartTime</th><th>Komment</th><th>Mérnöki</th></tr></thead><tbody>";
                for (int i = 0; i < pl.STATIONS.size(); i++) {
                    for (int n = 0; n < pl.STATIONS.get(i).PRODUCTS.size(); n++) {
                        Product p = pl.STATIONS.get(i).PRODUCTS.get(n);
                        lista.add(p);
//                        String starttime = formatter.print(p.getStartTime());
//                        message += "<tr><td>" + p.getPartnumber() + "</td><td>" + p.getJobnumber() + "</td><td>" + p.getQty() + "</td><td>" + p.getSumTeny() + "</td><td>" + starttime + "</td><td>" + p.getComment() + "</td><td>" + p.isEngeenering() + "</td></tr>";

                    }

                }
                //tegyuk sorba a gyartasiido szerint

                Collections.sort(lista, new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return o1.getStartTime().compareTo(o2.getStartTime());
                    }
                });

                //most porgessuk be es csinaljunk html kodot
                for (int i = 0; i < lista.size(); i++) {
                    Product p = lista.get(i);
                    String starttime = formatter.print(p.getStartTime());
                    message += "<tr><td>" + p.getPartnumber() + "</td><td>" + p.getJobnumber() + "</td><td>" + p.getSequence() + "</td><td>" + p.getQty() + "</td><td>" + p.getSumTeny() + "</td><td>" + starttime + "</td><td>" + p.getComment() + "</td><td>" + p.isEngeenering() + "</td></tr>";

                }

                message += "</tbody></table></body></html>";

//                mail.setFrom("Planner@sanmina.com");
//                mail.setTo("gabor.hanacsek@sanmina.com," + PlNner.USER.mail);
//                mail.setSubject("Terv mentés " + pl.getName());
//                mail.setMessage(message);
//                mail.send();
                l = new Levelkuldes("Terv mentés " + pl.getName(), message, "gabor.hanacsek@sanmina.com," + PlNner.USER.mail, "Planner@sanmina.com");
                l.run();
                break;

            case hiba:

//                mail = new EMAIL();
//                mail.setFrom("Planner@sanmina.com");
//                mail.setTo("gabor.hanacsek@sanmina.com");
//                mail.setSubject("Mentés hiba" + pl.getName());
//                mail.setMessage(ex.getMessage());
//                mail.send();
                l = new Levelkuldes("Terv mentés " + pl.getName(), message, "gabor.hanacsek@sanmina.com," + PlNner.USER.mail, "Planner@sanmina.com");
                l.run();

                break;

            case tervvaltozas:

                break;
        }
    }

}
