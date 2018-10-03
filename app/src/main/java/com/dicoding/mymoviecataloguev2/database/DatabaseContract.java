package com.dicoding.mymoviecataloguev2.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "favMovie";

    public static final class TableColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String VOTE_COUNT = "vote_count";
        public static String VOTE = "vote";
        public static String POSTER = "poster";
    }

    public static final String AUTHORITY = "com.dicoding.mymoviecataloguev2";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}