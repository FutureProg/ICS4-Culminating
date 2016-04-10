/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import icsculminating.scenes.SceneMap;
import icsculminating.scenes.SceneTitle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Nick
 */
public class MainPanel extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener {

    //dimensions
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    public static final int SCALE = 2;
    //resources
    public static Font gameFont;
    public SceneManager manager;
    //game thread
    private Thread thread;
    boolean running;
    public static final int FPS = 60;
    public static final long targetTime = 1000 / FPS;
    //image
    private transient BufferedImage img;
    private Graphics2D g;

    public MainPanel() {
        super();
        MainPanel.gameFont = new Font("Comic Sans MS", Font.PLAIN, 14);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        running = false;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void init() {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        manager = new SceneManager(new SceneTitle());
        g = img.createGraphics();
        g.setFont(MainPanel.gameFont);
        addKeyListener(manager);
        addKeyListener(this);
        addMouseListener(manager);
        addMouseListener(this);
        addMouseMotionListener(manager);
        addMouseMotionListener(this);
        running = true;
    }

    @Override
    public void run() {

        init();
        long start, elapsed, wait;

        while (running) {
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;

            if (wait < 1) {
                continue;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private void update() {
        manager.update();
    }

    private void draw() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("LOADING...", 20, 20);
        manager.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.isControlDown() && e.isAltDown() && e.getKeyChar() == KeyEvent.VK_ESCAPE){
            Main.exit();
        }
        else if(e.isShiftDown()&& e.isAltDown() && e.getKeyChar() == KeyEvent.VK_Q){
            manager.setScene(new SceneTitle());
            DataManager.canSave = false;
        }
        else if(DataManager.canSave && e.isControlDown() && e.isShiftDown() && e.isAltDown() && e.getKeyChar() == KeyEvent.VK_Q){
            manager.setScene(new SceneMap());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
