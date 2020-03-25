package mf.omdb.app.core.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import mf.omdb.app.R;
import mf.omdb.app.core.adapters.viewholders.MovieViewHolder;
import mf.omdb.app.core.listeners.MovieOnClickListener;
import mf.omdb.app.core.models.Movie;
import mf.omdb.app.core.utils.CommonUtils;
import mf.omdb.app.core.utils.Constants;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

	private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();

	private List<Movie> movieList;
	private Activity activity;

	public MovieRecyclerViewAdapter(Activity activity, List<Movie> movieList) {
		this.activity = activity;
		this.movieList = movieList;
	}

	@Override
	public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
		return new MovieViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final MovieViewHolder holder, final int position) {

		final Movie movie = movieList.get(position);
		final String title = movie.getTitle();
		final String year = movie.getYear();
		final String type = movie.getType();
		int color;
		int typeface;

		holder.mDirectorView.setText(type);
		holder.mTitleView.setText(title);
		holder.mYearView.setText(year);

		if (CommonUtils.isActualYear(year)) {
			color = Color.RED;
			typeface = Typeface.BOLD;
		} else {
			color = Color.BLACK;
			typeface = Typeface.NORMAL;
		}
		holder.mYearView.setTextColor(color);
		holder.mYearView.setTypeface(holder.mYearView.getTypeface(), typeface);

		final String imageUrl;
		if (!movie.getPoster().equals(Constants.N_A)) {
			imageUrl = movie.getPoster();
		} else {
			imageUrl = activity.getString(R.string.default_poster);
		}

		holder.mThumbImageView.layout(0, 0, 0, 0); // invalidate the width so that glide wont use that dimension
		Glide.with(activity).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.mThumbImageView);

		holder.mView.setOnClickListener(new MovieOnClickListener(activity, movie, holder.mThumbImageView, imageUrl));
	}

	@Override
	public int getItemCount() {
		if (movieList == null) {
			return 0;
		}
		return movieList.size();
	}

	@Override
	public void onViewRecycled(MovieViewHolder holder) {
		super.onViewRecycled(holder);
	}
}