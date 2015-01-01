package com.example.lijin.kahani;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lijin.myapplication.backend.storyContentApi.StoryContentApi;
import com.example.lijin.myapplication.backend.storyContentApi.model.StoryContent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by LIJIN on 12/29/2014.
 */
public class ReaderFragment extends Fragment {
    protected int page;
    protected String story;
    // newInstance constructor for creating fragment with arguments
    public static ReaderFragment newInstance(int page,String story) {
        ReaderFragment fragmentRead = new ReaderFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        args.putString("STORY",story);
        fragmentRead.setArguments(args);
        return fragmentRead;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=getArguments().getInt("PAGE", 0);
        story=getArguments().getString("STORY");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        TextView textStory=(TextView)view.findViewById(R.id.reader_text);
        textStory.setText(story);
        return view;
    }



}

