package data;

import java.util.ArrayList;
import java.util.List;

public class Berechne {

	private double grenze_xPlus = 2000;
	private double grenze_xMinus = -2000;
	private double grenze_yPlus = 2000;
	private double grenze_yMinus = -2000;
	private double grenze_zPlus = 2000;
	private double grenze_zMinus = -2000;

	private DataSource dataSource;

	private Hand rechteHand;
	private Hand linkeHand;

	List<Standard3D> listAll;
	List<Standard3D> listClean;

	public Berechne(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Standard3D> getListAll() {
		return listAll;
	}

	public List<Standard3D> getListClean() {
		return listClean;
	}

	public Hand getRechteHand() {
		return rechteHand;
	}

	public Hand getLinkeHand() {
		return linkeHand;
	}

	public void update() {
		List<Standard3D> list = new ArrayList<>();
		List<Standard3D> source = dataSource.getStandard3dList();

		for (Standard3D s3d : source) {
			if (match(s3d)) {
				list.add(s3d);
			}
		}
		listAll = source;
		listClean = list;
	}

	private boolean match(Standard3D s3d) {
		if (s3d.getX() > grenze_xPlus || s3d.getX() < grenze_xMinus || s3d.getY() > grenze_yPlus
				|| s3d.getY() < grenze_yMinus || s3d.getZ() > grenze_zPlus || s3d.getZ() < grenze_zMinus) {
			return false;
		}
		return true;
	}
	
	private void updateHand(){
		
	}

}
