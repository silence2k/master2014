package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HandART2 {
	
	public static final double scale = 0.001;

	public static final double maxAbstand = 120;

	private static final double maxGreifen = 25;
	private static final double minNichtGreifen = 80;
	private static final int minAnzahlFingerGreifen = 8;
	private static final int switchCounterMax = 5;
	
	private int switchCounter = 0;

	private Ringpuffer<Standard3DExtented> finger = new Ringpuffer<Standard3DExtented>(5);
	private Set<String> fingerID;
	private double ausdehnung;

	private Standard3D mittelPunkt = new Standard3D("", 0, 0, 0);
	private Standard3D entferntesterPunkt = new Standard3D("", 0, 0, 0);
	private Standard3D defaultPosition;

	// in Frames
	private long jidderTimer = 0;
	private long jidderIgnore = 50;

	private boolean grab;

	private boolean erfasst;

	public HandART2(double x, double y, double z) {
		super();
		mittelPunkt = new Standard3D("", x, y, z);
		defaultPosition = new Standard3D("", x, y, z);
	}

	public List<Standard3D> getFingerListe() {
		return new ArrayList<Standard3D>(finger.getAll());
	}

	public Standard3D getMittelPunkt() {
		return mittelPunkt;
	}

	public boolean isGrab() {
		return grab;
	}

	public boolean isErfasst() {
		return erfasst;
	}

	public double getAusdehnung() {
		return ausdehnung;
	}

	public void update(List<Standard3DExtented> list) {
		List<Standard3DExtented> cleanList = new ArrayList<>();

		if (!list.isEmpty()) {
			if (finger.isEmpty()) {
				Standard3DExtented s3d = getStartPunkt(list);
				cleanList.add(s3d);
				list.remove(s3d);
				internUpdate();
			}

			for (Standard3DExtented s3d : list) {
				if (enthaeltID(s3d.getId())) {
					cleanList.add(s3d);
				} else if (mittelPunkt.abstand(s3d) < maxAbstand) {
					cleanList.add(s3d);
				}

			}
		}
		finger.add(cleanList);
		internUpdate();

		if (finger.size() > minAnzahlFingerGreifen) {
			if (grab && ausdehnung > minNichtGreifen) {
				swich();
			} else if (!grab && ausdehnung < maxGreifen) {
				swich();
			}else{
				switchCounter = 0;
			}
		}else{
			switchCounter = 0;
		}
	}
	
	private void swich(){
		switchCounter++;
		if(switchCounter > switchCounterMax){
			grab = !grab;
			switchCounter = 0;
		}
	}

	private Standard3DExtented getStartPunkt(List<Standard3DExtented> list) {
		double abstand = Double.MAX_VALUE;
		Standard3DExtented result = null;
		for (Standard3DExtented s3d : list) {
			double tmp = 0;
			for (Standard3DExtented s3d2 : list) {
				tmp += s3d.abstand(s3d2);
			}
			if (tmp < abstand) {
				abstand = tmp;
				result = s3d;
			}
		}
		return result;
	}

	public boolean enthaeltID(String id) {
		return fingerID.contains(id);
	}

	private void internUpdate() {
		Set<String> set = new HashSet<>();
		double tmp = 0;
		double tmpAbstand = 0;
		for (Standard3D s3d : finger.getAll()) {
			tmpAbstand = s3d.abstand(mittelPunkt);
			if (tmpAbstand < maxAbstand) {
				if (tmpAbstand > tmp) {
					tmp = tmpAbstand;
					entferntesterPunkt = s3d;
				}
				// tmp = Math.max(tmp, tmpAbstand);

				set.add(s3d.getId());
			}
		}
		ausdehnung = tmp;
		fingerID = set;
		berechneSchwerpunkt();
	}

	public void berechneSchwerpunkt() {

		if (finger.getAll().isEmpty()) {
			entferntesterPunkt = null;
			if (jidderTimer == 0) {
				jidderTimer = 1;
			} else {
				jidderTimer++;
				if (jidderTimer > jidderIgnore) {
					mittelPunkt.setX(defaultPosition.getX());
					mittelPunkt.setY(defaultPosition.getY());
					mittelPunkt.setZ(defaultPosition.getZ());
				}

			}

		} else {
			jidderTimer = 0;
			double x = 0;
			double y = 0;
			double z = 0;

			int size = 0;

			for (Standard3D s3d : finger.getAll()) {
				x += s3d.getX();
				y += s3d.getY();
				z += s3d.getZ();
				size++;
			}
			if (size > 0) {
				mittelPunkt.setX(x / size);
				mittelPunkt.setY(y / size);
				mittelPunkt.setZ(z / size);
			}
		}
	}

	private Map<String, Standard3D> listToMap(List<Standard3D> list) {
		Map<String, Standard3D> map = new HashMap<>();
		for (Standard3D s3d : list) {
			map.put(s3d.getId(), s3d);
		}
		return map;
	}

	private List<Standard3D> mapToList(Map<String, Standard3D> map) {
		return new ArrayList<>(map.values());
	}

	private boolean enthaeltID(Map<String, Standard3DExtented> map, String id) {
		for (Standard3D s3d : map.values()) {
			if (id.equalsIgnoreCase(s3d.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Hand[x=" + mittelPunkt.getX()*scale + ";y=" + mittelPunkt.getY()*scale + ";z=" + mittelPunkt.getZ()*scale + ";grab="
				+ grab + "]";
	}

	public Standard3D getEntferntesterPunkt() {
		return entferntesterPunkt;
	}

}
