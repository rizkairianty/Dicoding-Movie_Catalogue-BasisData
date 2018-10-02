package com.dicoding.mymoviecataloguev2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPreference {

    public static final String PREFS_NAME = "FAV_MOVIE";
    public static final String FAVORITES = "fav_Favorite";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Context context;
    public UserPreference(){
        super();
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        //this.context = context;
    }

   /* public void addFavoriteMovie(Context context, MovieItem movieItem){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.pu(FAVORITES, movieItem.getId());
        editor.apply();
    }*/

    public void saveFavorites(Context context, ArrayList<MovieItem> favorites){

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, MovieItem movieItem){
        ArrayList<MovieItem> favorites = getFavorites(context);

        if(favorites == null)
            favorites = new ArrayList<MovieItem>();
        favorites.add(movieItem);
        saveFavorites(context,favorites);
    }

    public void removeFavorite(Context context, MovieItem movieItem) {
        ArrayList<MovieItem> favorites = getFavorites(context);
//        if (favorites != null) {
//                favorites.remove(favorites.indexOf(movieItem));
//            saveFavorites(context, favorites);
//        }
        if (favorites != null) {
            for (MovieItem codes : favorites) {
                if (codes.getJudul().equals(movieItem.getJudul())) {
                    int m = favorites.indexOf(codes);
                    favorites.remove(m);
                    break;
                }
            }
            saveFavorites(context, favorites);
        }
    }


    public ArrayList<MovieItem> getFavorites(Context context) {
        SharedPreferences settings;
        List<MovieItem> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MovieItem[] favoriteItems = gson.fromJson(jsonFavorites,
                    MovieItem[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MovieItem>(favorites);
        } else
            return null;

        return (ArrayList<MovieItem>) favorites;
    }
/*
    public void setFirstRun(Boolean input){
        editor = settings.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.app_first_run);
        return prefs.getBoolean(key, true);
    }*/
}
