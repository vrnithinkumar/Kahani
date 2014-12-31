package com.example.lijin.kahani;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lijin.myapplication.backend.storyApi.StoryApi;
import com.example.lijin.myapplication.backend.storyApi.model.Story;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LIJIN on 12/26/2014.
 */
public class MainFragment extends Fragment {

    CardAdapter ca=null;
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
        List<Story> s=new ArrayList<Story>();
        ca=new CardAdapter(s,getActivity());
        recList.setLayoutManager(llm);
        recList.setAdapter(ca);
        new StoryListAsyncTask(getActivity()).execute();
        return view;
    }

    public class StoryListAsyncTask  extends AsyncTask<Void, Void, List<Story>> {
        private StoryApi myApiService=null;
        private Context context;
        StoryListAsyncTask(Context context){
            this.context=context;
        }

        @Override
        protected List<Story> doInBackground(Void... params) {
            if(myApiService==null){
                StoryApi.Builder builder = new StoryApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null).setRootUrl("https://carbide-pilot-811.appspot.com/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                myApiService = builder.build();
            }
            try {
                return myApiService.list().execute().getItems();
            }catch (IOException e) {
                return Collections.EMPTY_LIST;
            }
        }
        @Override
        protected void onPostExecute(List<Story> result) {
            ca.setList(result);
        }


    }
}
