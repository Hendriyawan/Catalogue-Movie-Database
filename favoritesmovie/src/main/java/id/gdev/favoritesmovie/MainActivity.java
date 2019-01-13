package id.gdev.favoritesmovie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.gdev.favoritesmovie.adapter.FavoriteAdapter;

import static id.gdev.favoritesmovie.db.contract.MovieContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_favorite_movie)
    RecyclerView rvFavorite;

    private Cursor listFavorite;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bind view
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
        rvFavorite.setLayoutManager(new GridLayoutManager(this, 2));
        rvFavorite.setHasFixedSize(true);

        adapter = new FavoriteAdapter(this);
        adapter.setListFavorite(listFavorite);
        rvFavorite.setAdapter(adapter);

        new LoadFavoriteMovie().execute();
    }

    private class LoadFavoriteMovie extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            listFavorite = cursor;
            adapter.setListFavorite(listFavorite);
            adapter.notifyDataSetChanged();
        }
    }
}
