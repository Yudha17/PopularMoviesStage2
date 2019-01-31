package com.example.slametsudarsono.popularmoviesstage2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slametsudarsono.popularmoviesstage2.R;
import com.example.slametsudarsono.popularmoviesstage2.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{
    private Context mContext;
    private List<Review> reviewList;

    public ReviewAdapter(Context mContext, List<Review> reviewResults) {
        this.mContext = mContext;
        this.reviewList= reviewResults;

    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_card, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder (final ReviewAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title3.setText(reviewList.get(i).getAuthor());
        String url = reviewList.get(i).getUrl();
        viewHolder.url.setText(url);
        Linkify.addLinks(viewHolder.url, Linkify.WEB_URLS);

    }

    @Override
    public int getItemCount(){return reviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title3, url;

        public MyViewHolder(View view){
            super(view);
            title3 = (TextView) view.findViewById(R.id.review_author);
            url = (TextView) view.findViewById(R.id.review_link);

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();

                }

            });
        }
    }

}
