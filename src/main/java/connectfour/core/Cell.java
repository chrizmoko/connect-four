package connectfour.core;

public enum Cell {
	RED("Red"),
	YELLOW("Yellow"),
	EMPTY("[Empty]");

	private final String name;

	private Cell(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}