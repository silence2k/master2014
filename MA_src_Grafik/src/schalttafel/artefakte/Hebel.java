package schalttafel.artefakte;

public abstract class Hebel extends Artefakt {

	protected float rotation = 0.01f;
	
	
	
	
	
	float grenzeOben = 0.6f;
	
	float grenzeUnten = 2.6f;
	
	
	
	protected void init(){
		// Heben oben max
		minRot= 0.2f;
		
		// Heben unten max
		maxRot = 3f;
	}
	

	
}
