import java.util.*;

import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Client
{
    public static void main(String[] a)
    {
	Scanner scaner  = new Scanner(System.in);
	String  request = "ERROR";
	String  reply   = "ERROR";
	System.out.println("Enter your name");
	String name = scaner.nextLine();
	
	try (
	     Socket socket  = new Socket("localhost", 8080);

	     )
	    {
		
		try (
		     DataInputStream input   = new DataInputStream(socket.getInputStream());
		     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
		    
		    output.writeUTF(name);
		    output.flush();
		    System.out.println("You're connected!");

		     
		    Thread thread = new Thread(() ->
					       {
						   while(true)
						       {
							   try{
							       System.out.println(input.readUTF());
							   }
							   catch(Exception e)
							       {
								   System.out.println("ERRORRRRR");
							       }
							   try
							       {
								   Thread.sleep(50);
							       }
							   catch(Exception e)
							       {}
						       }
					       });
		   
		    

		    
		    while (!request.equals("quit"))
			{
			    reply = "ERROR";
			    System.out.print(name +"> ");
			    			
			    request = scaner.nextLine();
			    output.writeUTF(request);
			    output.flush();

			    reply = input.readUTF();

			    if (reply.equals("---- You're entered chat room ----"))
				{
		        	    System.out.println("---- You're entered chat room ----");
				    thread.start();
				    while(!request.equals("/quit"))
					{
					    
					    Date date = new Date();
					    
					    request = scaner.nextLine();
					    output.writeUTF(date + " "+ name +"> " + request);
					    output.flush();

					}
				    thread.stop();
				    reply = input.readUTF();
				}

			    System.out.println(reply);
			}

                    
                }
		 catch (IOException ex) {
		     ex.printStackTrace();
        }

	    
	    }
	catch (IOException ex) {
	    System.out.println("Cannot connect to server");
	}
    }
}
