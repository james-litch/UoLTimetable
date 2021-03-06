package com.group51.uoltimetable.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.group51.uoltimetable.activities.LoginActivity;
import com.group51.uoltimetable.activities.MainActivity;

public class SessionManager {
    private SharedPreferences pref;
    private Context context;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UserPref";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username) {
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean("isLoggedIn", false);
    }


    public void checkLogin() {
        // Check login status
        Intent i;
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            i = new Intent(context, LoginActivity.class);
        } else {
            i = new Intent(context, MainActivity.class);
        }


        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring  Activity
        context.startActivity(i);
    }

    public String getUsername() {
        return pref.getString("username", null);
    }


    public void logoutUser() {
        //clear and save preferences.
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    public void setAsLecturer() {
        editor.putBoolean("isLecturer", true);
        editor.commit();
    }

    public void setAsStudent() {
        editor.putBoolean("isLecturer", false);
        editor.commit();
    }

    public boolean isLecturer() {
        return pref.getBoolean("isLecturer", false);

    }

}
