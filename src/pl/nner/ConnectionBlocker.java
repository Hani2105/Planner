package pl.nner;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.nner.MySQL;
import pl.nner.PlNner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabor_hanacsek
 */
public class ConnectionBlocker implements Runnable {
//singletone

    private static ConnectionBlocker cb = null;

    private ConnectionBlocker() {

    }

    public static ConnectionBlocker getInstance() {
        //Create a threadgroup
        ThreadGroup threadGroup = new ThreadGroup("myThreadGroup");
        if (cb == null) {

            cb = new ConnectionBlocker();
//            Thread t = new Thread(threadGroup, cb);
//            t.start();
        }
        //legyen blokkolva a progi
        if (threadGroup.activeCount() < 1) {
            Thread t = new Thread(threadGroup, cb);
            t.start();
        }

        PlNner.kapocsiha.setVisible(true);
        return cb;

    }

    @Override
    public void run() {

        MySQL m = new MySQL("143.116.140.114", "3306", "plan", "plan500", "planningdb");
        //System.out.println(String.valueOf(m.isDBAlive()));
        while (!m.isDBAlive()) {
            //System.out.println(String.valueOf(m.isDBAlive()));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionBlocker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        PlNner.kapocsiha.setVisible(false);

    }

}
