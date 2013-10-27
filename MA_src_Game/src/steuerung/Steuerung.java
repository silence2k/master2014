package steuerung;

public interface Steuerung {
	
	public boolean isNaseHoch();
	
	public boolean isNaseRunter();
	
	public boolean isRollenLinks();
	
	public boolean isRollenRechts();
	
	public boolean isSeitenruderLinks();
	
	public boolean isSeitenruderRechts();
	
	public boolean isSchubGeben();
	
	public boolean isSchubWegnehmen();
	
	public void cleanUp();

}
