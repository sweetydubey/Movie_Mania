package com.example.moviemania;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView det_poster;
    ArrayList<genres> genreslist;
    TextView title, vote_avg, overview, release_date, genres , popularity;
    private String baseImagePath = "https://image.tmdb.org/t/p/w500/";
    Results results;
    MovieDetailsModel movieDetailsModel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        results = new Results();

        det_poster = findViewById(R.id.det_poster);
        title = findViewById(R.id.title);
        vote_avg = findViewById(R.id.vote_average);
        overview = findViewById(R.id.overview);
        release_date = findViewById(R.id.release_date);
        genres = findViewById(R.id.genres);
        popularity = findViewById(R.id.popularity);

        loadMovies();

    }

    private void loadMovies() {
        String api_key = "371d8f462ceaf576f8d5eb1e280efce9";
        RetrofitService
                .createService(AuthService.class)
                .Details(id,api_key)
                .enqueue(new Callback<MovieDetailsModel>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                        MovieDetailsModel res = response.body();
                        assert res != null;
                        Log.e("resG", "onResponse: "+res );
                        setData(res);

                    }

                    @Override
                    public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Form Server Side",Toast.LENGTH_LONG).show();
                        Log.e("server error", "onFailure: "+ t );
                    }
                });
    }

    private void setData(MovieDetailsModel res) {
        LoadImage(det_poster, res.getPoster_path());
        title.setText("Title : "+res.getTitle());
        vote_avg.setText("Votes : "+res.getVote_average());
        overview.setText("Overview : "+res.getOverview());
        release_date.setText("Release Date : "+res.getRelease_date());
        //genres;
        popularity.setText("Popularity : "+res.getPopularity());
        genreslist = res.getGenres();
        StringBuilder genereStr= new StringBuilder(genreslist.size() > 0 ? res.getGenres().get(0).getName() : "");

        for (int i=1;i<res.getGenres().size();i++){
            genereStr.append(", ").append(genreslist.get(i).getName());
        }


        genres.append(genereStr.toString());

    }

    public void LoadImage(ImageView imageView, String url){
        Glide.with(getApplicationContext())
                .load(baseImagePath+url)
                .fallback(R.drawable.d)
                .error(R.drawable.d)
                .into(imageView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}