import javafx.scene.layout.VBox;
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

            connect.setMinHeight(10);
            connect.setMinWidth(15);
            connect.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ecd5dc;");
            connect.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));

            portInstruction = new Label("Enter Port Number:");
            portInstruction.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

            portTextField = new TextField();
            portTextField.setMaxSize(100, 30);

            Label topLabel= new Label ("Server Port Connection");
            topLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));

            VBox ports = new VBox(portInstruction, portTextField, connect);


            BorderPane root = new BorderPane();
            root.setCenter(ports);
            root.setTop(topLabel);
            root.setAlignment(topLabel, Pos.CENTER);

            ports.setAlignment(Pos.CENTER);
            ports.setSpacing(20);
            return new Scene(root, 700, 600);
        }

}


