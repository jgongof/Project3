import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Server extends Thread{

	int playersCount = 0;
	ArrayList<ClientThread> players = new ArrayList<ClientThread>();
	ServerThreads server;
	private Consumer<Serializable> callback;
	public int port;
	CreateCategories categories;

	String correctWord;
	int chances;//3 chances
	Connectivity connectivity;


	Server(Consumer<Serializable> call, int port){

		callback = call;
		this.port = port;
		server = new ServerThreads();
		server.start();

		String announcement1 = "Server Started With Port: " + this.port + ".";
		callback.accept(announcement1);
		String announcement2 = "Server Is Waiting For A Connection...";
		callback.accept(announcement2);
	}


	public void sendUpdatedConnectivity(Connectivity updatedConnectivity) {
		for (int i=0; i<players.size(); i++) {
			try {
				(players.get(i)).out.writeObject(updatedConnectivity);
			} catch (Exception e) {
				e.printStackTrace();;
			}
		}
	}


	public class ServerThreads extends Thread{

		public void run() {

			try(ServerSocket mysocket = new ServerSocket(port)){
				System.out.println("Player Is Connecting...");

				while(true) {

					playersCount++;
					ClientThread playerThread = new ClientThread(mysocket.accept(), playersCount);
					players.add(playerThread);
					callback.accept("Player Connected To Server: " + "Player #" + playersCount);
					System.out.println("Player has connected to the server.");
					playerThread.start();

				}
			}
			catch(Exception e) {
				//	callback.accept("Server Socket Did Not Launch");
			}
		}

	}

	public class ClientThread extends Thread {

		Socket connection;
		int playerCount;
		ObjectInputStream in;
		ObjectOutputStream out;
		//String randomWord = "";
		//Connectivity connectivity = new Connectivity();
		// GameLogic gameLogic = new GameLogic();


		ClientThread(Socket s, int count) {
			this.connection = s;
			this.playerCount = count;
		}

		public void initialization(int playerNumber)
		{
			GameLogic gameLogic1 = new GameLogic();
			ClientThread temp = players.get(playerCount - 1);

			connectivity.correctDessert = gameLogic1.chooseRandomWord(1);
			connectivity.correctFairyTale = gameLogic1.chooseRandomWord(2);
			connectivity.correctCity = gameLogic1.chooseRandomWord(3);

			connectivity.dessertWordLength = connectivity.correctDessert.length();
			connectivity.ftWordLength = connectivity.correctFairyTale.length();
			connectivity.citiesWordLength = connectivity.correctCity.length();

			connectivity.numGuesses = 6;

			connectivity.wonDessert = false;

			connectivity.categoryNumber = 0;

			connectivity.playerActivity = "Initialization";

			connectivity.desserts_attempts = 3;
			connectivity.fairytales_attempts = 3;
			connectivity.cities_attempts = 3;

			try{
				connectivity.command = "connecting";
				temp.out.writeObject(connectivity);
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
			}

		}


		//updateClients("New Player On Server. Player #" + playerCount);
		public void updateClients(String message) {
			for(int i = 0; i < players.size(); i++) {
				ClientThread t = players.get(i);
				try {
					t.out.writeObject(message);
				}
				catch(Exception e) {}
			}
		}

		public void run() {

			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			} catch (Exception e) {
				System.out.println("Streams Are Not Open");
			}
			updateClients("New Player Has Arrived: Player#" + playerCount);

			connectivity = new Connectivity();

			initialization(playerCount);
			GameLogic myGame = new GameLogic();

			while (true) {
				try {
					Connectivity tempConnectivity=  (Connectivity) in.readObject();
					String message = "Player #" + playerCount + ": " + tempConnectivity.playerActivity;
					callback.accept(message);
					tempConnectivity.playerActivity = "";
					System.out.println("Category Number: " + tempConnectivity.categoryNumber);



					if (tempConnectivity.command!=null && tempConnectivity.command.equals("category")){
						tempConnectivity.reset();
						System.out.print("RESETTING CONNECTIVITY");
						myGame.reset();
						System.out.print("RESETTING GAME LOGIC");

						if (tempConnectivity.categoryNumber==1){

							if (tempConnectivity.desserts_attempts<3){
								System.out.print("YOU HAVE LESS THAN THREE ATTEMPTS IN DESSERTS");
								tempConnectivity.correctDessert = myGame.chooseRandomWord(1);
								tempConnectivity.dessertWordLength= tempConnectivity.correctDessert.length();
							}

							System.out.println("Word to guess: " + tempConnectivity.correctDessert);
							System.out.println("Length: " + tempConnectivity.dessertWordLength);

							correctWord = tempConnectivity.correctDessert;
							tempConnectivity.wordLength= tempConnectivity.dessertWordLength;
						}
						else if (tempConnectivity.categoryNumber==2){

							if (tempConnectivity.fairytales_attempts<3){
								System.out.print("YOU HAVE LESS THAN THREE ATTEMPTS IN FAIRY TALES");
								tempConnectivity.correctFairyTale = myGame.chooseRandomWord(2);
								tempConnectivity.ftWordLength= tempConnectivity.correctFairyTale.length();
							}


							System.out.println("Word to guess: " + tempConnectivity.correctFairyTale);
							System.out.println("Length: " + tempConnectivity.ftWordLength);
							correctWord = tempConnectivity.correctFairyTale;
							tempConnectivity.wordLength= tempConnectivity.ftWordLength;
						}
						else if (tempConnectivity.categoryNumber==3){

							if (tempConnectivity.cities_attempts<3){
								System.out.print("YOU HAVE LESS THAN THREE ATTEMPTS IN CITIES");
								tempConnectivity.correctCity = myGame.chooseRandomWord(3);
								tempConnectivity.citiesWordLength= tempConnectivity.correctCity.length();
							}

							System.out.println("Word to guess: " + tempConnectivity.correctCity);
							System.out.println("Length: " + tempConnectivity.citiesWordLength);
							correctWord = tempConnectivity.correctCity;
							tempConnectivity.wordLength= tempConnectivity.citiesWordLength;
						}

						// initialize curr user word in connectivity
						tempConnectivity.currUserWord = new char[tempConnectivity.wordLength];
						connectivity.currUserWord = new char[tempConnectivity.wordLength];

						for (int i=0; i<tempConnectivity.wordLength; i++){
							tempConnectivity.currUserWord[i]= ' ';
							connectivity.currUserWord[i] = ' ';
						}

						//tempConnectivity.currUserWord = myGame.setUserWord(correctWord); // initialize current user word in connectivity
						connectivity.categoryNumber= tempConnectivity.categoryNumber;
						connectivity.wordLength = tempConnectivity.wordLength;
						tempConnectivity.command = "word length";

					}

					System.out.println("this is my guess rn: " + tempConnectivity.userLetter + "++");

					// get character array and send it out
					if(connectivity.command!=null && tempConnectivity.command.equals("Playing")){

//						tempConnectivity.userLetter= connectivity.userLetter
						myGame.correctWord= correctWord;
						System.out.println("correct word: " + myGame.correctWord);
						myGame.setUserWord(myGame.correctWord);
						message = "Player# " + playerCount + ": Checking Letter";
						callback.accept(message);
						myGame.userGuess= tempConnectivity.userLetter;

						System.out.println("Before Checking: user guess: " + myGame.userGuess + " tempCon user guess: " + tempConnectivity.userLetter);
						myGame.checkLetter(myGame.userGuess);
						System.out.println("After Checking: " + "is correct: " + myGame.isCorrectLetter);
						//concantonate them together
						for(int i = 0; i < correctWord.length(); i++)
						{
							if(myGame.currUserWord[i] != ' ')
							{
								tempConnectivity.currUserWord[i] = myGame.currUserWord[i];
							}
						}

						String beString = new String(tempConnectivity.currUserWord);
						System.out.println("String of temp: " +  beString + "--");
						tempConnectivity.gotCorrectLetter = myGame.isCorrectLetter;
						tempConnectivity.numGuesses=myGame.numGuesses;
						tempConnectivity.alreadyGuessed = myGame.alreadyGuessed;

						System.out.print("have i already guessed this letter: " + myGame.userGuess + " --> " + tempConnectivity.alreadyGuessed);

						myGame.checkCorrectWord(tempConnectivity.currUserWord);
						tempConnectivity.gotCorrectWord = myGame.isCorrectWord;
						System.out.println("Correct word-->" + tempConnectivity.gotCorrectWord);
						message = "Did player " + playerCount + " get correct letter --> " +tempConnectivity.gotCorrectLetter;
						callback.accept(message);

						message = " Player # " + playerCount + " guessed letter: " + tempConnectivity.userLetter;

						callback.accept (message);
					}
					if(connectivity.command!=null && tempConnectivity.command.equals("WonCategory")){
						switch (tempConnectivity.categoryNumber)
						{
							case 1:
								tempConnectivity.wonDessert = true;
								break;
							case 2:
								tempConnectivity.wonFairytale = true;
								break;
							case 3:
								tempConnectivity.wonCities = true;
								break;
						}
					}
					sendUpdatedConnectivity(tempConnectivity);
				} catch (Exception e) {
					callback.accept("Something Wrong Happened With The Socket From Player #: " + playerCount + " closing down!" + e.getMessage());
					//updateClients("Player #" + playerCount + " has left the server!");
					players.remove(this);
					playersCount--;
					break;
				}
			}

		}
	}
}




