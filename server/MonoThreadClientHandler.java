import java.io.DataInputStream;
import java.util.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;
    String reply = "ERROR";
    public String message = "";
    public DataOutputStream out;
    public DataInputStream in;
    int state = 0; // 0 -- out 1 -- in chat room
    
    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
	try

	    
	    {
		out = new DataOutputStream(clientDialog.getOutputStream());
		in = new DataInputStream(clientDialog.getInputStream());
	    }
	catch(Exception e)
	    {
		
	    }
    }


    public void setMessage(String m)
    {
	this.message = m;
    }

    public String getMessage()
    {
	return this.message;
    }

    public void printMessage(String m)
    {
	try
	    {
		out.writeUTF(m);
	    }
	catch(IOException e)
	    {
		System.out.println("Sending message error");
	    }
    }

    
    @Override
    public void run() {

        try {
	    Date date = new Date();
		
	    System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");

	    String name = in.readUTF();

            while (!clientDialog.isClosed()) {
		
		reply = "No such command!";

		System.out.println();
                System.out.println("Server reading from channel");

                String entry = in.readUTF();
		String[] words = entry.split(" ");
                System.out.println("READ from " + name + " message - " + entry);






		// инициализация проверки условия продолжения работы с клиентом
                // по этому сокету по кодовому слову - quit в любом регистре
                if (entry.equalsIgnoreCase("quit") && state == 0) {

                    // если кодовое слово получено то инициализируется закрытие
                    // серверной нити
		    reply = "quitting...";
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF(">  " + reply);
                    Thread.sleep(3000);
                    break;
                }

		     if (entry.equalsIgnoreCase("help") && state == 0) {
			 reply = "This is list of commands\nquit - exiting this server\nhelp - show list of all commands.\nenter - enter the chat room";
                	     
                }

		     if (words[0].equalsIgnoreCase("send") && state == 0) {
			 reply = "ty molodec";
                	     
                }

		     if (entry.equalsIgnoreCase("enter") && state == 0) {
			 state = 1;
			 reply = "---- You're entered chat room ----";
			 out.writeUTF(reply);
                	     
                }

		     else if (entry.equals("/quit") && state == 1)
			 {
			 reply = "---- You're left chat room ----";
                	 state = 0;
			 out.writeUTF(reply);
			 
			 out.flush();
			 continue;    
			 }
		     else if (state == 1)  // В чат-комнтае
			 {
	        	     setMessage(entry);
			     System.out.println("message = " + this.getMessage());
			 }
		         if (state == 0)  // Вне чат-комнаты
		     out.writeUTF("server>  " + reply);
                







                out.flush();

                // возвращаемся в началло для считывания нового сообщения
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
