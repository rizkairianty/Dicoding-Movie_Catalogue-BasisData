package com.dicoding.mymoviecataloguev2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private UserPreference mUserPreference;

    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private View view;
    private ArrayList<MovieItem> movieLists;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUserPreference = new UserPreference();
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_favorite);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieLists = new ArrayList<MovieItem>();
        movieLists = mUserPreference.getFavorites(getActivity());
        adapter = new MovieListAdapter(getActivity(),movieLists);
rvCategory.setAdapter(adapter);
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                DetailFragment detailFragment = new DetailFragment();
                MovieItem movItem = movieLists.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("object",movItem);
                bundle.putString(DetailFragment.EXTRA_TITLE,movItem.getJudul());
                bundle.putString(DetailFragment.EXTRA_POSTER,movItem.getPoster());
                bundle.putString(DetailFragment.EXTRA_OVERVIEW,movItem.getOverview());
                bundle.putString(DetailFragment.EXTRA_RELEASE_DATE,movItem.getReleaseDate());
                bundle.putString(DetailFragment.EXTRA_VOTE,Double.toString(movItem.getVote()));
                bundle.putString(DetailFragment.EXTRA_VOTE_COUNT,Double.toString(movItem.getVoteCount()));

                detailFragment.setArguments(bundle);
                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransation = mFragmentManager.beginTransaction();
                mFragmentTransation.replace(R.id.contentainer,detailFragment);
                mFragmentTransation.addToBackStack(null);
                mFragmentTransation.commit();
            }
        });
        return view;
    }

    private void loadData() {
    }

}
