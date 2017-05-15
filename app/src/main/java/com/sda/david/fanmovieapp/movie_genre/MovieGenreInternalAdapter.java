package com.sda.david.fanmovieapp.movie_genre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sda.david.fanmovieapp.BaseActivity;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.movies.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by david on 04/05/2017.
 */

public class MovieGenreInternalAdapter extends RecyclerView.Adapter<MovieGenreInternalAdapter.MovieViewHolder> {

    private String TAG = "MovieGenreIntAdp";
    private List<Movie> movieList;
    private Context ctx;
    private LayoutInflater mLayoutInflater;

    public MovieGenreInternalAdapter(Context ctx, List<Movie> movieList) {
        this.ctx = ctx;
        this.movieList = movieList;
        mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.adapter_movie_genre_item, parent, false);

        return new MovieViewHolder(view);
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();

                Intent intent = new Intent(ctx, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ARG_MOVIE, movieList.get(position));
                intent.putExtra(MovieDetailActivity.ARG_USER, BaseActivity.user);
                ctx.startActivity(intent);
            }
        };
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.mainLayout.setOnClickListener(onClickListener());
        holder.mainLayout.setId(position);

        String urlMoviePoster = "!";

        if (movieList.get(position) != null && movieList.get(position).getPosterPath() != null)
            urlMoviePoster = movieList.get(position).getPosterPath();

        if (!urlMoviePoster.isEmpty() && urlMoviePoster.charAt(0) == '/')
            urlMoviePoster = "http://image.tmdb.org/t/p/w92" + urlMoviePoster;

        Picasso
                .with(ctx)
                .load(urlMoviePoster)
                .into(holder.ivMoviePoster);

        if (movieList.get(position) != null && movieList.get(position).getTitle() != null)
            holder.tvMovieTitle.setText(movieList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        CardView mainLayout;
        ImageView ivMoviePoster;
        TextView tvMovieTitle;

        MovieViewHolder(View itemView) {
            super(itemView);

            mainLayout = (CardView) itemView.findViewById(R.id.main_layout);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
        }
    }
}
