package com.example.lijin.kahani;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by LIJIN on 12/29/2014.
 */
public class ReaderFragment extends Fragment {
    protected int page;
    // newInstance constructor for creating fragment with arguments
    public static ReaderFragment newInstance(int page) {
        ReaderFragment fragmentRead = new ReaderFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragmentRead.setArguments(args);
        return fragmentRead;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=getArguments().getInt("PAGE", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);

        return view;
    }

}

