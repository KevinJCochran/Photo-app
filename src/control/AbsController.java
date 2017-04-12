package control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public abstract class AbsController {

    protected Stage currentStage;
    protected User user;
    /**
     * Executes when user clicks "logout". Current user is written to disk and
     * login window is opened.
     * @throws IOException If FXML file is not found.
     */
    public void logout() throws IOException {
        User.writeUser(this.user);
        currentStage.hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/LoginView.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Login");
        newStage.setResizable(false);
        newStage.show();
        LoginController loginController = loader.getController();
        loginController.start(newStage);
    }
}
