/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import icsculminating.Main;
import icsculminating.scenes.SceneBattleMoc;
import icsculminating.scenes.SceneGameClient;
import java.awt.Graphics2D;

/**
 *
 * @author Daneil Polo
 */
public class Magic extends GameObject{
    private int id, effect, type;
    private String name;
    public final static int TYPE_FIRE = 0, TYPE_WATER = 1, TYPE_EARTH = 2, TYPE_AIR = 3;
    public final static int EFFECT_DEFFENCE = 0, EFFECT_OFFENCE = 1;
    private int animCount;
    private int animSpeed;
    private int animIndex;
    
    public Magic(int id, int type, int effect, String name){
        super(0,0);
        this.id = id;
        this.type = type;
        this.effect = effect;
        animSpeed = 5;
        animCount = animSpeed;
        animIndex = 0;
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
    
    public void update(){
        if(animIndex >= 2){
            SceneGameClient scene = (SceneGameClient) Main.manager.getScene();
            scene.removeMagic(this);
        }
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
        g.drawImage(image,mapX,mapY,mapX + 40, mapY + 40, animIndex * 40, 0, animIndex * 40 + 40, 40, null);
    }
    
}
