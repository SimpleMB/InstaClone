package com.example.hp.instaclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private ProgressBar progressLoginBar;
    private View rootLayoutLogin;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        // Checking if user is logged on. If yes then logOut()

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }

        edtEmail            = findViewById(R.id.edtLoginEmail);
        edtPassword         = findViewById(R.id.edtLoginPassword);
        btnLogin            = findViewById(R.id.btnLoginLogin);
        progressLoginBar    = findViewById(R.id.progressLoginBar);
        rootLayoutLogin     = findViewById(R.id.rootLayoutLogin);


        progressLoginBar.setVisibility(View.INVISIBLE);

        //Setting onKey listener for password field. If user provide password then after clicking "Ok" button on keyboard onClick method is started

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLogin);
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(LoginActivity.this);

        rootLayoutLogin.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {

        //Switch If layout was clicked or button (or enter key on keyboard)
        switch (v.getId()) {

            //Closing the keyboard on clicking the layout
            case R.id.rootLayoutLogin: {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return;
            }

            //Registering user on clicking the btnReg
            case R.id.btnLoginLogin: {
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
                            Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_LONG).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            progressLoginBar.setVisibility(View.INVISIBLE);
                        } else {

                            // If we found something then getUsername and try to login

                            userName = user.getUsername();
                            ParseUser.logInInBackground(userName, edtPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser parseUser, ParseException e) {
                                    if (parseUser != null) {
                                        if (parseUser.getBoolean("emailVerified")) {
                                            Toast.makeText(LoginActivity.this, "Login Sucessful" + "\n" + "Welcome, " + userName, Toast.LENGTH_LONG).show();
                                            btnLogin.setVisibility(View.VISIBLE);
                                            progressLoginBar.setVisibility(View.INVISIBLE);
                                        } else {
                                            ParseUser.logOut();
                                            Toast.makeText(LoginActivity.this, "Login failed" + "\n" + "Please verify your email " + userName, Toast.LENGTH_LONG).show();
                                            btnLogin.setVisibility(View.VISIBLE);
                                            progressLoginBar.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        ParseUser.logOut();
                                        Toast.makeText(LoginActivity.this, "Login Fail" + "\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        btnLogin.setVisibility(View.VISIBLE);
                                        progressLoginBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });


                        }

                    }
                });

            }
        }
    }
}
