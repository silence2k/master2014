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
		map.put("18 - 20", 70);
		map.put("21 - 24", 70);
		map.put("25 - 29", 70);
		map.put("30 - 35", 70);
		map.put("36 - 45", 70);
		map.put("45 +", 80);
		map.put("Männlich", 56);
		map.put("Weiblich", 58);
		map.put("", 20);
		map.put("Ja", 90);
		map.put("Nein", 80);
		map.put("1 – 2", 80);
		map.put("3 – 4", 80);
		map.put("5 – 6", 80);
		map.put("7 – 9", 80);
		map.put("10 – 12", 68);
		map.put("12 +", 80);
		map.put("Keine Vorkenntisse", 0);
		map.put("Bis zu einem Jahr", 0);
		map.put("1 – 5", 80);
		map.put("5 +", 90);
		map.put("nie", 90);
		map.put("0 – 5", 80);
		map.put("5 – 15", 75);
		map.put("15 – 25", 65);
		map.put("25 – 40", 65);
		map.put("40 +", 80);
		map.put("keine", 75);
		map.put("wenig", 70);
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
		map.put("akustisches", 40);
		map.put("haptisches", 45);
		map.put("beides", 65);
		map.put("keins", 75);
		map.put("sehr schwer", 35);
		map.put("schwer", 65);
		map.put("M. Sc.",75);
		map.put("B. Sc.", 75);
		map.put("Informatik",48);
		map.put("Ang. Informatik",20);
		map.put("Tech Informatik",20);
		map.put("B. Eng", 70);
		map.put("Bio + Chemie",50);
		map.put("Maschinenbau",50);
		map.put("Pensionär",50);
		map.put("Einzelh. Kauffrau",50);
		map.put("Rentner",50);
		map.put("IT Consultant",50);
		map.put("ein wenig",50);
		
		
		
		


		
		
		abstandMap = map;
	}

}
