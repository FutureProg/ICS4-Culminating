/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.GameButton;
import icsculminating.ServerWaitingRoom;
import icsculminating.TextBox;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick
 */
public class SceneArena extends Scene {

    int state;
    public static int STATE_MENU = 0, STATE_JOIN = 1, STATE_HOST = 2;
    GameButton[] buttons;

    public SceneArena() {
        super();
        bg = DataManager.arenaBG;
        state = 0;
        buttons = new GameButton[3];
        GameButton b = new GameButton(50, 50, 100, 50, "Join");
        b.setActionCommand("JOIN");
        b.addActionListener(this);
        buttons[0] = b;
        b = new GameButton(50, 150, 100, 50, "Host");
        b.setActionCommand("HOST");
        b.addActionListener(this);
        buttons[1] = b;
        b = new GameButton(50, 250, 100, 50, "BACK");
        b.setActionCommand("BACK");
        b.addActionListener(this);
        buttons[2] = b;
        for (GameButton button : buttons) {
            add(button);
        }
        try {
            String hostname = "host address: " + InetAddress.getLocalHost().getHostAddress();
            TextBox box = new TextBox(250, 50, 300, 50);
            box.setText(hostname);
            box.setEnabled(false);
            add(box);
            hostname = "host name: " + InetAddress.getLocalHost().getHostName();
            box = new TextBox(250, 110, 300, 50);
            box.setText(hostname);
            box.setEnabled(false);
            add(box);
        } catch (UnknownHostException ex) {
            Logger.getLogger(SceneArena.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
    
    public void host(){
        if(DataManager.waitingRoom != null){
            int re = JOptionPane.showConfirmDialog(null, "The Server is alrady running.\n Would you like to stop it?");
            if(re == JOptionPane.YES_OPTION){
                DataManager.waitingRoom.stop();
                DataManager.waitingRoom = null;
            }
        }else{
            DataManager.waitingRoom = new ServerWaitingRoom();
            JOptionPane.showMessageDialog(null, "Server started");
        }
    }
    
    public void join(){
        setScene(new SceneWaitingRoom());
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "JOIN":
                join();
                break;
            case "HOST":
                host();
                break;
            case "BACK":
                setScene(new SceneMap());
                break;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (DataManager.waitingRoom != null) {
            g.setColor(Color.RED);
            g.drawString("The Server is running", 400, 400);
        }
    }

}
