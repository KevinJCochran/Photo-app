package control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Controller for Album view when album is opened.
 * @author Kevin Cochran
 */
public class AlbumViewController extends AbsController{
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
    protected ListView<Tag> tagListView;
    @FXML
    protected ListView<Photo> photosListView;


    private static final String pathToRes = (".." + File.separator + ".." + File.separator + "res" + File.separator);
    private Album album;

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

        photosListView.setItems(album.getObsList());

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
    }

    public void onAddPhoto() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(currentStage);
        if (file != null) {
            album.addPhoto(file);
            photosListView.setItems(album.getObsList());
            photosListView.getSelectionModel().select(album.getSize()-1);
        }
    }

    public void onDeletePhoto() {
        Photo photo = photosListView.getSelectionModel().getSelectedItem();
        if (album.deletePhoto(photo)) {
            photosListView.setItems(album.getObsList());
        }
    }

    public void onRecaption() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Recaption");
        dialog.setHeaderText("Captions are not unique so name at will!");
        dialog.setContentText("Please enter a new caption for your photo:");
        Optional<String> result = dialog.showAndWait();
        String input = result.isPresent() ? result.get() : "";
        if (!input.equals("")) {
            Photo photo = photosListView.getSelectionModel().getSelectedItem();
            album.recaptionPhoto(photo,input);
            photosListView.setItems(album.getObsList());
            photosListView.refresh();
        }
    }

    public void onMove() {
        ChoiceDialog<Album> dialog = new ChoiceDialog<>(null,user.albumList);
        dialog.setTitle("Move Photo");
        dialog.setHeaderText("Photo will NOT remain in album");
        dialog.setContentText("Choose new album:");
        Optional<Album> result = dialog.showAndWait();
        Album input = result.isPresent() ? result.get() : null;
        if (input != null) {
            Photo photo = photosListView.getSelectionModel().getSelectedItem();
            photosListView.setItems(album.getObsList());
            if (!user.movePhoto(this.album, input, photo)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Move Error");
                alert2.setHeaderText("Photo already exists in this album.");
                alert2.setContentText("Please select a different album.");
                alert2.showAndWait();
            }
        }
    }

    public void onCopy() {
        ChoiceDialog<Album> dialog = new ChoiceDialog<>(null,user.albumList);
        dialog.setTitle("Copy Photo");
        dialog.setHeaderText("Photo will remain in album");
        dialog.setContentText("Choose album:");
        Optional<Album> result = dialog.showAndWait();
        Album input = result.isPresent() ? result.get() : null;
        if (input != null) {
            Photo photo = photosListView.getSelectionModel().getSelectedItem();
            if (!user.copyPhoto(input, photo)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Copy Error");
                alert2.setHeaderText("Photo already exists in this album.");
                alert2.setContentText("Please select a different album.");
                alert2.showAndWait();
            }
        }
    }

    public void onAddTag() {
        Photo photo = photosListView.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag");
        dialog.setHeaderText("Tags are a unique type, value pair. Format: '[type]:[value]'");
        dialog.setContentText("Please enter a new tag for your photo:");
        Optional<String> result = dialog.showAndWait();
        String input = result.isPresent() ? result.get() : "";
        if (!input.equals("")) {
            String[] inputs = input.split(":");
            inputs[0] = inputs[0].trim();
            inputs[1] = inputs[1].trim();
            System.out.println("type: '" + inputs[0] + "' , value: '" + inputs[1] + "'");
            Tag tag = new Tag(inputs[0], inputs[1]);
            if (album.tagPhoto(photo, tag)) {
                tagListView.setItems(album.getPhoto(photo).getTagsObsList());
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Tag Error");
                alert2.setHeaderText("This photo already has this tag");
                alert2.setContentText("Please enter a new tag.");
                alert2.showAndWait();
            }
        }
    }

    public void onDeleteTag() {
        Tag tag = tagListView.getSelectionModel().getSelectedItem();
        Photo photo = photosListView.getSelectionModel().getSelectedItem();
        album.removeTag(photo,tag);
    }

    public void onNext() {
        int index = photosListView.getSelectionModel().getSelectedIndex();
        if ((index + 1) < album.getSize()) {
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
}