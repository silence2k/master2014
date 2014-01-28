package hud.steuerung;


public class SpaceCoreSteuerungTest extends SpaceCoreSteuerung{
	

	
	
	public void setSchub(double dx){
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
	

}
