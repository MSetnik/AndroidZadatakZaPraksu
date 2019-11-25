package com.example.androidpraksazadatak.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidpraksazadatak.activities.NewsActivity;
import com.example.androidpraksazadatak.model.article;

import java.util.ArrayList;

import com.example.androidpraksazadatak.R;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    private Context mContext;
    private ArrayList<article> mData;

    public RecyclerViewAdapter(Context context, ArrayList<article> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final article currentItem = mData.get(position);

        final String title = currentItem.getTitle();
        final String urlToImage = currentItem.getUrlToImage();
        final String url = currentItem.getUrl();

        holder.title.setText(title);

        Picasso.with(mContext).load(urlToImage).fit().centerCrop().into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lArticles", mData);
                bundle.putInt("position", position);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView image;
        public LinearLayout parentLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.TextView1);
            image = itemView.findViewById(R.id.image);
            parentLayout= itemView.findViewById(R.id.parentLayout);
        }
    }
}

