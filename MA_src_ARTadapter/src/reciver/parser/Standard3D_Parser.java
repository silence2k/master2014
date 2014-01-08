package reciver.parser;

import java.util.ArrayList;
import java.util.List;

import data.Standard3D;
import data.Standard3DExtented;

public class Standard3D_Parser {
	
	
	public static Standard3D parse(String s){
		// 5 1.000][2137.712 2761.494 1013.095
		String tmp[] = s.split("\\]\\[");
		String t1 = tmp[0].trim();
		String tmp2[] = t1.split(" ");

		String t2 = tmp[1].trim();
		if(t2.endsWith("]")){
			t2 = t2.substring(0, t2.length()-1);
		}
		String tmp3[] = t2.split(" ");
		
		return new Standard3D(tmp2[0],Double.parseDouble(tmp3[0]),Double.parseDouble(tmp3[1]),Double.parseDouble(tmp3[2]));
	}
	
	public static Standard3DExtented parse(String s, int frameNr){
		return new Standard3DExtented(parse(s), frameNr);
	}
	
	
	
	public static List<Standard3DExtented> parseList(String s, int frameNr){
		List<Standard3DExtented> list = new ArrayList<>();
		// 3d 3 [2 1.000][2510.753 3256.690 911.964] [5 1.000][2137.712 2761.494 1013.095] [6 1.000][2464.065 3120.322 925.481]
		String tmp[] = s.split(" ");
		int anzahl = Integer.parseInt(tmp[1]);
		if(anzahl > 0){
			tmp = s.substring(s.indexOf("[")).split("\\]\\s\\[");
			for (String string : tmp) {
				list.add(parse(string,frameNr));
			}
		}
		return list;
	}

}
