package com.example.androidpraksazadatak.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.androidpraksazadatak.Adapters.ViewPagerAdapter;
import com.example.androidpraksazadatak.R;
import com.example.androidpraksazadatak.model.article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    ArrayList<article> lArticles;
    Integer newsPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundleObject = getIntent().getExtras();
        lArticles = (ArrayList<article>) bundleObject.getSerializable("lArticles");
        newsPosition = bundleObject.getInt("position");


       ViewPager viewPager = findViewById(R.id.viewPager);
       ViewPagerAdapter adapter = new ViewPagerAdapter(this,lArticles);
       viewPager.setAdapter(adapter);
       viewPager.setCurrentItem(newsPosition);
    }
}
