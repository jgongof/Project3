import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Server{

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



				while (true) {
					try {
						connectivity = (Connectivity) in.readObject();
						callback.accept(connectivity);
						String temp = "Player #" + playerCount + ": " + connectivity.playerActivity;
						callback.accept(temp);
						//temp = "Server recived message from Player #" + playerCount;
						//callback.accept(temp);
						//System.out.println("From server: " + connectivity.categoryWordLength);
						connectivity.playerActivity = "";

						guessingWord = gameLogic.chooseRandomWord(connectivity.categoryNumber);
						System.out.println("Word to guess: " + guessingWord);
						connectivity.categoryWordLength = guessingWord.length();
						System.out.println("Length: " + connectivity.categoryWordLength);

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


	
	

	
