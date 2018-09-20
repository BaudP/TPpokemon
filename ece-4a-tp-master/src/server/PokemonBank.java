package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import shared.Request;
import shared.Pokemon;

/**
 * This class represents the server application, which is a Pok√©mon Bank.
 * It is a shared account: everyone's Pok√©mons are stored together.
 * @author strift
 *
 */
public class PokemonBank {
	
	public static final int SERVER_PORT = 3000;
	public static final String DB_FILE_NAME = "pokemons.db";
	
	/**
	 * The database instance
	 */
	private Database db;
	
	/**
	 * The ServerSocket instance
	 */
	private ServerSocket server;
	
	/**
	 * The Pok√©mons stored in memory
	 */
	private ArrayList<Pokemon> pokemons;
	
	/**
	 * Constructor
	 //* @param port		the port on which the server should listen
	 //* @param fileName	the name of the file used for the database
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public PokemonBank() throws IOException, ClassNotFoundException {
		/*
		 * TODO
		 * Here, you should initialize the Database and ServerSocket instances.
		 */
		socketserver = new ServerSocket(3000);//On initialise le socket avec le port 3000
		

		System.out.println("Banque Pok√©mon (" + DB_FILE_NAME + ") d√©marr√©e sur le port " + SERVER_PORT);
		
		// Let's load all the Pok√©mons stored in database
		this.pokemons = this.db.loadPokemons();
		this.printState();
	}
	
	/**
	 * The main loop logic of your application goes there.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void handleClient() throws IOException, ClassNotFoundException {
		System.out.println("En attente de connexion...");
		/*
		 * TODO
		 * Here, you should wait for a client to connect.
		 */
		private Socket socket = null;
		socket = server.accept();
		System.out.println("Un client s'est connectÈ");
		
		/*
		 * TODO
		 * You will one stream to read and one to write.
		 * Classes you can use:
		 * - ObjectInputStream
		 * - ObjectOutputStream
		 * - BankOperation
		 */
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
		
		// For as long as the client wants it
		boolean running = true;
		while (running) {
			/*
			 * TODO
			 * Here you should read the stream to retrieve a Request object
			 */
			Request=in.readObject();
			Request request;
			
			/*
			 * Note: the server will only respond with String objects.
			 */
			switch(request) {
			case LIST:
				System.out.println("Request: LIST");
				if (this.pokemons.size() == 0) {
					/*
					 * TODO
					 * There is no Pok√©mons, so just send a message to the client using the output stream.
					 */
					out.println("Il n'y a pas de PokÈmon");
					
				} else {
					/*
					 * TODO
					 * Here you need to build a String containing the list of Pok√©mons, then write this String
					 * in the output stream.
					 * Classes you can use:
					 * - StringBuilder
					 * - String
					 * - the output stream
					 */
					StringWriter sw = new StringWriter();
				    StringReader sr;
				    sr =new StringReader(sw.toString());
				    int i;
				    int j=0;
					String str = "";
					while((i=sr.read())!=-1)//Tant qu'il reste des choses ‡ lire 
						str+=(out.append(pokemons[j])) ;//On va ajouter les pokÈmons dans le string
						j+=j;
					System.out.println(str);
					out.flush();
				}
				break;
				
			case STORE:
				System.out.println("Request: STORE");
				/*
				 * TODO
				 * If the client sent a STORE request, the next object in the stream should be a Pok√©mon.
				 * You need to retrieve that Pok√©mon and add it to the ArrayList.
				 */
				
				
				/*
				 * TODO
				 * Then, send a message to the client so he knows his Pok√©mon is safe Name and level.
				 */
				int i;
				for (int i = 0; i < this.pokemons.size(); i++) {
					if (i > 0) {
						System.out.print(", ");
					}
					out.println(this.pokemons.get(i).toString() + "a bien ÈtÈ reÁu");
				}
				
				

				break;
				
			case CLOSE:
				System.out.println("Request: CLOSE");
				/*
				 * TODO
				 * Here, you should use the output stream to send a nice 'Au revoir !' message to the client. 
				 */
				out.println("Nous vous souhaitons une trËs bonne journÈe");
				out.flush();
				
				// Closing the connection
				System.out.println("Fermeture de la connexion...");
				running = false;
				break;
			}
			this.printState();
		};
		
		/*
		 * TODO
		 * Now you can close both I/O streams, and the client socket.
		 */
		in.close();
		out.close();
		server.close();
		
		/*
		 * TODO
		 * Now that everything is done, let's update the database.
		 */
		
	}
	
	/**
	 * Print the current state of the bank
	 */
	private void printState() {
		System.out.print("[");
		for (int i = 0; i < this.pokemons.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(this.pokemons.get(i));
		}
		System.out.println("]");
	}
	
	/**
	 * Stops the server.
	 * Note: This function will never be called in this project.
	 * @throws IOException 
	 */
	public void stop() throws IOException {
		this.server.close();
	}
}
