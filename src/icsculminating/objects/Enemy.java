/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import icsculminating.DataManager;
import icsculminating.Main;
import icsculminating.scenes.SceneBattleMoc;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Daneil Polo
 */
public class Enemy extends GameObject {

    int hp;

    public Enemy(int mapX, int mapY) {
        super(mapX, mapY);
        image = DataManager.enemyImage;
        hp = 50;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawString(hp + "", mapX, mapY - 10);
        g.drawImage(image, mapX, mapY, null);
    }
    
    @Override
    public void update(){
        super.update();
        if(hp <= 0){
            SceneBattleMoc scene = (SceneBattleMoc)Main.manager.getScene();
            scene.removeEnemy(this);
        }
    }

    public boolean isCollision(int x, int y, int dir) {
        return false;
//        int width = 0, height = 0;
//        if (dir == Player.DIRECTION_DOWN || dir == Player.DIRECTION_UP) {
//            width = 40;
//            height = 2;
//        }else{
//            width = 2;
//            height = 40;
//        }
//        Rectangle oRect = new Rectangle(x, y, 5, 40);
//        Rectangle rect = new Rectangle(mapX, mapY, image.getWidth(), image.getHeight());
//        return rect.intersects(oRect);
    }

}
