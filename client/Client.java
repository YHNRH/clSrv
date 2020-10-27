// DEP == DEPRECATED

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Client {
    public static void main(String[] a) {
        MyFrame frame = new MyFrame();

        // Scanner scaner  = new Scanner(System.in);       DEP

        // String reply = "ERROR";
        // System.out.println("Enter your name");          DEP
        // String name = scaner.nextLine();                DEP
        String name = frame.getName();

        try (
            Socket socket = new Socket("localhost", 8080)
        ) {
            try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                Thread thread = new Thread(() ->
                {
                    while (true) {
                        try {
                            frame.getMainArea().printMessage(input.readUTF());

                        } catch (Exception e) {
                            System.out.println("ERRORRRRR");
                        }

                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                        }
                    }
                });

                output.writeUTF(name);
                output.flush();
                // System.out.println("You're connected!\nPrint help to show all commands");
                thread.start();


                frame.getBotPanel().getButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Date date = new Date();
                        try {
                            String request = frame.getBotPanel().getTextField().getText();
                            frame.getBotPanel().getTextField().setText("");
                            output.writeUTF(date + " " + name + "> " + request);
                            output.flush();
                        } catch (Exception ex) {

                        }
                    }
                });

//				while (!request.equals("quit")) {
//					reply = "ERROR";
//					System.out.print(name + "> "); // DEP
//
//					request = scaner.nextLine();
//					output.writeUTF(request);
//					output.flush();
//
//					reply = input.readUTF();
//
//					if (reply.equals("---- You're entered chat room ----")) {
//						System.out.println(
//						    "---- You're entered chat room ----\nPrint /quit to left"
//						);
//						frame.getMainArea().printMessage(
//						    "---- You're entered chat room ----\nPrint /quit to left"
//					    );
//
//						while (!request.equals("/quit")) {
//						}
//						thread.stop();
//						reply = input.readUTF();
//					}
//
//					System.out.println(reply);
//				}

                while (true) {
                    frame.getMainArea().repaint();
                    try {
                        Thread.sleep(150);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        } catch (IOException ex) {
            System.out.println("Cannot connect to server");
        }
    }
}
