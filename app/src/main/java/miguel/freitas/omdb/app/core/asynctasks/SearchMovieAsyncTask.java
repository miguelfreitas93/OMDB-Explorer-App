package miguel.freitas.omdb.app.core.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;

import miguel.freitas.omdb.app.R;
import miguel.freitas.omdb.app.core.adapters.MovieRecyclerViewAdapter;
import miguel.freitas.omdb.app.core.enums.ResponseType;
import miguel.freitas.omdb.app.core.interfaces.OmdbApiInterface;
import miguel.freitas.omdb.app.core.models.Result;
import miguel.freitas.omdb.app.core.utils.CommonUtils;
import retrofit2.Call;

public class SearchMovieAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = SearchMovieAsyncTask.class.getSimpleName();

	private RecyclerView recyclerView;
	private Activity activity;
	private String movieTitle, movieYear, movieType;
	private Result result;
	private ProgressBar progressBar;
	private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
	private int page;

	public SearchMovieAsyncTask(Activity activity, RecyclerView recyclerView, String movieTitle, ProgressBar progressBar, String movieYear, String movieType) {
		this.recyclerView = recyclerView;
		this.activity = activity;
		this.movieTitle = movieTitle;
		this.progressBar = progressBar;
		this.movieType = movieType;
		this.movieYear = movieYear;
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
		if(result != null) {
			movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(activity, result.getSearch());
			recyclerView.setAdapter(movieRecyclerViewAdapter);
		}
		progressBar.setVisibility(View.GONE);
		recyclerView.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected Void doInBackground(Void... params) {
		if(movieType.equals(activity.getString(R.string.all))){
			movieType = "";
		}
		if(movieYear.equals(activity.getString(R.string.all))){
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