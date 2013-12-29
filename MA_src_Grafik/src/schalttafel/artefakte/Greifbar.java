package schalttafel.artefakte;

import aktor.Aktor;

import com.jme3.math.Vector3f;

public interface Greifbar {
	
	public boolean isGreifbar();
	
	public void setGreifbar(boolean greifbar);
	
	public Vector3f getGreifbarePostion();
	
	public void setAktor(Aktor aktor);

}
