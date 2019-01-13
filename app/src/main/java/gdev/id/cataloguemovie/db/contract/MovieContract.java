package gdev.id.cataloguemovie.db.contract;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String TABLE_MOVIE = "movie";

    public static class MovieColumns implements BaseColumns {
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

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
