package com.example.hp.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUser, edtEmail, edtPassword;
    private Button btnReg;
    private ProgressBar progressRegisterBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtUser = findViewById(R.id.edtRegisterUser);
        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        btnReg = findViewById(R.id.btnRegisterReg);
        progressRegisterBar = findViewById(R.id.progressRegisterBar);

        progressRegisterBar.setVisibility(View.INVISIBLE);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnReg.setVisibility(View.INVISIBLE);
                progressRegisterBar.setVisibility(View.VISIBLE);
                // Reset errors
                edtUser.setError(null);
                edtEmail.setError(null);
                edtPassword.setError(null);

                ParseUser user = new ParseUser();
                // Set the user's username and password, which can be obtained by a forms
                user.setUsername(edtUser.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setPassword(edtPassword.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Dupa",edtEmail.getText().toString());
                            Toast.makeText(RegisterActivity.this,"Successful Registration!" + "\n" + "Welcome " + edtUser.getText().toString() + "!", Toast.LENGTH_LONG).show();
                            ParseUser.logOut();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            ParseUser.logOut();
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            btnReg.setVisibility(View.VISIBLE);
                            progressRegisterBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });



    }
}
