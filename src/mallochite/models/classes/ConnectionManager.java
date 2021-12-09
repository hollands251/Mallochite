package mallochite.models.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import mallochite.database.DatabaseConnection;
import mallochite.encryption.AESEncryption;

public class ConnectionManager extends Thread {
	private Socket metaSocket; // responsible for listening for incoming connections
	private MallochiteMessageManager mallochiteMessageManager = new MallochiteMessageManager();
	private User thisUser;
	private boolean metaSocketAvailable;

	private boolean debugging = false;

	public ConnectionManager() {
		this.metaSocketAvailable = false; // some method require the metaSocket so throw an exception if
											// metaSocketAvailable is false
	}

	public ConnectionManager(Socket socket) throws IOException, NoSuchAlgorithmException {
		this.metaSocket = socket;
		metaSocketAvailable = true;
	}

	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out = null;
		String localIpAddress = thisUser.getIP();
		String thisUserUuid = String.valueOf(thisUser.getUUID());
		boolean listening = true;

		try {

			// must initialize in and out within a try catch in case of IOException
			in = new BufferedReader( new InputStreamReader(this.metaSocket.getInputStream()) );
			out = new PrintWriter( this.metaSocket.getOutputStream() );
			String messageIn = "";
			String messageOut = "";
			String terminatingString = "RECEIVED";

			while (listening) 
			{

				// move into method to clean up code?
				if (messageIn != null && messageIn != "") {
					// validate message

					HashMap<String, String> parsedData = mallochiteMessageManager.parseDataFromHeader(messageIn);
					// check method
					if ( respondToQuery( parsedData ) != null )
					{
						messageOut = respondToQuery( parsedData );
					}
					else
					{
						//thisUser.addMessageToConversation(parsedData.get("UUID"), messageIn);
						
						
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");  //change format?
	                    LocalDateTime now = LocalDateTime.now();
	                    System.out.println(dtf.format(now));

	                    String CurrentDate = dtf.format(now);
						String[] messageData = splitMessage(messageIn);
						String userName = messageData[0];
						messageIn = messageData[1];
						System.out.println("here" + messageData[0] + messageData[1]);
						int id = DatabaseConnection.getMessagesRecipient(userName);
						DatabaseConnection.messageInsert(messageIn, CurrentDate, 1, 1, id, 0);
						//DatabaseConnection.messageInsert(parsedData.get("message"), "09-30-2021 10:30:54", 1, 1, 1, 0);
						
						messageOut = mallochiteMessageManager.messageRecievedReply(thisUserUuid, localIpAddress);
					}


					out.println(messageOut);
					out.flush();
				}

				if ( messageOut != null && messageOut.contains(terminatingString) )
					listening = false;

				messageIn = in.readLine();

				if (messageIn != null)
						System.out.println(messageIn);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				in.close();
				this.metaSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			out.close();
			this.interrupt();

			if (debugging) {
				System.out.println("Connection closed");
			}
		}
	}
	
	// gets the parsed data of the message. Checks the method and responds with affirm or deny if it is QUERY header.
	// Will respond with null if not a QUERY header
	public String respondToQuery ( HashMap<String, String> parsedData )
	{		
		if ( !parsedData.get( "method" ).equals( "QUERY" ) )
			return null;
		
		boolean userInList = false;
		
		for ( User user : this.thisUser.getUserList() )
		{
			if ( user.getUUID().equals( parsedData.get("UUIDToQuery") ))
				userInList = true;
		}
		
		if ( userInList )
			return "AFFIRM";
		else
			return "NEGATE";
		
	}

	/*
	 * Creates a socket with the intent of sending a "GREET" header with public key
	 * Waits for response from socket and reacts accordingly
	 */
	public void sendMessage(User userToContact, String messageToSend) throws Exception {
		
		if ( this.debugging )
		{
			System.out.println("opening socket to send message");
		}
		
		Socket socket = new Socket(userToContact.getIP(), userToContact.getPort());
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		String messageIn = "";

		try {

			String messageToSendFormated = messageToSend;

			out.println(messageToSendFormated);
			out.flush();
			System.out.println("message sent");

			while (true) {
				if (messageIn != null && messageIn != "") {
					if (debugging) {
						System.out.println(messageIn);
					}

					if (messageIn.contains("RECEIVED")) {
						break;
					}
				}

				messageIn = in.readLine();

			}

		} catch (Exception ex) {
			throw ex;
		}

		finally {
			if ( this.debugging )
			{
				System.out.println("closing socket for sending messages");
			}
			socket.close();
			in.close();
			out.close();
		}
	}

	public Socket getMetaSocket() {
		return metaSocket;
	}

	public void setMetaSocket(Socket metaSocket) throws IOException {
		this.metaSocket = metaSocket;
		this.metaSocketAvailable = true;
	}

	public User getThisUser() {
		return thisUser;
	}

	public void setThisUser(User thisUser) {
		this.thisUser = thisUser;
	}
	
	public String[] splitMessage(String message) {
		String[] tokens = message.split(":"); 
		return tokens;
	}

}
