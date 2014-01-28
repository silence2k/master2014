package schalttafel.artefakte;

import com.jme3.audio.AudioNode;

public abstract class Rad extends Artefakt {

	protected float rotation = 0;

	protected AudioNode audioRaddreh;
	protected AudioNode audioRadende;

	public void setAuf(boolean auf) {
		if (auf) {
			rotation = maxRot;
		} else {
			rotation = minRot;
		}

	}

	@Override
	protected void init() {
		minRot = 0;

		maxRot = (float) (6 * Math.PI);

	}

	protected float zielwert() {
		float tmp = maxRot - minRot;

		float tmp2 = rotation / tmp * 100f;
		System.out.println(tmp + " : " + tmp2);
		if (tmp2 > 100f) {
			tmp2 = 100f;
		}
		return tmp2;
	}

}
