package spacecore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;


public class OBJLoader {
    public static Model loadModel(File objFile, File mtlFile) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(objFile));
        Model m = new Model();
        String line;
        
        Map<String,Mtl> mtlMap = loadMtl(mtlFile);
        
        Mtl mtl = new Mtl("grau", new Color(0.5f,0.5f,0.5f));
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);
                m.vertices.add(new Vector3f(x,y,z));
            } else if (line.startsWith("f ")) {
                try {
                    Vector3f vertexIndices = new Vector3f(
                            Float.valueOf(line.split(" ")[1].split("/")[0]), 
                            Float.valueOf(line.split(" ")[2].split("/")[0]),
                            Float.valueOf(line.split(" ")[3].split("/")[0]));
                    m.faces.add(new Face(vertexIndices,mtl));
                }
                catch(Exception e) { }
            }else if(line.startsWith("usemtl"))
			{
            	String [] s = line.split(" ");
            	if(mtlMap.size()>0){
            		mtl = mtlMap.get(s[1]);
            	}else{
        			mtl = new Mtl("grau", new Color(0.5f,0.5f,0.5f));
        		}
//        		if(s.length>1&&"weiss".equals(s[1])){
//        			mtl = new Mtl("weiss", new Color(1f,1f,1f));
//        		}else if(s.length>1&&"schwarz".equals(s[1])){
//        			mtl = new Mtl("schwarz", new Color(0f,0f,0f));
//        		}else{
//        			mtl = new Mtl("grau", new Color(0.5f,0.5f,0.5f));
//        		}
			}
        }
        reader.close();
        return m;
    }
    
    private static Map<String,Mtl> loadMtl(File mtlFile){
    	 Map<String,Mtl> mtlMap = new HashMap<>();
    	 BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(mtlFile));
		
    	 String line;
    	 String name = "";
    	 Color c;
    	 String[] s;
    	 while ((line = reader.readLine()) != null) {
    		 if(line.startsWith("newmtl")){
    			 s = line.split(" ");
    			 name = s[1];
    		 }else if(line.startsWith("Kd")){
    			 s = line.split(" ");
    			 mtlMap.put(name,new Mtl(name, new Color(Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]))));
    		 }
    	 }
    	 reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return mtlMap;
    }

    public static Model loadModel(String FileString)
    {
        Model model = new Model();
        try {
            model = OBJLoader.loadModel(new File(FileString+".obj"),new File(FileString+".mtl"));
        } catch (FileNotFoundException e) {
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        }
        return model;
    }
}