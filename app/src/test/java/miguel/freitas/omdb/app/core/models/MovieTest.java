package miguel.freitas.omdb.app.core.models;

import org.junit.Before;
import org.junit.Test;

import miguel.freitas.omdb.app.core.enums.VideoType;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MovieTest {

	private Movie movie;

	@Before
	public void setUp() throws Exception {

		movie = new Movie();
		movie.setTitle("MovieTitle");
		movie.setImdbID("123");
		movie.setPoster("http://www.omdbapi.com");
		movie.setType(VideoType.MOVIE.toString());
	}

	@Test
	public void verifyIfTitleIsSetup() {
		assertEquals(movie.getTitle(), "MovieTitle");
	}

	@Test
	public void verifyIfPosterIsSetup() {
		assertEquals(movie.getPoster(), "http://www.omdbapi.com");
	}

	@Test
	public void verifyIfImdbIdIsSetup() {
		assertEquals(movie.getImdbID(), "123");
	}

	@Test
	public void verifyIfMovieHasTitle() {
		assertNotEquals(movie.getTitle(), "");
	}

	@Test
	public void verifyIfMovieHasImdbId() {
		assertNotEquals(movie.getImdbID(), "");
	}

	@Test
	public void verifyIfMovieHasPoster() {
		assertNotEquals(movie.getPoster(), "");
	}

	@Test
	public void verifyIfMovieHasType() {
		assertNotEquals(movie.getType(), "");
	}

	@Test
	public void verifyIfMovieHasTypeMovie() {
		assertNotEquals(movie.getType(), VideoType.MOVIE);
	}
}