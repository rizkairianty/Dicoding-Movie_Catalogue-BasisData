package com.dicoding.mymoviecataloguev2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavMovieListAdapter extends RecyclerView.Adapter<FavMovieListAdapter.CategoryViewHolder> {
    private Context context;
    private Cursor listFavMovie;
    private ArrayList<MovieItem> listMovie;

    ArrayList<MovieItem> getListMovie() {
        return listMovie;
    }
    void setListMovie(ArrayList<MovieItem> listMovie) {
        this.listMovie = listMovie;
    }

    FavMovieListAdapter(Context context){
        this.context = context;
    }
    FavMovieListAdapter(Context context, ArrayList<MovieItem> listMovie){
        this.context = context;
        this.listMovie = listMovie;

    }

    public void setListFavMovie(Cursor listFavMovie) {
        this.listFavMovie = listFavMovie;
    }

    @Override
    public FavMovieListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FavMovieListAdapter.CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(FavMovieListAdapter.CategoryViewHolder holder, int position) {
//        holder.tvJudul.setText(getListMovie().get(position).getJudul());
//        holder.tvRelease.setText(getListMovie().get(position).getReleaseDate());
//        holder.tvOverviews.setText(getListMovie().get(position).getOverview());
//        Glide.with(context)
//                .load("http://image.tmdb.org/t/p/w185/"+ getListMovie().get(position).getPoster())
//                .placeholder(R.drawable.placeholder)
//                .override(65,90)
//                .into(holder.imgMoviePoster);

        final MovieItem movieItem = getItem(position);
        holder.tvJudul.setText(movieItem.getJudul());
        holder.tvRelease.setText(movieItem.getReleaseDate());
        holder.tvOverviews.setText(movieItem.getOverview());
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+ movieItem.getPoster()
                )
                .placeholder(R.drawable.placeholder)
                .override(65,90)
                .into(holder.imgMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (listFavMovie==null)
            return 0;
        return listFavMovie.getCount();
    }

    private MovieItem getItem(int position){
        if (!listFavMovie.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(listFavMovie);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul, tvOverviews, tvRelease;
        ImageView imgMoviePoster;

        CategoryViewHolder(View itemView) {
            super(itemView);
            tvJudul = (TextView)itemView.findViewById(R.id.tvTitle);
            tvRelease = (TextView)itemView.findViewById(R.id.tvReleaseDate);
            tvOverviews = (TextView) itemView.findViewById(R.id.tvOverview);
            imgMoviePoster = (ImageView) itemView.findViewById(R.id.imgPoster);
        }
    }
}
