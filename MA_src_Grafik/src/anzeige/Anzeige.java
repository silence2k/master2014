package anzeige;

import java.util.ArrayList;
import java.util.List;

import schalttafel.anzeige.Anzeiger;
import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Griff;
import aktor.Aktor;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.AnalogListener;

public abstract class Anzeige extends SimpleApplication implements AnalogListener {

	protected boolean physic = false;

	private boolean links = true;
	private boolean rechts = false;

	protected Aktor handRechts = new Aktor(this, rechts);
	protected Aktor handLinks = new Aktor(this, links);

	protected List<Artefakt> artefakte = new ArrayList<>();

	protected List<Anzeiger> anzeiger = new ArrayList<>();

	public Griff dichtesterGriff(Aktor aktor) {
		Griff result = null;
		float distance = Float.MAX_VALUE;
		float tmp;
		for (Artefakt arte : artefakte) {
			tmp = arte.distanceFreierGriff(aktor);
			if (tmp < distance) {
				result = arte.dichtesterFreierGriff(aktor);
				distance = tmp;
			}
		}

		return result;
	}
}
