package com.dicoding.mymoviecataloguev2;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {


    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private View view;
    private ArrayList<MovieItem> movieLists;

    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="+BuildConfig.ApiKey+"&language=en-US";

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_nowplaying);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieLists = new ArrayList<MovieItem>();
        loadData();
        return view;
    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {

                        MovieItem movies = new MovieItem();

                        JSONObject data = array.getJSONObject(i);
                        movies.setJudul(data.getString("title"));
                        movies.setOverview(data.getString("overview"));
                        movies.setReleaseDate(data.getString("release_date"));
                        movies.setPoster(data.getString("poster_path"));
                        movies.setVote(data.getDouble("vote_average"));
                        movies.setVoteCount(data.getDouble("vote_count"));
                        movieLists.add(movies);

                    }

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


                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),R.string.no_connection,Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.internet_fail));
                builder.setMessage(getResources().getString(R.string.want_to_reload));
                builder.setPositiveButton(getResources().getString(R.string.reload), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadData();
                    }
                });
                builder.create().show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}