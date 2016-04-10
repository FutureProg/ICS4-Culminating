/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Nick
 */
public class Main {

    public static MainPanel panel;
    public static SceneManager manager;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        init();
        panel = new MainPanel();
        JFrame frame = new JFrame("Culminating Game");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                Main.exit();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        manager = panel.manager;
    }
    
    private static void init(){
        initResources();
        DataManager.items = new ArrayList<>();
        DataManager.magic = new ArrayList<>();
        //MusicPlayer.init();
    }

    private static void initResources(){
        try {
            DataManager.mapBG = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/map.png"));
            DataManager.battleBG = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/battle_scene.png"));
            DataManager.playerWater = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/player/player_water_sheet.png"));
            DataManager.playerFire = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/player/player_fire_sheet.png"));
            DataManager.playerAir = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/player/player_air_sheet.png"));
            DataManager.playerEarth = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/player/player_earth_sheet.png"));
            DataManager.enemyImage = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/chuchu.png"));
            DataManager.arenaBG = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/background/wait_bg_2.png"));
            DataManager.waitBG = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/background/wait_bg_1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SceneManager getSceneManager() {
        return panel.manager;
    }

    public static void exit() {
        if (DataManager.canSave && DataManager.currentSlot > 0) {
            if (saveData(DataManager.currentSlot)) {
                System.out.println("Saved to slot" + DataManager.currentSlot);
            } else {
                System.err.println("Error saving game to slot" + DataManager.currentSlot);
            }
        };
        System.exit(0);
    }

    public static boolean saveData(int slot) {
        FileOutputStream f = null;
        try {
            String filename = "save" + slot + ".SAV";
            File file = new File("save");
            if (!file.isDirectory()) {
                file.mkdir();
            }
            file = new File("save/" + filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            f = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(DataManager.name);
            out.writeInt(DataManager.type);
            out.writeInt(DataManager.money);
            out.flush();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static boolean loadData(int slot) {
        try {
            ObjectInputStream input = null;

            FileInputStream f;
            String filename = "save" + slot + ".SAV";
            File file = new File("save/" + filename);
            f = new FileInputStream(file);
            input = new ObjectInputStream(f);
            DataManager.name = (String) input.readObject();
            DataManager.type = input.readInt();
            DataManager.money = input.readInt();
            DataManager.currentSlot = slot;
            return true;

        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }
    }

}
