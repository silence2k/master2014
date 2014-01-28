package hud.steuerung;


public abstract class SpaceCoreSteuerung {
	


	protected abstract String getSchub();
	
	protected abstract String getFahrgestell();
	
	protected abstract String getLinks();
	
	protected abstract String getRechts();
	
	protected abstract String getHoch();
	
	protected abstract String getRunter();
	
	
	protected float schub;
	
	protected boolean links;
	protected boolean rechts;
	protected boolean hoch;
	protected boolean runter;
	protected boolean fahrwerkEingezogen;


	
	
	public String getAMQMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append(getSchub());
		sb.append(";");
		sb.append(getFahrgestell());
		sb.append(";");
		sb.append(getLinks());
		sb.append(";");
		sb.append(getRechts());
		sb.append(";");
		sb.append(getHoch());
		sb.append(";");
		sb.append(getRunter());
		
		links = false;
		rechts = false;
		hoch = false;
		runter = false;
		
		return sb.toString();
	}
	
	

}
