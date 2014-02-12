package draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Mathe;
import util.Paar;

public abstract class Stab {
	
	protected String[] names;
	
	protected int[] gewichte;
	protected int[] values;
	
	protected double mittelwert;
	
	protected Map<String, Integer> xMap;
	
	
	protected Stab(String[] names, int[] gewichte, int[] values) {
		super();
		this.names = names;
		this.gewichte = gewichte;
		this.values = values;
		
		
		if(names.length != gewichte.length || gewichte.length != values.length){
			throw new RuntimeException("Längen sind ungleich!!!! names: " + names +" gewichte: "+gewichte+" values: "+values);
		}
		
		init();
	}
	
	
	private void init(){
		List<Paar> paare = new ArrayList<Paar>();
		
		for(int i = 0; i < values.length; i++){
			paare.add(new Paar(gewichte[i], values[i]));
		}
		
	mittelwert = Mathe.mittelWert(paare);
	
	
	
	initxMap();
	}
	
	protected void initxMap(){
		xMap = new HashMap<String, Integer>();
	}
	
	

	public abstract void draw(int Xoffset, int Yoffset, Graphics g);
	
	
}
