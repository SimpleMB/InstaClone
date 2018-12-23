package com.example.hp.instaclone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText edtUser, edtEmail, edtPassword;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtUser = findViewById(R.id.edtUser);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnReg = findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Toast.makeText(MainActivity.this,"Sucessful Sign Up!" + "\n" + "Welcome " + edtUser.getText().toString() + "!", Toast.LENGTH_LONG).show();
                        } else {
                            ParseUser.logOut();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }
}
