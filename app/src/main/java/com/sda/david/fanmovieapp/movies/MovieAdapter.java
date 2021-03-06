package com.sda.david.fanmovieapp.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by david on 29/04/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private String TAG = "MovieAdp";
    private List<Movie> movieList;
    private Context ctx;
    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mClickListener;

    public MovieAdapter(Context ctx, List<Movie> movieList, View.OnClickListener mClickListener) {
        this.ctx = ctx;
        this.movieList = movieList;
        this.mClickListener = mClickListener;
        mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.adapter_movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.mainLayout.setOnClickListener(mClickListener);
        holder.mainLayout.setId(position);

        String urlMoviePoster = "!";

        if(movieList.get(position) != null && movieList.get(position).getPosterPath() != null)
            urlMoviePoster = movieList.get(position).getPosterPath();

        if(!urlMoviePoster.isEmpty() && urlMoviePoster.charAt(0) == '/')
            urlMoviePoster = "http://image.tmdb.org/t/p/w92" + urlMoviePoster;

        final ProgressBar progressView = holder.progressBar;
        Picasso
            .with(ctx)
            .load(urlMoviePoster)
            .into(holder.ivMoviePoster, new Callback() {
                @Override
                public void onSuccess() {
                    progressView.setVisibility(View.GONE);
                }

                @Override
                public void onError() {}
            });

        if(movieList.get(position) != null && movieList.get(position).getTitle() != null)
            holder.tvMovieTitle.setText(movieList.get(position).getTitle());

        if(movieList.get(position) != null && movieList.get(position).getVoteAverage() != 0f)
            holder.tvMovieNote.setText(String.valueOf(movieList.get(position).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mainLayout;
        ImageView ivMoviePoster;
        TextView tvMovieTitle;
        TextView tvMovieNote;
        ProgressBar progressBar;

        MovieViewHolder(View itemView) {
            super(itemView);

            mainLayout = (RelativeLayout) itemView.findViewById(R.id.main_layout);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            tvMovieNote = (TextView) itemView.findViewById(R.id.tv_movie_note);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_image);
        }
    }
}
