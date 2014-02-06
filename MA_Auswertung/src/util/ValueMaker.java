package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValueMaker {
	
	public static int[] makeValues(int[] daten, int[] gewichte){
		int[] values = new int[gewichte.length];
		Map<Integer,Integer> gMap = buildMap(gewichte);
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
		
		int[] daten = {1,1,2,2,3,3,3,4,4,4,4,5,5,5,5,5};
		int[] gewichte = {1,2,3,4,5};
		
		int[] values = makeValues(daten, gewichte);
		System.out.println(Arrays.toString(values));
	}

}
