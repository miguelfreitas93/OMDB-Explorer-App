package miguel.freitas.omdb.app.core.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import miguel.freitas.omdb.app.R;
import miguel.freitas.omdb.app.core.adapters.viewholders.MovieViewHolder;
import miguel.freitas.omdb.app.core.listeners.MovieOnClickListener;
import miguel.freitas.omdb.app.core.models.Movie;
import miguel.freitas.omdb.app.core.utils.CommonUtils;
import miguel.freitas.omdb.app.core.utils.Constants;

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
		holder.mDirectorView.setText(type);
		holder.mTitleView.setText(title);
		holder.mYearView.setText(year);

		if(CommonUtils.isActualYear(year)){
			holder.mYearView.setTextColor(Color.RED);
			holder.mYearView.setTypeface(holder.mYearView.getTypeface(), Typeface.BOLD);
		} else{
			holder.mYearView.setTextColor(Color.BLACK);
			holder.mYearView.setTypeface(holder.mYearView.getTypeface(), Typeface.NORMAL);
		}

		final String imageUrl;
		if (!movie.getPoster().equals(Constants.N_A)) {
			imageUrl = movie.getPoster();
		} else {
			imageUrl = activity.getString(R.string.default_poster);
		}

		holder.mThumbImageView.layout(0, 0, 0, 0); // invalidate the width so that glide wont use that dimension
		Glide.with(activity).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.mThumbImageView);

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
		Glide.clear(holder.mThumbImageView);
	}

	public void joinList(List<Movie> newList){
		List<Movie> joinList = new ArrayList<>();
		joinList.addAll(this.movieList);
		joinList.addAll(newList);

		this.movieList = joinList;
		notifyDataSetChanged();
	}
}