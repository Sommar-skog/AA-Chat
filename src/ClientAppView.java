import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientAppView extends Application {

    private static ListView<String> chatView;
    Stage window;
    Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        chatView = new ListView<>();


        window = primaryStage;
        window.setOnCloseRequest(event -> {
            event.consume(); //Istället för att stänga ner oavsätt jag eller nej på kontrollfrågan så körs hela metoden och om anv. säger nej stängs inte programmet av
            closeProgram();
        });
        window.setTitle("AA-Chat");

        ClientApp.connectToServer();

        Label label1 = new Label("AA-Chat");

        //Chatruta
        TextField messageField = new TextField();
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                String message = messageField.getText().trim();
                if (!message.isEmpty()){
                    addMessageToChat( ClientApp.userName + ": " + message); //metod för att lägga till meddelande
                    messageField.clear();
                    ClientApp.sendMessageToServer(message); //Metod för att skicka meddelande till server
                }
            }
        });

        Label label2 = new Label("Skriv ditt meddelande. Tryck 'Enter' för att skicka");

        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, chatView, label2, messageField);

        //Scene
        scene = new Scene(layout,400,500);

        window.setScene(scene);
        window.show();
        ClientApp.user();
    }



    private void closeProgram() {
        boolean answer = ConfirmBox.confirmDisplay("Avsluta", "Vill du verkligen avsluta?");
        System.out.println(answer);
        if (answer == true) {
            System.out.println("File is saved");
            window.close();
        }
    }

    public static void addMessageToChat (String message){
        //Uppdatera chatten med meddalanden,
        Platform.runLater(() -> chatView.getItems().add(message));
    }


}
