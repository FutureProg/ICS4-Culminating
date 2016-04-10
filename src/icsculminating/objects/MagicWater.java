/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.objects;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Daneil Polo
 */
public class MagicWater extends Magic{
    
    public MagicWater(int id){
        super(id,TYPE_AIR,EFFECT_OFFENCE,"Water Magic");
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/magic/attack_water.png"));
        } catch (IOException ex) {
            Logger.getLogger(MagicAir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
