package gdev.id.cataloguemovie.mvp.movie;

import android.content.Context;
import android.os.Handler;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gdev.id.cataloguemovie.model.MovieModel;

public class MoviePresenter {
    private Context context;
    private MovieView movieView;
    private static final String DEFAULT_NETWORK_ERROR = "Something wrong ! Please try again";
    private static final String NETWORK_ERROR_NO_CONNECTION = "No connection internet !";
    private static final String NETWORK_ERROR_TIMEOUT = "Connection timeout !";

    public MoviePresenter(Context context, MovieView movieView) {
        this.context = context;
        this.movieView = movieView;
    }

    public void loadData(final GoogleProgressBar progressBar, String url) {
        progressBar.setVisibility(GoogleProgressBar.VISIBLE);

        final RequestQueue queue = Volley.newRequestQueue(context);
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(GoogleProgressBar.GONE);
                ArrayList<MovieModel> listMovie = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject values = jsonArray.getJSONObject(i);
                        //put parcelable
                        MovieModel movie = new MovieModel();
                        movie.setPosterPath(values.getString("poster_path"));
                        movie.setTitle(values.getString("title"));
                        movie.setVote(values.getString("vote_count"));
                        movie.setRating(values.getString("vote_average"));
                        movie.setDateOfRelease(values.getString("release_date"));
                        movie.setOverview(values.getString("overview"));
                        listMovie.add(movie);
                    }

                } catch (JSONException e) {
                    //handle exception
                    e.printStackTrace();
                }
                movieView.onLoadSuccess(listMovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    progressBar.setVisibility(GoogleProgressBar.GONE);
                    message = DEFAULT_NETWORK_ERROR;

                } else if (error instanceof NoConnectionError) {
                    progressBar.setVisibility(GoogleProgressBar.GONE);
                    message = NETWORK_ERROR_NO_CONNECTION;

                } else if (error instanceof TimeoutError) {
                    progressBar.setVisibility(GoogleProgressBar.GONE);
                    message = NETWORK_ERROR_TIMEOUT;
                }
                else {
                    progressBar.setVisibility(GoogleProgressBar.GONE);
                    message = DEFAULT_NETWORK_ERROR;
                }
                movieView.onLoadError(message);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(request);
            }
        }, 2000);
    }
}