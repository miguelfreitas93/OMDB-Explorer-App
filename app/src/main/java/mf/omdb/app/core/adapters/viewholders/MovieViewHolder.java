package mf.omdb.app.core.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import mf.omdb.app.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
	public final View mView;
	public final TextView mTitleView;
	public final TextView mYearView;
	public final TextView mDirectorView;
	public final ImageView mThumbImageView;

	public MovieViewHolder(View view) {
		super(view);
		ButterKnife.bind(this, view);
		mView = view;
		mTitleView = (TextView) view.findViewById(R.id.movie_title);
		mYearView = (TextView) view.findViewById(R.id.movie_year);
		mThumbImageView = (ImageView) view.findViewById(R.id.thumbnail);
		mDirectorView = (TextView) view.findViewById(R.id.movie_director);
	}
}