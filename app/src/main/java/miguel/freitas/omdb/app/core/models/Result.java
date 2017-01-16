package miguel.freitas.omdb.app.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable {

	private List<Movie> Search;
	private String totalResults;
	private String Response;

	public Result() {
	}

	public List<Movie> getSearch() {
		return Search;
	}

	public void setSearch(List<Movie> search) {
		this.Search = search;
	}

	public void joinListOfResults(List<Movie> newSearch) {
		List<Movie> union = new ArrayList<>();
		union.addAll(newSearch);
		union.addAll(Search);
		setSearch(union);
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		this.Response = response;
	}

	@Override
	public String toString() {
		return "getMovieList{" +
				"Search=" + Search +
				", totalResults='" + totalResults + '\'' +
				", Response='" + Response + '\'' +
				'}';
	}
}