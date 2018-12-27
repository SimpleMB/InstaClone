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
public class SharePhotoTabFragment extends Fragment {


    public SharePhotoTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_photo_tab, container, false);
    }

}
