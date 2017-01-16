package miguel.freitas.omdb.app.core.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import miguel.freitas.omdb.app.core.enums.VideoType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ResultTest {

	private Result result;

	@Before
	public void setUp() throws Exception {
		result = new Result();
		result.setTotalResults("100");
		result.setResponse("TRUE");

		List<Movie> movieList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Movie movie = new Movie();
			movie.setTitle("MovieTitle");
			movie.setImdbID("123");
			movie.setPoster("http://www.omdbapi.com");
			movie.setType(VideoType.MOVIE.toString());
			movieList.add(movie);
		}

		result.setSearch(movieList);
	}

	@Test
	public void verifyIfTotalResultIsOneHundred() {
		assertEquals(result.getTotalResults(), "100");
	}

	@Test
	public void verifyIfResponseIsTrue() {
		assertEquals(result.getResponse(), "TRUE");
	}

	@Test
	public void verifyIfMovieListHasTenElements() {
		assertEquals(result.getSearch().size(), 10);
	}

	@Test
	public void verifyIfEachMovieOnTheListHasTitle() {
		for (Movie movie : result.getSearch()) {
			assertNotEquals(movie.getTitle(), "");
		}
	}

	@Test
	public void verifyIfEachMovieOnTheListHasImdbId() {
		for (Movie movie : result.getSearch()) {
			assertNotEquals(movie.getImdbID(), "");
		}
	}

	@Test
	public void verifyIfEachMovieOnTheListHasPoster() {
		for (Movie movie : result.getSearch()) {
			assertNotEquals(movie.getPoster(), "");
		}
	}

	@Test
	public void verifyIfEachMovieOnTheListHasType() {
		for (Movie movie : result.getSearch()) {
			assertNotEquals(movie.getType(), "");
		}
	}

	@Test
	public void verifyIfEachMovieOnTheListHasTypeMovie() {
		for (Movie movie : result.getSearch()) {
			assertNotEquals(movie.getType(), VideoType.MOVIE);
		}
	}
}