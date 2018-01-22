/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfx;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javafx.application.Platform;

public class MulticastListenerThread extends Thread {

    String port;
    String ip;
    private MulticastSocket socket;
    private InetAddress group;
    private byte[] buf;
    private final ChatFX chat;

    public MulticastListenerThread(String ip, String port, ChatFX chat) {
        this.port = port;
        this.ip = ip;
        this.chat = chat;
    }

    @Override
    public void run() {
        try {
            buf = new byte[256];
            socket = new MulticastSocket(Integer.parseInt(port));
            group = InetAddress.getByName(ip);
            socket.joinGroup(group);
            
            while (!Thread.currentThread().isInterrupted()) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                send(received);
                System.out.println("RECEIVED: " + received);
            }
            socket.leaveGroup(group);
            socket.close();
            System.out.println("Properly exited!");
        } catch (IOException ex) {
            //Logger.getLogger(MulticastListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            this.socket.leaveGroup(group);
        } catch (IOException ex) {
            
        }
        this.socket.close();
    }

    public void send(String string) {
        Platform.runLater(() -> {
            chat.processMessage(string);
        });
    }
}
