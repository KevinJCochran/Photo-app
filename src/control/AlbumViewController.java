package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.User;
import java.io.IOException;

/**
 * Controller for Album view when album is opened.
 * @author Kevin Cochran
 */
public class AlbumViewController {
    @FXML
    protected TextField searchField;
    @FXML
    protected RadioButton byDateButton, byTagButton;
    @FXML
    protected Button backButton, deleteButton, editButton, logoutButton, addButton, moveButton,
            copyButton, addTagButton, deleteTagButton, prevButton, nextButton;
    @FXML
    protected ImageView imageView;
    @FXML
    protected Text dateText, captionText;
    @FXML
    protected ListView tagListView, photosListView;

    private Stage currentStage;
    private Album album;
    private User user;

    /**
     * Set-up controller fields
     * @param u User that owns album
     * @param a Album to use in controller
     * @param stage current Stage
     */
    public void start(User u, Album a, Stage stage) {
        currentStage = stage;
        this.user = u;
        this.album = a;

        currentStage.setOnCloseRequest(event -> {
            System.out.println("Album view closing...");
            try {
                User.writeUser(this.user);
                System.out.println("Wrote user \"" + this.user + "\" to disk...");
            } catch (IOException e) {
                System.out.println("Failed to write out user " + this.user);
            }
        });
    }

}