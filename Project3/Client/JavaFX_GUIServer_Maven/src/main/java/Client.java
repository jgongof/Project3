import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Locale;
import java.util.function.Consumer;

public class Client {
	Socket socketPlayer;
	Clientele player;
	ObjectOutputStream out;
	ObjectInputStream in;
	private Consumer<Serializable> callback;
	int port;

	Connectivity connection = new Connectivity();

	Client(Consumer<Serializable> call, int port){

		callback = call;
		this.port = port;
		player = new Clientele();
		player.start();
	}

	public void message(Connectivity message)
	{
		try{
			out.writeObject(message);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public Connectivity update()
	{
		return connection;
	}

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
					connection = (Connectivity) in.readObject();
					System.out.println("Player Recieved Something From Server.");
					System.out.println("Updated: " + connection.userLetter);
					callback.accept(connection);
				}
				catch(Exception e) {}
			}

		}
	}
}
