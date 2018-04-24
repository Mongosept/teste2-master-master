package com.example.danin.teste;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    List<Movie> lstMovie;
    List<Show> lstShow;
    RecyclerView myrv;
    recyclerViewAdapter myAdapter;
    recyclerViewAdapterShow myAdapterShow;
    Context present=this;
    TextView filmes_tv;
    MainActivity activity;
   // OrientationEventListener mOrientationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstMovie = new ArrayList<>();
        lstShow = new ArrayList<>();

      filmes_tv = (TextView)findViewById(R.id.textView);
        filmes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,MainMovieActivity.class);
                startActivity(myIntent);
            }
        });

        new DataLoaderMovie().execute();
        new DataLoaderShow().execute();
    }



    class DataLoaderMovie extends AsyncTask<Void, Void, String> {

        private Exception exception;

        private String API_KEY="b84f68ff07bfbb69c9b82d27f4b7d0b5";

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }


            try {
                JSONObject result =  (JSONObject) new JSONTokener(response).nextValue();
                JSONArray list=result.getJSONArray("results");

                for (int i=0; i<10;i++){

                    String url=list.getJSONObject(i).getString("poster_path");
                    if(url!=null){
                        String poster_url = "http://image.tmdb.org/t/p/w185/"+url;
                        int id=list.getJSONObject(i).getInt("id");
                        String title=list.getJSONObject(i).getString("title");
                        String description=list.getJSONObject(i).getString("overview");
                        List<Integer> categories=new ArrayList<>();
                        JSONArray genders=list.getJSONObject(i).getJSONArray("genre_ids");
                        Log.d("v","array - "+genders);
                        for (int w=0; w<genders.length();w++){
                            categories.add(genders.getInt(w));
                        }

                        lstMovie.add(new Movie(id,title,categories,description,poster_url));

                    }



                }
                initRecyclerViewMovie();
                initRecyclerViewShow();




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }




    class DataLoaderShow extends AsyncTask<Void, Void, String> {

        private Exception exception;

        private String API_KEY="adf7727ec16c599f65f44f4eb122a27b";

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.themoviedb.org/3/tv/popular?api_key="+API_KEY+"&language=en-US&page=1");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }


            try {
                JSONObject result =  (JSONObject) new JSONTokener(response).nextValue();
                JSONArray list=result.getJSONArray("results");

                for (int i=0; i<10;i++){

                    String url=list.getJSONObject(i).getString("poster_path");
                    if(url!=null){
                        String poster_url = "http://image.tmdb.org/t/p/w185/"+url;
                        int id=list.getJSONObject(i).getInt("id");
                        String title=list.getJSONObject(i).getString("name");
                        String description=list.getJSONObject(i).getString("overview");
                        List<Integer> categories=new ArrayList<>();
                        JSONArray genders=list.getJSONObject(i).getJSONArray("genre_ids");
                        Log.d("v","array - "+genders);
                        for (int w=0; w<genders.length();w++){
                            categories.add(genders.getInt(w));
                        }

                        lstShow.add(new Show(id,title,categories,description,poster_url));

                    }



                }
                initRecyclerViewShow();




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }


    private void initRecyclerViewMovie(){

        activity=this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrv=(RecyclerView) findViewById(R.id.recyclerview);
        myrv.setLayoutManager(layoutManager);
       // myrv.setLayoutManager(new GridLayoutManager(present,200,GridLayoutManager.VERTICAL, false));
        myAdapter = new recyclerViewAdapter(present,lstMovie);
        myrv.setItemAnimator(new DefaultItemAnimator());
        myrv.setAdapter(myAdapter);


       /* LinearLayoutManager LinearLayoutManger = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(LinearLayoutManger);
        recyclerViewAdapter adapter = new recyclerViewAdapter(this,);
        recyclerView.setAdapter(adapter);*/



    }
    private void initRecyclerViewShow(){

        activity=this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrv=(RecyclerView) findViewById(R.id.recyclerview2);
        myrv.setLayoutManager(layoutManager);
        // myrv.setLayoutManager(new GridLayoutManager(present,200,GridLayoutManager.VERTICAL, false));
        myAdapterShow = new recyclerViewAdapterShow(present,lstShow);
        myrv.setItemAnimator(new DefaultItemAnimator());
        myrv.setAdapter(myAdapterShow);


       /* LinearLayoutManager LinearLayoutManger = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(LinearLayoutManger);
        recyclerViewAdapter adapter = new recyclerViewAdapter(this,);
        recyclerView.setAdapter(adapter);*/



    }


}
