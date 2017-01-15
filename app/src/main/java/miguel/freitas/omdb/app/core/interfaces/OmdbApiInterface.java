package miguel.freitas.omdb.app.core.interfaces;

import miguel.freitas.omdb.app.core.models.MovieDetail;
import miguel.freitas.omdb.app.core.models.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApiInterface {
	@GET(".")
	Call<Result> getMovieList(@Query("s") String title, @Query("page") int page, @Query("r") String responseType, @Query("y") String year, @Query("type") String movieType);

	@GET(".")
	Call<MovieDetail> getMovieDetail(@Query("i") String imdbId, @Query("tomatoes") boolean tomatoes, @Query("plot") String plot, @Query("r") String responseType);
}