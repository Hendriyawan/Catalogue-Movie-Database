package gdev.id.cataloguemovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gdev.id.cataloguemovie.BuildConfig;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.adapter.MovieAdapter;
import gdev.id.cataloguemovie.model.MovieModel;
import gdev.id.cataloguemovie.mvp.movie.MoviePresenter;
import gdev.id.cataloguemovie.mvp.movie.MovieView;
import gdev.id.cataloguemovie.utils.Utils;

public class ActivitySearchMovie extends AppCompatActivity implements MovieView {
    public static final String EXTRA_QUERY = "extra_query";
    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_notification_network_error)
    LinearLayout linearLayout;
    @BindView(R.id.tv_notif_network_message)
    TextView tvMessage;
    @BindView(R.id.progressbar)
    GoogleProgressBar progressBar;
    private MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        //bind view
        ButterKnife.bind(this);

        setupToolbar();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        String query = getIntent().getStringExtra(EXTRA_QUERY);
        String url = BuildConfig.URL_SEARCH_MOVIE + query;

        presenter = new MoviePresenter(this, this);
        presenter.loadData(progressBar, url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onLoadSuccess(ArrayList<MovieModel> data) {
        MovieAdapter adapter = new MovieAdapter(this);
        adapter.addItem(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new MovieAdapter.OnItemClick() {
            @Override
            public void OnItemClicked(MovieModel model) {
                Intent detailIntent = new Intent(ActivitySearchMovie.this, DetailMovieActivity.class);
                detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, model);
                startActivity(detailIntent);

            }
        });
    }

    @Override
    public void onLoadError(String message) {
        Utils.showNotificationNetworkError(this, linearLayout, tvMessage, message);
    }

    //setup toolbar
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
