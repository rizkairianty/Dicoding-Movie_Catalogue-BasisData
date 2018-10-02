package com.dicoding.mymoviecataloguev2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


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
    private UserPreference mUserPreference;
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

        mUserPreference = new UserPreference();
        Bundle bundle = getArguments();
        if (bundle!=null){
            movieItem = bundle.getParcelable("object");
        }
        tvDetailTitle.setText(movieItem.getJudul());
        tvDetailReleaseDate.setText(movieItem.getReleaseDate());
        tvDetailVote.setText(getArguments().getString(EXTRA_VOTE));
        tvDetailVoteCount.setText(getArguments().getString(EXTRA_VOTE_COUNT));
        tvDetailOverview.setText(getArguments().getString(EXTRA_OVERVIEW));
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w92/" + getArguments().getString(EXTRA_POSTER))
                .override(55,90)
                .placeholder(R.drawable.placeholder)
                .into(ivDetailPoster);
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
//                    String tag = switchFav.getTag().toString();
//                    if (tag.equalsIgnoreCase("no")) {
                    mUserPreference.addFavorite(getActivity(), movieItem);
//
//                        switchFav.setTag("yes");
//                        switchFav.setChecked(true);
                } else {
                    mUserPreference.removeFavorite(getActivity(),movieItem);
//                        switchFav.setTag("no");
//                        switchFav.setChecked(false);
                }
            }
        });
    }
    public boolean checkFavoriteItem(MovieItem checkProduct) {
        boolean check = false;
        ArrayList<MovieItem> favorites = mUserPreference.getFavorites(getActivity());
        if (favorites != null) {
            for (MovieItem codes : favorites) {
                if (codes.getJudul().equals(checkProduct.getJudul())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}