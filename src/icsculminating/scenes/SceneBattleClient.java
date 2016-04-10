/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.MainPanel;
import icsculminating.objects.GameObject;
import icsculminating.objects.QuickMenu;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class SceneBattleClient extends Scene implements Runnable {

    int hp, mp, rank;
    int mapX, mapY;
    BufferedImage img;
    Socket server;
    ObjectOutputStream out;
    ObjectInputStream input;
    boolean data[];
    ArrayList<GameObject> newObjects;
    private boolean stop;
    int playerStats[][];
    private Graphics2D g;
    private int animIndex;
    private int direction;

    public SceneBattleClient(Socket server) {
        super();
        this.server = server;
        if (server != null) {
            try {
                this.input = new ObjectInputStream(server.getInputStream());
                this.out = new ObjectOutputStream(server.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(SceneBattleClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data = new boolean[9];
        img = new BufferedImage(DataManager.battleBG.getWidth(), DataManager.battleBG.getHeight(), BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();
        newObjects = new ArrayList<>();
        mapX = 0;
        mapY = 0;
        stop = false;
        new Thread(this).start();
        playerStats = new int[4][8];
    }

    @Override
    public void run() {
        while (stop == false) {
            try {
                System.out.println("sending...");
                //client first sends boolean of input
                out.writeInt(data.length);
                out.flush();
                for (int i = 0; i < data.length; i++) {
                    out.writeBoolean(data[i]);
                    out.flush();
                }
                System.out.println("sent input");
                //send any newly instantiated objects from list
                out.writeInt(newObjects.size());
                out.flush();
                for (int i = 0; i < newObjects.size(); i++) {
                    out.writeObject(newObjects.get(i));
                    out.flush();
                    newObjects.remove(i);
                }
                System.out.println("sent objects");
                //Receive status of player (hp, mp, placing, mapX, mapY)
                hp = input.readInt();
                mp = input.readInt();
                rank = input.readInt();
                mapX = input.readInt();
                mapY = input.readInt();
                System.out.println(mapX + "," + mapY);
                animIndex = input.readInt();
                direction = input.readInt();
                System.out.println("got status");
                for (int x = 0; x < playerStats.length; x++) {
                    for (int y = 0; y < playerStats[x].length; y++) {
                        playerStats[x][y] = input.readInt();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(SceneBattleClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update() {
        if (DataManager.qmenu != null) {
            DataManager.qmenu.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g.drawImage(DataManager.battleBG, 0, 0, null);
        for (int[] playerStat : playerStats) {
            int aIndex = playerStat[6];
            int dir = playerStat[7];
            int mx = playerStat[4];
            int my = playerStat[5];
            System.err.println(playerStat[4] + ", " + playerStat[5] + " : " + aIndex + ", " + dir);
            g.drawImage(DataManager.playerWater, mx, my, mx + 40, my + 40, 40 * dir, aIndex * 40, 40 * dir + 40, aIndex * 40 + 40, null);
        }
        g2.drawImage(img, -mapX, -mapY, null);
        drawHUD(g2);
        if (DataManager.qmenu != null) {
            DataManager.qmenu.draw(g2);
        }
    }

    public void drawHUD(Graphics2D g) {
        g.setColor(Color.GRAY);
        Color c = g.getColor();
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
        g.setColor(c);
        g.fill3DRect(MainPanel.WIDTH - 100, 10, 100, 90, true);
        g.setColor(Color.RED);
        g.drawString(hp + "", MainPanel.WIDTH - 80, 30);
        g.setColor(Color.MAGENTA);
        g.drawString(mp + "", MainPanel.WIDTH - 80, 60);
        g.setColor(Color.RED);
        g.drawString("Place: " + rank, MainPanel.WIDTH - 80, 90);
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_Q:
                if (DataManager.qmenu == null) {
                    DataManager.qmenu = new QuickMenu(10, 10);
                    return;
                }
                break;
            case KeyEvent.VK_0:
                setScene(new SceneMap());
                stop = true;
                break;
        }
        if (DataManager.qmenu != null) {
            DataManager.qmenu.keyReleased(evt);
        }
    }

}
