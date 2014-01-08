package filereader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reciver.parser.Parser;
import reciver.parser.Standard3D_Parser;
import data.DataSource;
import data.Standard3D;
import data.Standard3DExtented;

public class Reader implements DataSource{
	
	protected List<List<Standard3DExtented>> list;
	
	private int index = 0;
	private long lastTime;
	private int bufferSize = 5;
	
	protected long warteZeit = 50; // 50ms
	
	
	
	public Reader() {
		super();
		list = new ArrayList<>();
		lastTime = System.currentTimeMillis();
	}
	
	
	@Override
	public List<Standard3DExtented> getStandard3dList() {
		List<Standard3DExtented> result = new ArrayList<>();
		index = (index+1)%list.size();
		int tmp = 0;
		for(int i = 0; i < bufferSize; i++){
			tmp = index-i;
			if(tmp < 0){
				tmp = list.size()+tmp;
			}
			result.addAll(list.get(tmp));
		}
		return result;
	}
	
	public static Reader instance(File dataFile) throws FileNotFoundException, IOException {
		Reader r = new Reader();
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;
        List<Standard3DExtented> tmpList = null;
        int frameNr = 0;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("fr ")) {
                // neu beginnen und alle liste speichern, falls vorhanden
            	frameNr = Parser.parseFrame(line);
            	if(tmpList!=null){
            		r.list.add(tmpList);
            		tmpList = new ArrayList<>();
            	}}
            else if(line.startsWith("3d ")){
            	tmpList = Standard3D_Parser.parseList(line,frameNr);
            }
			
        }
        reader.close();
        System.out.println("Anzahl Listen: "+r.list.size());
        return r;
    }










}
