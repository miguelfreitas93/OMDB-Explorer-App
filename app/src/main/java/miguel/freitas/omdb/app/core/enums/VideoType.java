package miguel.freitas.omdb.app.core.enums;

public enum VideoType {

	MOVIE("movie"),
	SERIES("series"),
	EPISODE("episode"),
	DEFAULT("movie");

	private final String type;

	private VideoType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}