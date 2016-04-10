/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class TextBox {

    int x, y, width, height;
    String input;
    boolean focused;
    ArrayList<ActionListener> listeners;
    boolean enabled;
    String command;

    public TextBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.enabled = true;
        input = "";
        command = "";
        listeners = new ArrayList<>();
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill3DRect(x, y, width, height, true);
        g.setColor(Color.BLACK);
        if (enabled || focused) {
            g.drawString(input + " _", x + 30, y + 20);
        } else {
            g.drawString(input, x + 30, y + 20);
        }
    }

    public void keyTyped(KeyEvent evt) {
        if (focused && enabled) {
            input += evt.getKeyChar() + "";
        }
    }

    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                for (ActionListener list : listeners) {
                    list.actionPerformed(new ActionEvent(this, 0, command));
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (input.length() > 1) {
                    input = input.substring(0, input.length() - 2);
                }
                break;
        }
    }

    public void setFocused(boolean focus) {
        focused = focus;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setText(String str) {
        this.input = str;
    }

    public String getText() {
        return input;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addActionListener(ActionListener list) {
        listeners.add(list);
    }

    public void setActionCommand(String str) {
        this.command = str;
    }

}
