package mf.omdb.app.core.enums;

public enum ResponseType {

	JSON("json"),
	XML("xml"),
	DEFAULT("json");

	private final String reponseType;

	private ResponseType(final String reponseType) {
		this.reponseType = reponseType;
	}

	@Override
	public String toString() {
		return reponseType;
	}
}