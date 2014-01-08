package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandART {

	private Map<String,Standard3DExtented> fingerMap = new HashMap<>();
	private Standard3D mittelPunkt = new Standard3D("", 0, 0, 0);
	private boolean geschlossen;

	private boolean erfasst;
	
	

	public HandART(double x, double y, double z) {
		super();
		mittelPunkt = new Standard3D("", x, y, z);
	}

	public List<Standard3D> getFingerListe() {
		return new ArrayList<Standard3D>(fingerMap.values());
	}

	public Standard3D getMittelPunkt() {
		return mittelPunkt;
	}

	public boolean isGeschlossen() {
		return geschlossen;
	}

	public boolean isErfasst() {
		return erfasst;
	}

	public double ausdehnung() {
		double tmp = 0;
		for (Standard3D s3d : fingerMap.values()) {
			tmp = Math.max(tmp, s3d.abstand(mittelPunkt));
		}
		return tmp;
	}
	
	
	public void update(List<Standard3D> list){
		Map<String, Standard3D> daten = listToMap(list);
		Map<String, Standard3D> neuePunkte = new HashMap<>();
		
		for (Standard3D s3d : list) {
			if(enthaeltID(fingerMap, s3d.getId())){
				daten.remove(s3d.getId());
				neuePunkte.put(s3d.getId(), s3d);
			}
		}
		
		
	}
	
	private Map<String, Standard3D> listToMap(List<Standard3D> list){
		Map<String, Standard3D> map = new HashMap<>();
		for (Standard3D s3d : list) {
			map.put(s3d.getId(), s3d);
		}
		return map;
	}
	
	private List<Standard3D> mapToList(Map<String, Standard3D> map){
		return new ArrayList<>(map.values());
	}
	
	private boolean enthaeltID(Map<String,Standard3DExtented> map, String id){
		for (Standard3D s3d : map.values()) {
			if(id.equalsIgnoreCase(s3d.getId())){
				return true;
			}
		}
		return false;
	}

}
