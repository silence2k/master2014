package schalttafel.artefakte;

import aktor.Aktor;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

public class AktorGriff {

	protected final ColorRGBA Greifbar = new ColorRGBA(0f, 1f, 0f, 1f);
	protected final ColorRGBA Gegriffen = new ColorRGBA(1f, 0f, 0f, 1f);
	protected final ColorRGBA Inaktiv = new ColorRGBA(1f, 1f, 1f, 1f);

	protected Artefakt artefakt;

	protected MyMaterial griffmaterial;

	protected Geometry geo = null;

	protected boolean aktive = true;

	protected Aktor aktor;

	public AktorGriff(Artefakt artefakt, MyMaterial griffmaterial, Geometry geo) {
		super();
		this.artefakt = artefakt;
		this.griffmaterial = griffmaterial;
		this.geo = geo;
	}

	public float distanceToActor() {
		return distance(aktor);
	}

	public boolean isAktive() {
		return aktive;
	}

	public void greifen(Aktor aktor) {
		this.aktor = aktor;
		griffmaterial.setColor(Gegriffen);
	}

	public void loslassen() {
		this.aktor = null;
		if (aktive) {
			griffmaterial.setColor(Greifbar);
		} else {
			griffmaterial.setColor(Inaktiv);
		}

	}

	public void handloesen() {
		if (aktor != null) {
			aktor.handOeffnen2();
		}
	}

	public void setAktive(boolean aktive) {
		this.aktive = aktive;
		if (aktive) {
			griffmaterial.setColor(Greifbar);
		} else {
			griffmaterial.setColor(Inaktiv);
		}
	}

	public boolean isGegriffen() {
		return aktor != null;
	}

	public boolean isGreifbar() {
		return aktive && aktor == null;
	}

	public float distance(Aktor aktor) {
		if (aktor == null) {
			return Float.MAX_VALUE;
		}
		return geo.getWorldTranslation().distance(aktor.getLocalTranslation());
	}

}
