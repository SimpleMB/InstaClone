package com.example.hp.instaclone.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.instaclone.Activity.StartActivity;
import com.example.hp.instaclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ProfileTabFragment extends Fragment {

    private EditText edtUsername, edtBio, edtProfession, edtSports;
    private Button btnUpdate;
    private ParseUser parseUser;

    public ProfileTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtUsername     = view.findViewById(R.id.edtProfileUsername);
        edtBio          = view.findViewById(R.id.edtProfileBio);
        edtProfession   = view.findViewById(R.id.edtProfileProfession);
        edtSports       = view.findViewById(R.id.edtProfileSports);
        btnUpdate       = view.findViewById(R.id.btnProfileUpdate);

        parseUser = ParseUser.getCurrentUser();
        if (parseUser != null) {
            getInfo();
        } else startActivity(new Intent(getContext(), StartActivity.class));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser != null) {
                    updateInfo();
                }
            }
        });


        //This onCreate method need to return object of type view. This return must be at the very bottom.
        return view;

    }

    private void getInfo() {

        // Get info from server
        if (parseUser.get("profileName")!= null) {
            edtUsername.setText(parseUser.get("profileName").toString());
        } else {
            edtUsername.setText("");
        }

        if (parseUser.get("profileBio")!= null) {
            edtBio.setText(parseUser.get("profileBio").toString());
        } else {
            edtBio.setText("");
        }

        if (parseUser.get("profileProfession")!= null) {
            edtProfession.setText(parseUser.get("profileProfession").toString());
        } else {
            edtProfession.setText("");
        }

        if (parseUser.get("profileSports")!= null) {
            edtSports.setText(parseUser.get("profileSports").toString());
        } else {
            edtSports.setText("");
        }
    }


    private void updateInfo() {

        // Update info on server code
        parseUser.put("profileName", edtUsername.getText().toString());
        parseUser.put("profileBio", edtBio.getText().toString());
        parseUser.put("profileProfession", edtProfession.getText().toString());
        parseUser.put("profileSports", edtSports.getText().toString());

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Toast.makeText(getContext(),"Update successfull!",Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
