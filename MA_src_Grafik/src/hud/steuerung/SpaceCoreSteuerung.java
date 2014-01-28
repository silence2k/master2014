package hud.steuerung;


public abstract class SpaceCoreSteuerung {
	


	protected abstract String getSchub();
	
	protected abstract String getFahrgestell();
	
	protected abstract String getLinks();
	
	protected abstract String getRechts();
	
	protected abstract String getHoch();
	
	protected abstract String getRunter();


	
	
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
		
		return sb.toString();
	}
	
	

}
