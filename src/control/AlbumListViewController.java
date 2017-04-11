package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.User;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

public class AlbumListViewController extends AbsController{

    @FXML
    protected TableView<Album> albumTable;
    @FXML
    protected TableColumn<Album, String> albumColumn;
    @FXML
    protected TableColumn<Album,Integer> sizeColumn;
    @FXML
    protected TableColumn<Album, Calendar> startDateColumn, endDateColumn;
    @FXML
    protected TextField searchField;
    @FXML
    protected RadioButton byDateButton, byTagButton;
    @FXML
    protected Button renameButton, deleteButton, createButton, logoutButton;

    private User user;
    private Stage currentStage;

    public void start(User user, Stage currentStage) {
        this.user = new User();
        this.currentStage = currentStage;
        currentStage.setOnCloseRequest(event -> {
            System.out.println("Album list closing...");
            try {
                User.writeUser(this.user);
                System.out.println("Wrote user \"" + this.user + "\" to disk...");
            } catch (IOException e) {
                System.out.println("Failed to write out user " + this.user);
            }
        });
        currentStage.setTitle(this.user + "'s Photo Albums");

        albumColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        albumTable.setItems(this.user.getObsAlbumList());
    }

    public void onCreate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Albums are uniquely identified by name.");
        dialog.setContentText("Please enter a name for your album:");
        Optional<String> result = dialog.showAndWait();
        String input = result.isPresent() ? result.get() : "";
        if (!input.equals("")) {
            if (user.createAlbum(input)) {
                albumTable.setItems(user.getObsAlbumList());
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("User Creation Error");
                alert2.setHeaderText("Username already exists.");
                alert2.setContentText("Please enter a unique username.");
                alert2.showAndWait();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("User Creation Error");
            alert2.setHeaderText("Form was left blank.");
            alert2.setContentText("Please enter a valid username.");
            alert2.showAndWait();
        }
    }

    public void onDelete() {
        Album selection = albumTable.getSelectionModel().getSelectedItem();
        if (user.deleteAlbum(selection)) {
            albumTable.setItems(user.getObsAlbumList());
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("User Deletion Error");
            alert2.setHeaderText("Username does not exist.");
            alert2.setContentText("Please enter a valid username.");
            alert2.showAndWait();
        }
    }

    public void onRename() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Albums are uniquely identified by name.");
        dialog.setContentText("Please enter a new name for your album:");
        Optional<String> result = dialog.showAndWait();
        String input = result.isPresent() ? result.get() : "";
        if (!input.equals("")) {
            Album selection = albumTable.getSelectionModel().getSelectedItem();
            if (user.renameAlbum(selection,input)) {
                albumTable.setItems(user.getObsAlbumList());
                albumTable.refresh();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("User Renaming Error");
                alert2.setHeaderText("Username already exists.");
                alert2.setContentText("Please enter a unique username.");
                alert2.showAndWait();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("User Renaming Error");
            alert2.setHeaderText("Form was left blank.");
            alert2.setContentText("Please enter a unique username.");
            alert2.showAndWait();
        }
    }

    public void onRowClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Album album = albumTable.getSelectionModel().getSelectedItem();
            currentStage.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/AlbumView.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Login");
            newStage.setResizable(false);
            newStage.show();
        }

    }
    @Override
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
