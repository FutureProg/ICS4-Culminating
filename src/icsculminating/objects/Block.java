/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package icsculminating.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Nick
 */
public class Block {
    
    private int x,y,width,height;
    private Rectangle rect;
    
    public Block(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rectangle(x, y, width, height);
    }
    
    public boolean isCollision(int x, int y){
        Point point = new Point(x,y);
        return rect.contains(point);
    }
    
    public void draw(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
    
}
