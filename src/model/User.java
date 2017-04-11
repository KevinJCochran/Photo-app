package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class User implements Serializable, Comparable<User>{

    private static final String path = "res" + File.separator + "data" + File.separator;
    public String username;
    public ArrayList<Album> albumList;

    public User() {
        this.username = "test";
        this.albumList = new ArrayList<>();
        albumList.add(new Album("Nova Scotia",
                12,
                new GregorianCalendar(2014,8,20),
                new GregorianCalendar(2014,8,27)
        ));
        albumList.add(new Album("New York",
                19,
                new GregorianCalendar(2011,12,15),
                new GregorianCalendar(2011,12,17)
        ));
        albumList.add(new Album("Toronto",
                39,
                new GregorianCalendar(2012,9,3),
                new GregorianCalendar(2012,9,10)
        ));
        System.out.println("State of album list at creation:\n" + toString());
    }

    /**
     * Create a new user with the passed username
     * @param username the username for new user
     */
    public User(String username) {
        this.username = username;
        this.albumList = new ArrayList<>();
    }

    /**
     * Get observable List of albums.
     * @return List of albums
     */
    public ObservableList<Album> getObsAlbumList() {
        return FXCollections.observableList(albumList);
    }

    /**
     * Create an Album with the specified name
     * @param name name of new album
     * @return true if successful
     */
    public boolean createAlbum(String name) {
        if (!inAlbumList(name)) {
            albumList.add(new Album(name, 0, null, null));
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAlbum(Album album) {
        if (albumList.contains(album)) {
            albumList.remove(album);
            return true;
        } else {
            return false;
        }
    }

    public boolean renameAlbum(Album album, String newName) {
        if (albumList.contains(album) && !inAlbumList(newName)) {
            int index = albumList.indexOf(album);
            albumList.get(index).setName(newName);
            return true;
        } else {
            return false;
        }
    }

    // TODO search photos

    private boolean inAlbumList(String str) {
        boolean containName = false;
        for (Album a : albumList) {
            if (a.getName().equals(str)) {
                containName = true;
            }
        }
        return containName;
    }
    public static void writeUser(User user) throws IOException {
        System.out.println("Attempting to write out user...");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + user.username + ".dat"));
        oos.writeObject(user);
        System.out.println("Successfully wrote user to disk");
    }

    public static User readUser(String username) throws IOException, ClassNotFoundException {
        System.out.println("Attempting to read in user...");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + username + ".dat"));
        System.out.println("Successfully read in user");
        return (User)ois.readObject();
    }

    @Override
    public int compareTo(User u) {
        return this.username.compareTo(u.username);
    }

    @Override
    public String toString() {
        return username;
    }
}
