import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


public class ClientGUI extends Application{

    //global variables
    public String chosenCategory;
    public GameLogic myGame;
    private List<Label> lettersList;
//    int categoryOneChances = 3;
//    int categoryTwoChances = 3;
//    int categoryThreeChances = 3;
    public boolean guessedCat1Word= false; //true if user guessed right word
    public boolean guessedCat2Word= false;
    public boolean guessedCat3Word= false;
    public boolean hasWon;
    TextField portTextField;
    Button connect;
    Label portInstruction, errorLabel;

    Consumer<Stage> updateWordCallback;

    Connectivity connectivity = new Connectivity();
    //Server serverConnection; // be able to connect to server
    Client clientConnection;
    int port;
    ListView<String> listItems = new ListView<String>();



    //Getting port number scene
    @Override
    public void start(Stage primaryStage) {
        createTitleScene(primaryStage);

        primaryStage.setTitle("Client");
        primaryStage.setScene(serverConnectionScene());
        primaryStage.show();

        //Action done needing to connect to server
        connect.setOnAction(e->{

            try
            {
                port = Integer.parseInt(portTextField.getText());

                if(port >= 0)
                {

                    clientConnection = new Client(data -> {
                        Platform.runLater(() -> {
                            connectivity = (Connectivity) data;
                            if (connectivity.command == null) {
                                return;
                            }

                            if (connectivity.command.equals("word length")) {
                                createGameScene(primaryStage);
                                return;
                            }

                            if (connectivity.command.equals("Playing")) {
                                updateWordCallback.accept(primaryStage);
                            }

                        });
                    }, port);
                }
            }
            catch(NumberFormatException ex)
            {
                errorLabel.setText("Invalid port number. Please enter a valid integer.");
            }
            createTitleScene(primaryStage); //goes into the title scene to begin the game
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    //Making of the actual port getting scene
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

        Label topLabel= new Label ("Client Port Connection");
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


    // title scene
    void createTitleScene(Stage primaryStage){
        primaryStage.setTitle("Welcome to Hangman!");
        Button pressPlay = new Button("press to play!");
        pressPlay.setMinWidth(100);
        pressPlay.setMinHeight(50);
        pressPlay.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ffffff;");
        pressPlay.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));

        //Title
        Label gameTitle = new Label("Hangman!!");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50));


        VBox titlePageTexts = new VBox (gameTitle, pressPlay);
        titlePageTexts.setSpacing(50);
        titlePageTexts.setAlignment(Pos.CENTER);
        gameTitle.setAlignment(Pos.TOP_CENTER);

        //Make it more in the middle
        BorderPane root = new BorderPane();
        root.setCenter(titlePageTexts);
        Scene titleScene = new Scene(root, 700, 600);
        primaryStage.setScene(titleScene);

        pressPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                createCategoriesScene(primaryStage); //move to a scene where player chose category
            }
        });

        primaryStage.show();
    }

    void updateWord(Stage primaryStage,
                    Label startingMessage,
                    Label endingMessage,
                    Label guessesRemainingLabel,
                    int numLettersInWord) {

        System.out.println("Letters in word: " + connectivity.wordLength);
        System.out.println("Correct letter? " + connectivity.gotCorrectLetter);
        // myGame.setUserGuess(userInput.getText().charAt(0)); // send this to the server

        //Messages given to the user depending on how well they guessed
        // myGame.checkLetter(myGame.userGuess); // server should check the letter


        if (connectivity.alreadyGuessed){
            startingMessage.setText("whoops! you already guessed this letter. try again");
        }
        else if(connectivity.gotCorrectLetter){
            startingMessage.setText(" ' " + connectivity.userLetter + " ' is correct!!");
        }
        else{
            startingMessage.setText(" ' " + connectivity.userLetter + " ' is incorrect. Try again");
            guessesRemainingLabel.setText("Guesses Remaining: " + connectivity.numGuesses);
        }


        //prints forming word with correct letter positions
        for (int i=0; i<numLettersInWord; i++){
            char currChar= connectivity.currUserWord[i];
            if (!Character.isWhitespace(currChar)){
                lettersList.get(i).setText(Character.toString(connectivity.currUserWord[i]));
            }
        }
        String listToString = new String (connectivity.currUserWord);
        System.out.println("Word so far: " + listToString);


        //Set the booleans for each category to true if formed word is correct
        System.out.println("Got correct Word-->" + connectivity.gotCorrectWord);
        if (connectivity.gotCorrectWord){

            if (connectivity.category == "Desserts"){
                connectivity.wonDessert = true;
                connectivity.command = "WonCategory";
            }
            else if (connectivity.category == "Fairy Tales"){
                connectivity.wonFairytale = true;
            }
            else if (connectivity.category == "World Cities"){
                connectivity.wonCities = true;
            }

            //good ending
            endingMessage.setText("Congrats! You guessed the word!");
            endingMessage.setStyle("-fx-text-fill: green;");
            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(3000), event2 ->
            {
                createCategoriesScene(primaryStage);
            }));

            timeline1.play();
        }
        //bad ending
        else if (connectivity.numGuesses==0){

            endingMessage.setText("sorry, you ran out of guesses");
            endingMessage.setStyle("-fx-text-fill: red;");
            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(3000), event2 ->
            {
                createCategoriesScene(primaryStage);
            }));

            timeline2.play();
        }

    }

    //choose which category to play from
    void createCategoriesScene(Stage primaryStage){
        //myGame = new GameLogic();
        System.out.println("In create category");
        Label chooseACatLabel = new Label("Choose a category!");
        chooseACatLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        Button category1Button = new Button("Desserts");
        Button category2Button = new Button("Fairy Tales");
        Button category3Button = new Button("World Cities");

        Label tries1Label = new Label(connectivity.desserts_attempts + "/3 tries remaining");
        Label tries2Label = new Label(connectivity.fairytales_attempts + "/3 tries remaining");
        Label tries3Label = new Label(connectivity.cities_attempts + "/3 tries remaining");

        VBox cat1Info = new VBox(category1Button, tries1Label);
        VBox cat2Info = new VBox(category2Button, tries2Label);
        VBox cat3Info = new VBox(category3Button, tries3Label);


        category1Button.setMinWidth(50);
        category1Button.setMinHeight(25);
        category1Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ffffff;");
        category1Button.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        category2Button.setMinWidth(50);
        category2Button.setMinHeight(25);
        category2Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ffffff;");
        category2Button.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        category3Button.setMinWidth(50);
        category3Button.setMinHeight(25);
        category3Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ffffff;");
        category3Button.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));


        //add labels here for chances
        HBox categoriesHBox = new HBox (cat1Info, cat2Info, cat3Info);
        VBox categoriesVBox= new VBox(chooseACatLabel, categoriesHBox);

        categoriesHBox.setSpacing(20);
        categoriesVBox.setSpacing(25);
        categoriesHBox.setAlignment(Pos.CENTER);
        chooseACatLabel.setAlignment(Pos.TOP_CENTER);
        categoriesVBox.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setCenter(categoriesVBox);
        Scene categoriesScene = new Scene(root, 700, 600);
        primaryStage.setScene(categoriesScene);

        if (connectivity.desserts_attempts == 0 || connectivity.fairytales_attempts == 0 || connectivity.cities_attempts == 0){
            hasWon = false;
            createFinalScene(primaryStage); //goes to final scene if the user lost every chance in one category
        }

        System.out.println("Won-->" + connectivity.wonDessert);

        //disables buttons if got the word right
        if (connectivity.wonDessert){
            category1Button.setDisable(true);
            category1Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #87ae73;");
        }
        if (guessedCat2Word){
            category2Button.setDisable(true);
            category2Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #87ae73;");
        }
        if (guessedCat3Word){
            category3Button.setDisable(true);
            category3Button.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #87ae73;");
        }

        //moves to final scene if got all the categories correct
        if (guessedCat1Word && guessedCat2Word && guessedCat3Word){
            hasWon=true;
            createFinalScene(primaryStage);
        }

        System.out.println("Desserts " + connectivity.desserts_attempts);
        System.out.println("Fairy Tale " + connectivity.fairytales_attempts);
        System.out.println("Cities " + connectivity.cities_attempts);

        ///Action events for all buttons
        category1Button.setOnAction(event -> {
            if (connectivity.desserts_attempts > 0) {
                System.out.println("category 1 clicked!");
                connectivity.category = "desserts";
                connectivity.categoryNumber = 1;
                connectivity.playerActivity = "Chose Dessert Category";
                connectivity.desserts_attempts--;
                connectivity.command= "category";
                clientConnection.message(connectivity);

                // myGame.correctWord=connectivity.;


			//	createGameScene(primaryStage);


            }
        });

        category2Button.setOnAction(event -> {
            if (connectivity.fairytales_attempts > 0) {
                System.out.println("category 2 clicked!");
                connectivity.category = "Fairy Tales";
                connectivity.categoryNumber = 2;
                connectivity.playerActivity = "Chose Fairy Tales Category";
                connectivity.fairytales_attempts--;
                connectivity.command= "category";
                clientConnection.message(connectivity);
//				System.out.print(connectivity.categoryWordLength);

               // createGameScene(primaryStage);
            }
        });

        category3Button.setOnAction(event -> {
            if (connectivity.cities_attempts > 0) {
                System.out.println("category 3 clicked!");

                connectivity.category = "World Cities";
                connectivity.categoryNumber = 3;
                connectivity.playerActivity = "Chose World Cities Category";
                connectivity.cities_attempts--;
                connectivity.command= "category";
                clientConnection.message(connectivity);
//				System.out.print(connectivity.categoryWordLength);


               // createGameScene(primaryStage);
            }
        });
    }

    //The actual gameplay
    void createGameScene(Stage primaryStage){
        //Information on the game
        Label chosenCategoryLabel = new Label("Chosen Category: " + connectivity.category);
        chosenCategoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        Label guessesRemainingLabel = new Label("Guesses Remaining: " + connectivity.numGuesses);
        guessesRemainingLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        BorderPane topBorderPane = new BorderPane();
        topBorderPane.setLeft(chosenCategoryLabel);
        topBorderPane.setRight(guessesRemainingLabel);

        int numLettersInWord = connectivity.wordLength;

        Label startingMessage= new Label("There are " + numLettersInWord + " letters in this word\n You have 6 guesses");
        Label endingMessage = new Label(" ");

        lettersList =  new ArrayList<>();

        HBox lettersInWordHBox = new HBox(numLettersInWord);
        for (int i=0; i<numLettersInWord; i++){
            Label newLetter = new Label ("__");
            newLetter.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
            lettersList.add(newLetter);
            lettersInWordHBox.getChildren().add(newLetter);
        }

        lettersInWordHBox.setAlignment(Pos.CENTER);


        //Make it bigger, instructions to play game
        Label instruction = new Label("Guess A Letter:");
        instruction.setStyle("-fx-background-color: #ffffff;");
        instruction.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));

        TextField userInput = new TextField();
        userInput.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ffff;");
        userInput.setMaxSize(30, 30);

        Button guessButton = new Button();
        guessButton.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #ecd5dc;");
        guessButton.setText("Guess!");
        guessButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));

        VBox guess = new VBox(lettersInWordHBox, startingMessage, instruction,userInput,guessButton, endingMessage);
        guess.setSpacing(10);

        instruction.setAlignment(Pos.TOP_CENTER);
        userInput.setAlignment(Pos.CENTER);
        guessButton.setAlignment(Pos.BOTTOM_CENTER);
        guess.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(topBorderPane);
        root.setCenter(guess);
        root.setStyle("-fx-background-color: #ffffff");
        chosenCategoryLabel.setAlignment(Pos.TOP_RIGHT);

        Scene gameScene = new Scene(root, 700, 600);

        primaryStage.setScene(gameScene);
        primaryStage.show();

        //Use gamelogic here to play the game

        //would be going forever if user got answer correct without miss guessing a letter
        if (connectivity.numGuesses > 0 && !connectivity.gotCorrectWord){

            guessButton.setOnAction(event->{

                connectivity.userLetter= userInput.getText().charAt(0);
                System.out.println("Letter input: " + connectivity.userLetter + ": Sending to server");
                connectivity.command= "Playing";
                updateWordCallback = stage -> updateWord(stage,
                        startingMessage,
                        endingMessage,
                        guessesRemainingLabel,
                        numLettersInWord);

                clientConnection.message(connectivity);
            });
        }
        else{
            createCategoriesScene(primaryStage);
        }

    }

    //final scene
    void createFinalScene(Stage primaryStage){
        Label winningLabel = new Label ();

        if (hasWon){
            winningLabel.setText("CONGRATS! You won the game!!!");
            winningLabel.setStyle("-fx-text-fill: green;");
        }
        else{
            winningLabel.setText("OH NO! You Lost :(");
            winningLabel.setStyle("-fx-text-fill: red;");
        }

        winningLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));

        Button playAgainButton = new Button("Play Again");
        Button quitButton = new Button ("Quit");
        //play again
        playAgainButton.setMinWidth(50);
        playAgainButton.setMinHeight(25);
        playAgainButton.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #87ae73;");
        playAgainButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        //exit
        quitButton.setMinWidth(50);
        quitButton.setMinHeight(25);
        quitButton.setStyle("-fx-border-color: #ecd5dc; -fx-border-width: 1px; -fx-background-color: #D87272;");
        quitButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));

        playAgainButton.setOnAction(event -> {
            // reset whole game
            connectivity.desserts_attempts = 3;
            connectivity.fairytales_attempts = 3;
            connectivity.cities_attempts = 3;
            connectivity.currUserWord = null;
            guessedCat1Word= false;
            guessedCat2Word= false;
            guessedCat3Word= false;
            hasWon=false; // has player won the game
            createTitleScene(primaryStage);
        });

        //should also make the server output saying that a player logged off
        quitButton.setOnAction(event -> {
            Platform.exit();
        });


        HBox buttonsHBox= new HBox(playAgainButton, quitButton);

        VBox allFinalSceneVBox = new VBox(winningLabel, buttonsHBox);
        buttonsHBox.setAlignment(Pos.CENTER);
        allFinalSceneVBox.setAlignment(Pos.CENTER);
        allFinalSceneVBox.setSpacing(25);

        BorderPane root= new BorderPane();
        root.setCenter(allFinalSceneVBox);
        root.setStyle("-fx-background-color: #ffffff");

        Scene finalWinningScene = new Scene(root, 700, 600);

        primaryStage.setScene(finalWinningScene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
