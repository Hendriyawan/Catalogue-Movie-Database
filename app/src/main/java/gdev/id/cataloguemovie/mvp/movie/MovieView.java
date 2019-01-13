package gdev.id.cataloguemovie.mvp.movie;

import java.util.ArrayList;
import java.util.List;

import gdev.id.cataloguemovie.model.MovieModel;

public interface MovieView {
    void onLoadSuccess(ArrayList<MovieModel> data);

    void onLoadError(String message);
}
