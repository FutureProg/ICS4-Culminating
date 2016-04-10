/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating.scenes;

import icsculminating.DataManager;
import icsculminating.GameButton;
import icsculminating.Main;
import icsculminating.Server;
import icsculminating.ServerWaitingRoom;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick
 */
public class SceneWaitingRoom extends Scene {

    Socket server;
    ObjectInputStream in;
    ObjectOutputStream out;
    String message;
    String host;
    Thread thread;
    boolean stop;
    int number;
    GameButton recBut;

    public SceneWaitingRoom() {
        super();
        bg = DataManager.waitBG;
        GameButton gb = new GameButton(100, 100, 100, 50, "BACK");
        gb.setActionCommand("BACK");
        gb.addActionListener(this);
        add(gb);
        recBut = new GameButton(100, 160, 100, 50, "RECONNECT");
        recBut.setActionCommand("RECONNECT");
        recBut.addActionListener(this);
        add(recBut);
        stop = false;
        message = "";
        number = 0;
        host = null;
        host = JOptionPane.showInputDialog("Enter host name:", DataManager.lastIP);
        if (host == null) {
            setScene(new SceneArena());
            return;
        }
        DataManager.lastIP = host;
        connect();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        });
        thread.start();
    }

    private void connect() {
        try {
            System.out.println("connecting...");
            server = new Socket(InetAddress.getByName(host), Server.SOCKET_NUMBER);
            remove(recBut);
            in = new ObjectInputStream(server.getInputStream());
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            out.writeObject(DataManager.name);
            out.flush();
            System.out.println("CONNECTED");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server");
            Main.manager.setScene(new SceneMap());
        }
    }

    @Override
    public void update() {

    }

    private void checkConnection() {
        try {
            String input = "";
            while (!input.equals(ServerWaitingRoom.STATUS_READY)) {
                try {
                    if (server == null || server.isClosed()) {
                        return;
                    }
                    if (!server.isConnected()) {
                        JOptionPane.showMessageDialog(null, "Lost connection to Server");
                        Main.manager.setScene(new SceneMap());
                        return;
                    }
                    in.readInt();
                    input = (String) in.readObject();
                    switch (input) {
                        case ServerWaitingRoom.STATUS_WAITING:
                            message = "Waiting for players...";
                            break;
                        case ServerWaitingRoom.STATUS_READY:
                            message = "Ready!";
                            break;
                    }
//                System.out.println(message);
                } catch (IOException | ClassNotFoundException ex) {
                    return;
                }
            }
            int id = in.readInt();
            System.out.println("READY");
            setScene(new SceneGameClient(server, id));
        } catch (IOException ex) {
            Logger.getLogger(SceneWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "BACK":
                try {
                    if (server != null && out != null) {
                        out.writeObject(ServerWaitingRoom.LEAVING);
                        server.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SceneWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.manager.setScene(new SceneArena());
                break;
            case "RECONNECT":
                connect();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkConnection();
                    }
                });
                thread.start();
                break;
        }
    }

    public void stop() {
        stop = true;
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.drawString(message, 50, 50);
    }

}
