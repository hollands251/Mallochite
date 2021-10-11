package mallochite.implementations.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import mallochite.models.classes.*;
import mallochite.models.classes.nodes.SubNode;

public class Mallochite 
{
	public static void main ( String [] args ) throws Exception
	{
		
		Scanner scanner = new Scanner( System.in );
		InetAddress inetAddress = InetAddress.getLocalHost();
		SubNode subNode1 = null;
		User thisUser = new User();
		User remoteUser = new User();
		
		
		
		
			
			remoteUser = new User();
			
			remoteUser.setIP( inetAddress.getHostAddress() );
			remoteUser.setPort(23457);
			remoteUser.setUsername( "user1" );
			remoteUser.setUUID( "asdf-123" );
			
		
		
		
		
		thisUser.setUsername( "user2" );
		thisUser.setIP( inetAddress.getHostAddress() );
		thisUser.setPort(12121);
		thisUser.setUUID( "asdf-321" );
		
		thisUser.getUserList().add(remoteUser);

		subNode1 = new SubNode( thisUser );
		
		try
		{
			
			
			subNode1.openServerSocket( subNode1.getThisUser().getPort());
			subNode1.start();
			ChatManager manager  = new ChatManager(subNode1);
			while( subNode1.isListening() )
			{
				manager.menu();
			}   
      
		}
		catch(IOException ex) 
		{
			ex.printStackTrace();
		}
		
		finally
		{
			System.out.println(subNode1.getServerSocket().toString());
			if ( subNode1.getServerSocket() != null )
			{
				
				subNode1.closeServerSocket();
				
			}
		}
        
	}
}