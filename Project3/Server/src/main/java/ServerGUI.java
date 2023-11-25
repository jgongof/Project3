import java.util.HashMap;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.ServerSocket;
import java.net.Socket;


public class ServerGUI extends Application{

    ListView<String> listItems = new ListView<String>();
    Server server;
    private Connectivity connectivity;
    Button connect;
    TextField portTextField;
    Label portInstruction;
    int port;

    ObjectOutputStream out;
    ObjectInputStream in;


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Server");
        //create a listview to see every action from the clients
        primaryStage.setScene(serverConnectionScene());
        connect.setOnAction(e-> {

                    try {
                        port = Integer.parseInt(portTextField.getText());

                        if (port >= 0) {
                            server = new Server(data -> {
                                Platform.runLater(() -> {
                                   //connectivity = (Connectivity) data;
                                   listItems.getItems().add(data.toString());

                                });
                            }, port);
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid port number. Please enter a valid integer.");
                    }

                    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent t) {
                            Platform.exit();
                            System.exit(0);
                        }
                    });


            listItems.setStyle("-fx-background-color: white; -fx-border-color: pink; -fx-border-width: 30");
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(10));
            root.setStyle("-fx-background-color: white");
            root.setCenter(listItems);

            Scene serverCommunication = new Scene(root, 700, 600);
            primaryStage.setScene(serverCommunication);
        });


        primaryStage.show();
    }

        public Scene serverConnectionScene()
        {
            connect = new Button("Connect");
            connect.setStyle("-fx-pref-width: 300px");
            connect.setStyle("-fx-pref-height: 300px");
            connect.setMinHeight(10);
            connect.setMinWidth(15);

            portInstruction = new Label("Enter Port Number:");
            portInstruction.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));

            portTextField = new TextField();
            portTextField.setMinHeight(10);
            portTextField.setMinWidth(10);

            HBox ports = new HBox(portInstruction, portTextField, connect);

            BorderPane root = new BorderPane();
            root.setTop(ports);
            return new Scene(root, 700, 600);
        }

}
