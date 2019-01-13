package gdev.id.cataloguemovie.mvp.favorite;

import java.util.ArrayList;

import gdev.id.cataloguemovie.model.MovieModel;

public interface FavoriteView {
    void onLoadSuccess(ArrayList<MovieModel> movieModels);
}
