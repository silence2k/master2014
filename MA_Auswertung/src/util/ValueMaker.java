package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValueMaker {
	
	public static int[] werteZaehlen(int[] daten, int[] werte){
		int[] values = new int[werte.length];
		Map<Integer,Integer> gMap = buildMap(werte);
		Integer index = null;
		for(int d : daten){
			index = gMap.get(d);
			if(index!=null)
			values[index]++;
		}
		
		return values;
	}
	
	private static Map<Integer,Integer> buildMap(int[] gewichte){
		Map<Integer,Integer> gewichtMap = new HashMap<>();
		for(int i = 0; i < gewichte.length; i++){
			gewichtMap.put(gewichte[i], i);
		}
		
		return gewichtMap;
	}
	
	
	
	public static void main(String args[]){
		
		int[] daten = {2,2,2,2,3,3,3,4,4,4,4,5,5,5,5,5};
		int[] gewichte = {1,2,3,4,5};
		
		int[] values = werteZaehlen(daten, gewichte);
		System.out.println(Arrays.toString(values));
	}

}
