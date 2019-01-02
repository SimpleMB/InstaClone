package com.example.hp.instaclone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.instaclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUser, edtEmail, edtPassword;
    private Button btnReg;
    private ProgressBar progressRegisterBar;
    private View rootLayoutRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        // Checking if user is logged on. If yes then logOut()

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }

        edtUser = findViewById(R.id.edtRegisterUser);
        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        btnReg = findViewById(R.id.btnRegisterReg);

        progressRegisterBar = findViewById(R.id.progressRegisterBar);
        progressRegisterBar.setVisibility(View.INVISIBLE);

        rootLayoutRegister = findViewById(R.id.rootLayoutRegister);

        //Setting onClick listener for layout. If user want to close keyboard when typing, he just need to click on the background
        rootLayoutRegister.setOnClickListener(RegisterActivity.this);


        //Setting onKey listener for password field. If user provide password then after clicking "Ok" button on keyboard onClick method is started
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //passing on the btnReg id to onClick method
                    onClick(btnReg);
                }

                return false;
            }
        });

        btnReg.setOnClickListener(RegisterActivity.this);


    }

    @Override
    public void onClick(View v) {

        //Switch If layout was clicked or button (or enter key on keyboard)
        switch (v.getId()) {

            //Closing the keyboard on clicking the layout
            case R.id.rootLayoutRegister: {

                //Try will prevent crashes if layout is clicked and keyboard was not opened.

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Registering user on clicking the btnReg
            case R.id.btnRegisterReg: {


                btnReg.setVisibility(View.INVISIBLE);
                progressRegisterBar.setVisibility(View.VISIBLE);

                // Reset errors
                edtUser.setError(null);
                edtEmail.setError(null);
                edtPassword.setError(null);

                //Checking fields
                if (edtUser.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    btnReg.setVisibility(View.VISIBLE);
                    progressRegisterBar.setVisibility(View.INVISIBLE);
                } else {

                    ParseUser user = new ParseUser();
                    // Set the user's username and password, which can be obtained by a forms
                    user.setUsername(edtUser.getText().toString());
                    user.setEmail(edtEmail.getText().toString());
                    user.setPassword(edtPassword.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("Dupa", edtEmail.getText().toString());
                                Toast.makeText(RegisterActivity.this, "Successful Registration!" + "\n" + "Welcome " + edtUser.getText().toString() + "!", Toast.LENGTH_LONG).show();
                                ParseUser.logOut();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                btnReg.setVisibility(View.VISIBLE);
                                progressRegisterBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }

            }
        }
    }
}