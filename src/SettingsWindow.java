import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow {
    private static String userName;
    private static ListView<String> view = new ListView<>();


    public static String userNameWindow(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //Måste ta hand om fönstret innan användaren kan göra något annat
        window.setTitle("Settings");
        window.setMaxWidth(250);
        window.setMaxHeight(165);

        Label label1 = new Label("Ange användarnamn:");

        TextField userNameFeild = new TextField();
        userNameFeild.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                userName = userNameFeild.getText().trim();
                userNameFeild.clear();
                String savedUserName = "Användarnamn: " + userName;
                view.getItems().add(savedUserName);
            }
        });



        Button closeButton = new Button("Stäng fönstret");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1,userNameFeild, view ,closeButton);
        layout.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return userName;
    }
}
