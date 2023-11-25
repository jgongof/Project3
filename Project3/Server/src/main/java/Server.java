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

	String guessingWord;
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
		for (ClientThread t : players) {
			try {
				t.out.writeObject(updatedConnectivity);
			} catch (Exception e) {
				e.printStackTrace();;
			}
		}
	}

	public void updatePlayersConnectivity() {
		sendUpdatedConnectivity(connectivity);

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
			GameLogic gameLogic = new GameLogic();


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

				connectivity.attempts = 6;

				connectivity.gotCorrectLetter = false;
				connectivity.gotCorrectWord = false;

				connectivity.categoryNumber = 0;

				connectivity.playerActivity = "Initialization";

				connectivity.desserts_attempts = 3;
				connectivity.fairytales_attempts = 3;
				connectivity.cities_attempts = 3;

				try{
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

				while (true) {
					try {
						connectivity = (Connectivity) in.readObject();
						System.out.println("#" + playerCount + "+" + connectivity.playerActivity);
						String message = "Player #" + playerCount + ": " + connectivity.playerActivity;
						callback.accept(message);
						connectivity.playerActivity = "";
						System.out.println("Word to guess: " + connectivity.correctDessert);
						//connectivity.categoryWordLength = guessingWord.length();
						System.out.println("Length: " + connectivity.dessertWordLength);

					} catch (Exception e) {
						callback.accept("Something Wrong Happened With The Socket From Player #: " + playerCount + " closing down!");
						//updateClients("Player #" + playerCount + " has left the server!");
						players.remove(this);
						playersCount--;
						break;
					}
				}

			}
		}
	}


	
	

	
