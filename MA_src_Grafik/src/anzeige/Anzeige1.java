
package anzeige;

import java.util.ArrayList;
import java.util.List;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Rad1;
import schalttafel.artefakte.Schieber1;
import aktor.Aktor;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;


public class Anzeige1 extends SimpleApplication implements AnalogListener {
	
	Rad1 rad1 = new Rad1();
	Rad1 rad2 = new Rad1();
	Rad1 rad3 = new Rad1();
	Rad1 rad4 = new Rad1();
	Hebel1 hebel1 = new Hebel1();
	
	Schieber1 schieber1 = new Schieber1();
	//Rad2 rad2 = new Rad2();
	
	Aktor handRechts = new Aktor();
	
	List<Artefakt> artefakte = new ArrayList<>();
	
	
	
	
	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;
	
	boolean toggle = false;

    public static void main(String[] args) {
        Anzeige1 app = new Anzeige1();
        app.start();
    }

    @Override
    public void simpleInitApp() {
    	
    	cam.setLocation(new Vector3f(0f, 0f, 5f));
    	
    	setupKeys();


        rootNode.attachChild(rad1.init(assetManager, new Vector3f(0.8f,0.5f,0)));
        rootNode.attachChild(rad2.init(assetManager, new Vector3f(-0.8f,0.5f,0)));
        rootNode.attachChild(rad3.init(assetManager, new Vector3f(0.8f,-0.5f,0)));
      //  rootNode.attachChild(rad4.init(assetManager, new Vector3f(-0.8f,-0.5f,0)));
        
        rootNode.attachChild(schieber1.init(assetManager, new Vector3f(-0.8f,-0.5f,0)));
        
        rootNode.attachChild(hebel1.init(assetManager, new Vector3f(0,0,0)));
    //	rootNode.attachChild(rad2.init(assetManager));
        rootNode.attachChild(handRechts.init(assetManager));

        
        artefakte.add(rad1);
        artefakte.add(rad2);
        artefakte.add(rad3);
        //artefakte.add(rad4);
        artefakte.add(hebel1);
        artefakte.add(schieber1);


        /** You must add a light to make the model visible */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
        rootNode.addLight(sun);
    }

	@Override
	public void simpleUpdate(float tpf) {
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		refreshTime();
		
		
//        Geometry g = (Geometry)teapot.getChild("rad1-geom-0");
//        Material mat_default = g.getMaterial();
//        if(lasttime < System.currentTimeMillis()-1000){
//        	lasttime = System.currentTimeMillis();
//        	toggle(mat_default);
//   
//        	
//        }
//        teapot.rotate(0, 0, -0.002f);
		
		//rad.rotate(deltaTime);
		
		//rad1.update();
		
		for(Artefakt arte: artefakte){
			arte.update();
		}
	}
	
	private void refreshTime(){
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}
    
    
	private void toggle(Material mat_default){
		if(toggle){
			toggle = false;
			mat_default.setColor("Color", new ColorRGBA(1f,0f,0f, 1f));
		}else{
			toggle = true;
			mat_default.setColor("Color", new ColorRGBA(1f,0f,1f, 1f));
		}
	}
	
	private void setupKeys() {
//        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
//        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addMapping("Swing", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addMapping("hoch", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("runter", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("links", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("rechts", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("greifen", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(this, "hoch","runter","links","rechts","greifen");
    }

    public void onAnalog(String binding, float value, float tpf) {
    	
    	switch (binding) {
		case "hoch":
			handRechts.hoch(deltaTime);
			break;
		case "runter":
			handRechts.runter(deltaTime);
			break;
		case "links":
			handRechts.links(deltaTime);
			break;
		case "rechts":
			handRechts.rechts(deltaTime);
			break;
		case "greifen":
			handRechts.toggleGreifen(dichtesterGriff(handRechts));
			break;
		default:
			break;
		}
    }


    
    
    private Artefakt dichtesterGriff(Aktor aktor){
    	Artefakt result = null;
    	float distance = Float.MAX_VALUE;
    	float tmp;
    	for(Artefakt arte: artefakte){
    		tmp = arte.distanceFreierGriff(aktor);
    		if(tmp < distance){
    			result = arte;
    			distance = tmp;
    		}
    	}
    	
    	return result;
    }
}
