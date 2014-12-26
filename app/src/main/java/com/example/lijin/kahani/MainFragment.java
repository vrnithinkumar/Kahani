package com.example.lijin.kahani;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIJIN on 12/26/2014.
 */
public class MainFragment extends Fragment {

    // newInstance constructor for creating fragment with arguments
    public static MainFragment newInstance() {
        MainFragment fragmentMain = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.card_list_main);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CardAdapter ca = new CardAdapter(createList(30));
        recList.setAdapter(ca);
        return view;
    }
    private List<StoryCard> createList(int size) {

        List<StoryCard> result = new ArrayList<StoryCard>();
        for (int i=1; i <= size; i++) {
            StoryCard ci = new StoryCard();
            ci.title = "dd" + i;
            ci.author ="dffdff" + i;

            result.add(ci);

        }

        return result;
    }
}
