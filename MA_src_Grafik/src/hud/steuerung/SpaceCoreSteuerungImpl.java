package hud.steuerung;

import schalttafel.anzeige.IF_Anzeiger;

public class SpaceCoreSteuerungImpl extends SpaceCoreSteuerung{
	
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

	protected String getSchub(){
		return "50.0";
	}
	
//	String hoch = "s";
//	String runter = "w";
//	String links = "a";
//	String rechts = "d";
	
	protected String getFahrgestell(){
		return fahrwerkEingezogen?"-h":"h";
	}
	
	protected String getLinks(){
		return links?"a":"-a";
	}
	
	protected String getRechts(){
		return rechts?"d":"-d";
	}
	
	protected String getHoch(){
		return hoch?"s":"-s";
	}
	
	protected String getRunter(){
		return runter?"w":"-w";
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
