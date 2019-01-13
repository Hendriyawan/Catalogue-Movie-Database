package id.gdev.favoritesmovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_DATE_RELEASE;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_OVERVIEW;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_POSTER_PATH;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_RATING;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_TITLE;
import static id.gdev.favoritesmovie.db.contract.MovieContract.MovieColumn.MOVIE_VOTE;
import static id.gdev.favoritesmovie.db.contract.MovieContract.getColumnInt;
import static id.gdev.favoritesmovie.db.contract.MovieContract.getColumnString;

public class MovieModel implements Parcelable {
    private int id;
    private String posterPath;
    private String title;
    private String vote;
    private String rating;
    private String dateOfRelease;
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeString(this.vote);
        dest.writeString(this.rating);
        dest.writeString(this.dateOfRelease);
        dest.writeString(this.overview);
    }

    public MovieModel() {
    }

    public MovieModel(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.posterPath = getColumnString(cursor, MOVIE_POSTER_PATH);
        this.title = getColumnString(cursor, MOVIE_TITLE);
        this.vote = getColumnString(cursor, MOVIE_VOTE);
        this.rating = getColumnString(cursor, MOVIE_RATING);
        this.dateOfRelease = getColumnString(cursor, MOVIE_DATE_RELEASE);
        this.overview = getColumnString(cursor, MOVIE_OVERVIEW);
    }

    protected MovieModel(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.vote = in.readString();
        this.rating = in.readString();
        this.dateOfRelease = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
