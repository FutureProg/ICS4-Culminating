/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.GameButton;
import icsculminating.Main;
import icsculminating.MainPanel;
import icsculminating.TextBox;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

/**
 *
 * @author Nick
 */
public class SceneMap extends Scene {

    private TextBox nameInputBox;

    public SceneMap() {
        super();
        bg = DataManager.mapBG;
        DataManager.canSave = true;
        GameButton gb = new GameButton(419, 154, 100, 50, "Arena");
        gb.setActionCommand("ARENA");
        gb.addActionListener(this);
        add(gb);
        gb = new GameButton(36, 122, 100, 50, "COMING SOON");
        gb.setActionCommand("");
        gb.addActionListener(this);
        add(gb);
//        if(DataManager.newGame){
//            nameInputBox = new TextBox(30, 100, 200, 50);
//            nameInputBox.addActionListener(this);
//            nameInputBox.setActionCommand("NAME");
//            nameInputBox.setEnabled(true);
//            nameInputBox.setFocused(true);
//            add(nameInputBox);
//        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "ARENA":
                Main.manager.setScene(new SceneArena());
                break;
            case "MISSION":
                setScene(new SceneBattleMoc());
                break;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setColor(Color.GRAY);
        g.fill3DRect(MainPanel.WIDTH - 100, MainPanel.HEIGHT - 50, 100, 50, true);
        g.setColor(Color.BLUE);
        g.drawString("GOLD: " + DataManager.money, MainPanel.WIDTH - 90, MainPanel.HEIGHT - 20);
    }

}
