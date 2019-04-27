package com.group51.uoltimetable.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.SessionManager;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText usernameTxtBox;
    EditText passwordTxtBox;
    Switch lecturerSwitch;
    SessionManager session;
    RequestQueue queue;
    String url = "https://student.csc.liv.ac.uk/~sgmbray/DBFetch.php";
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        loginBtn = findViewById(R.id.login_button);
        usernameTxtBox = findViewById(R.id.username_txt_box);
        passwordTxtBox = findViewById(R.id.password_txt_box);
        lecturerSwitch = findViewById(R.id.lecturer_switch);
        queue = Volley.newRequestQueue(this);

        session = new SessionManager(getApplicationContext());


        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO change this to make it secure and login shit here.
                username = usernameTxtBox.getText().toString();
                password = passwordTxtBox.getText().toString();

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == "success") {
                            session.createLoginSession(username);
                            session.setAsStudent();
                            goToMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Details", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("lecturer", Boolean.toString(!lecturerSwitch.isChecked()));
                        params.put("id", username);
                        params.put("pword", password);

                        return params;
                    }
                };;
                queue.add(request);
            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}



