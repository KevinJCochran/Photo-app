package model;

public class UserList {

    private static UserList ourInstance = new UserList();
    //private static ArrayList<User>

    public static UserList getInstance() {
        return ourInstance;
    }

    private UserList() {
    }
}
