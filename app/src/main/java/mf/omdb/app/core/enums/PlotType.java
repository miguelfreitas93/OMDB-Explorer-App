package mf.omdb.app.core.enums;

public enum PlotType {

	SHORT("short"),
	FULL("full"),
	DEFAULT("short");

	private final String plotType;

	private PlotType(final String plotType) {
		this.plotType = plotType;
	}

	@Override
	public String toString() {
		return plotType;
	}

}