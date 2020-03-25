package mf.omdb.app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mf.omdb.app.R;
import mf.omdb.app.core.models.MovieDetail;
import mf.omdb.app.core.utils.CommonUtils;
import mf.omdb.app.core.utils.Constants;
import mf.omdb.app.databinding.DetailActivityBinding;

public class MovieDetailsActivity extends AppCompatActivity {

	private static final String TAG = MovieDetailsActivity.class.getSimpleName();

	@BindView(R.id.year)
	TextView year;
	@BindView(R.id.main_backdrop)
	ImageView mainBackDrop;
	@BindView(R.id.main_collapsing)
	CollapsingToolbarLayout mainCollapsing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DetailActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.detail_activity);
		ButterKnife.bind(this);

		final MovieDetail detail = (MovieDetail) getIntent().getSerializableExtra(Constants.MOVIE_DETAIL);
		final String imageUrl = getIntent().getStringExtra(Constants.IMAGE_URL);
		Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(mainBackDrop);

		if (detail != null) {
			mainCollapsing.setTitle(detail.getTitle());
			binding.setMovieDetails(detail);
			if (CommonUtils.isActualYear(detail.getYear())) {
				year.setTextColor(Color.RED);
				year.setTypeface(year.getTypeface(), Typeface.BOLD);
			}
		}
	}
}