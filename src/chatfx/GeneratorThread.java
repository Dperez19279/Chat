/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfx;

import javafx.application.Platform;

public class GeneratorThread extends Thread {

    ChatFX chat;
    
    public GeneratorThread(ChatFX chat) {
        this.chat = chat;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break; 
            }
            send("0:Fake Message");
        }
    }

    public void send(String string) {
        Platform.runLater(() -> {
            chat.processMessage(string);
        });
    }
}