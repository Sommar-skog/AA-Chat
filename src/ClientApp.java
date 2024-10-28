import java.io.*;
import java.net.Socket;

public class ClientApp {

     static BufferedReader reader;
     static PrintWriter writer;
     static Socket socket;
     static String userName;


    static void connectToServer(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Connected to server");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            //Hantera inkommande meddelanden i separat tråd för att slippa blockad
            new Thread(ClientApp::handleIncomingMessages).start();

        } catch (IOException e){
            System.out.println("Could not connect to server...");
            e.printStackTrace();
        }
    }

   static void user() {
       userName = SettingsWindow.userNameWindow();
   }

    public static void sendMessageToServer(String message){
        if (message != null && !message.trim().isEmpty()){
            writer.println(userName + ": " + message);
        }
    }

    private static void handleIncomingMessages() {
        String messageFromServer;
        try {
           while ((messageFromServer = reader.readLine()) != null){
               //Uppdatera ClientAppView med meddelanden
               ClientAppView.addMessageToChat(messageFromServer);
           }
        } catch (IOException e) {
            System.out.println("Connection closed by server");
        }
    }

    /*static void chat(String name, PrintWriter writer, BufferedReader reader){
       boolean exit = false;
       Scanner userMessage = new Scanner(System.in);

        while (!exit){
            System.out.print(name + ": ");
            String clientMessage = userMessage.nextLine();
            writer.println(name + ": " + clientMessage);

            String serverMessage = null;
            try {
                serverMessage = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Serven: " + serverMessage);

            if (serverMessage.equalsIgnoreCase("Chat offline")) {
                System.out.println("Exit program...");
                exit = true;
            }
        }
    }*/


}
