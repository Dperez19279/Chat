/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatfx;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class ChatFX extends Application {

    //MulticastListenerThread listener;
    GeneratorThread generator;
    HBox hbox;
    TabPane tabpane;
    ArrayList<Chat> chats;

    @Override
    public void start(Stage primaryStage) {
        hbox = new HBox();
        tabpane = new TabPane();

        chats = new ArrayList();
        createChat(0, "Unencrypted", "230.0.0.1", "4446");

        hbox.getChildren().addAll(tabpane, new Text("TODO: Controls"));
        HBox.setHgrow(tabpane, Priority.ALWAYS);

        Scene scene = new Scene(hbox, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        launchAllThreads();
    }

    private void createChat(int id, String name, String ip, String port) {
        Chat chat = new Chat(id, name, ip, port);
        chats.add(chat);
        tabpane.getTabs().add(chat.tab);
    }

    @Override
    public void stop() {
        killAllThreads();
    }

    public void processMessage(String string) {
        try {
            String[] parts = string.split(":");

            int id = Integer.parseInt(parts[0]);
            for (Chat chat : chats) {
                if (chat.id == id) {
                    chat.messages.getChildren().add(new Text(parts[1]));
                    return;
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            
        }
        Logger.getLogger(ChatFX.class.getName()).log(Level.INFO, "Unprocessed Message: {0}", string);
    }

    public void launchAllThreads() {
        //listener = new MulticastListenerThread("230.0.0.1", "4446", this);
        //listener.start();
        generator = new GeneratorThread(this);
        generator.start();
    }

    public void killAllThreads() {
        //if (listener != null) listener.interrupt();
        if (generator != null) {
            generator.interrupt();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
