package com.example.lijin.kahani; /**
 * Created by LIJIN on 12/26/2014.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.StoryViewHolder> {

    private List<StoryCard> storyCardList;

    public CardAdapter(List<StoryCard> storyCardList) {
        this.storyCardList = storyCardList;
    }


    @Override
    public int getItemCount() {
        return storyCardList.size();
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int i) {
        StoryCard sc = storyCardList.get(i);
        storyViewHolder.vTitle.setText(sc.title);
        storyViewHolder.vAuthor.setText(sc.author);
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new StoryViewHolder(itemView);
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vAuthor;
        protected ImageView vPic;

        public StoryViewHolder(View v) {
            super(v);
            vTitle =  (TextView) v.findViewById(R.id.titleText);
            vAuthor = (TextView)  v.findViewById(R.id.authorText);
            vPic = (ImageView)  v.findViewById(R.id.thumbnail_pic);
        }
    }
}
