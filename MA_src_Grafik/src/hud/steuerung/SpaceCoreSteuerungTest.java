package hud.steuerung;

import schalttafel.anzeige.IF_Anzeiger;

public class SpaceCoreSteuerungTest extends SpaceCoreSteuerung{
	
	private float schub;
	
	private boolean links;
	private boolean rechts;
	private boolean hoch;
	private boolean runter;
	private boolean fahrwerkEingezogen;
	
	
	public void setSchub(float dx){
		schub += dx;
		if(schub < 0){
			schub = 0;
		}else if(schub > 100){
			schub = 100;
		}
	}
	
	

	public void setLinks(boolean links) {
		this.links = links;
	}



	public void setRechts(boolean rechts) {
		this.rechts = rechts;
	}



	public void setHoch(boolean hoch) {
		this.hoch = hoch;
	}



	public void setRunter(boolean runter) {
		this.runter = runter;
	}



	public void setFahrwerkEingezogen(boolean fahrwerkEingezogen) {
		this.fahrwerkEingezogen = fahrwerkEingezogen;
	}



	protected String getSchub(){
		return Float.toString(schub);
	}
	
	protected String getFahrgestell(){
		return fahrwerkEingezogen?"-h":"h";
	}
	
	protected String getLinks(){
		return links?"l":"-l";
	}
	
	protected String getRechts(){
		return rechts?"r":"-u";
	}
	
	protected String getHoch(){
		return hoch?"h":"-h";
	}
	
	protected String getRunter(){
		return runter?"u":"-u";
	}
	

}
