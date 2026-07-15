package com.shreya.utils;

import java.util.prefs.Preferences;

public class PasswordManager {

    private static final Preferences preferences =
            Preferences.userNodeForPackage(PasswordManager.class);

    private static final String PASSWORD_KEY = "notesphere_password";

    // Default password
    private static final String DEFAULT_PASSWORD = "1234";

    public static boolean hasPassword() {
        return preferences.get(PASSWORD_KEY, null) != null;
    }

    public static void setPassword(String password) {
        preferences.put(PASSWORD_KEY, password);
    }

    public static boolean checkPassword(String password) {

        String savedPassword =
                preferences.get(
                        PASSWORD_KEY,
                        DEFAULT_PASSWORD);

        return savedPassword.equals(password);
    }

    public static void changePassword(
            String newPassword) {

        preferences.put(
                PASSWORD_KEY,
                newPassword);
    }
}