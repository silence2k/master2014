package util;

import java.util.HashMap;
import java.util.Map;

public class Abstand {
	private static Map<String, Integer> abstandMap;
	public static Map<String, Integer> getAbstandsMap(){
		if(abstandMap == null){
			init();
		}
		return abstandMap;
	}
	
	private static void init(){
		Map<String, Integer> map = new HashMap<>();
		map.put("18 - 20", 20);
		map.put("21 - 24", 20);
		map.put("25 - 29", 20);
		map.put("30 - 35", 20);
		map.put("36 - 45", 20);
		map.put("45 +", 20);
		map.put("Männlich", 20);
		map.put("Weiblich", 20);
		map.put("", 20);
		map.put("Ja", 20);
		map.put("Nein", 20);
		map.put("1 – 2", 20);
		map.put("3 – 4", 20);
		map.put("5 – 6", 20);
		map.put("7 – 9", 20);
		map.put("10 – 12", 20);
		map.put("12 +", 20);
		map.put("Keine Vorkenntisse", 0);
		map.put("Bis zu einem Jahr", 20);
		map.put("1 – 5", 20);
		map.put("5 +", 20);
		map.put("nie", 20);
		map.put("0 - 5", 20);
		map.put("5 – 15", 20);
		map.put("15 - 25", 20);
		map.put("25 – 40", 20);
		map.put("40 +", 20);
		map.put("keine", 20);
		map.put("wenig", 20);
		map.put("mittelmäßig", 20);
		map.put("gute Kenntnisse", 20);
		map.put("selten", 20);
		map.put("gelegentlich", 20);
		map.put("häufig", 20);

		map.put("gar nicht", 60);
		map.put("schwer", 65);
		map.put("mittelmäßig", 40);
		map.put("gut", 90);
		map.put("sehr gut", 60);
		map.put("keiner", 80);
		map.put("ART-Tracker", 40);
		map.put("Kinect", 80);
		map.put("Beide gleich", 40);
		map.put("nein", 20);
		map.put("unbenutzbar", 20);
		map.put("ein wenig", 20);
		map.put("viel", 20);
		map.put("sehr schlecht", 35);
		map.put("schlecht", 60);
		map.put("akustisches", 20);
		map.put("haptisches", 20);
		map.put("beides", 20);
		map.put("keins", 20);
	

		
		abstandMap = map;
	}

}
