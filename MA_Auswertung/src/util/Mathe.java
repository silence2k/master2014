package util;

import java.util.Collection;

public class Mathe {
	
	public static double mittelWert(Collection<Paar> coll){
		double result = 0;
		int sum = 0;
		int prod = 0;
		for (Paar paar : coll) {
			sum = sum + paar.getAnzahl();
			prod = prod + (paar.getAnzahl() * paar.getGewicht());
		}
		
		result = sum / (double)prod;
		
		return result;
	}

	
}
