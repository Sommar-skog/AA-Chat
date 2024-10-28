import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp {

    static BufferedReader input;
    static PrintWriter writer;
    static ServerSocket serverSocket;
    static Socket clientSocket;

    static void connectToClient(){
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Waiting for client to connect...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            input = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            //Starta tråd för att lyssna efter och hantera inkommande meddelanden från klienten
            new Thread(ServerApp::listeningForMessage).start();
            //Starta tråd för att lyssna efter och hantera meddelanden från serven
            new Thread(ServerApp::sendMessageToClient).start();

        } catch (IOException e) {
            System.out.println("Could not connect");
            e.printStackTrace();
        }
    }

    static void listeningForMessage(){
        String messageFromClient;
        Scanner serverInput = new  Scanner (System.in);

        try {
            while ((messageFromClient = input.readLine()) !=null) {
                    System.out.println(messageFromClient);
                    //Någon mer hantering av meddelandet??
                }
        } catch (Exception e) {
            System.out.println("Connection closed by client");
        }
    }

    static void sendMessageToClient(){
        Scanner serverInput = new Scanner(System.in);
        while (true){
            //Skickar meddelanden från serven
            String serverMessage = serverInput.nextLine();
            if (!serverMessage.trim().isEmpty()) {
                writer.println("Server: " + serverMessage);
            } else {
                System.out.println("Message empty, try again");
            }

        }
    }

    /*static void chat(PrintWriter writer, BufferedReader input){
         boolean exit = false;
                Scanner scanner = new Scanner(System.in);

                while (!exit) {
                    String messageFromClient = null;
                    try {
                        messageFromClient = input.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(messageFromClient);
                    if (messageFromClient.equalsIgnoreCase("Exit chat")){
                        System.out.println("Exit program...client offline");
                        writer.println("Chat offline");
                        exit = true;
                    } else {
                        System.out.print("Server: ");
                        String serverMessage = scanner.nextLine();
                        writer.println(serverMessage);
                    }
                }
    }*/
}
