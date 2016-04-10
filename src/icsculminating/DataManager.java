/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package icsculminating;

import icsculminating.objects.Item;
import icsculminating.objects.Magic;
import icsculminating.objects.QuickMenu;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class DataManager {
 
    public static int TYPE_FIRE = 0, TYPE_WATER = 1, TYPE_EARTH = 2;
    
    public static int money = 0;
    public static int type = 0;
    public static String name = "";
    
    public static boolean canSave;
    public static int currentSlot;
    public static ServerWaitingRoom waitingRoom = null;
    
    public static BufferedImage mapBG, battleBG,enemyImage,waitBG,arenaBG;
    public static BufferedImage playerAir, playerWater, playerEarth, playerFire;
    
    public static ArrayList<Magic>magic;
    public static ArrayList<Item>items;
    
    public static QuickMenu qmenu = null;
    public static boolean newGame = false;
    
    public static String lastIP = "localhost";
}
