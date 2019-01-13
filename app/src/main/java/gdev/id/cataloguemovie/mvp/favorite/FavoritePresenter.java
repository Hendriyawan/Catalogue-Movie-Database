package gdev.id.cataloguemovie.mvp.favorite;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import gdev.id.cataloguemovie.db.helper.MovieHelper;
import gdev.id.cataloguemovie.model.MovieModel;

public class FavoritePresenter {
    private Context context;
    private FavoriteView favoriteView;

    public FavoritePresenter(Context context, FavoriteView favoriteView) {
        this.context = context;
        this.favoriteView = favoriteView;
    }

    public void loadFavorite(GoogleProgressBar progressBar) {
        new LoadData(progressBar).execute();
    }

    private class LoadData extends AsyncTask<Void, Void, ArrayList<MovieModel>> {
        private GoogleProgressBar progressBar;
        private MovieHelper movieHelper;

        public LoadData(GoogleProgressBar progressBar){
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            movieHelper = new MovieHelper(context);
        }

        @Override
        protected ArrayList<MovieModel> doInBackground(Void... voids) {
            movieHelper.open();
            return movieHelper.getAllFavoriteMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> movieModels) {
            super.onPostExecute(movieModels);
            progressBar.setVisibility(View.GONE);
            favoriteView.onLoadSuccess(movieModels);
        }
    }
}
