package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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


    public Photo(String caption, Calendar date) {
        this.caption = caption;
        this.date = date;
    }

    // TODO write Photo class
    // TODO photo tags
    // TODO photo date (use Java API)
}
