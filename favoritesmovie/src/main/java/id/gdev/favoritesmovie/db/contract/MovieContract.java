package id.gdev.favoritesmovie.db.contract;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String TABLE_MOVIE = "movie";

    public static class MovieColumn implements BaseColumns {
        public static String MOVIE_POSTER_PATH = "posterPath";
        public static String MOVIE_TITLE = "title";
        public static String MOVIE_VOTE = "vote";
        public static String MOVIE_RATING = "rating";
        public static String MOVIE_DATE_RELEASE = "dateOfRelease";
        public static String MOVIE_OVERVIEW = "overview";
    }

    public static final String AUTHORITY = "gdev.id.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnNAme) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnNAme));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }
}
