package filereader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reciver.parser.Standard3D_Parser;
import data.DataSource;
import data.Standard3D;

public class Reader implements DataSource{
	
	protected List<List<Standard3D>> list;
	
	private int index = 0;
	private long lastTime;
	
	protected long warteZeit = 50; // 50ms
	
	
	
	public Reader() {
		super();
		list = new ArrayList<>();
		lastTime = System.currentTimeMillis();
	}
	
	
	@Override
	public List<Standard3D> getStandard3dList() {
//		long tmp = System.currentTimeMillis();
//		if((lastTime+warteZeit) < tmp){
//			lastTime = tmp;
			index = (index+1)%list.size();
//		}
		return list.get(index);
	}
	
	public static Reader instance(File dataFile) throws FileNotFoundException, IOException {
		Reader r = new Reader();
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;
        List<Standard3D> tmpList = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("fr ")) {
                // neu beginnen und alle liste speichern, falls vorhanden
            	if(tmpList!=null){
            		r.list.add(tmpList);
            		tmpList = new ArrayList<>();
            	}}
            else if(line.startsWith("3d ")){
            	tmpList = Standard3D_Parser.parseList(line);
            }
			
        }
        reader.close();
        System.out.println("Anzahl Listen: "+r.list.size());
        return r;
    }










}
