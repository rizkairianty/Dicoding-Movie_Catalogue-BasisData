package com.dicoding.mymoviecataloguev2;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private static final String TAG = "MyActivity";
    private UserPreference mUserPreference;
    private Cursor list;
    private RecyclerView rvCategory;
    //private RecyclerView.Adapter adapter;
    private FavMovieListAdapter adapter;
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
        //movieLists = mUserPreference.getFavorites(getActivity());
        //adapter = new MovieListAdapter(getActivity(),movieLists);
        adapter = new FavMovieListAdapter(getActivity());
        adapter.setListFavMovie(list);
        rvCategory.setAdapter(adapter);
        new LoadData().execute();
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

private class LoadData extends AsyncTask<Void,Void,Cursor>{
    @Override
    protected Cursor doInBackground(Void... voids) {
        Log.v(TAG,"wowow");
        return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        list = cursor;
        adapter.setListFavMovie(list);
        adapter.notifyDataSetChanged();
    }
}

}
