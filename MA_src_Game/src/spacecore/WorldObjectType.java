package spacecore;

public enum WorldObjectType {

	// @formatter:off
	CUBE("Cube"), AIRSTRIP("airstrip");

	// @formatter:on
	String name;

	private WorldObjectType(String name) {
		this.name = name;
	}
}
