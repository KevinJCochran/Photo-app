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

/**
 * Controller for initial view upon launch.
 * @author Kevin Cochran
 */
public class LoginController {
    @FXML
    protected TextField usernameField;
    @FXML
    protected Button loginButton;

    private Stage currentStage;
    private Admin admin;

    /**
     * Start-up method to set controller fields and set on-close behavior.
     * @param primaryStage
     */
    public void start(Stage primaryStage) {
        currentStage = primaryStage;
        currentStage.setOnCloseRequest(event -> {
            System.out.println("Login closing...");
        });
        admin = Admin.getInstance();
        System.out.println("Users read from disk:\n" + admin.getUserList());
    }

    /**
     * Executed when user clicks "login" or presses enter. Logs the user in or launches admin controls
     */
    public void processLogin() throws IOException, ClassNotFoundException {
        String username = usernameField.getText();
        if (username.equals("")) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error logging in");
            alert2.setHeaderText("Username field was left blank.");
            alert2.setContentText("Please enter a username.");
            alert2.showAndWait();
            return;
        }

        if (username.equals("admin")) {
            currentStage.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AdminView.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Admin Controls");
            newStage.setResizable(false);
            newStage.show();
            AdminViewController adminViewController = loader.getController();
            adminViewController.start(newStage);
            return;
        }

        User user = admin.getUser(username);
        if (user != null || username.equals("test")) {
            currentStage.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UserView.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("photo albums");
            newStage.setResizable(false);
            newStage.show();
            UserViewController userViewController = loader.getController();
            userViewController.start(user,newStage);
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error logging in");
            alert2.setHeaderText("Username does not exist.");
            alert2.setContentText("Please enter a valid username.");
            alert2.showAndWait();
        }
    }
}
