package id.gdev.favoritesmovie.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.gdev.favoritesmovie.BuildConfig;
import id.gdev.favoritesmovie.R;
import id.gdev.favoritesmovie.model.MovieModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Activity activity;
    private Cursor listFavorite;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavoriteHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder, int i) {
        final MovieModel movie = getItem(i);
        Picasso.get().load(BuildConfig.IMG_URL + movie.getPosterPath())
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_blank_image100))
                .error(activity.getResources().getDrawable(R.drawable.ic_blank_image100)).into(favoriteHolder.imgPoster);

        favoriteHolder.tvTitle.setText(movie.getTitle());
        favoriteHolder.tvDateRelease.setText(movie.getDateOfRelease());
        favoriteHolder.tvVote.setText(movie.getVote());

    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    public MovieModel getItem(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Invalid position");
        }
        return new MovieModel(listFavorite);
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_fav_img_poster)
        ImageView imgPoster;
        @BindView(R.id.item_fav_title)
        TextView tvTitle;
        @BindView(R.id.item_fav_date_release)
        TextView tvDateRelease;
        @BindView(R.id.item_fav_vote)
        TextView tvVote;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
