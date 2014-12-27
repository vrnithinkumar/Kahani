package com.example.lijin.kahani;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView recList = (RecyclerView) findViewById(R.id.card_list_search);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        final CardAdapter ca = new CardAdapter(createList(30));
        recList.setAdapter(ca);
        final EditText search=(EditText)findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                // adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ca.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
