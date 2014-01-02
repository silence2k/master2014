package schalttafel.artefakte;

import aktor.Aktor;

public interface Greifbar {

	public boolean isGreifbar();

	public float distanceFreierGriff(Aktor aktor);

}
