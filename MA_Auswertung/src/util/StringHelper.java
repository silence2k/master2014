package util;

public class StringHelper {
	
	public static String doubleToString(double wert, int nachkommaStellen){
		if(wert == 0.0){
			return "0";
		}
		StringBuilder sb = new StringBuilder();
		
		double d = wert;
		for(int i = 0;i < nachkommaStellen; i++){
			d = d * 10;
		}
		
		String tmp = Math.round(d)+"";
		
		sb.append(tmp.substring(0,tmp.length()-nachkommaStellen));
		if(wert < 1.0){
			sb.append("0");
		}
		sb.append(",");
		sb.append(tmp.substring(tmp.length()-nachkommaStellen));
		
		
		return sb.toString();
	}
	
	
	public static void main(String arg[]){
		System.out.println(doubleToString(1.2345, 2));
	}

}
