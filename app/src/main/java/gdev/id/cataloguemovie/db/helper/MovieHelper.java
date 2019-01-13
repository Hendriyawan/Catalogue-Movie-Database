package gdev.id.cataloguemovie.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import gdev.id.cataloguemovie.db.contract.MovieContract;
import gdev.id.cataloguemovie.model.MovieModel;

import static android.provider.BaseColumns._ID;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_DATE_RELEASE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_OVERVIEW;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_POSTER_PATH;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_RATING;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_TITLE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.MovieColumns.MOVIE_VOTE;
import static gdev.id.cataloguemovie.db.contract.MovieContract.TABLE_MOVIE;

public class MovieHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public ArrayList<MovieModel> getAllFavoriteMovie() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_MOVIE + " ORDER BY " + _ID + " DESC", null);
        cursor.moveToFirst();

        ArrayList<MovieModel> arrayList = new ArrayList<>();
        MovieModel movieModel;
        if (cursor.getCount() > 0) {
            do {
                movieModel = new MovieModel();
                movieModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieModel.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_PATH)));
                movieModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                movieModel.setVote(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_VOTE)));
                movieModel.setRating(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_RATING)));
                movieModel.setDateOfRelease(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE_RELEASE)));
                movieModel.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));
                arrayList.add(movieModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieModel movie) {
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_POSTER_PATH, movie.getPosterPath());
        cv.put(MOVIE_TITLE, movie.getTitle());
        cv.put(MOVIE_VOTE, movie.getVote());
        cv.put(MOVIE_RATING, movie.getRating());
        cv.put(MOVIE_DATE_RELEASE, movie.getDateOfRelease());
        cv.put(MOVIE_OVERVIEW, movie.getOverview());
        return database.insert(TABLE_MOVIE, null, cv);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + "= '" + id + "'", null);
    }

    public String getFavoriteByTitle(String title) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_MOVIE + " WHERE " + MOVIE_TITLE + " LIKE '%" + title + "%'", null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)) : "";
    }


    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_MOVIE,
                null
                , null
                , null
                , null
                , null, _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, _ID + " = ?", new String[]{id});
    }
}