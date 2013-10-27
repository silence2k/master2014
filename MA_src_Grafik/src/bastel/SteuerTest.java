

package bastel;

import java.io.ObjectInputStream.GetField;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class SteuerTest extends SimpleApplication {
	
	Geometry cube;
	Long lastTime = 0l;

  public static void main(String[] args) {
    SteuerTest app = new SteuerTest();
    app.start();
    
  }

  @Override
  public void simpleInitApp() {
	  
	getFlyByCamera().setMoveSpeed(10);

    /** A simple textured cube -- in good MIP map quality. */
    Box boxshape1 = new Box(new Vector3f(-3f,1.1f,0f), 1f,1f,1f);
    cube = new Geometry("My Textured Box", boxshape1);
    Material mat_stl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    Texture tex_ml = assetManager.loadTexture("Interface/Logo/Monkey.jpg");
    mat_stl.setTexture("ColorMap", tex_ml);
    cube.setMaterial(mat_stl);
    rootNode.attachChild(cube);

    
    
    /** Must add a light to make the lit object visible! */
    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
    sun.setColor(ColorRGBA.White);
    rootNode.addLight(sun);

  }
  
  @Override
  public void simpleUpdate(final float tpf) {
	  float speed = 0.5f;
	  if(lastTime == 0l){
		  lastTime = System.currentTimeMillis();
	  }
	  float mull  = (float)( (System.currentTimeMillis() - lastTime)/1000.0);
	  if(cube!=null){
		  cube.move(0, speed*mull, 0);
	  }
	  
	  
	  lastTime = System.currentTimeMillis();
  }
  
  
}
