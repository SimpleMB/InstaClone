package com.example.hp.instaclone.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.hp.instaclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UsersTabFragment extends Fragment {

    private ListView lstUsersListView;
    private ArrayList usersList;
    private ArrayAdapter arrayAdapter;
    private ProgressBar barUsersProgressBar;


    public UsersTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment. We need variable view to findViewById method

        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);



        // Initializing layout object that will be populated with data
        lstUsersListView = view.findViewById(R.id.lstUsersListView);
        lstUsersListView.setVisibility(View.INVISIBLE);

        // Ini progressbar to appear before we load data from query
        barUsersProgressBar = view.findViewById(R.id.barUsersProgressBar);
        barUsersProgressBar.setVisibility(View.VISIBLE);


        // Creating list of users that we get from ParseQuery
        usersList = new ArrayList();


        // Creating adapter to populate list object from layout - lstUsersListView.
        // In ArrayAdapter we need to specify Context, how this list will look (layout of the list) and we need to pass the ArrayList with data to be displayed.
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, usersList);



        // Now we create a server query to get users

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        // We want to find every user that is different from current user

        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().toString());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                //Now an automatic List called objects will be created. This list will store every user that query found
                // If something went wrong we get Exception e, if query found nothing size of the object list will be zero
                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        usersList.add(user.getUsername());

                    }
                    lstUsersListView.setAdapter(arrayAdapter);
                    barUsersProgressBar.setVisibility(View.INVISIBLE);
                    lstUsersListView.setVisibility(View.VISIBLE);

                }
            }
        });

         return view;
    }

}
