/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculmunating;

import java.awt.Graphics2D;

/**
 *
 * @author Daneil Polo
 */
public class Item extends SceneObject{
    public final static int TYPE_DEFENCE = 0, TYPE_OFFENCE = 1;
    public final static int EFFECT_HP = 0, EFFECT_MP=1;
    private int type, effect;
    
    public Item(int type, int effect){
        super(0,0);
        this.type = type;
        this.effect = effect;
    }
    public void use(){
        
    }
    public void update(){
        
    }
    public int getType(){
        return type;
    }
    public int getEffect(){
        return effect;
    }
    public void setType(int type){
        this.type = type;
    }
    public void setEffect(int effect){
        this.effect = effect; 
    }

    @Override
    public void draw(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
 