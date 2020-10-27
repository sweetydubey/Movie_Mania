package com.example.moviemania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements  MoviesAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<Results> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    Results resultsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        loadMovies();

    }



    private void loadMovies() {
        String api_key = "371d8f462ceaf576f8d5eb1e280efce9";
        RetrofitService
                .createService(AuthService.class)
                .movies(api_key,"en-US", "1")
                .enqueue(new Callback<MoviesModel>() {
                    @Override
                    public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                        MoviesModel res = response.body();
                        assert res != null;
                        movieList = new ArrayList<>(Arrays.asList(res.getResults()));
                        Log.e("response", "onResponse: " + res);
                        mAdapter = new MoviesAdapter(movieList, getApplicationContext());
                        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
                        recyclerView.addItemDecoration(decoration);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.setClickListener(MainActivity.this);
                       // mAdapter.notifyDataSetChanged();
//                        if (res.results.length>0){
//
//                           // Log.e("title", "onResponse: " + movieList.getTitle());
//
//                        } 

                    }

                    @Override
                    public void onFailure(Call<MoviesModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Form Server Side",Toast.LENGTH_LONG).show();
                        Log.e("server error", "onFailure: "+ t );
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("id",movieList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }
}