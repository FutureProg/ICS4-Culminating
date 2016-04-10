/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import icsculminating.DataManager;
import icsculminating.Main;
import icsculminating.scenes.SceneBattleMoc;
import icsculminating.scenes.SceneGameClient;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Daneil Polo
 */
public class Player extends GameObject {

    private int hp, mp, speed, direction, animSpeed, animCount, animIndex;
    private ArrayList<Object> items;
    private Object weapon;
    private boolean moving;
    private boolean left, right, up, down;

    public final static int DIRECTION_DOWN = 0;
    public final static int DIRECTION_LEFT = 2;
    public final static int DIRECTION_RIGHT = 1;
    public final static int DIRECTION_UP = 3;

    public int type;

    public Player(int id, int mapX, int mapY) {
        super(mapX, mapY);
        this.id = id;
        speed = 2;
        animSpeed = 15;
        animCount = animSpeed;
        animIndex = 0;
        hp = 100;
        image = DataManager.playerAir;
    }

    public int getHP() {
        return hp;
    }

    public int getMP() {
        return mp;
    }

    public ArrayList<Object> getItems() {
        return items;
    }

    public Object getWeapons() {
        return weapon;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public void setMP(int mp) {
        this.mp = mp;
    }

    public void setItems(ArrayList<Object> items) {
        this.items = items;
    }

    public void setWeapons(int weapon) {
        this.weapon = weapon;
    }

    public int getDirection() {
        return direction;
    }

    public void setDircetion(int direction) {
        this.direction = direction;
    }

    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
            moving = true;
            direction = DIRECTION_UP;
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            moving = true;
            direction = DIRECTION_LEFT;
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
            moving = true;
            direction = DIRECTION_DOWN;
        }
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
            moving = true;
            direction = DIRECTION_RIGHT;
        }
        if (evt.getKeyCode() == KeyEvent.VK_R) {
        }//Defence
        if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
        }  //Sprint
    }

    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
            moving = false;
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
            moving = false;
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
            moving = false;
        }
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
            moving = false;
        }
        if (evt.getKeyCode() == KeyEvent.VK_Q) {
        }  //Quick Menu

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        }  //Main Menu
    }

    public void moveUp() {
        mapY -= speed;
        direction = DIRECTION_UP;
    }

    public void moveDown() {
        mapY += speed;
        direction = DIRECTION_DOWN;
    }

    public void moveLeft() {
        mapX -= speed;
        direction = DIRECTION_LEFT;
    }

    public void moveRight() {
        mapX += speed;
        direction = DIRECTION_RIGHT;
    }

    @Override
    public void update() {
        animate();
        if (!canMove(direction)) {
            return;
        }
        if (left) {
            moveLeft();
        }
        if (right) {
            moveRight();
        }
        if (down) {
            moveDown();
        }
        if (up) {
            moveUp();
        }
    }

    public void animate() {
        if (!moving) {
            animIndex = 0;
            return;
        }
        animCount -= 1;
        if (animCount <= 0) {
            animCount = animSpeed;
            animIndex += 1;
        }
        if (animIndex > 3) {
            animIndex = 0;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, mapX, mapY, mapX + 40, mapY + 40, 40 * direction, animIndex * 40, 40 * direction + 40, animIndex * 40 + 40, null);
    }

    public int[] getStatus() {
        return new int[]{type, hp, mp, 0, mapX, mapY, animIndex, direction};
    }

    public boolean canMove(int dir) {
        int nX = mapX, nY = mapY;
        if (dir == DIRECTION_DOWN) {
            nY += speed + 40;
        }
        if (dir == DIRECTION_LEFT) {
            nX -= speed;
        }
        if (dir == DIRECTION_RIGHT) {
            nX += speed + 40;
        }
        if (dir == DIRECTION_UP) {
            nY -= speed;
        }
        try {
            SceneGameClient scene = (SceneGameClient) Main.manager.getScene();

            return !scene.isCollision(nX, nY);
        } catch (Exception ex) {
        }
        return false;
    }

    private void attack() {
        int nx = mapX, ny = mapY;
        switch (direction) {
            case DIRECTION_DOWN:
                ny += 40;
                break;
            case DIRECTION_UP:
                ny -= 40;
                break;
            case DIRECTION_LEFT:
                nx -= 40;
                break;
            case DIRECTION_RIGHT:
                nx += 40;
                break;
        }
        SceneBattleMoc scene = (SceneBattleMoc) Main.manager.getScene();
        Enemy e = scene.getEnemy(nx, ny);
        if (e != null) {
            e.hp -= 10;
        }
    }

    private void magic() {
        MagicAir magic = new MagicAir(0);
        magic.setMapX(mapX);
        magic.setMapY(mapY);
        SceneBattleMoc scene = (SceneBattleMoc) Main.manager.getScene();
        scene.magics.add(magic);
    }

    public int getAnimSpeed() {
        return animSpeed;
    }

    public int getAnimCount() {
        return animCount;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public void setAnimIndex(int animIndex) {
        this.animIndex = animIndex;
    }

}
