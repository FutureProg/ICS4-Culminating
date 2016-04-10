/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.MainPanel;
import icsculminating.objects.Block;
import icsculminating.objects.Enemy;
import icsculminating.objects.GameObject;
import icsculminating.objects.Magic;
import icsculminating.objects.Player;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Daneil Polo
 */
public class SceneBattle {

    private BufferedImage image;
    public ArrayList<Block> blocks;
    public ArrayList<Enemy> enemies;
    public ArrayList<Magic> magics;
    private BufferedImage foreground, background;
    private Graphics2D fg, bg, g;
    private ArrayList<GameObject> objects;
    public Player[] players;

    public SceneBattle(Player[] players) { 
        DataManager.canSave = false;
        this.players = players;
        image = DataManager.battleBG;
        g = image.createGraphics();
        foreground = new BufferedImage(MainPanel.WIDTH, MainPanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        fg = foreground.createGraphics();
        background = new BufferedImage(MainPanel.WIDTH, MainPanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        bg = background.createGraphics();
        objects = new ArrayList<>();
        enemies = new ArrayList<>();
        magics = new ArrayList<>();
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

    public void update() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        for (int i = 0; i < magics.size(); i++) {
            magics.get(i).update();
        }
        for (int i = 0; i < players.length; i++) {
            players[i].update();
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, null);
    }

    public void bufferImage() {
        g.drawImage(background, 0, 0, null);
        for (int i = 0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            obj.draw(fg);
        }
        g.drawImage(foreground, 0, 0, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void addMagic(Magic magic){
        magics.add(magic);
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }
    
    public boolean isCollision(int x, int y, Player player) {
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
    
}
