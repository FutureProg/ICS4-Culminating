/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

/**
 *
 * @author Daneil Polo
 */
public class Obstacle {

    private int id, type, effect;
    public final static int EFFECT_BLOCK = 0, EFFECT_HP = 0;
    private int width, height, mapX, mapY;

    public Obstacle(int id, int type, int effect) {
        this.id = id;
        this.type = type;
        this.effect = effect;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

}
