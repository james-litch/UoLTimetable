package com.group51.uoltimetable.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.SessionManager;
import com.group51.uoltimetable.DB.QueryHandler;


public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText usernameTxtBox;
    EditText passwordTxtBox;
    Switch lecturerSwitch;
    SessionManager session;
    QueryHandler queryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        loginBtn = findViewById(R.id.login_button);
        usernameTxtBox = findViewById(R.id.username_txt_box);
        passwordTxtBox = findViewById(R.id.password_txt_box);
        lecturerSwitch = findViewById(R.id.lecturer_switch);

        session = new SessionManager(getApplicationContext());
        //queryHandler = new QueryHandler();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO change this to make it secure and login shit here.
                String username = usernameTxtBox.getText().toString();
                String password = passwordTxtBox.getText().toString();
                session.createLoginSession();
                session.isStudent();
                goToMainActivity();

 /*               if (lecturerSwitch.isChecked() && queryHandler.checkLecturerPassword(username, password)) {
                    session.createLoginSession();
                    session.isLecturer();
                    goToMainActivity();
                    //TODO change this for a lecturer view.
                } else if (!lecturerSwitch.isChecked() && queryHandler.checkStudentPassword(username, password)) {
                    session.createLoginSession();
                    session.isStudent();
                    goToMainActivity();

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Incorrect credentials", Toast.LENGTH_LONG).show();
                }*/

            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}



