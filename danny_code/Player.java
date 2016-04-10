/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculmunating;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Daneil Polo
 */
public class Player extends SceneObject{
    private int hp, mp, speed, direction, animSpeed, animCount, animIndex;
    private ArrayList<Object> items;
    private Object weapon;
    
    public final static int DIRECTION_LEFT = -1;
    public final static int DIRECTION_RIGHT = 1;
    
    public Player (int id, int mapX, int mapY){
        super(mapX, mapY);
        this.id = id;
        speed = 4; 
        animSpeed = 30;
        animCount = animSpeed;
        animIndex = 0;
    }
    
    public int getHP(){
        return hp;
    }
    public int getMP(){
        return mp;
    }
    public ArrayList<Object> getItems(){
        return items;
    }
    public Object getWeapons(){
        return weapon;
    }
    public void setHP(int hp){
        this.hp = hp;
    }
    public void setMP(int mp){
        this.mp = mp;
    }
    public void setItems(ArrayList<Object> items){
        this.items = items;
    }
    public void setWeapons(int weapon){
        this.weapon = weapon;
    }
    public int getDirection (){
        return direction;
    }
    public void setDircetion (int direction){
        this.direction = direction;
    }
    
            
    public void keyPressed(KeyEvent evt){
        
            if (evt.getKeyCode() == KeyEvent.VK_W)
                mapY -= 4;
            if (evt.getKeyCode() == KeyEvent.VK_A)
                mapX -= 4;
            if (evt.getKeyCode() == KeyEvent.VK_S)
                mapY += 4;
            if (evt.getKeyCode() == KeyEvent.VK_D)
                mapX += 4;
            if (evt.getKeyCode() == KeyEvent.VK_R)
            {}//Defence
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT)
            {}  //Sprint
        
    }
    public void keyReleased(KeyEvent evt){
        
            if (evt.getKeyCode() == KeyEvent.VK_Q)
            {}  //Quick Menu
            if (evt.getKeyCode() == KeyEvent.VK_E)
            {}  //Use
            if (evt.getKeyCode() == KeyEvent.VK_ESCAPE)
            {}  //Main Menu
        }
    
    public void moveUp(){
        mapY += speed;
    }
    public void moveDown(){
        mapY -= speed;
    }
    public void moveLeft(){
        mapX -= speed;
    }
    public void moveRight(){
        mapX += speed;
    }
    public void update(){
        animate();
    }
    public void animate(){
        animCount -= 1;
        if (animCount <= 0){
            animCount = animSpeed;
            animIndex += 1;
        }
    }
    

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, null);
        
    }
    
}
