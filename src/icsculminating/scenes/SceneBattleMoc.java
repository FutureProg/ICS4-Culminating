/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.MainPanel;
import icsculminating.objects.Block;
import icsculminating.objects.Enemy;
import icsculminating.objects.Magic;
import icsculminating.objects.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class SceneBattleMoc extends Scene {

    public Player player;
    public ArrayList<Block> blocks;
    public ArrayList<Enemy> enemies;
    public ArrayList<Magic> magics;
    private BufferedImage layer;
    private Graphics2D g;

    public SceneBattleMoc() {
        bg = DataManager.battleBG;
        player = new Player(0, 110, 100);
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        magics = new ArrayList<>();
        layer = new BufferedImage(bg.getWidth(), bg.getHeight(), BufferedImage.TYPE_INT_RGB);
        g = layer.createGraphics();
        initBlocks();
        Enemy e = new Enemy(500, 500);
        enemies.add(e);
        e = new Enemy(400, 100);
        enemies.add(e);
        e = new Enemy(800, 800);
        enemies.add(e);
    }

    @Override
    public void update() {
        player.update();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        for (int i = 0; i < magics.size(); i++) {
            magics.get(i).update();
        }
    }

    public boolean isCollision(int x, int y) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isCollision(x, y)) {
                return true;
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isCollision(x, y, player.getDirection())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        player.keyReleased(evt);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        player.keyPressed(evt);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);
        g.drawImage(bg, 0, 0, null);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        player.draw(g);
        for (int i = 0; i < magics.size(); i++) {
            magics.get(i).draw(g);
        }
        g2.drawImage(layer, -player.getMapX() + MainPanel.WIDTH / 2, -player.getMapY() + MainPanel.HEIGHT / 2, null);
        drawHUD(g2);
    }

    private void initBlocks() {
        Block b = new Block(0, 0, 1200, 80);
        blocks.add(b);
        b = new Block(0, 0, 100, 1200);
        blocks.add(b);
        b = new Block(0, 910, 1200, 100);
        blocks.add(b);
        b = new Block(875, 155, 318, 545);
        blocks.add(b);
        b = new Block(1127, 82, 64, 907);
        blocks.add(b);
    }

    public void drawHUD(Graphics2D g) {
        g.setColor(Color.GRAY);
        Color c = g.getColor();
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
        g.setColor(c);
        g.fill3DRect(MainPanel.WIDTH - 100, 10, 100, 90, true);
        g.setColor(Color.RED);
        g.drawString(player.getHP() + "", MainPanel.WIDTH - 80, 30);
        g.setColor(Color.MAGENTA);
        g.drawString(player.getMP() + "", MainPanel.WIDTH - 80, 60);
        g.setColor(Color.RED);
        g.drawString("Place: " + 0, MainPanel.WIDTH - 80, 90);
    }

    public Enemy getEnemy(int x, int y) {
        Rectangle rect = new Rectangle(x, y, 40, 40);
        for (Enemy e : enemies) {
            Rectangle eRect = new Rectangle(e.getMapX(), e.getMapY(), 40, 40);
            if (rect.intersects(eRect)) {
                return e;
            }
        }
        return null;
    }

    public boolean removeEnemy(Enemy e) {
        return enemies.remove(e);
    }

}
