package gdev.id.cataloguemovie.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_DATE_RELEASE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_OVERVIEW;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_POSTER_PATH;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_RATING;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_TITLE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_VOTE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbmovie";
    public static final int DATABASE_VERSION = 1;
    //create table movie
    public static final String CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE_MOVIE +
            " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
            MOVIE_TITLE + " TEXT NOT NULL, " +
            MOVIE_VOTE + " TEXT NOT NULL, " +
            MOVIE_RATING + " TEXT NOT NULL, " +
            MOVIE_DATE_RELEASE + " TEXT NOT NULL, " +
            MOVIE_OVERVIEW + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }
}
