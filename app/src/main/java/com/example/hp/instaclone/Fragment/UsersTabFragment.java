package com.example.hp.instaclone.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.instaclone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTabFragment extends Fragment {

    public UsersTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment. We need variable view to findViewById method

         View view = inflater.inflate(R.layout.fragment_users_tab, container, false);


         return view;
    }

}
