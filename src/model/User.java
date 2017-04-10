package model;

import java.io.*;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Comparable<User>{

    private static final String path = "res" + File.separator + "data" + File.separator;
    public String username;
    private ArrayList<Album> albumList;

    public User(String username) {
        this.username = username;
    }


    @Override
    public int compareTo(User u) {
        return this.username.compareTo(u.username);
    }

    // TODO create album
    // TODO delete album
    // TODO rename album
    // TODO search photos
    // TODO logout

    public static void writeUser(User user) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + user.username + ".dat"));
        oos.writeObject(user);
    }

    public static User readUser(String username) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + username + ".dat"));
        return (User)ois.readObject();
    }

    @Override
    public String toString() {
        return username;
    }
}
