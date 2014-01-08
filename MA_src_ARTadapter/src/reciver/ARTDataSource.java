package reciver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reciver.parser.DataParser;
import reciver.parser.Parser;
import reciver.parser.Standard3D_Parser;

import data.DataSource;
import data.Standard3DExtented;
import filereader.Reader;

public class ARTDataSource implements DataSource, DataParser {

	protected List<List<Standard3DExtented>> list;

	private int index = 0;
	private long lastTime;
	private int bufferSize = 5;

	protected long warteZeit = 50; // 50ms

	private List<Standard3DExtented> standard3dList = new ArrayList<>();

	public ARTDataSource() {
		super();
		list = new ArrayList<>();
		lastTime = System.currentTimeMillis();
	}

	@Override
	public List<Standard3DExtented> getStandard3dList() {
		return standard3dList;
	}

	// public static Reader instance(File dataFile) throws
	// FileNotFoundException, IOException {
	// Reader r = new Reader();
	// BufferedReader reader = new BufferedReader(new FileReader(dataFile));
	// String line;
	// List<Standard3DExtented> tmpList = null;
	// int frameNr = 0;
	// while ((line = reader.readLine()) != null) {
	// if (line.startsWith("fr ")) {
	// // neu beginnen und alle liste speichern, falls vorhanden
	// frameNr = Parser.parseFrame(line);
	// if(tmpList!=null){
	// r.list.add(tmpList);
	// tmpList = new ArrayList<>();
	// }}
	// else if(line.startsWith("3d ")){
	// tmpList = Standard3D_Parser.parseList(line,frameNr);
	// }
	//
	// }
	// reader.close();
	// System.out.println("Anzahl Listen: "+r.list.size());
	// return r;
	// }

	@Override
	public void parse(String _data) {
		String[] data = _data.split("\n");
		int frameNr = 0;
		List<Standard3DExtented> tmpList = new ArrayList<>();
		for (String line : data) {
			if (line.startsWith("fr ")) {
				frameNr = Parser.parseFrame(line);
			} else if (line.startsWith("3d ")) {
				tmpList = Standard3D_Parser.parseList(line, frameNr);
			}
		}
		standard3dList = tmpList;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
