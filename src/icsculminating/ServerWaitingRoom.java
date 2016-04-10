/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsculminating;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick
 */
public class ServerWaitingRoom implements Runnable {

    public static final String STATUS_READY = "01", STATUS_WAITING = "00",
            STATUS_ERROR = "10";
    public static final String LEAVING = "-1";

    ArrayList<String> players;
    ArrayList<Socket> clientSockets;
    ArrayList<ObjectOutputStream> output;
    ArrayList<ObjectInputStream> input;
    ServerSocket socket;
    boolean usingLists;
    Thread thread;
    private boolean stop;

    public ServerWaitingRoom() {
        usingLists = false;
        try {
            players = new ArrayList<>();
            clientSockets = new ArrayList<>(4);
            output = new ArrayList<>(4);
            input = new ArrayList<>(4);
            socket = new ServerSocket(Server.SOCKET_NUMBER);
            thread = new Thread(this);
            thread.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    acceptPlayers();
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            stop = true;
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void run1() {
        System.out.println("Started Server.");
        while (players.size() < 4) {
            try {
                Socket pSocket = socket.accept();
                System.out.println("Accept turn");
                clientSockets.add(pSocket);
                output.add(new ObjectOutputStream(pSocket.getOutputStream()));
                input.add(new ObjectInputStream(pSocket.getInputStream()));
                int index = input.size() - 1;
                ObjectOutputStream out = output.get(index);
                out.flush();
                ObjectInputStream in = input.get(index);
                players.add((String) in.readObject());
                System.out.println(players);
                out.writeObject(STATUS_WAITING);
                out.flush();
                out.writeInt(players.size());
                out.flush();
            } catch (IOException | ClassNotFoundException ex) {

            }
            if (players.size() == 4) {
                checkHasLeft();
            }
        }
        sendReady();
    }

    @Override
    public void run() {
        System.out.println("Started Server");
        while (players.size() < 2) {
            if (usingLists) {
                continue;
            }

//            System.out.println("RUN");
            usingLists = true;
            checkHasLeft();
            for (int i = 0; i < output.size(); i++) {
                if (clientSockets.get(i).isClosed() || !clientSockets.get(i).isConnected()) {
                    output.remove(i);
                    players.remove(i);
                    clientSockets.remove(i);
                    input.remove(i);
                    System.out.println("Removed " + i);
                }
                try {
                    if (stop == true) {
                        output.get(i).writeObject(LEAVING);
                    } else {
                        output.get(i).writeObject(STATUS_WAITING);
                    }
                    output.get(i).flush();
                } catch (IOException ex) {
                    output.remove(i);
                    players.remove(i);
                    clientSockets.remove(i);
                    input.remove(i);
                    System.out.println("Removed " + i);
                }
            }
            usingLists = false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (stop == true) {
                return;
            }
        }
        sendReady();
    }

    private void checkHasLeft() {
        for (int i = 0; i < players.size(); i++) {
//            System.out.println("CHECKING>>>");
            if (clientSockets.get(i).isClosed() || !clientSockets.get(i).isConnected()) {
                try {
                    output.remove(i);
                    players.remove(i);
                    clientSockets.get(i).close();
                    clientSockets.remove(i);
                    input.remove(i);
                    System.out.println("Removed " + i);
                } catch (IOException ex) {
                    Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
                i -= 1;
                continue;
            }
            try {
                output.get(i).writeInt(0);
                output.get(i).flush();
            } catch (Exception ex) {
                output.remove(i);
                players.remove(i);
                clientSockets.remove(i);
                input.remove(i);
                System.out.println("Removed " + i);
                i -= 1;
                continue;
            }
//            try {
//                String str = (String) input.get(i).readObject();
//                if (str.contains(ServerWaitingRoom.LEAVING)) {
//                    output.remove(i);
//                    players.remove(i);
//                    clientSockets.remove(i);
//                    input.remove(i);
//                    System.out.println("Removed " + i);
//                    i--;
//                }
//            } catch (Exception ex) {
//                output.remove(i);
//                players.remove(i);
//                clientSockets.remove(i);
//                input.remove(i);
//                System.out.println("Removed " + i);
//                i--;
//            }
        }
    }

    private synchronized void checkLeaving() {
        while (players.size() < 4) {
            for (int i = 0; i < players.size(); i++) {
                System.out.println("CHECKING>>>");
                if (clientSockets.get(i).isClosed() || !clientSockets.get(i).isConnected()) {
                    try {
                        output.remove(i);
                        players.remove(i);
                        clientSockets.get(i).close();
                        clientSockets.remove(i);
                        input.remove(i);
                        System.out.println("Removed " + i);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i -= 1;
                    continue;
                }
                try {
                    output.get(i).write(0);
                    output.get(i).flush();
                } catch (Exception ex) {
                    output.remove(i);
                    players.remove(i);
                    clientSockets.remove(i);
                    input.remove(i);
                    System.out.println("Removed " + i);
                    i -= 1;
                    continue;
                }
                try {
                    String str = (String) input.get(i).readObject();
                    if (str.contains(ServerWaitingRoom.LEAVING)) {
                        output.remove(i);
                        players.remove(i);
                        clientSockets.remove(i);
                        input.remove(i);
                        System.out.println("Removed " + i);
                        i--;
                    }
                } catch (Exception ex) {
                    output.remove(i);
                    players.remove(i);
                    clientSockets.remove(i);
                    input.remove(i);
                    System.out.println("Removed " + i);
                    i--;
                }
            }
        }
    }

    public void sendReady() {
        usingLists = true;
        checkHasLeft();
        for (int i = 0; i < players.size(); i++) {
            try {
                output.get(i).writeObject(STATUS_READY);
                output.get(i).flush();
                output.get(i).writeInt(i);
                output.get(i).flush();
            } catch (IOException ex) {
                Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("REady!");
        GameServer server = new GameServer(socket, players.toArray(new String[players.size()]), clientSockets.toArray(new Socket[clientSockets.size()]));
    }

    private void acceptPlayers() {
        while (players.size() < 2 && stop == false) {
            try {
                Socket pSocket = socket.accept();
                while (usingLists) {
                    System.out.println("Using lists__pause add");
                }
                usingLists = true;
                System.out.println("Accept turn");
                clientSockets.add(pSocket);
                output.add(new ObjectOutputStream(pSocket.getOutputStream()));
                input.add(new ObjectInputStream(pSocket.getInputStream()));
                int index = input.size() - 1;
                ObjectOutputStream out = output.get(index);
                out.flush();
                ObjectInputStream in = input.get(index);
                players.add((String) in.readObject());
                System.out.println(players);
                usingLists = false;
//                System.out.println("loop");
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ServerWaitingRoom.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        }
    }

}
