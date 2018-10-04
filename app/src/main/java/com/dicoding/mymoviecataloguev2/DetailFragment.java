package com.dicoding.mymoviecataloguev2;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.mymoviecataloguev2.database.DatabaseContract;
import com.dicoding.mymoviecataloguev2.database.FavMovieHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.CONTENT_URI;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.TITLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvDetailTitle;
    TextView tvDetailReleaseDate;
    TextView tvDetailOverview;
    TextView tvDetailVote;
    TextView tvDetailVoteCount;
    ImageView ivDetailPoster;
    Switch switchFav;
    MovieItem movieItem;
    private ArrayList<MovieItem> movieItemList;
    private UserPreference mUserPreference;
    private Uri uri;
    private FavMovieHelper favMovieHelper;
    ArrayList<MovieItem> favMovie = new ArrayList<MovieItem>();
    public final static String EXTRA_TITLE = "TITLE";
    public final static String EXTRA_RELEASE_DATE = "RELEASE_DATE";
    public final static String  EXTRA_VOTE_COUNT = "VOTE_COUNT";
    public final static String EXTRA_VOTE = "VOTE";
    public final static String EXTRA_POSTER = "POSTER";
    public final static String EXTRA_OVERVIEW = "OVERVIEW";
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        switchFav = (Switch) view.findViewById(R.id.switch1);
        tvDetailTitle = (TextView) view.findViewById(R.id.tv_detail_title);
        tvDetailReleaseDate = (TextView) view.findViewById(R.id.tv_detail_release_date);
        tvDetailOverview = (TextView) view.findViewById(R.id.tv_overview);
        tvDetailVote = (TextView) view.findViewById(R.id.tv_detail_vote);
        tvDetailVoteCount = (TextView) view.findViewById(R.id.tv_detail_vote_count);
        ivDetailPoster = (ImageView) view.findViewById(R.id.iv_detail_poster);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favMovieHelper = new FavMovieHelper(getActivity());
        favMovieHelper.open();
        mUserPreference = new UserPreference();
        Bundle bundle = getArguments();
        if (bundle!=null){
            movieItem = bundle.getParcelable("object");
        }
        uri = Uri.parse(CONTENT_URI+"/"+movieItem.getId());

        if (uri != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst())
                    movieItem = new MovieItem(cursor);
                cursor.close();
            }
        }
        tvDetailTitle.setText(movieItem.getJudul());
        tvDetailReleaseDate.setText(movieItem.getReleaseDate());
        tvDetailVote.setText(String.valueOf(movieItem.getVote()));
        tvDetailVoteCount.setText(String.valueOf(movieItem.getVoteCount()));
        tvDetailOverview.setText(movieItem.getOverview());
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w92/" + movieItem.getPoster())
                .override(55,90)
                .placeholder(R.drawable.placeholder)
                .into(ivDetailPoster);


        movieItemList = new ArrayList<MovieItem>();
        final Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            MovieItem movItem = new MovieItem(cursor);
            movieItemList.add(movItem);
        }

        if (checkFavoriteItem(movieItem)){
            switchFav.setChecked(true);
            switchFav.setTag("yes");
        }else {
            switchFav.setChecked(false);
            switchFav.setTag("no");
        }
        switchFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                 ContentValues contentValues = new ContentValues();
                    contentValues.put(TITLE,movieItem.getJudul());
                    contentValues.put(DatabaseContract.TableColumns.RELEASE_DATE,movieItem.getReleaseDate());
                    contentValues.put(DatabaseContract.TableColumns.OVERVIEW,movieItem.getOverview());
                    contentValues.put(DatabaseContract.TableColumns.VOTE,movieItem.getVote());
                    contentValues.put(DatabaseContract.TableColumns.VOTE_COUNT,movieItem.getVoteCount());
                    contentValues.put(DatabaseContract.TableColumns.POSTER,movieItem.getPoster());
//                    String tag = switchFav.getTag().toString();
//                    if (tag.equalsIgnoreCase("no")) {
                    //mUserPreference.addFavorite(getActivity(), movieItem);
                    getContext().getContentResolver().insert(CONTENT_URI,contentValues);
                } else {
                    //mUserPreference.removeFavorite(getActivity(),movieItem);
                    int id=0;
                    cursor.moveToPosition(-1);
                    while (cursor.moveToNext()){
//                        for (MovieItem codes : movieItemList) {
//                            String s = cursor.getString(cursor.getColumnIndex(TITLE));
//                            if (codes.getJudul().equals(s)) {
//                                id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
//                                break;
//                            }
//                        }
                        String s = cursor.getString(cursor.getColumnIndex(TITLE));
                            if (movieItem.getJudul().equals(s)) {
                                id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                                break;
                            }
                    }
                    Uri uri = Uri.parse(CONTENT_URI+"/"+id);
                    //getContext().getContentResolver().delete(uri,DatabaseContract.TableColumns.TITLE+" = ?",selectionArgs);
                    getContext().getContentResolver().delete(uri,null,null);
                }
            }
        });
    }
    public boolean checkFavoriteItem(MovieItem checkProduct) {
        boolean check = false;
        if (movieItemList != null) {
            for (MovieItem codes : movieItemList) {
                if (codes.getJudul().equals(checkProduct.getJudul())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}