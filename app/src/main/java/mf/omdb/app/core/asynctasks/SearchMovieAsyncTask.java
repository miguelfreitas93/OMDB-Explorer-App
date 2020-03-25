package mf.omdb.app.core.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import mf.omdb.app.R;
import mf.omdb.app.core.adapters.MovieRecyclerViewAdapter;
import mf.omdb.app.core.enums.ResponseType;
import mf.omdb.app.core.interfaces.OmdbApiInterface;
import mf.omdb.app.core.models.Result;
import mf.omdb.app.core.utils.CommonUtils;
import retrofit2.Call;

public class SearchMovieAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = SearchMovieAsyncTask.class.getSimpleName();

	private RecyclerView recyclerView;
	private Activity activity;
	private String movieTitle, movieYear, movieType;
	private Result result;
	private ProgressBar progressBar;
	private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
	private ImageView appImage;
	private int page;

	public SearchMovieAsyncTask(Activity activity, RecyclerView recyclerView, String movieTitle, ProgressBar progressBar, ImageView appImage, String movieYear, String movieType) {
		this.recyclerView = recyclerView;
		this.activity = activity;
		this.movieTitle = movieTitle;
		this.progressBar = progressBar;
		this.movieType = movieType;
		this.movieYear = movieYear;
		this.appImage = appImage;
		this.page = 1;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(activity.getResources().getInteger(R.integer.grid_column_count), StaggeredGridLayoutManager.VERTICAL);
		staggeredGridLayoutManager.setItemPrefetchEnabled(false);
		recyclerView.setItemAnimator(null);
		recyclerView.setLayoutManager(staggeredGridLayoutManager);
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if (result != null) {
			movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(activity, result.getSearch());
			recyclerView.setAdapter(movieRecyclerViewAdapter);
		}
		progressBar.setVisibility(View.GONE);
		if (result != null && result.getSearch() != null && result.getSearch().size() > 0) {
			recyclerView.setVisibility(View.VISIBLE);
			appImage.setVisibility(View.GONE);
		} else {
			recyclerView.setVisibility(View.GONE);
			appImage.setVisibility(View.VISIBLE);
			Snackbar.make(recyclerView, activity.getString(R.string.snackbar_title_not_found), Snackbar.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (movieType.equals(activity.getString(R.string.all))) {
			movieType = "";
		}
		if (movieYear.equals(activity.getString(R.string.all))) {
			movieYear = "";
		}

		try {
			OmdbApiInterface omdbApiInterface = CommonUtils.getOmdbApiInterface();
			Call<Result> call = omdbApiInterface.getMovieList(movieTitle, page, ResponseType.JSON.toString(), movieYear, movieType);
			result = call.execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}