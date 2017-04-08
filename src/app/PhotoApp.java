package app;

import control.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class PhotoApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/LoginView.fxml"));

        AnchorPane root = loader.load();
        LoginController loginController = loader.getController();
        loginController.start(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photo Album Viewer");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // TODO safe exit


    public static void main(String[] args) throws IOException {
        /*User kevin = new User("Kevin");
        User Farah = new User("Farah");
        User Isabel = new User("Isabel");
        User.writeUser(kevin);
        User.writeUser(Farah);
        User.writeUser(Isabel);*/
        launch(args);
    }
}
