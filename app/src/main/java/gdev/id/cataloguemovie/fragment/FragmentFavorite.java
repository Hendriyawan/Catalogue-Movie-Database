package gdev.id.cataloguemovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.activity.DetailMovieActivity;
import gdev.id.cataloguemovie.adapter.MovieAdapter;
import gdev.id.cataloguemovie.model.MovieModel;
import gdev.id.cataloguemovie.mvp.favorite.FavoritePresenter;
import gdev.id.cataloguemovie.mvp.favorite.FavoriteView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements FavoriteView {
    @BindView(R.id.progressbar)
    GoogleProgressBar progressBar;
    @BindView(R.id.rv_favorite_movie)
    RecyclerView rvFavoriteMovie;
    private FavoritePresenter presenter;


    public FragmentFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_favorite, container, false);
        ButterKnife.bind(this, view);

        rvFavoriteMovie.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvFavoriteMovie.setHasFixedSize(true);

        presenter = new FavoritePresenter(getActivity(), this);
        presenter.loadFavorite(progressBar);

        return view;
    }

    @Override
    public void onLoadSuccess(ArrayList<MovieModel> movieModels) {
        MovieAdapter adapter = new MovieAdapter(getActivity());
        adapter.addItem(movieModels);
        adapter.notifyDataSetChanged();
        rvFavoriteMovie.setAdapter(adapter);
        adapter.setOnItemClick(new MovieAdapter.OnItemClick() {
            @Override
            public void OnItemClicked(MovieModel model) {
                Intent detailIntent = new Intent(getActivity(), DetailMovieActivity.class);
                detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, model);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadFavorite(progressBar);
    }
}
