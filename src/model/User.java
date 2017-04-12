package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Class that represents the User model. Contains a list of albums that
 * belong the user.
 * @author Kevin Cochran
 */
public class User implements Serializable, Comparable<User>{

    private static final String path = "res" + File.separator + "data" + File.separator;
    public String username;
    public ArrayList<Album> albumList;

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
            albumList.add(new Album(name));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete the album referenced by parameter
     * @param album album to delete
     * @return true if successful
     */
    public boolean deleteAlbum(Album album) {
        if (albumList.contains(album)) {
            albumList.remove(album);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Rename the specified album with the passed in name;
     * @param album album to rename
     * @param newName New name of album. Must not already be in use.
     * @return true if successful
     */
    public boolean renameAlbum(Album album, String newName) {
        if (albumList.contains(album) && !inAlbumList(newName)) {
            int index = albumList.indexOf(album);
            albumList.get(index).setName(newName);
            return true;
        } else {
            return false;
        }
    }

    public boolean movePhoto(Album currentAlbum, Album album, Photo photo) {
        if (albumList.contains(album)) {
            if (album.addPhoto(photo)) {
                currentAlbum.deletePhoto(photo);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean copyPhoto(Album album, Photo photo) {
        if (albumList.contains(album)) {
            return album.addPhoto(photo);
        } else {
            return false;
        }
    }

    // TODO search photos

    /**
     * Helper function to look up albums by string.
     * @param str Album name to look for.
     * @return true if found.
     */
    private boolean inAlbumList(String str) {
        boolean containName = false;
        for (Album a : albumList) {
            if (a.getName().equals(str)) {
                containName = true;
            }
        }
        return containName;
    }

    /**
     * Write user to disk specified by path field.
     * @param user User to write out
     */
    public static void writeUser(User user) throws IOException {
        System.out.println("Attempting to write out user...");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + user.username + ".dat"));
        oos.writeObject(user);
        System.out.println("Successfully wrote user to disk");
    }

    /**
     * Read user in from disk with the passed in username.
     * @param username User to read in.
     * @return User
     */
    public static User readUser(String username) throws IOException, ClassNotFoundException {
        System.out.println("Attempting to read in user...");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + username + ".dat"));
        System.out.println("Successfully read in user");
        return (User)ois.readObject();
    }

    /**
     * Just your average compare method.
     * @param u User to compare with
     * @return zero if equal
     */
    @Override
    public int compareTo(User u) {
        return this.username.compareTo(u.username);
    }

    /**
     * Just your average toSting method.
     * @return Username
     */
    @Override
    public String toString() {
        return username;
    }
}
