/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.GameButton;
import icsculminating.Main;
import icsculminating.MusicPlayer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 *
 * @author Nick
 */
public class SceneTitle extends Scene {

    int index, branch;
    String[] choices;
    GameButton buttons[];

    public SceneTitle() {
        super();
        index = 0;
        choices = new String[3];
        choices[0] = "PLAY";
        choices[1] = "INSTRUCTIONS";
        choices[2] = "QUIT";
        buttons = new GameButton[3];
        for (int i = 0; i < 3; i++) {
            GameButton button;
            button = new GameButton(200, i * 50 + 150, 150, 30, choices[i]);
            button.addActionListener(this);
            button.setActionCommand(choices[i]);
            buttons[i] = button;
            add(button);
        }
        this.bg = DataManager.mapBG;
        //MusicPlayer.playBGM();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        /*g.setColor(Color.WHITE);
        g.fill3DRect(10, 10, MainPanel.WIDTH - 20, 100, true);
        g.setColor(Color.BLACK);
        g.drawString("TITLE", 20, 40);*/
    }

    public void updateBranch() {
        if (branch == 0) {
            choices = new String[3];
            choices[0] = "PLAY";
            choices[1] = "INSTRUCTIONS";
            choices[2] = "QUIT";
            for (int i = 0; i < 3; i++) {
                remove(buttons[i]);
                GameButton button = new GameButton(200, i * 50 + 150, 150, 30, choices[i]);
                button.addActionListener(this);
                button.setActionCommand(choices[i]);
                buttons[i] = button;
                add(button);
            }
        } else if (branch == 1) {
            choices = new String[3];
            String temp = "";
            if (!(new File("save/save1.SAV")).exists()) {
                temp = " - Empty";
            }
            choices[0] = "SAVE 1" + temp;
            temp = "";
            if (!(new File("save/save2.SAV")).exists()) {
                temp = " - Empty";
            }
            choices[1] = "SAVE 2" + temp;
            temp = "";
            choices[2] = "BACK";
            for (int i = 0; i < 3; i++) {
                remove(buttons[i]);
                GameButton button = new GameButton(200, i * 50 + 150, 150, 30, choices[i]);
                button.addActionListener(this);
                buttons[i] = button;
                add(button);
            }
            buttons[0].setActionCommand("SAVE 1");
            buttons[1].setActionCommand("SAVE 2");
            buttons[2].setActionCommand("BACK");
        }
    }

    public void loadSave(int slot) {
        if (Main.loadData(slot)) {
            System.out.println("Game Loaded");
        } else {
            System.out.println("NEW Game");
            DataManager.currentSlot = slot;
            DataManager.newGame = true;
        }
        Main.manager.setScene(new SceneMap());
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        super.keyReleased(evt);
        /*switch (evt.getKeyCode()) {
         case KeyEvent.VK_W:
         index = Math.max(index - 1, 0);
         break;
         case KeyEvent.VK_S:
         index = Math.min(index + 1, choices.length - 1);
         break;
         case KeyEvent.VK_ESCAPE:
         if (branch == 0) {
         Main.exit();
         }
         branch = Math.max(branch - 1, 0);
         updateBranch();
         break;
         case KeyEvent.VK_E:
         case KeyEvent.VK_SPACE:
         case KeyEvent.VK_ENTER:
         processIndex();
         break;
         }*/
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "PLAY":
                branch++;
                updateBranch();
                break;
            case "INSTRUCTIONS":
                break;
            case "QUIT":
                Main.exit();
                break;
            case "SAVE 1":
                loadSave(1);
                break;
            case "SAVE 2":
                loadSave(2);
                break;
            case "BACK":
                branch = Math.max(branch - 1, 0);
                updateBranch();
                break;
        }
    }

}
