package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mathe {
	
	public static double mittelWert(int[] gewicht, int[] anzahl){
		if(gewicht.length != anzahl.length){
			throw new RuntimeException("Längen sind ungleich!!!! gewichte: "+gewicht+" anzahl: "+anzahl);
		}
		List<Paar> paare = new ArrayList<>();
		for(int i = 0; i < anzahl.length; i++){
			paare.add(new Paar(gewicht[i], anzahl[i]));
		}
		return mittelWert(paare);
	}
	
	public static double mittelWert(Collection<Paar> coll){
		double result = 0;
		int sum = 0;
		int prod = 0;
		for (Paar paar : coll) {
			sum = sum + paar.getAnzahl();
			prod = prod + (paar.getAnzahl() * paar.getGewicht());
		}
		
		result = (double)prod / sum;
		
		return result;
	}
	
	public static void main(String args[]){
		int[] gewicht = {1,2,3,4,5,6};
		int[] anzahl = {1,1,1,1,1,1};
		double mw = mittelWert(gewicht, anzahl);
		System.out.println("mw: "+mw);
	}

	
}
