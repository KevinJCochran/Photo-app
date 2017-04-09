package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Admin class keeps a list of all usernames as String objects. There should never be more than one admin therefore
 * it implements the singleton design pattern.
 *
 * @author Kevin Cochran
 *
 */
public class Admin implements Serializable{

    private static Admin ourInstance = readAdmin();
    private ArrayList<String> users;

    /**
     * Private constructor to enforce singleton design pattern
     */
    private Admin() {
        System.out.println("Creating new Admin user list...");
        users = new ArrayList<>();
    }

    /**
     * Will return the single instance of Admin.
     * @return Admin object
     */
    public static Admin getInstance() {
        return ourInstance;
    }

    public ArrayList<String> getUserList() {
        return users;
    }

    /**
     * Adds a user to the list. Will throw exception if it already exists.
     * @return True if username is unique.
     * @param user Username to add.
     */
    public boolean addUser(String user) {
        if (users.contains(user)) {
            return false;
        }
        else {
            users.add(user);
            return true;
        }
    }

    public boolean deleteUser(String user) {
        if (users.contains(user)) {
            users.remove(user);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean renameUser(String oldUser, String newUser) {
        if (users.contains(oldUser) && !users.contains(newUser)) {
            users.remove(oldUser);
            users.add(newUser);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns true if this user was previously created by the admin.
     * @param username the username to look up.
     * @return True if username is valid.
     */
    public boolean isValidUsername(String username) {
        return users.contains(username);
    }

    /**
     * Serialize admin and its list of usernames to 'res/admin'.
     */
    public void writeAdmin() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/data/admin.dat"));
        oos.writeObject(this);
    }

    /**
     * Read in admin from last session
     * @return Admin that was just read in.
     */
    public static Admin readAdmin() {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("res/data/admin.dat"));
            return (Admin) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Admin();
        }
    }

    @Override
    public String toString() {
        return users.toString();
    }
}
