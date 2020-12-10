/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 *
 * @author krisztian_csekme1
 */
public class OvalIcon implements Icon {

    private int width;
    private int height;
    private Color color;

    public OvalIcon(int w, int h, Color color) {
        if ((w | h) < 0) {
            throw new IllegalArgumentException("Illegal dimensions: "
                    + "(" + w + ", " + h + ")");
        }
        this.width = w;
        this.height = h;
        this.color = (color == null) ? Color.BLACK : color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D gd = (Graphics2D) g;

        Color temp = gd.getColor();
        gd.setColor(color);
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.fillOval(x, y, getIconWidth(), getIconHeight());
        gd.setColor(temp);

    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
