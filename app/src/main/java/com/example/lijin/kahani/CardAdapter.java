package com.example.lijin.kahani; /**
 * Created by LIJIN on 12/26/2014.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lijin.myapplication.backend.storyApi.model.Story;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.StoryViewHolder> implements Filterable {

    private List<Story> storyCardList;
    private List<Story> mCardList;
    public  CardFilterAdapter cFilter;
    public Context mActivity;
    public CardAdapter(List<Story> storyCardList,Context mActivity) {
        this.storyCardList = storyCardList;
        this.mCardList=storyCardList;
        this.mActivity=mActivity;
    }


    @Override
    public int getItemCount()
    {
        if(storyCardList==null){
            return 0;
        }
        return storyCardList.size();
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, final int i) {
        final Story sc = storyCardList.get(i);
        storyViewHolder.vTitle.setText(sc.getTitle());
        storyViewHolder.vAuthor.setText(sc.getAuthor());
        storyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity,StoryViewActivity.class);
                i.putExtra("STORYID",sc.getId());
                mActivity.startActivity(i);
            }
        });
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new StoryViewHolder(itemView);
    }

    @Override
    public Filter getFilter() {
        if(cFilter==null){
            cFilter=new CardFilterAdapter();
        }
        return cFilter;
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
    public void setList(List<Story> sc){
        this.storyCardList=sc;
        this.mCardList=sc;
        notifyDataSetChanged();
    }
    private class CardFilterAdapter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values=storyCardList;
                results.count=storyCardList.size();
            }
            else {
                List<Story> matchedStoryCardList= new ArrayList<Story>();
                for(Story sc:mCardList){
                    if (sc.getTitle().toUpperCase().startsWith(constraint.toString().toUpperCase())
                            ||sc.getAuthor().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        matchedStoryCardList.add(sc);
                    }
                }
                results.values=matchedStoryCardList;
                results.count=matchedStoryCardList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            storyCardList=(List<Story>)results.values;
            notifyDataSetChanged();
        }
    }
}
