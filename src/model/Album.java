package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Album {

    private String name;
    private int size;
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<Photo> photos;

    public String getName() {
        return name;
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


    // TODO create user

    // TODO delete user
}
