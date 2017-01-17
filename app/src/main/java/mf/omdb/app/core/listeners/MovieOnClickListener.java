package mf.omdb.app.core.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import mf.omdb.app.core.asynctasks.GetMovieDetailsAsyncTask;
import mf.omdb.app.core.models.Movie;

public class MovieOnClickListener implements View.OnClickListener {

	private static final String TAG = MovieOnClickListener.class.getSimpleName();
	private Activity activity;
	private Movie movie;
	private ImageView mThumbImageView;
	private String imageUrl;

	public MovieOnClickListener(Activity activity, Movie movie, ImageView mThumbImageView, String imageUrl) {
		this.activity = activity;
		this.movie = movie;
		this.mThumbImageView = mThumbImageView;
		this.imageUrl = imageUrl;
	}

	@Override
	public void onClick(View v) {
		GetMovieDetailsAsyncTask getMovieDetailsAsyncTask = new GetMovieDetailsAsyncTask(activity, movie, mThumbImageView, imageUrl);
		getMovieDetailsAsyncTask.execute();
	}
}