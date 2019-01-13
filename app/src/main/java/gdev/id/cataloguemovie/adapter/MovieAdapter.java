package gdev.id.cataloguemovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gdev.id.cataloguemovie.BuildConfig;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.model.MovieModel;

//created by Hendriyawan 5 December 2018
//Submisson 2 (MADE) : Catalogue Movie Ui Ux

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context context;
    private ArrayList<MovieModel> listMovie;
    private OnItemClick itemClick;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void addItem(ArrayList<MovieModel> listMovie) {
        this.listMovie = listMovie;
    }

    public void setOnItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
        notifyDataSetChanged();
    }

    public ArrayList<MovieModel> getListMovie() {
        return listMovie;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MovieHolder movieHolder) {
        super.onViewDetachedFromWindow(movieHolder);
        movieHolder.getView().clearAnimation();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_movie, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, final int position) {
        movieHolder.getView().setAnimation(AnimationUtils.loadAnimation(context, R.anim.scroll_anim));

        final MovieModel movie = listMovie.get(position);
        Picasso.get().load(BuildConfig.IMG_URL + movie.getPosterPath())
                .placeholder(context.getResources().getDrawable(R.drawable.ic_blank_image100))
                .error(context.getResources().getDrawable(R.drawable.ic_blank_image100)).into(movieHolder.imgPoster);
        movieHolder.tvTitle.setText(movie.getTitle());
        movieHolder.tvDateRelease.setText(movie.getDateOfRelease());
        movieHolder.tvVote.setText(movie.getVote());
        movieHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.OnItemClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    //interface
    public interface OnItemClick {
        void OnItemClicked(MovieModel model);
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_grid_img_poster)
        ImageView imgPoster;
        @BindView(R.id.item_grid_tv_title)
        TextView tvTitle;
        @BindView(R.id.item_grid_tv_date_release)
        TextView tvDateRelease;
        @BindView(R.id.item_grid_tv_vote)
        TextView tvVote;
        View view;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public View getView() {
            return view;
        }
    }
}