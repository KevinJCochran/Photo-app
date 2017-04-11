package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class that represents album model. Contains a list of photos in album.
 * @author Kevin Cochran
 */
public class Album implements Serializable, Comparable<Album> {

    private String name;
    private int size;
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<Photo> photos;

    // To be used for testing only.
    public Album() {
        this.name = "Tokyo";
        this.photos.add(new Photo("City", getCalendar(2,9,2009)));
        this.photos.add(new Photo("Nighttime", getCalendar(2,15,2009)));
    }

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, int size, Calendar startDate, Calendar endDate) {
        this.name = name;
        this.size = size;
        this.startDate = startDate;
        this.endDate = endDate;
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

    /**
     * Helper function that returns formatted date (MM/dd/yyyy) of passed in Calendar.
     * @param cal Calendar to use.
     * @return Formatted string
     */
    public static String calToString(Calendar cal) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(cal.getTime());
    }

    /**
     * Just your average toString method.
     * @return String with name and size
     */
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
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
