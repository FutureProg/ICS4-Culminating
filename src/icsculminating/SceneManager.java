/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;


import icsculminating.scenes.Scene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Nick
 */
public class SceneManager implements KeyListener, MouseListener, MouseMotionListener{

    private Scene currentScene;

    public SceneManager() {
        currentScene = new Scene() {

            @Override
            public void update() {
            }

            @Override
            public void draw(Graphics2D g) {
            }

        };
    }

    public SceneManager(Scene scene) {
        this.currentScene = scene;
    }

    public void setScene(Scene scene) {
        currentScene = scene;
        Main.saveData(DataManager.currentSlot);
    }

    public Scene getScene() {
        return currentScene;
    }

    public void update() {
        currentScene.update();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT);
        currentScene.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        currentScene.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentScene.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentScene.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentScene.mouseClicked(e);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentScene.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentScene.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        currentScene.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        currentScene.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentScene.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentScene.mouseMoved(e);
    }

}
