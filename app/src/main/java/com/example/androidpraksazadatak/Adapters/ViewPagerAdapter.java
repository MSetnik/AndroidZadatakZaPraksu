package com.example.androidpraksazadatak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.androidpraksazadatak.R;
import com.example.androidpraksazadatak.model.article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<article> lArticles;

    public ViewPagerAdapter(Context context, ArrayList<article>lArticles) {
        this.context = context;
        this.lArticles = lArticles;
    }

    @Override
    public int getCount() {
        return lArticles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_activity,container,false);

        article currentItem = lArticles.get(position);

        ImageView imageUrl =  view.findViewById(R.id.imageNews);
        Picasso.with(context).load(currentItem.getUrlToImage()).into(imageUrl);

        TextView Title = view.findViewById(R.id.TitleNews);
        Title.setText(currentItem.getTitle());

        String urlToNews = currentItem.getUrl();
        initWebView(urlToNews, view);
        container.addView(view);

        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    private void initWebView(String url, View v)
    {
        WebView webView = v.findViewById(R.id.newsContent);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }



}
