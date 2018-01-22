/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfx;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Chat {
    String name;
    int id;
    String ip;
    String port;
    boolean encrypted;
    Tab tab;
    VBox chatView;
    ScrollPane scroll;
    VBox messages;
    TextField input;
    
    public Chat(int id, String name, String ip, String port) {
        this.name = name;
        this.id = id;
        this.ip = ip;
        this.port = port;
        
        this.encrypted = false;
        tab = new Tab();
        tab.setText(name);
        chatView = new VBox();
        scroll = new ScrollPane();
        messages = new VBox();
        
        messages.heightProperty().addListener((observable, oldvalue, newValue) -> {
            scroll.setVvalue((Double) newValue);
        });
        messages.getChildren().add(new Text("LOL"));
        scroll.setContent(messages);
        
        input = new TextField();
        
        chatView.getChildren().addAll(scroll, input);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        tab.setContent(chatView);
    }
}
