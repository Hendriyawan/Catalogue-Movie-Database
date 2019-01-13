package gdev.id.cataloguemovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gdev.id.cataloguemovie.BuildConfig;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.activity.DetailMovieActivity;
import gdev.id.cataloguemovie.adapter.MovieAdapter;
import gdev.id.cataloguemovie.model.MovieModel;
import gdev.id.cataloguemovie.mvp.movie.MoviePresenter;
import gdev.id.cataloguemovie.mvp.movie.MovieView;
import gdev.id.cataloguemovie.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
//created by Hendriyawan 5 December 2018
//Submisson 2 (MADE) : Catalogue Movie Ui Ux

public class FragmentUpcoming extends Fragment implements MovieView {
    @BindView(R.id.recycler_view_upcoming)
    RecyclerView recyclerView;
    @BindView(R.id.layout_notification_network_error)
    LinearLayout linearLayout;
    @BindView(R.id.tv_notif_network_message)
    TextView tvMessage;
    @BindView(R.id.progressbar)
    GoogleProgressBar progressBar;

    private MoviePresenter presenter;

    public FragmentUpcoming() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_upcoming, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);

        String url = BuildConfig.URL_MOVIE_UP_COMING;

        presenter = new MoviePresenter(getActivity(), this);
        presenter.loadData(progressBar, url);
        return view;
    }

    @Override
    public void onLoadSuccess(ArrayList<MovieModel> data) {
        MovieAdapter adapter = new MovieAdapter(getActivity());
        adapter.addItem(data);
        recyclerView.setAdapter(adapter);
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
    public void onLoadError(String message) {
        Utils.showNotificationNetworkError(getActivity(), linearLayout, tvMessage, message);
    }
}
