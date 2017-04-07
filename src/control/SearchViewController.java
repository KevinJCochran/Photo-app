package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SearchViewController {

    @FXML
    protected Button backButton, logoutButton, addTagButton, deleteTagButton, prevButton, nextButton, newAlbumButton;
    @FXML
    protected ImageView imageView;
    @FXML
    protected Text dateText, captionText;
    @FXML
    protected ListView tagListView, photosListView, resultsListView;

}
