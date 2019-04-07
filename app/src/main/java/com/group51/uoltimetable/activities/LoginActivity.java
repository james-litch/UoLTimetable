package com.group51.uoltimetable.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.SessionManager;


public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText emailTxtBox;
    EditText passwordTxtBox;
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



