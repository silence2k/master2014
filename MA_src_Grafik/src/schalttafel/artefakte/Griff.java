package schalttafel.artefakte;

import aktor.Aktor;

import com.jme3.scene.Geometry;

public class Griff {
	
	protected Artefakt artefakt;
	
	protected MyMaterial griffmaterial;
	
	protected Geometry geo = null;
	
	protected boolean aktive = true;
	
	protected Aktor aktor;
	
	
	
	public Griff(Artefakt artefakt, MyMaterial griffmaterial, Geometry geo) {
		super();
		this.artefakt = artefakt;
		this.griffmaterial = griffmaterial;
		this.geo = geo;
	}
	
	


	public boolean isAktive() {
		return aktive;
	}




	public void setAktive(boolean aktive) {
		this.aktive = aktive;
	}




	public boolean isGreifbar(){
		return aktive && aktor!=null;
	}
	
	
	public float distance(Aktor aktor){
		return geo.getWorldTranslation().distance(aktor.getLocalTranslation());
	}

}
