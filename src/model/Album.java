package model;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Album implements Serializable, Comparable<Album> {

    private String name;
    private int size;
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<Photo> photos;

    public Album(String name, int size, Calendar startDate, Calendar endDate) {
        this.name = name;
        this.size = size;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return name.equals(album.name);
    }

    @Override
    public int compareTo(Album a) {
        return name.compareTo(a.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
