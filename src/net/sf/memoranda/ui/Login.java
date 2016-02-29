package net.sf.memoranda.ui;

import net.sf.memoranda.util.Configuration;

public class Login{
    public static boolean authenticate(String username, String password){
        String user = Configuration.get("USERNAME").toString();
        String pass = Configuration.get("PASSWORD").toString();

        if (username.equals(user) && password.equals(pass)) {
            return true;
        }
        else {
            return false;
        }
    }
}
