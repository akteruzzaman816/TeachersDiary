package com.example.akteruzzaman.tejgaoncollege.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akteruzzaman.tejgaoncollege.R;

/**
 * Created by AKTERUZZAMAN on 10/25/2016.
 */
public class FriFragment extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fri,container,false);
        return v;

    }
}
