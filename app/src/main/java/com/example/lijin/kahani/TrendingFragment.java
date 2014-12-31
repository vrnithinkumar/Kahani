package com.example.lijin.kahani;

/**
 * Created by LIJIN on 12/26/2014.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lijin.myapplication.backend.storyApi.model.Story;
import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {

    // newInstance constructor for creating fragment with arguments
    public static TrendingFragment newInstance() {
        TrendingFragment fragmentMain = new TrendingFragment();
        return fragmentMain;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.card_list_trending);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CardAdapter ca = new CardAdapter(createList(30),getActivity());
        recList.setAdapter(ca);
        return view;
    }
    private List<Story> createList(int size) {

        List<Story> result = new ArrayList<Story>();
        for (int i=1; i <= size; i++) {
            Story ci = new Story();
            ci.setTitle( "dd" + i);
            ci.setAuthor("dffdff" + i);

            result.add(ci);

        }

        return result;
    }
}

