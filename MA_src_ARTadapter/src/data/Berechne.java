package data;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import amq.AMQ_Sender;

public class Berechne {

	private double grenze_xPlus = 2000;
	private double grenze_xMinus = -2000;
	private double grenze_yPlus = 2000;
	private double grenze_yMinus = -2000;
	private double grenze_zPlus = 2000;
	private double grenze_zMinus = -2000;

	private DataSource dataSource;

	private HandART2 rechteHand = new HandART2(1000, 0, 0);
	private HandART2 linkeHand = new HandART2(-1000, 0, 0);

	AMQ_Sender sender;

	List<Standard3DExtented> listAll;
	List<Standard3DExtented> listClean;

	Ringpuffer<Standard3DExtented> puffer = new Ringpuffer<>(5);

	public Berechne(DataSource dataSource) {
		super();
		this.dataSource = dataSource;

		try {
			sender = new AMQ_Sender(rechteHand, linkeHand);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Standard3DExtented> getListAll() {
		return listAll;
	}

	public List<Standard3DExtented> getListClean() {
		return listClean;
	}

	public List<Standard3DExtented> getListPuffer() {
		return puffer.getAll();
	}

	public HandART2 getRechteHand() {
		return rechteHand;
	}

	public HandART2 getLinkeHand() {
		return linkeHand;
	}

	public void update() {
		List<Standard3DExtented> list = new ArrayList<>();
		List<Standard3DExtented> source = dataSource.getStandard3dList();

		for (Standard3DExtented s3d : source) {
			if (match(s3d)) {
				list.add(s3d);
			}
		}
		listAll = source;
		listClean = list;
		puffer.add(listClean);
		updateHand(listClean);
	}

	private boolean match(Standard3DExtented s3d) {
		if (s3d.getX() > grenze_xPlus || s3d.getX() < grenze_xMinus || s3d.getY() > grenze_yPlus
				|| s3d.getY() < grenze_yMinus || s3d.getZ() > grenze_zPlus || s3d.getZ() < grenze_zMinus) {
			return false;
		}
		return true;
	}

	private void updateHand(List<Standard3DExtented> list) {
		List<Standard3DExtented> rechts = new ArrayList<Standard3DExtented>();
		List<Standard3DExtented> links = new ArrayList<Standard3DExtented>();

		for (Standard3DExtented s3d : list) {
			dichter(s3d, rechts, links).add(s3d);
		}

		rechteHand.update(rechts);
		linkeHand.update(links);
	}

	private List<Standard3DExtented> dichter(Standard3DExtented s3d, List<Standard3DExtented> rechts,
			List<Standard3DExtented> links) {
		double abstandRechts = rechteHand.getMittelPunkt().abstand(s3d);
		double abstandLinks = linkeHand.getMittelPunkt().abstand(s3d);
		if (abstandLinks > abstandRechts) {
			return rechts;
		}
		return links;
	}

}
