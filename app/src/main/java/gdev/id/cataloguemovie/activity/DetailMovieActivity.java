package gdev.id.cataloguemovie.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.db.helper.MovieHelper;
import gdev.id.cataloguemovie.model.MovieModel;
//created by Hendriyawan 5 December 2018
//Submisson 2 (MADE) : Catalogue Movie Ui Ux

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.img_poster)
    ImageView imgPoster;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_date_of_release)
    TextView tvDateRelease;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    private MovieHelper movieHelper;
    private MovieModel movie;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        //bind view
        ButterKnife.bind(this);

        setupToolbar();

        //get parcelable
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String posterPath = movie.getPosterPath();
        final String title = movie.getTitle();
        String vote = movie.getVote();
        String rating = movie.getRating();
        String dateOfRelease = movie.getDateOfRelease();
        String overview = movie.getOverview();

        collapsingToolbarLayout.setTitle(title);
        Picasso.get().load("http://image.tmdb.org/t/p/w342/" + posterPath)
                .placeholder(getResources().getDrawable(R.drawable.ic_blank_image100))
                .error(getResources().getDrawable(R.drawable.ic_blank_image100)).into(imgPoster);

        tvPopularity.setText(vote);
        tvRating.setText(rating + "/10");
        tvDateRelease.setText(dateOfRelease);
        tvOverview.setText(overview);

        //android database
        movieHelper = new MovieHelper(this);
        movieHelper.open();

        //get status favorite in db
        if (title.equals(movieHelper.getFavoriteByTitle(title))) {
            isFavorite = true;
            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite, getTheme()));
        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    deleteFavorite();
                    fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border, getTheme()));
                } else {
                    addFavorite();
                    fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite, getTheme()));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    //add to favorite
    private void addFavorite() {
        movieHelper.insert(movie);
        showSnackbar(String.format(getResources().getString(R.string.movie_added_to_favorite), movie.getTitle()));
    }

    //delete favorite
    private void deleteFavorite() {
        movieHelper.delete(movie.getId());
        showSnackbar(String.format(getResources().getString(R.string.movie_removed_from_favorite), movie.getTitle()));
    }

    private void showSnackbar(String message) {
        Snackbar.make(fabFavorite, message, Snackbar.LENGTH_LONG).show();
    }
}