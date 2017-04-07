package model;

import java.io.*;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable, Comparable<User>{

    public String username;

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
    // TODO open album
    // TODO search photos
    // TODO logout

    public static void serializeUser(User user) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("../../res/" + user.username));
        oos.writeObject(user);
    }

    public static User readUser(String username) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("../../res/" + username));
        User user = (User)ois.readObject();
        return user;
    }
}
