package com.dicoding.mymoviecataloguev2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dicoding.mymoviecataloguev2.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.POSTER;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.RELEASE_DATE;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.TITLE;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.VOTE;
import static com.dicoding.mymoviecataloguev2.database.DatabaseContract.TableColumns.VOTE_COUNT;

public class FavMovieHelper {
    private static String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavMovieHelper(Context context){
        this.context = context;
    }

    public FavMovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<MovieItem> query(){
        ArrayList<MovieItem> arrayList = new ArrayList<MovieItem>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        MovieItem movieItem;
        if (cursor.getCount()>0) {
            do {
                movieItem = new MovieItem();
                movieItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItem.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieItem.setVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                movieItem.setVoteCount(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                movieItem.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movieItem);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieItem movieItem){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, movieItem.getJudul());
        initialValues.put(RELEASE_DATE, movieItem.getReleaseDate());
        initialValues.put(OVERVIEW, movieItem.getOverview());
        initialValues.put(VOTE, movieItem.getVote());
        initialValues.put(VOTE_COUNT, movieItem.getVoteCount());
        initialValues.put(POSTER, movieItem.getPoster());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(MovieItem movieItem){
        ContentValues args = new ContentValues();
        args.put(TITLE, movieItem.getJudul());
        args.put(RELEASE_DATE, movieItem.getReleaseDate());
        args.put(OVERVIEW, movieItem.getOverview());
        args.put(VOTE, movieItem.getVote());
        args.put(VOTE_COUNT, movieItem.getVoteCount());
        args.put(POSTER, movieItem.getPoster());

        return database.update(DATABASE_TABLE, args, _ID + "= '" + movieItem.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(DATABASE_TABLE, _ID + " = '"+id+"'", null);
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE, null ,_ID+" = ?",
                new String[]{id},
                null,null,null,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,null,
                null,null,null,null,_ID+" DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID+" = ?",
                new String[]{id});
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID+" = ?",new String[]{id});
    }
}
