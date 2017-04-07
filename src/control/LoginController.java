package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    protected TextField usernameField;
    @FXML
    protected Button loginButton;

    private Stage currentStage;

    public void start(Stage primaryStage) {
        currentStage = primaryStage;
    }


    public void processLogin(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        if (username.equals("")) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error logging in");
            alert2.setHeaderText("Username field was left blank.");
            alert2.setContentText("Please enter a username.");
            alert2.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/AlbumListView.fxml"));

        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Photo Albums");
        newStage.setResizable(false);
        newStage.show();
        currentStage.hide();


    }
}
