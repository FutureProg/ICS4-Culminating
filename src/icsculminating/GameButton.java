/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class GameButton {

    protected ArrayList<ActionListener> listeners;
    protected int x, y, width, height;
    int textX, textY;
    protected String text, command;
    protected Color col;

    public GameButton(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.col = Color.BLUE;
       // col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 180);
        listeners = new ArrayList<>();
    }

    public void setText(String txt) {
        this.text = txt;
        textX = 0;
    }

    public String getText() {
        return text;
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void setActionCommand(String command) {
        this.command = command;
    }

    public void draw(Graphics2D g) {
        if (textX == 0) {
            FontMetrics f = g.getFontMetrics();
            textX = x + width / 2 - (f.stringWidth(text) / 2);
            textY = y + (height / 2 + f.getHeight() / 2);
        }
        g.setColor(col);
        g.fill3DRect(x, y, width, height, true);
        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);
    }

    public void mouseMoved(MouseEvent evt) {
        Rectangle rect = new Rectangle(x, y, width, height);
        if (rect.contains(evt.getPoint())) {
            col = Color.RED;
        } else {
            col = Color.BLUE;
        }
     //   col = new Color(col.getRed(), col.getGreen(), col.getBlue(), 180);
    }

    public void mouseClicked(MouseEvent evt) {
        if (col == Color.BLUE) {
            return;
        }
        ActionEvent aevt = new ActionEvent(this, 0, command);
        for (ActionListener lis : listeners) {
            lis.actionPerformed(aevt);
        }
    }

}
