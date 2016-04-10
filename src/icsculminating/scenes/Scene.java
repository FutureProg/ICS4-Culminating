/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.GameButton;
import icsculminating.Main;
import icsculminating.TextBox;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public abstract class Scene implements KeyListener, MouseListener, MouseMotionListener, ActionListener {

    protected BufferedImage bg;
    protected ArrayList<GameButton> buttons;
    protected ArrayList<TextBox> textBoxes;

    public Scene() {
        buttons = new ArrayList<>();
        textBoxes = new ArrayList<>();
    }

    public abstract void update();

    public void draw(Graphics2D g) {
        if (bg != null) {
            g.drawImage(bg, null, null);
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g);
        }
        for (int i = 0; i < textBoxes.size(); i++) {
            textBoxes.get(i).draw(g);
        }
    }

    public void setScene(Scene scene) {
        Main.manager.setScene(scene);
    }

    public void add(GameButton button) {
        buttons.add(button);
    }

    public void add(TextBox box) {
        textBoxes.add(box);
    }

    public boolean remove(Object obj) {
        if (obj instanceof TextBox) {
            return textBoxes.remove(obj);
        }
        if (obj instanceof GameButton) {
            return buttons.remove(obj);
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (TextBox t : textBoxes) {
            t.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (TextBox t : textBoxes) {
            t.keyReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameButton b;
        for (int i = 0; i < buttons.size(); i++) {
            b = buttons.get(i);
            b.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GameButton b : buttons) {
            b.mouseMoved(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
