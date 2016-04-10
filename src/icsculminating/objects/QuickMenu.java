/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import icsculminating.DataManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Nick
 */
public class QuickMenu {

    public static final int SECTION_ITEMS = 0, SECTION_MAGIC = 1;

    int x, y;
    int width, height;
    Item[] items;
    Magic[] magic;
    int section, index;
    BufferedImage img;
    Graphics2D g;

    public QuickMenu(int x, int y) {
        this.x = x;
        this.y = y;
        items = new Item[6];
        magic = new Magic[6];
        items = DataManager.items.toArray(items);
        magic = DataManager.magic.toArray(magic);
        index = 0;
        section = SECTION_ITEMS;
        width = 90;
        height = 130;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();
    }

    public void update() {
        buffer();
    }

    public void buffer() {
        g.setColor(Color.BLACK);
        g.fill3DRect(0, 0, width, height, true);
        g.setColor(Color.WHITE);
        if (index <= 1) {
            g.drawRect(index * 30 + 10, 10, 30, 30);
        } else {
            int dx, dy;
            if (index % 2 == 0) {
                dx = 10;
            } else {
                dx = 40;
            }
            if (index == 2 || index == 3) {
                dy = 50;
            } else {
                dy = 90;
            }
            g.drawRect(dx, dy, 30, 30);
        }
        if (section == SECTION_ITEMS) {
            g.drawString("Items", 10, 10);
        } else {
            g.drawString("Magic", 10, 10);
        }
    }

    public void indexLeft() {
        index = Math.max(index - 1, 0);
    }

    public void indexRight() {
        switch (section) {
            case SECTION_ITEMS:
                index = Math.min(index + 1, items.length - 1);
                break;
            case SECTION_MAGIC:
                index = index = Math.min(index + 1, items.length - 1);
                break;
        }
    }

    public void indexUp() {
        index = Math.max(0, index - 2);
    }

    public void indexDown() {
        switch (section) {
            case SECTION_ITEMS:
                index = Math.min(index + 2, items.length - 1);
                break;
            case SECTION_MAGIC:
                index = index = Math.min(index + 2, magic.length - 1);
                break;
        }
    }

    public void changeSection() {
        if (section == SECTION_ITEMS) {
            section = SECTION_MAGIC;
        } else {
            section = SECTION_ITEMS;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(img, x, y, null);
    }

    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                indexUp();
                break;
            case KeyEvent.VK_DOWN:
                indexDown();
                break;
            case KeyEvent.VK_LEFT:
                indexLeft();
                break;
            case KeyEvent.VK_RIGHT:
                indexRight();
                break;
            case KeyEvent.VK_R:
                changeSection();
                break;
            case KeyEvent.VK_Q:
            case KeyEvent.VK_ESCAPE:
                DataManager.qmenu = null;
                break;
        }
    }

}
