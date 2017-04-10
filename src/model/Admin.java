package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static final String path = ("res" + File.separator + "data" + File.separator);
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

    /**
     * Returns list of current usernames
     * @return ArrayList of Strings.
     */
    public ArrayList<String> getUserList() {
        return users;
    }

    /**
     * Checks that user exists and then returns User object that was read from disk.
     * Will create new user if disk does not contain user data or if it was corrupted.
     * @param username username of the user to look for
     * @return User object
     */
    public User getUser(String username) {
        User user;
        if (isValidUsername(username)) {
            try {
                user = User.readUser(username);
                return user;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Data file corrupted...");
                System.out.println("Creating new user file...");
                user = new User(username);
                try {
                    User.writeUser(user);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println("Fail to write out user...");
                }
                return user;
            }
        } else {
            return null;
        }
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
            User newUser = new User(user);
            try {
                User.writeUser(newUser);
            } catch (IOException e) {
                System.out.println("Failed to write out new user");
            }
            return true;
        }
    }

    /**
     * Deletes user specified by the passed in string.
     * @param user user to delete
     * @return true if successful
     */
    public boolean deleteUser(String user) {
        if (users.contains(user)) {
            users.remove(user);
            try {
                Files.delete(Paths.get(path + user + ".dat"));
            } catch (IOException e) {
                System.out.println("Failed to delete " + user);
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Renames user specified by the parameters
     * @param oldUser Current username
     * @param newUser New username
     * @return true if successful
     */
    public boolean renameUser(String oldUser, String newUser) {
        if (users.contains(oldUser) && !users.contains(newUser)) {
            users.remove(oldUser);
            users.add(newUser);
            if (new File(path + oldUser + ".dat").renameTo(new File(path + newUser + ".dat"))) {
                System.out.println("File successfully renamed");
            }
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
    private boolean isValidUsername(String username) {
        return users.contains(username);
    }

    /**
     * Serialize admin and its list of usernames to 'res/admin'.
     */
    public void writeAdmin() throws IOException {
        System.out.println("Writing admin data to: " + path + "admin.dat");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + "admin.dat"));
        oos.writeObject(this);
    }

    /**
     * Read in admin from last session
     * @return Admin that was just read in.
     */
    private static Admin readAdmin() {
        ObjectInputStream ois;
        try {
            System.out.println("Looking for admin data in: " + path + "admin.dat");
            ois = new ObjectInputStream(new FileInputStream(path + "admin.dat"));
            System.out.println("Found admin data...");
            return (Admin) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Admin data not found...");
            return new Admin();
        }
    }

    /**
     * Just your average toString method
     * @return toString of ArrayList
     */
    @Override
    public String toString() {
        return users.toString();
    }
}
