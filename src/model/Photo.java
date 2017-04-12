package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Class that represents the photo model. Contains a list of tags on this photo.
 * @author Kevin Cochran
 */
public class Photo implements Serializable{

    private String caption;
    private Calendar date;
    private List<Tag> tags;
    private String pathToImage;


    public Photo(String path, String caption, Calendar date) {
        this.pathToImage = path;
        this.caption = caption;
        this.date = date;
        this.tags = new ArrayList<>();
    }

    public Image getImage() {
        System.out.println("Looking for photo in: " + pathToImage);
        File file = new File(pathToImage);
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            System.out.println("Bad URL...");
        }
        return (localUrl != null) ? new Image(localUrl) : null;
    }

    public String getCaption() {
        return caption;
    }

    public String getPath() {
        return pathToImage;
    }

    public ObservableList<Tag> getTagsObsList() {
        return FXCollections.observableList(tags);
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateAsString() {
        return Album.calToString(this.date);
    }

    public Calendar getDateAsCal() {
        return date;
    }

    public boolean addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTag(Tag tag) {
        return tags.remove(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return pathToImage.equals(photo.getPath());
    }

    // TODO write Photo class
    // TODO photo tags
    // TODO photo date (use Java API)
}
