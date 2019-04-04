package com.group51.uoltimetable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText emailTxtBox;
    EditText passwordTxtBox;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        loginBtn = (Button) findViewById(R.id.login_button);
        emailTxtBox = (EditText) findViewById(R.id.email_txt_box);
        passwordTxtBox = (EditText) findViewById(R.id.password_txt_box);
        session = new SessionManager(getApplicationContext());



        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO change this to make it secure and login shit here.
                String email = emailTxtBox.getText().toString();
                String password = passwordTxtBox.getText().toString();

                if(email.equals("test")&&password.equals("test")){

                }
                session.createLoginSession();
                goToMainActivity();

            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}



