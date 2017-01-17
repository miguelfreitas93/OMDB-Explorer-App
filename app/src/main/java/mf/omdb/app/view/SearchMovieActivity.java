package mf.omdb.app.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mf.omdb.app.R;
import mf.omdb.app.core.asynctasks.SearchMovieAsyncTask;
import mf.omdb.app.core.utils.CommonUtils;
import mf.omdb.app.core.utils.Constants;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private static final String TAG = SearchMovieActivity.class.getSimpleName();

	@BindView(R.id.recycler_view)
	RecyclerView mMovieListRecyclerView;
	@BindView(R.id.progress_spinner)
	ProgressBar mProgressBar;
	@BindView(R.id.app_image)
	ImageView appImage;
	private String movieTitle, movieYear, movieType;
	private SearchMovieAsyncTask searchMovieAsyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_movie_activiy);
		ButterKnife.bind(this);
		if (savedInstanceState != null) {
			movieTitle = savedInstanceState.getString(Constants.MOVIE_TITLE);
			movieYear = savedInstanceState.getString(Constants.MOVIE_YEAR);
			movieType = savedInstanceState.getString(Constants.MOVIE_TYPE);
		} else {
			movieTitle = Constants.FIRST_SEARCH;
			movieYear = getString(R.string.all);
			movieType = getString(R.string.all);
		}
		onQueryTextSubmit(movieTitle);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Constants.MOVIE_TITLE, movieTitle);
		outState.putString(Constants.MOVIE_YEAR, movieYear);
		outState.putString(Constants.MOVIE_TYPE, movieType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryRefinementEnabled(true);
		searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Log.e(TAG, query);
		if (CommonUtils.isNetworkAvailable(this)) {
			movieTitle = query;
			CommonUtils.hideSoftKeyboard(this);
			mProgressBar.setVisibility(View.VISIBLE);
			appImage.setVisibility(View.GONE);
			mMovieListRecyclerView.setVisibility(View.GONE);
			searchMovieAsyncTask = new SearchMovieAsyncTask(this, mMovieListRecyclerView, query, mProgressBar, appImage, movieYear, movieType);
			searchMovieAsyncTask.execute();
		} else {
			Snackbar.make(mMovieListRecyclerView, getString(R.string.network_not_available), Snackbar.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.filters:
				showFiltersDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		Log.e(TAG, newText);
		return true;
	}

	private void showFiltersDialog() {
		LayoutInflater li = LayoutInflater.from(this);
		View filtersDialog = li.inflate(R.layout.filters_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(filtersDialog);

		final Spinner yearSpinner = (Spinner) filtersDialog.findViewById(R.id.spinnerYear);
		final Spinner typeSpinner = (Spinner) filtersDialog.findViewById(R.id.spinnerType);

		List<String> years = new ArrayList<>();
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		int selectedYear = 0;
		years.add(getString(R.string.all));
		for (int i = thisYear; i >= Constants.FIRST_YEAR; i--) {
			if (movieYear.equals(Integer.toString(i))) {
				selectedYear = thisYear - i + 1;
			}
			years.add(Integer.toString(i));
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
		yearSpinner.setAdapter(adapter);
		yearSpinner.setSelection(selectedYear);
		String[] videoType = this.getResources().getStringArray(R.array.video_types);

		for (int i = 0; i < videoType.length; i++) {
			if (movieType.equals(videoType[i])) {
				typeSpinner.setSelection(i);
			}
		}

		alertDialogBuilder.setPositiveButton(getString(R.string.save),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						movieYear = yearSpinner.getSelectedItem().toString();
						movieType = typeSpinner.getSelectedItem().toString();
						onQueryTextSubmit(movieTitle);
					}
				}).setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}