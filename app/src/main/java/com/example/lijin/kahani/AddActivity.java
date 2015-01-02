package com.example.lijin.kahani;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.lijin.myapplication.backend.storyApi.StoryApi;
import com.example.lijin.myapplication.backend.storyApi.model.Story;
import com.example.lijin.myapplication.backend.storyContentApi.StoryContentApi;
import com.example.lijin.myapplication.backend.storyContentApi.model.StoryContent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class AddActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EditText storyText=(EditText)findViewById(R.id.story_add);
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN)
            storyText.setBackground(null);
        else
            storyText.setBackgroundDrawable(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_story) {
            new StoryAddAsyncTask(this).execute();
        }

        return super.onOptionsItemSelected(item);
    }

    public class StoryAddAsyncTask  extends AsyncTask<Void, Void, Long> {
        private StoryApi myApiService=null;
        private Context context;
        private ProgressDialog dialog;
        StoryAddAsyncTask(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            dialog= new ProgressDialog(context);
            dialog.setMessage("Please wait");
            dialog.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
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
                EditText title =(EditText)findViewById(R.id.title_add);
                Story story=new Story();
                story.setAuthor("Lijin");
                story.setTitle(title.getText().toString().trim());
                story.setPages(0);
                return myApiService.insert(story).execute().getId();

            }catch (IOException e) {
                return new Long(-1);
            }
        }
        @Override
        protected void onPostExecute(Long result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            new StoryContentAddAsyncTask(context).execute(result);
            //update main activity after it.
        }


    }

    public class StoryContentAddAsyncTask  extends AsyncTask<Long, Void, Boolean> {
        private StoryContentApi myApiService=null;
        private Context context;
        private ProgressDialog dialog;
        StoryContentAddAsyncTask(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            dialog= new ProgressDialog(context);
            dialog.setMessage("Please wait");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Long... params) {
            if(myApiService==null){
                StoryContentApi.Builder builder = new StoryContentApi.Builder(AndroidHttp.newCompatibleTransport(),
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
                EditText storyText =(EditText)findViewById(R.id.story_add);
                StoryContent content=new StoryContent();
                content.setStory(storyText.getText().toString().trim());
                content.setId(params[0]);
                content.setNextId(new Long(0));
                myApiService.insert(content).execute();
                return true;
            }catch (IOException e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            finish();
            //update main activity after it.
        }


    }


}
