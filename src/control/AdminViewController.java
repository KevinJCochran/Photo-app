package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class AdminViewController extends AbsController{

    @FXML
    protected Button createButton, renameButton, deleteButton, logoutButton;
    @FXML
    protected ListView<String> userListView;

    private ObservableList<String> obsUserList;
    private Stage currentStage;
    private Admin admin;


    /**
     * Set up the controller to start accepting commands.
     * @param currentStage current stage used to close the window when done
     */
    public void start(Stage currentStage) {
        this.currentStage = currentStage;
        admin = Admin.getInstance();

        obsUserList = FXCollections.observableList(admin.getUserList());
        userListView.setItems(obsUserList);
    }

    /**
     * Creates a new user and adds it to the admin model. When users are created they are immediately
     * written out to disk.
     */
    public void createUser() {
        // Open dialog to ask for username
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create user");
        dialog.setHeaderText("Users are uniquely identified by username.");
        dialog.setContentText("Please enter new username:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String username = result.get();
            if (admin.addUser(username)) {
                userListView.setItems(FXCollections.observableArrayList(admin.getUserList()));
                User newUser = new User(username);
                try {
                    User.writeUser(newUser);
                } catch (IOException e) {
                    System.out.println("Failed to write out new user");
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Username already exists.");
                alert2.setContentText("Please enter a unique username.");
                alert2.showAndWait();
            }
        } else {
            System.out.println("No input entered");
        }
    }

    public void deleteUser() {
        /*TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete user");
        dialog.setHeaderText("Users are uniquely identified by username.");
        dialog.setContentText("Please enter username to delete:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {*/
            String username = userListView.getSelectionModel().getSelectedItem();
            if (admin.deleteUser(username)) {
                userListView.setItems(FXCollections.observableArrayList(admin.getUserList()));
                try {
                    Files.delete(Paths.get("res/data/" + username + ".dat"));
                } catch (IOException e) {
                    System.out.println("Failed to delete " + username);
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Username does not exist.");
                alert2.setContentText("Please enter a valid username.");
                alert2.showAndWait();
            }
        //}
    }

    public void renameUser() {
        // TODO consider rewriting method to copy user with new name instead of attempting to rename.
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("renameUser");
        dialog.setHeaderText("Users are uniquely identified by username.");
        dialog.setContentText("Please enter new username:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String username = result.get();
            String username2 = userListView.getSelectionModel().getSelectedItem();
            if (admin.renameUser(username2,username)) {
                userListView.setItems(FXCollections.observableArrayList(admin.getUserList()));
                if (new File("res/data/" + username2 + ".dat").renameTo(new File("res/data/" + username + ".dat"))) {
                    System.out.println("File successfully renamed");
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Rename Error");
                alert2.setHeaderText("Username does not exist.");
                alert2.setContentText("Please enter a valid username.");
                alert2.showAndWait();
            }
        }
    }

    public void logout() throws IOException {
        admin.writeAdmin();
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
