import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
	Socket socketPlayer;
	Clientele player;
	ObjectOutputStream out;
	ObjectInputStream in;
	private Consumer<Serializable> callback;
	int port;

	//Connectivity connectivity;

	Client(Consumer<Serializable> call, int port){

		callback = call;
		this.port = port;
		player = new Clientele();
		player.start();
	}

	public void send(Connectivity message)
	{
		try{
			System.out.println("In Send: " + message.playerActivity);
			out.writeObject(message);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

//	public Connectivity update()
//	{
//		return connectivity;
//	}

	public class Clientele extends Thread{

		public void run() {

			try {
				socketPlayer= new Socket("127.0.0.1",port);
				out = new ObjectOutputStream(socketPlayer.getOutputStream());
				in = new ObjectInputStream(socketPlayer.getInputStream());
				socketPlayer.setTcpNoDelay(true);
			}
			catch(Exception e) {}

			while(true) {

				try {
					Connectivity connectivity = (Connectivity) in.readObject();
					System.out.println("Length of the word is: " + connectivity.dessertWordLength);
					//System.out.println("Updated: " + connectivity.userLetter);
					callback.accept(connectivity);
				}
				catch(Exception e) {}
			}

		}
	}
}