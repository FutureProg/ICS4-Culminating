/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import icsculminating.objects.GameObject;
import icsculminating.objects.Player;
import icsculminating.scenes.SceneBattle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Nick
 */
public class Server implements Runnable {

    public static final int SOCKET_NUMBER = 8888;
    String[] playerNames;
    Player[] players;
    ArrayList<Object> objects;
    Socket[] clientSockets;
    ObjectOutputStream[] output;
    ObjectInputStream[] input;
    ServerSocket socket;
    SceneBattle scene;
    boolean[] data;

    public Server(ServerSocket socket, String[] playerNames, Socket[] clientSockets) throws IOException {
        this.playerNames = playerNames;
        this.clientSockets = clientSockets;
        this.socket = socket;
        objects = new ArrayList<>();
        output = new ObjectOutputStream[playerNames.length];
        input = new ObjectInputStream[playerNames.length];
        for (int i = 0; i < clientSockets.length; i++) {
            output[i] = new ObjectOutputStream(clientSockets[i].getOutputStream());
            input[i] = new ObjectInputStream(clientSockets[i].getInputStream());
        }

        players = new Player[playerNames.length];
        System.out.println(playerNames.length);
        for (int i = 0; i < players.length; i++) {
            int mx = 600, my;
            if (i == 0 || i == 2) {
                mx = 50;
            }
            if (i == 0 || i == 1) {
                my = 50;
            } else {
                my = 800;
            }
            players[i] = new Player(i, mx, my);
            players[i].type = 0;
        }
        System.out.println(players);
        this.scene = new SceneBattle(players);
        new Thread(this).start();
        System.out.println("Battle Start!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 0; i < output.length; i++) {
                    System.out.println("LOOP " + i);
                    data = new boolean[input[i].readInt()];
                    System.out.println(data);
                    for (int j = 0; j < data.length; j++) {
                        data[j] = input[i].readBoolean();
                    }
                    System.out.println(data);
                    int max = input[i].readInt();
                    for (int j = 0; j < max; j++) {
                        scene.addObject((GameObject) input[i].readObject());
                    }
                }
                System.out.println("bla");
                process();
                for (int i = 0; i < output.length; i++) {
                    int[] status = scene.players[i].getStatus();
                    for (int j = 0; j < status.length; j++) {
                        output[i].writeInt(status[j]);
                        output[i].flush();
                    }
                    for (int k = 0; k < scene.players.length; k++) {
                        if (k == i) {
                            continue;
                        }
                        status = scene.players[k].getStatus();
                        for (int j = 0; j < status.length; j++) {
                            output[i].writeInt(status[j]);
                            output[i].flush();
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void process() {
        processInput();
        scene.update();
    }

    public void draw() {

    }

    private void processInput() {
    }

}
