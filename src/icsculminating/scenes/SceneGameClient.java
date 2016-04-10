/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.MainPanel;
import icsculminating.objects.Block;
import icsculminating.objects.Magic;
import icsculminating.objects.MagicFire;
import icsculminating.objects.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Nick
 */
public class SceneGameClient extends Scene implements Runnable {

    private Player player;
    private BufferedImage playerImage;
    private BufferedImage image;
    private Graphics2D g;
    private boolean stop;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private int id;
    private Point mPos;
    MouseEvent mPressed;
    ArrayList<Block> blocks;
    ArrayList<Magic> magics;
    int timeRemaining;
    private Timer timer;

    public SceneGameClient(Socket socket, int id) {
        try {
            this.id = id;
            this.socket = socket;
            playerImage = DataManager.playerEarth;
            image = new BufferedImage(DataManager.battleBG.getWidth(),
                    DataManager.battleBG.getHeight(), BufferedImage.TYPE_INT_RGB);
            g = image.createGraphics();
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            mPressed = null;
            initBlocks();
            magics = new ArrayList<>();
            player = new Player(0, 100, 100);
            new Thread(this).start();
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    timeRemaining--;
                }
            });
            timeRemaining = 30;
            timer.start();
        } catch (IOException ex) {
            Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        mPos = new Point(-500, -500);
        while (!stop) {
            if (id == 0) {
                try {
                    System.out.println(player.getMapX());
                    Point point = new Point(player.getMapX(), player.getMapY());
                    output.writeObject(point);
                    output.flush();
                    output.writeInt(player.getDirection());
                    output.flush();
                    output.writeInt(player.getAnimIndex());
                    output.flush();
                    if ((mPressed = (MouseEvent) input.readObject()) != null) {
                        MagicFire m = new MagicFire(0);
                        m.mapX = mPressed.getX() + player.getMapX() - MainPanel.WIDTH / 2;
                        m.mapY = mPressed.getY() + player.getMapY() - MainPanel.HEIGHT / 2;
                        mPressed = null;
                        magics.add(m);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    input.read();
                    output.writeObject(mPressed);
                    output.flush();
//                    System.out.println(mPressed);
                    if (mPressed != null) {
                        MagicFire m = new MagicFire(0);
                        m.mapX = mPressed.getX() + player.getMapX() - MainPanel.WIDTH / 2;
                        m.mapY = mPressed.getY() + player.getMapY() - MainPanel.HEIGHT / 2;
                        mPressed = null;
                        magics.add(m);
                    }
                    //output.flush();
                    mPressed = null;
                    Point p = (Point) input.readObject();
//                    System.out.println(p);
                    player.setMapX(p.x);
                    player.setMapY(p.y);
                    player.setAnimIndex(input.read());
                    player.setDircetion(input.read());
                    
//                    System.out.println("px : " + player.getMapX() + " , " + player.getMapY());
                } catch (IOException ex) {
                    Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void initBlocks() {
        blocks = new ArrayList<>();
        Block b = new Block(0, 0, 1200, 80);
        blocks.add(b);
        b = new Block(0, 0, 100, 1200);
        blocks.add(b);
        b = new Block(0, 910, 1200, 100);
        blocks.add(b);
        b = new Block(875, 155, 318, 545);
        blocks.add(b);
        b = new Block(1127, 82, 64, 907);
        blocks.add(b);
    }

    public boolean removeMagic(Magic magic) {
        return magics.remove(magic);
    }

    public boolean isCollision(int x, int y) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isCollision(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMagicCollision(int x, int y) {
        Rectangle rect = new Rectangle(x, y, 40, 40);
        for (int i = 0; i < magics.size(); i++) {
            Magic m = magics.get(i);
            Rectangle oRect = new Rectangle(m.getMapX(), m.getMapY(), 40, 40);
            if (rect.intersects(oRect)) {
                removeMagic(m);
                return true;
            }
        }
        return false;
    }

    public void stop() {
        try {
            stop = true;
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SceneGameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
        player.update();
        for (int i = 0; i < magics.size(); i++) {
            magics.get(i).update();
        }
        if (isMagicCollision(player.getMapX(), player.getMapY())) {

            player.setHP(player.getHP() - 10);
        }

        if (timeRemaining == 0) {
            JOptionPane.showMessageDialog(null, "PLAYER 1 WINS 50 GOLD!");
            if (id == 0) {
                DataManager.money += 50;
            }
            setScene(new SceneArena());
            stop();
            timer.stop();
            if (DataManager.waitingRoom != null) {
                DataManager.waitingRoom = null;
            }
        } else if (player.getHP() <= 0) {
            JOptionPane.showMessageDialog(null, "PLAYER 2 WINS 50 GOLD!");
            stop();
            if (id == 1) {
                DataManager.money += 50;
            }
            setScene(new SceneArena());
            timer.stop();
            if (DataManager.waitingRoom != null) {
                DataManager.waitingRoom = null;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (id == 0) {
            player.keyReleased(evt);
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (id == 0) {
            player.keyPressed(evt);
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if (id == 1) {
            mPressed = evt;
            mPos = evt.getPoint();
//            System.out.println(mPos);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g.drawImage(DataManager.battleBG, 0, 0, null);
        player.draw(g);
        for (int i = 0; i < magics.size(); i++) {
            magics.get(i).draw(g);
        }
        g2.drawImage(image, -player.getMapX() + MainPanel.WIDTH / 2, -player.getMapY() + MainPanel.HEIGHT / 2, null);
        drawHUD(g2);
    }

    public void drawHUD(Graphics2D g) {
        g.setColor(Color.GRAY);
        Color c = g.getColor();
        c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
        g.setColor(c);
        g.fill3DRect(MainPanel.WIDTH - 100, 10, 100, 90, true);
        g.setColor(Color.RED);
        g.drawString(player.getHP() + "", MainPanel.WIDTH - 80, 30);
        g.setColor(Color.MAGENTA);
        g.drawString(player.getMP() + "", MainPanel.WIDTH - 80, 60);
        g.setColor(Color.WHITE);
        g.drawString(timeRemaining + "", MainPanel.WIDTH - 80, 90);
    }

}
