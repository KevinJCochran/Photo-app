package control;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchViewController {

    @FXML
    protected Button backButton, logoutButton, prevButton, nextButton, newAlbumButton;
    @FXML
    protected ImageView imageView;
    @FXML
    protected Text dateText, captionText;
    @FXML
    protected ListView<Tag> tagListView;
    @FXML
    protected ListView<Photo> photosListView;
    @FXML
    protected ListView<Photo> resultsListView;

    private User user;
    private Stage currentStage;
    private static final int BY_DATE = 1;
    private static final int BY_TAG = 2;
    private int mode;
    private String query;
    private ArrayList<Photo> results;

    public void start(Stage currentStage, User user, String mode, String query) {
        this.currentStage = currentStage;
        this.user = user;
        this.query = query;
        System.out.println("Search: " + query);
        if (mode.equals("byDate")) this.mode = BY_DATE;
        if (mode.equals("byTag")) this.mode = BY_TAG;

        currentStage.setOnCloseRequest(event -> {
            System.out.println("Album view closing...");
            try {
                User.writeUser(this.user);
                System.out.println("Wrote user \"" + this.user + "\" to disk...");
            } catch (IOException e) {
                System.out.println("Failed to write out user " + this.user);
            }
        });

        photosListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo photo, boolean empty) {
                super.updateItem(photo, empty);
                if (empty || photo ==null)
                {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    imageView.setImage(photo.getImage());
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    setGraphic(imageView);
                }
            }
        });

        photosListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        imageView.setImage(newValue.getImage());
                        captionText.setText(newValue.getCaption());
                        dateText.setText(newValue.getDateAsString());
                        tagListView.setItems(newValue.getTagsObsList());
                        tagListView.getSelectionModel().select(0);
                    }
                }
        );

        photosListView.getSelectionModel().select(0);

        results = doSearch();
        System.out.println("Results: \n" + results);
        resultsListView.setItems(FXCollections.observableArrayList(results));
        photosListView.setItems(FXCollections.observableArrayList(results));
    }

    private ArrayList<Photo> doSearch() {
        ArrayList<Photo> results = new ArrayList<>();
        if (mode == BY_TAG) {
            String[] str = query.split(":");
            if (str.length < 2) {
                System.out.println("Bad input");
                return null;
            }
            Tag searchTag = new Tag(str[0].trim(),str[1].trim());
            for (Album album : user.albumList) {
                for (Photo photo : album.photos) {
                    for (Tag tag : photo.tags) {
                        if (tag.equals(searchTag) && !results.contains(photo)) {
                            results.add(photo);
                            break;
                        }
                    }
                }
            }
        } else if (mode == BY_DATE) {

        }
        return results;
    }

    public void onNewAlbum() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Album");
        dialog.setHeaderText("Albums are uniquely identified by name");
        dialog.setContentText("Please enter a name for your new album:");
        Optional<String> result = dialog.showAndWait();
        String input = result.isPresent() ? result.get() : "";
        if (!input.equals("")) {
            Album newAlbum = new Album(input, results);
            if (user.createAlbum(newAlbum)) {
                onBack();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Create Error");
                alert2.setHeaderText("Another album is using this name.");
                alert2.setContentText("Please select a different name.");
                alert2.showAndWait();
            }
        }
    }

    public void onNext() {
        int index = photosListView.getSelectionModel().getSelectedIndex();
        if ((index + 1) < results.size()) {
            photosListView.getSelectionModel().select(index + 1);
            photosListView.scrollTo(index + 1);
        }
    }

    public void onPrev() {
        int index = photosListView.getSelectionModel().getSelectedIndex();
        if ((index - 1) >= 0) {
            photosListView.getSelectionModel().select(index - 1);
            photosListView.scrollTo(index - 1);
        }
    }

    public void onBack() throws IOException {
        currentStage.hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/UserView.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Photo albums");
        newStage.setResizable(false);
        newStage.show();
        UserViewController userViewController = loader.getController();
        userViewController.start(user,newStage);
    }

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
