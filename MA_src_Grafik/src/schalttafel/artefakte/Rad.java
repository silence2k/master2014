package schalttafel.artefakte;

public abstract class Rad extends Artefakt {

	protected float rotation = 0;

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
		return rotation / tmp * 100f;
	}

}
