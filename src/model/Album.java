package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that represents album model. Contains a list of photos in album.
 * @author Kevin Cochran
 */
public class Album implements Serializable, Comparable<Album> {

    private String name;
    private int size;
    private Calendar startDate;
    private Calendar endDate;
    public ArrayList<Photo> photos;


    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public Album(String name, ArrayList<Photo> list) {
        this.name = name;
        this.photos = list;
        updateAlbumInfo();
    }

    public boolean addPhoto(Photo photo) {
        if (photos.contains(photo)) return false;
        else {
            photos.add(photo);
            updateAlbumInfo();
            return true;
        }
    }

    // TODO make size and date range dependent on photos list
    public boolean addPhoto(File file) {
        if (file == null) return false;
        System.out.println("Adding photo...");
        String path = file.getAbsolutePath();
        System.out.println("Path: " + path);
        String caption = file.getName();
        System.out.println("Caption: " + caption);
        Calendar date = new GregorianCalendar();
        date.setTime(new Date(file.lastModified()));
        date.set(Calendar.MILLISECOND,0);
        System.out.println("Date: " + calToString(date));
        Photo photo = new Photo(path, caption, date);
        if (photos.contains(photo)) return false;
        else {
            photos.add(photo);
            updateAlbumInfo();
            return true;
        }
    }

    public boolean deletePhoto(Photo photo) {
        if (photos.contains(photo)) {
            photos.remove(photo);
            updateAlbumInfo();
            return true;
        } else {
            return false;
        }
    }

    public boolean recaptionPhoto(Photo photo, String caption) {
        int index = photos.indexOf(photo);
        if (index >= 0) {
            photos.get(index).setCaption(caption);
            return true;
        } else {
            return false;
        }
    }

    public boolean tagPhoto(Photo photo, Tag tag) {
        int index = photos.indexOf(photo);
        if (index >= 0) {
            return photos.get(index).addTag(tag);
        } else {
            return false;
        }
    }

    public boolean removeTag(Photo photo, Tag tag) {
        int index = photos.indexOf(photo);
        return index >= 0 && photos.get(index).deleteTag(tag);
    }

    /**
     * Set the album name.
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get an observable list for JavaFX stuff.
     * @return Obs List created from photos list.
     */
    public ObservableList<Photo> getObsList() {
        return FXCollections.observableList(photos);
    }

    /**
     * Get the name of this album.
     * @return Album name
     */
    public String getName() {
        return name;
    }

    public Photo getPhoto(Photo photo) {
        int index = photos.indexOf(photo);
        if (index >= 0) {
            return photos.get(index);
        } else {
            return null;
        }
    }

    /**
     * Get the number of photos in this album.
     * @return Number of photos
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the starting date of album
     * @return Date as formatted string
     */
    public String getStartDate() {
        return calToString(startDate);
    }

    /**
     * Get the ending date of album
     * @return Date as formatted string
     */
    public String getEndDate() {
        return calToString(endDate);
    }

    /**
     * Helper method to get a Calendar object with date set and hour, second, and
     * millisecond set to zero. Useful for comparing calendars.
     * @param month month
     * @param day day
     * @param year year
     * @return Setup Calendar
     */
    public static Calendar getCalendar(int month, int day, int year) {
        Calendar cal = new GregorianCalendar(year,month,day);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal;
    }

    private void updateAlbumInfo() {
        this.size = photos.size();
        if (photos.size() > 0) {
            Photo photo = photos.get(0);
            Calendar minDate = photo.getDateAsCal();
            Calendar maxDate = photo.getDateAsCal();
            for (Photo p : photos) {
                if (p.getDateAsCal().compareTo(maxDate) > 0) {
                    maxDate = p.getDateAsCal();
                }
                if (p.getDateAsCal().compareTo(minDate) < 0) {
                    minDate = p.getDateAsCal();
                }
            }
            this.startDate = minDate;
            this.endDate = maxDate;
        } else {
            this.startDate = null;
            this.endDate = null;
        }
    }

    /**
     * Helper function that returns formatted date (MM/dd/yyyy) of passed in Calendar.
     * @param cal Calendar to use.
     * @return Formatted string
     */
    public static String calToString(Calendar cal) {
        if (cal == null) return "";
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(cal.getTime());
    }

    /**
     * Just your average toString method.
     * @return String with name and size
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Albums are equal if names are the same.
     * @param o What to compare with
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return name.equals(album.name);
    }

    /**
     * Just your average compare method. Compares album names.
     * @param a What to compare with
     * @return The usual...
     */
    @Override
    public int compareTo(Album a) {
        return name.compareTo(a.getName());
    }
}
