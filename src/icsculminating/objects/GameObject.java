/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Daneil Polo
 */
public abstract class GameObject {
    
    public int x, y, mapX, mapY;
    protected int id;
    
    protected BufferedImage image;
    
    public GameObject(int mapX, int mapY){
        this.mapX = mapX;
        this.mapY = mapY;
        
    }
    
    public int getX(){
        return x;
    }
    public void setX (int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getMapX(){
        return mapX;
    }
    public void setMapX(int mapX){
        this.mapX = mapX;
    }
    public int getMapY(){
        return mapY;
    }
    public void setMapY(int mapY){
        this.mapY = mapY;
    }
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }
    public BufferedImage getBufferedImage(){
        return image;
    }
    
    public abstract void draw(Graphics2D g);

    public void update() {
    }
    
    
}
