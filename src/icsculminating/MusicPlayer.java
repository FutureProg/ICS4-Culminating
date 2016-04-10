/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package icsculminating;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Nick
 */
public class MusicPlayer {
    
    private static Clip mainBGM;
    
    public static void init(){
        try {
            mainBGM = AudioSystem.getClip();
            mainBGM.open(getAudioInputStream("mainBGM.wav"));
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void playBGM(){
        mainBGM.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void stopBGM(){
        mainBGM.stop();
    }
    
    public static AudioInputStream getAudioInputStream(String fileName) {
		try {
			return AudioSystem.getAudioInputStream(Main.class.getClass()
					.getResourceAsStream("/music/" + fileName));
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
}
