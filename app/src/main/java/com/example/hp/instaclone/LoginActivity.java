package com.example.hp.instaclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private ProgressBar progressLoginBar;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail            = findViewById(R.id.edtLoginEmail);
        edtPassword         = findViewById(R.id.edtLoginPassword);
        btnLogin            = findViewById(R.id.btnLoginLogin);
        progressLoginBar    = findViewById(R.id.progressLoginBar);

        progressLoginBar.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setVisibility(View.INVISIBLE);
                progressLoginBar.setVisibility(View.VISIBLE);

                // New Parse Query to find if there is a user with matching email.
                ParseQuery<ParseUser> query = ParseUser.getQuery();

                // Checking if in column email is record equal to email provided by user then...
                query.whereEqualTo("email", edtEmail.getText().toString());

                //...Getting callback

                query.getFirstInBackground(new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {


                        // If callback is null then record with email not found


                        if (user == null) {
                            Toast.makeText(LoginActivity.this,"Email not found", Toast.LENGTH_LONG).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            progressLoginBar.setVisibility(View.INVISIBLE);
                        } else {

                        // If we found something then getUsername and try to login

                            userName = user.getUsername();
                            ParseUser.logInInBackground(userName, edtPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser parseUser, ParseException e) {
                                    if (parseUser != null) {
                                        if(parseUser.getBoolean("emailVerified")) {
                                            Toast.makeText(LoginActivity.this,"Login Sucessful"+"\n"+"Welcome, " + userName, Toast.LENGTH_LONG).show();
                                            btnLogin.setVisibility(View.VISIBLE);
                                            progressLoginBar.setVisibility(View.INVISIBLE);
                                        }
                                        else
                                        {
                                            ParseUser.logOut();
                                            Toast.makeText(LoginActivity.this,"Login failed"+"\n"+"Please verify your email " + userName, Toast.LENGTH_LONG).show();
                                            btnLogin.setVisibility(View.VISIBLE);
                                            progressLoginBar.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        ParseUser.logOut();
                                        Toast.makeText(LoginActivity.this, "Login Fail"+"\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        btnLogin.setVisibility(View.VISIBLE);
                                        progressLoginBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });


                        }

                    }
                });
            }
        });
    }
}
