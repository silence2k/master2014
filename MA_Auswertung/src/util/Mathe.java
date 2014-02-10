package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mathe {
	
	public static List<Paar> paare(int[] gewicht, int[] anzahl){
		if(gewicht.length != anzahl.length){
			throw new RuntimeException("Längen sind ungleich!!!! gewichte: "+gewicht+" anzahl: "+anzahl);
		}
		List<Paar> paare = new ArrayList<>();
		for(int i = 0; i < anzahl.length; i++){
			paare.add(new Paar(gewicht[i], anzahl[i]));
		}
		return paare;
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
		System.out.println("mittelwert: "+result);
		return result;
	}
	
	public static void main(String args[]){
		int[] gewicht = {1,2,3,4,5,6};
//		int[] anzahl = {1,1,1,1,1,1};
		int[] anzahl = {0,0,1,1,0,0};
		List<Paar> coll = paare(gewicht, anzahl);
		double mw = mittelWert(coll);
		System.out.println("mw: "+mw);
		System.out.println("va: "+varianz(coll, mw));
		System.out.println("st: "+standardabweichung(coll, mw));
	}

	public static double varianz(Collection<Paar> coll, double mittelwert){
		double tmp = 0;
		int anzahl = 0;
		for(Paar paar : coll){
			tmp = tmp + (paar.getAnzahl() * Math.pow((paar.getGewicht()-mittelwert),2));
			anzahl += paar.getAnzahl();
		}
		tmp = tmp / anzahl;
		return tmp;
	}
	
	public static double standardabweichung(Collection<Paar> coll, double mittelwert){
		return Math.sqrt(varianz(coll, mittelwert));
	}
	
}
