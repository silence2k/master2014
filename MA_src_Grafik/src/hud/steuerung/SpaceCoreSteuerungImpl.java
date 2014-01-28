package hud.steuerung;

import schalttafel.anzeige.IF_Anzeiger;

public class SpaceCoreSteuerungImpl extends SpaceCoreSteuerung{
	
	private JoyStickAnzeiger jsa = new JoyStickAnzeiger();
	private SchieberAnzeiger sa = new SchieberAnzeiger();
	private HebelAnzeiger ha = new HebelAnzeiger();
	
	
	
	
	
	
	
	public JoyStickAnzeiger getJsa() {
		return jsa;
	}

	public SchieberAnzeiger getSa() {
		return sa;
	}

	public HebelAnzeiger getHa() {
		return ha;
	}

	protected String getSchub(){
		return "50.0";
	}
	
	protected String getFahrgestell(){
		return "h";
	}
	
	protected String getLinks(){
		return "l";
	}
	
	protected String getRechts(){
		return "r";
	}
	
	protected String getHoch(){
		return "h";
	}
	
	protected String getRunter(){
		return "u";
	}
	
	
	
	
	class JoyStickAnzeiger implements IF_Anzeiger{

		@Override
		public void setZielWert(float zielwert) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SchieberAnzeiger implements IF_Anzeiger{

		@Override
		public void setZielWert(float zielwert) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(float deltaTime) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class HebelAnzeiger implements IF_Anzeiger{

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
