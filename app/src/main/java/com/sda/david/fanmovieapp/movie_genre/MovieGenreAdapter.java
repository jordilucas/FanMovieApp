package com.sda.david.fanmovieapp.movie_genre;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.model.Movie;
import com.sda.david.fanmovieapp.model.MovieTypeItem;
import com.sda.david.fanmovieapp.util.MovieGenre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 04/05/2017.
 */

public class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.MovieViewHolder> {

    private String TAG = "MovieGenreAdp";
    private List<MovieTypeItem> movieTypeItems;
    private Context ctx;
    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mClickListener;

    public MovieGenreAdapter(Context ctx, List<MovieTypeItem> movieTypeItems, View.OnClickListener mClickListener) {
        this.ctx = ctx;
        this.movieTypeItems = movieTypeItems;
        this.mClickListener = mClickListener;
        mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0) {
            view = mLayoutInflater.inflate(R.layout.adapter_movie_genre_title_item, parent, false);
            return new TitleViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.adapter_movie_genre_content_item, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if(holder.getItemViewType() == 0) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tvGenreTitle.setText(movieTypeItems.get(position).getGenreTitle());
        } else {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.rvMoviesGenreInsider.setHasFixedSize(false);
            final CarouselLayoutManager mLinearLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
            mLinearLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ctx);
//            mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            contentViewHolder.rvMoviesGenreInsider.setLayoutManager(mLinearLayoutManager);
            MovieGenreInternalAdapter adapter = new MovieGenreInternalAdapter(ctx, movieTypeItems.get(position).getMovies(), mClickListener);
            contentViewHolder.rvMoviesGenreInsider.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return movieTypeItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movieTypeItems.get(position).getType().equals(MovieTypeItem.Type.TITLE)) {
            return 0;
        } else {
            return 1;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        MovieViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TitleViewHolder extends MovieViewHolder {

        TextView tvGenreTitle;

        TitleViewHolder(View itemView) {
            super(itemView);

            tvGenreTitle = (TextView) itemView.findViewById(R.id.tv_genre_title);
        }
    }

    private class ContentViewHolder extends MovieViewHolder {

        RecyclerView rvMoviesGenreInsider;

        ContentViewHolder(View itemView) {
            super(itemView);

            rvMoviesGenreInsider = (RecyclerView) itemView.findViewById(R.id.rv_movies_genre_insider);
        }
    }
}
