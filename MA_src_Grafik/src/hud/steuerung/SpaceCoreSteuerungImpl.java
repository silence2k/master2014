package hud.steuerung;

import schalttafel.anzeige.IF_Anzeiger;

public class SpaceCoreSteuerungImpl extends SpaceCoreSteuerung {

	private JoyStickAnzeiger jsa = new JoyStickAnzeiger();
	private SchieberAnzeiger sa = new SchieberAnzeiger();
	private HebelAnzeiger ha = new HebelAnzeiger();

	public JoyStickAnzeiger getJoyStickAnzeiger() {
		return jsa;
	}

	public SchieberAnzeiger getSchieberAnzeiger() {
		return sa;
	}

	public HebelAnzeiger getHebelAnzeiger() {
		return ha;
	}

	protected String getSchub() {
		return Float.toString(schub);
	}

	// String hoch = "s";
	// String runter = "w";
	// String links = "a";
	// String rechts = "d";

	protected String getFahrgestell() {
		return fahrwerkEingezogen ? "-h" : "h";
	}

	protected String getLinks() {
		return links ? "a" : "-a";
	}

	protected String getRechts() {
		return rechts ? "d" : "-d";
	}

	protected String getHoch() {
		return hoch ? "s" : "-s";
	}

	protected String getRunter() {
		return runter ? "w" : "-w";
	}

	/*
	 * .....-
	 * ...1 2 3
	 *  + 4...5 -
	 * ...6 7 8
	 * .... +
	 */

	class JoyStickAnzeiger implements IF_Anzeiger {

		@Override
		public void setZielWert(float zielwert) {
			runter = false;
			links = false;
			rechts = false;
			hoch = false;

			if (zielwert == 1f || zielwert == 2f || zielwert == 3f) {
				runter = true;
			}
			if (zielwert == 1f || zielwert == 4f || zielwert == 6f) {
				links = true;
			}
			if (zielwert == 3f || zielwert == 5f || zielwert == 8f) {
				rechts = true;
			}
			if (zielwert == 6f || zielwert == 7f || zielwert == 8f) {
				hoch = true;
			}

		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub

		}

	}

	class SchieberAnzeiger implements IF_Anzeiger {

		@Override
		public void setZielWert(float zielwert) {
			schub = zielwert;
		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub

		}

	}

	class HebelAnzeiger implements IF_Anzeiger {

		@Override
		public void setZielWert(float zielwert) {
			// TODO Auto-generated method stub

		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub

		}

	}

}
