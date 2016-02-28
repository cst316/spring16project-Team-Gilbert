package net.sf.memoranda.ui;

public class Login{
    public static boolean authenticate(String username, String password){
        if (username.equals("user") && password.equals("password")) {
            return true;
        } else {
            return false;
        }
    }
}
