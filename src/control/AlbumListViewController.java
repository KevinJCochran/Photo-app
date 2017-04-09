package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class AlbumListViewController extends AbsController{

    @FXML
    protected TableView albumTable;
    @FXML
    protected TextField searchField;
    @FXML
    protected RadioButton byDateButton, byTagButton;
    @FXML
    protected Button renameButton, deleteButton, createButton, logoutButton;


    private User user;
    private Stage currentStage;

    public void start(User user, Stage currentStage) {
        this.user = user;
        this.currentStage = currentStage;
        currentStage.setTitle(user.username + "'s Photo Albums");
    }
}
