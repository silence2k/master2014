
package anzeige;

import schalttafel.artefakte.Rad1;
import aktor.Aktor;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


public class Anzeige1 extends SimpleApplication implements AnalogListener {
	
	Rad1 rad = new Rad1();
	
	Aktor handRechts = new Aktor();
	
	
	
	
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


        rootNode.attachChild(rad.init(assetManager));
        rootNode.attachChild(handRechts.init(assetManager));



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
		
		rad.update();
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
			handRechts.toggleGreifen(rad);
			break;

		default:
			break;
		}


    	
    }
}
