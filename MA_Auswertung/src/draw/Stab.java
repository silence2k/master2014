package draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Abstand;
import util.Mathe;
import util.Paar;

public abstract class Stab {
	
	protected String name;
	protected String name2;
	protected String[] names;
	
	protected int[] werte;
	protected int[] anzahl;
	
	protected int anzahlAntworten;
	
	protected double mittelwert;
	
	protected Map<String, Integer> xMap = Abstand.getAbstandsMap();
	
	
	
	protected Stab(String name, String name2, String[] names, int[] werte, int[] anzahl,int anzahlAntworten) {
		super();
		this.name = name;
		this.name2 = name2;
		this.names = names;
		this.werte = werte;
		this.anzahl = anzahl;
		this.anzahlAntworten = anzahlAntworten;
		
		
		if(names.length != werte.length || werte.length != anzahl.length){
			throw new RuntimeException("Längen sind ungleich!!!! name: " + name +"name länge: "+names.length+" werte: "+werte.length+" anzahl: "+anzahl.length);
		}
		
		init();
	}
	
	
	private void init(){
		List<Paar> paare = new ArrayList<Paar>();
		
		for(int i = 0; i < anzahl.length; i++){
			paare.add(new Paar(werte[i], anzahl[i]));
		}
		
	mittelwert = Mathe.mittelWert(paare);
	
	
	}
	
	
	

	public abstract void draw(int xStart, int yStart, int Xoffset, int Yoffset, Graphics g);
	
	
}
