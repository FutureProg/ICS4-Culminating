/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick
 */
public class GameServer implements Runnable {

    ServerSocket server;
    String[] names;
    Socket[] sockets;
    ObjectOutputStream[] output;
    ObjectInputStream[] input;
    private boolean stop;
    Point playerPos;
    int plyrDir, plyrIndex;

    public GameServer(ServerSocket server, String[] names, Socket[] sockets) {
        this.server = server;
        this.names = names;
        input = new ObjectInputStream[2];
        output = new ObjectOutputStream[2];
        this.sockets = sockets;
        stop = false;
        for (int i = 0; i < names.length; i++) {
            try {
                input[i] = new ObjectInputStream(sockets[i].getInputStream());
                output[i] = new ObjectOutputStream(sockets[i].getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        playerPos = null;
        //System.out.println("start");
        while (!stop) {
            try {
                //get protagonist position, direction, and animIndex
                playerPos = (Point) input[0].readObject();
                System.out.println(playerPos.x);
                plyrDir = input[0].readInt();
                plyrIndex = input[0].readInt();
                //check If mouse was pressed 0=false, 1=true
                output[1].write(0);
                output[1].flush();
                MouseEvent mPressed = (MouseEvent) input[1].readObject();
                /*let the player know*/
                output[0].writeObject(mPressed);
                output[0].flush();
                //send the player position, animIndex, and directions
                output[1].writeObject(playerPos);
                output[1].write(plyrIndex);
                output[1].write(plyrDir);
                output[1].flush();
            } catch (Exception ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Server Shut Down");
                stop();
                return;
            }
        }
    }

    public void stop() {
        stop = true;
        if(DataManager.waitingRoom != null){
            DataManager.waitingRoom = null;
        }
    }

}
