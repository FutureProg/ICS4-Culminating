/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import java.awt.Graphics2D;

/**
 *
 * @author Daneil Polo
 */
public class Weapon extends GameObject{
    private int id, effect, type;
    private String name;
    public final static int TYPE_FIRE = 0, TYPE_WATER = 1, TYPE_EARTH = 0;
    public final static int EFFECT_DEFFENCE = 0, EFFECT_OFFENCE = 1;
    
    public Weapon(int id, int effect, int type, String name){
        super(0, 0);
        this.id = id;
        this.effect = effect;
        this.type = type;
        this.name = name;
    }
     public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getEffect(){
        return effect;
    }
    public void setEffect(int effect){
        this.effect = effect;
    }

    @Override
    public void draw(Graphics2D g) {
    }
    
}
