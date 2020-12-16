/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krisztian_csekme1
 */
public class Session implements Serializable {

    List<String> files = new ArrayList<>();
    List<String> temp = new ArrayList<>();

    public int SW_NOW_AND_FUTURE = 0;
    public int SW_TERVEZOS_SEGED = 1; //Tervező segéd autómatikus indítása
    public int SW_PAN = 1;  //Panelizáció ellenőrzése
    public int SW_ENGINEER = 1; //Mérnöki gyártás ellenőrzése
    public int VAL_FROM = 8; //Mérnöki gyártás indítási intervallum kezdeti
    public int VAL_TO = 10; //Mérnöki gyártás indítási intervallum max
    public int POP_UP = 10; //Észrevételek felugrásának gyakorisága időintervallumban
    public float OPACITY_TERV_SEGED = 0.9f; //Tervező segéd átlátszósága
    public int SW_QTY_LOST = 1; //elhagyott darabszámok ellenőrzése
    public int SW_QTY_AUTO_CORRECT = 0; //elhagyott darabszámok autómatikus kiígazítása
    public int SW_KIT = 1; //Kittekési folyamatok ellenőrzése
    public int SW_CONTROL_PANEL = 1; //Control panel autómatikus indulása
    public float OPACITY_CONTROL_PANEL = 0.9f; //Control Panel átlátszósága
    public int SW_PARTNUMBER_PLR = 0; //Partnumber meglétének ellenőrzése
    public   String DIR = "S:\\SiteData\\BUD1\\EMS\\planning\\Gyartastervek";
    public   int LOGIN_REQ = 1; //Bejelentkezés megkövetelése
    public int DEFAULT_RUNUP_MIN=30;
    public int ErrorMsgDelay = 600000;
    public int LAST_PRODUCED_CHECK=90;

    public void addFile(String file) {
        boolean can = true;
        for (int i = 0; i < files.size(); i++) {
            if (file.equals(files.get(i))) {
                can = false;
            }
        }

        if (can) {
            files.add(file);
        }

    }

    public void pushValue() {
        PlNner.SW_NOW_AND_FUTURE = SW_NOW_AND_FUTURE;
        PlNner.SW_TERVEZOS_SEGED = SW_TERVEZOS_SEGED;
        PlNner.SW_PAN = SW_PAN;
        PlNner.SW_ENGINEER = SW_ENGINEER;
        PlNner.VAL_FROM = VAL_FROM;
        PlNner.VAL_TO = VAL_TO;
        PlNner.POP_UP = POP_UP;
        PlNner.OPACITY_TERV_SEGED = OPACITY_TERV_SEGED;
        PlNner.SW_QTY_LOST = SW_QTY_LOST;
        PlNner.SW_QTY_AUTO_CORRECT = SW_QTY_AUTO_CORRECT;
        PlNner.SW_KIT = SW_KIT;
        PlNner.SW_CONTROL_PANEL = SW_CONTROL_PANEL;
        PlNner.OPACITY_CONTROL_PANEL = OPACITY_CONTROL_PANEL;
        PlNner.ErrorMsgDelay = ErrorMsgDelay;
        PlNner.SW_PARTNUMBER_PLR = SW_PARTNUMBER_PLR;
        PlNner.DEFAULT_RUNUP_MIN = DEFAULT_RUNUP_MIN;
        PlNner.LAST_PRODUCED_CHECK = LAST_PRODUCED_CHECK;
    }

    public void pullValue() {

        SW_NOW_AND_FUTURE = PlNner.SW_NOW_AND_FUTURE;
        SW_TERVEZOS_SEGED = PlNner.SW_TERVEZOS_SEGED;
        SW_PAN = PlNner.SW_PAN;
        SW_ENGINEER = PlNner.SW_ENGINEER;
        VAL_FROM = PlNner.VAL_FROM;
        VAL_TO = PlNner.VAL_TO;
        POP_UP = PlNner.POP_UP;
        OPACITY_TERV_SEGED = PlNner.OPACITY_TERV_SEGED;
        SW_QTY_LOST = PlNner.SW_QTY_LOST;
        SW_QTY_AUTO_CORRECT = PlNner.SW_QTY_AUTO_CORRECT;
        SW_KIT = PlNner.SW_KIT;
        SW_CONTROL_PANEL = PlNner.SW_CONTROL_PANEL;
        OPACITY_CONTROL_PANEL = PlNner.OPACITY_CONTROL_PANEL;
        ErrorMsgDelay = PlNner.ErrorMsgDelay;
        SW_PARTNUMBER_PLR = PlNner.SW_PARTNUMBER_PLR;
        DEFAULT_RUNUP_MIN = PlNner.DEFAULT_RUNUP_MIN;
        LAST_PRODUCED_CHECK = PlNner.LAST_PRODUCED_CHECK;

    }

}
