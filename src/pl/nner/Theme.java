/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Csekme
 */
public class Theme implements Serializable {
    
    public Color BG_COLOR;
    public Color SELECTED_COLOR;
    public Color FG_COLOR;
    public Color HALF_VISIBLE;
    public Color BG_PROGRESS;
    public Color FG_PROGRESS;
    public ImageIcon BG;
    public int img_pos;
   
}
