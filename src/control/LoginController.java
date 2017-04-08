package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.User;

import java.io.IOException;

public class LoginController {
    @FXML
    protected TextField usernameField;
    @FXML
    protected Button loginButton;

    private Stage currentStage;
    private Admin admin;

    public void start(Stage primaryStage) {
        currentStage = primaryStage;
        admin = Admin.getInstance();
    }


    public void processLogin(ActionEvent e) throws IOException, ClassNotFoundException {
        String username = usernameField.getText();
        if (username.equals("")) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error logging in");
            alert2.setHeaderText("Username field was left blank.");
            alert2.setContentText("Please enter a username.");
            alert2.showAndWait();
            return;
        }

        if (admin.isValidUsername(username)) {
            currentStage.hide();
            User user = User.readUser(username);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AlbumListView.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Photo Albums");
            newStage.setResizable(false);
            newStage.show();
            AlbumListViewController albumListViewController = loader.getController();
            albumListViewController.start(user,newStage);
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error logging in");
            alert2.setHeaderText("Username does not exist.");
            alert2.setContentText("Please enter a valid username.");
            alert2.showAndWait();
        }
    }
}
