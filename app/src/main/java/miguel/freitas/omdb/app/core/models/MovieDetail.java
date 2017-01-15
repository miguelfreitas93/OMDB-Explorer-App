package miguel.freitas.omdb.app.core.models;

import java.io.Serializable;

public class MovieDetail extends Movie implements Serializable {

	private String Rated;
	private String Released;
	private String Runtime;
	private String Genre;
	private String Director;
	private String Writer;
	private String Actors;
	private String Plot;
	private String Language;
	private String Country;
	private String Awards;
	private String Metascore;
	private String imdbRating;
	private String imdbVotes;
	private String Response;
	private String tomatoMeter;
	private String tomatoImage;
	private String tomatoRating;
	private String tomatoReviews;
	private String tomatoFresh;
	private String tomatoRotten;
	private String tomatoConsensus;
	private String tomatoUserMeter;
	private String tomatoUserRating;
	private String tomatoUserReviews;
	private String tomatoURL;
	private String DVD;
	private String BoxOffice;
	private String Production;
	private String Website;

	@Override
	public String toString() {
		return "getMovieDetail{" +
				"Rated='" + Rated + '\'' +
				", Released='" + Released + '\'' +
				", Runtime='" + Runtime + '\'' +
				", Genre='" + Genre + '\'' +
				", Director='" + Director + '\'' +
				", Writer='" + Writer + '\'' +
				", Actors='" + Actors + '\'' +
				", Plot='" + Plot + '\'' +
				", Language='" + Language + '\'' +
				", Country='" + Country + '\'' +
				", Awards='" + Awards + '\'' +
				", Metascore='" + Metascore + '\'' +
				", imdbRating='" + imdbRating + '\'' +
				", imdbVotes='" + imdbVotes + '\'' +
				", Response='" + Response + '\'' +
				'}';
	}

	public String getRated() {
		return Rated;
	}

	public void setRated(String rated) {
		this.Rated = rated;
	}

	public String getReleased() {
		return Released;
	}

	public void setReleased(String released) {
		this.Released = released;
	}

	public String getRuntime() {
		return Runtime;
	}

	public void setRuntime(String runtime) {
		this.Runtime = runtime;
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		this.Genre = genre;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		this.Director = director;
	}

	public String getWriter() {
		return Writer;
	}

	public void setWriter(String writer) {
		this.Writer = writer;
	}

	public String getActors() {
		return Actors;
	}

	public void setActors(String actors) {
		this.Actors = actors;
	}

	public String getPlot() {
		return Plot;
	}

	public void setPlot(String plot) {
		this.Plot = plot;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		this.Language = language;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		this.Country = country;
	}

	public String getAwards() {
		return Awards;
	}

	public void setAwards(String awards) {
		this.Awards = awards;
	}

	public String getMetascore() {
		return Metascore;
	}

	public void setMetascore(String metascore) {
		this.Metascore = metascore;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		this.Response = response;
	}

	public String getTomatoMeter() {
		return tomatoMeter;
	}

	public void setTomatoMeter(String tomatoMeter) {
		this.tomatoMeter = tomatoMeter;
	}

	public String getTomatoImage() {
		return tomatoImage;
	}

	public void setTomatoImage(String tomatoImage) {
		this.tomatoImage = tomatoImage;
	}

	public String getTomatoRating() {
		return tomatoRating;
	}

	public void setTomatoRating(String tomatoRating) {
		this.tomatoRating = tomatoRating;
	}

	public String getTomatoReviews() {
		return tomatoReviews;
	}

	public void setTomatoReviews(String tomatoReviews) {
		this.tomatoReviews = tomatoReviews;
	}

	public String getTomatoFresh() {
		return tomatoFresh;
	}

	public void setTomatoFresh(String tomatoFresh) {
		this.tomatoFresh = tomatoFresh;
	}

	public String getTomatoRotten() {
		return tomatoRotten;
	}

	public void setTomatoRotten(String tomatoRotten) {
		this.tomatoRotten = tomatoRotten;
	}

	public String getTomatoConsensus() {
		return tomatoConsensus;
	}

	public void setTomatoConsensus(String tomatoConsensus) {
		this.tomatoConsensus = tomatoConsensus;
	}

	public String getTomatoUserMeter() {
		return tomatoUserMeter;
	}

	public void setTomatoUserMeter(String tomatoUserMeter) {
		this.tomatoUserMeter = tomatoUserMeter;
	}

	public String getTomatoUserRating() {
		return tomatoUserRating;
	}

	public void setTomatoUserRating(String tomatoUserRating) {
		this.tomatoUserRating = tomatoUserRating;
	}

	public String getTomatoUserReviews() {
		return tomatoUserReviews;
	}

	public void setTomatoUserReviews(String tomatoUserReviews) {
		this.tomatoUserReviews = tomatoUserReviews;
	}

	public String getTomatoURL() {
		return tomatoURL;
	}

	public void setTomatoURL(String tomatoURL) {
		this.tomatoURL = tomatoURL;
	}

	public String getDVD() {
		return DVD;
	}

	public void setDVD(String DVD) {
		this.DVD = DVD;
	}

	public String getBoxOffice() {
		return BoxOffice;
	}

	public void setBoxOffice(String boxOffice) {
		BoxOffice = boxOffice;
	}

	public String getProduction() {
		return Production;
	}

	public void setProduction(String production) {
		Production = production;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String website) {
		Website = website;
	}
}