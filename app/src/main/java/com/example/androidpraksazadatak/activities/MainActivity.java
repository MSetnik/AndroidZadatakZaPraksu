package com.example.androidpraksazadatak.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidpraksazadatak.Adapters.RecyclerViewAdapter;
import com.example.androidpraksazadatak.R;
import com.example.androidpraksazadatak.model.article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter Adapter;
    private ArrayList<article> lArticles;
    private RecyclerView recycleview;
    private RequestQueue mRequest;
    private JSONObject news = new JSONObject();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleview = findViewById(R.id.recyclerviewid);
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
       lArticles = new ArrayList<>();
       mRequest = Volley.newRequestQueue(this);
       jsonParse();
    }

    private void jsonParse() {

        SharedPreferences pref_json = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        final String old_json = pref_json.getString("jsonData", "");

        SharedPreferences pref_date = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        Date lastStoredDate = new Date(pref_date.getLong("lastTime", 0));

        Date currentDate = new Date(System.currentTimeMillis());
        long difference = currentDate.getTime() - lastStoredDate.getTime();
        long seconds = difference / 1000;
        long minutes = seconds / 60;

        if (minutes > 5 || old_json.length() == 0) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Učitavanje vijesti...");
            progressDialog.show();

            String url = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();


                    getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit().putString("jsonData", response.toString()).apply();

                    SharedPreferences pref = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                    String new_json = pref.getString("jsonData", "");

                    try {
                        news = new JSONObject(new_json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Date date = new Date(System.currentTimeMillis());
                    getSharedPreferences("PREFS_NAME", MODE_PRIVATE).edit().putLong("lastTime", date.getTime()).apply();

                    FillList();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();


                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Greška")
                            .setMessage("Došlo je do pogreške prilikom učitavanja vijesti.")
                            .setCancelable(false)
                            .setPositiveButton("U REDU", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (old_json.length() != 0) {
                                        try {
                                            news = new JSONObject(old_json);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        FillList();
                                    }
                                }
                            })
                            .show();
                }
            });

            mRequest.add(request);
        } else {
            try {
                news = new JSONObject(old_json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FillList();
        }
    }


    public void FillList()
    {
        try {
            JSONArray jsonArray = news.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject article = jsonArray.getJSONObject(i);

                String title = article.getString("title");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");

                lArticles.add(new article(title, url, urlToImage));

            }
            Adapter = new RecyclerViewAdapter(MainActivity.this, lArticles);
            recycleview.setAdapter(Adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


