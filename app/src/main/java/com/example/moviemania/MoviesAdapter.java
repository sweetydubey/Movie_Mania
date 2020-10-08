package com.example.moviemania;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Results> moviesList;
    private Context context;

    private ItemClickListener mClickListener;
    private String baseImagePath = "https://image.tmdb.org/t/p/w500/";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, vote, genre;
        public ImageView poster;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt);
//            genre = (TextView) view.findViewById(R.id.genre);
            vote = (TextView) view.findViewById(R.id.vote);
            poster = (ImageView) view.findViewById(R.id.poster);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public MoviesAdapter(ArrayList<Results> moviesList, Context c) {
        this.moviesList = moviesList;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Results movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        Log.e("TAG", "onBindViewHolder: " + movie.getTitle() );
//        holder.genre.setText(movie.getGenre());
        holder.vote.setText(movie.getVote_average()+ "/10");
        LoadImage(holder.poster, movie.getPoster_path());
    }
    public void LoadImage(ImageView imageView, String url){
        Glide.with(context)
                .load(baseImagePath+url)
                .fallback(R.drawable.d)
                .error(R.drawable.d)
                .into(imageView);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
