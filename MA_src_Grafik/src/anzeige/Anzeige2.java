/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package anzeige;

import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/** Sample 3 - how to load an OBJ model, and OgreXML model, 
 * a material/texture, or text. */
public class Anzeige2 extends SimpleApplication {
	
	Node teapot;
	long lasttime = System.currentTimeMillis();
	boolean toggle = false;
	
	float rotation = 0;
	float max = 3f;
	float min = 0.1f;
	
	float dRotation = 0.02f;

    public static void main(String[] args) {
        Anzeige2 app = new Anzeige2();
        app.start();
    }

    @Override
    public void simpleInitApp() {

//        /** Load a teapot model (OBJ file from test-data) */
//        Spatial teapot = assetManager.loadModel("Models/Teapot/Teapot.obj");
//        Material mat_default = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
//        teapot.setMaterial(mat_default);
//        rootNode.attachChild(teapot);
    	
        /** Load a teapot model (OBJ file from test-data) */
        teapot = (Node) assetManager.loadModel("obj/hebel1/hebel1.obj");
        
        List<Spatial> childs = teapot.getChildren();
        for (Spatial spatial : childs) {
			System.out.println(spatial);
		}
        
        Geometry g = (Geometry)teapot.getChild("hebel1-geom-1");
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default.setColor("Color", new ColorRGBA(1f,0f,0f, 1f));
        g.setMaterial(mat_default);
        
        
        g = (Geometry)teapot.getChild("hebel1-geom-0");
        mat_default = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
      //  mat_default.setColor("rad1-geom-0", new ColorRGBA(1f,0f,1f, 1f));
        g.setMaterial(mat_default);
        
        
     //   Material mat_default = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
//        Material mat_default = assetManager.loadMaterial("obj/rad1/rad1.mtl");
//        mat_default.setColor("rad", new ColorRGBA(1f,0f,1f, 1f));
     //   teapot.setMaterial(mat_default);
       // Material mat = teapot.get
        rootNode.attachChild(teapot);
        
        
        teapot.rotate(min, 0, 0);
        rotation = min;
        
      //  teapot.get

        // /obj/rad1/rad1.obj

//        /** Display a line of text (default font from test-data) */
//        setDisplayStatView(false);
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        BitmapText helloText = new BitmapText(guiFont, false);
//        helloText.setSize(guiFont.getCharSet().getRenderedSize());
//        helloText.setText("Hello World");
//        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
//        guiNode.attachChild(helloText);


        /** You must add a light to make the model visible */
//        DirectionalLight sun = new DirectionalLight();
//        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
//        rootNode.addLight(sun);
        
        //add some light so that it is visible
//        PointLight light = new PointLight();
//        light.setColor(ColorRGBA.White);
//        light.setPosition(new Vector3f(5, 5, 5));
//        light.setRadius(20);
//        rootNode.addLight(light);
//        light = new PointLight();
//        light.setColor(ColorRGBA.White);
//        light.setPosition(new Vector3f(-5, -5, -5));
//        light.setRadius(20);
//        rootNode.addLight(light);
    }

	@Override
	public void simpleUpdate(float tpf) {
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		
        Geometry g = (Geometry)teapot.getChild("hebel1-geom-1");
//        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material mat_default = g.getMaterial();
        if(lasttime < System.currentTimeMillis()-1000){
        	lasttime = System.currentTimeMillis();
        	toggle(mat_default);
   
        	
        }
       // g.setMaterial(mat_default);
        rotate();
       
	}
    
    
	private void toggle(Material mat_default){
		if(toggle){
			mat_default.setColor("Color", new ColorRGBA(1f,0f,0f, 1f));
		}else{
			mat_default.setColor("Color", new ColorRGBA(0f,1f,0f, 1f));
		}
		toggle = !toggle;
	}
	
	private void rotate(){
		if(rotation + dRotation > max || rotation + dRotation < min){
			dRotation = -dRotation;
		}
		rotation += dRotation;
		 teapot.rotate(dRotation, 0, 0);
	}
}
