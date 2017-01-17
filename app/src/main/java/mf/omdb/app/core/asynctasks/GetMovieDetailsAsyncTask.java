package mf.omdb.app.core.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import java.io.IOException;

import mf.omdb.app.core.enums.PlotType;
import mf.omdb.app.core.enums.ResponseType;
import mf.omdb.app.core.interfaces.OmdbApiInterface;
import mf.omdb.app.core.models.Movie;
import mf.omdb.app.core.models.MovieDetail;
import mf.omdb.app.core.utils.CommonUtils;
import mf.omdb.app.core.utils.Constants;
import mf.omdb.app.view.MovieDetailsActivity;
import retrofit2.Call;

public class GetMovieDetailsAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = GetMovieDetailsAsyncTask.class.getSimpleName();

	private Movie movie;
	private Activity activity;
	private String imageUrl;
	private MovieDetail movieDetail;
	private ImageView mThumbImageView;
	private ProgressDialog progressDialog;

	public GetMovieDetailsAsyncTask(Activity activity, Movie movie, ImageView mThumbImageView, String imageUrl) {
		this.activity = activity;
		this.movie = movie;
		this.imageUrl = imageUrl;
		this.mThumbImageView = mThumbImageView;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("Loading. Please wait...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);

		Intent intent = new Intent(activity, MovieDetailsActivity.class);
		// Pass data object in the bundle and populate details activity.
		intent.putExtra(Constants.MOVIE_DETAIL, movieDetail);
		intent.putExtra(Constants.IMAGE_URL, imageUrl);

		ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, mThumbImageView, Constants.POSTER);
		activity.startActivity(intent, options.toBundle());
		progressDialog.dismiss();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			OmdbApiInterface omdbApiInterface = CommonUtils.getOmdbApiInterface();
			Call<MovieDetail> call = omdbApiInterface.getMovieDetail(movie.getImdbID(), true, PlotType.FULL.toString(), ResponseType.JSON.toString());
			movieDetail = call.execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}