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
public class MagicAir extends Magic{
    
    public MagicAir(int id){
        super(id,TYPE_AIR,EFFECT_OFFENCE,"Air Magic");
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/magic/attack_air.png"));
        } catch (IOException ex) {
            Logger.getLogger(MagicAir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
