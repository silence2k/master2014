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

package physik.artefakte.alt;

import physik.PhysicsTestHelper;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.joints.SliderJoint;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Schieber2 extends SimpleApplication implements AnalogListener {
    private BulletAppState bulletAppState;
    private HingeJoint joint;
    
    boolean verbunden = false;
    float bremsen = 0.01f;
    
//    private  Node hammerNode;
//    private  Node griffNode;
    
    private Node hand;
    
    private float dx = 0.001f;
    
    private SixDofJoint slider;
    
    SliderJoint ghJoint;
    
    String lastBinding = "";

    public static void main(String[] args) {
        Schieber2 app = new Schieber2();
        app.start();
    }

    private void setupKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Swing", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addMapping("hoch", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("runter", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("links", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("rechts", new KeyTrigger(KeyInput.KEY_H));
        
        inputManager.addListener(this, "Left", "Right", "Swing","hoch","runter","links","rechts");
    }

    public void onAnalog(String binding, float value, float tpf) {
    //	hammerNode.getControl(RigidBodyControl.class).activate();
    	hand.getControl(RigidBodyControl.class).activate();
    	
//		if (!lastBinding.equals(binding)) {
//			lastBinding = binding;
//			if (binding.equals("Left")) {
//				joint.enableMotor(true, 1, .1f);
//			} else if (binding.equals("Right")) {
//				joint.enableMotor(true, -1, .1f);
//			} else if (binding.equals("Swing")) {
//				if (verbunden) {
//					if (getPhysicsSpace().getJointList().contains(slider)) {
//						getPhysicsSpace().remove(slider);
//					}
//					slider.destroy();
//					joint.enableMotor(false, 0, 0);
//					verbunden = false;
//				} else {
//					slider = new SixDofJoint(hand.getControl(RigidBodyControl.class),
//							hammerNode.getControl(RigidBodyControl.class), new Vector3f(), new Vector3f(0, 0, 0), true);
//
//					if (!getPhysicsSpace().getJointList().contains(slider)) {
//						getPhysicsSpace().add(slider);
//					}
//					joint.enableMotor(true, 0, .1f);
//					verbunden = true;
//				}

//			} else 
    		Vector3f v = null;
				if (binding.equals("hoch")) {
				v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
				v.y = v.y + dx;
				
			} else if (binding.equals("runter")) {
				v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
				v.y = v.y - dx;
				//hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
			} else if (binding.equals("links")) {
				v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
				v.x = v.x - dx;
				//hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
			} else if (binding.equals("rechts")) {
				v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
				v.x = v.x + dx;
				//hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
			}
			
				if (v != null) {
					v = checkPosition(v);
					hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
				}

//		}
    	
    }
    
    private Vector3f checkPosition(Vector3f v){
    	
    	float xMax = 2;
    	float yMax = 2;
    	float zMax = 0;
    	
    	Vector3f neu = new Vector3f();
    	neu.x = inRange(v.x, xMax, xMax);
    	neu.y = inRange(v.y, 0,2);
    	neu.z = inRange(v.z, zMax);
    	
    	return neu;
    }
    
    private float inRange(float wert, float grenze){
    	return inRange(wert, -grenze, grenze);
    }
    
    private float inRange(float wert, float grenzeMin, float grenzeMax){
    	float result;
    	if(wert > grenzeMax){
    		result = grenzeMax;
    	}else if(wert < grenzeMin){
    		result = grenzeMin;
    	}else{
    		result = wert;
    	}
    	return result;
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        setupKeys();
//        setupJoint();
        setupHand();
    }

    private PhysicsSpace getPhysicsSpace(){
        return bulletAppState.getPhysicsSpace();
    }
    
    public void setupHand() {
        hand=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .1f, .1f, .1f)),0);
        hand.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(2f,0,0f));
        rootNode.attachChild(hand);
        getPhysicsSpace().add(hand);
        
    }

//    public void setupJoint() {
//        Node holderNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .1f, .1f, .1f)),0);
//        holderNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,0,0f));
//        rootNode.attachChild(holderNode);
//        getPhysicsSpace().add(holderNode);
//        
//        
//
//        hammerNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .5f, .3f, .5f)),1);
//        hammerNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,0,-1f));
//        rootNode.attachChild(hammerNode);
//        getPhysicsSpace().add(hammerNode);
//       // hammerNode.getControl(RigidBodyControl.class).setSleepingThresholds(0.001f, 0.001f);
//
//        joint=new HingeJoint(holderNode.getControl(RigidBodyControl.class), hammerNode.getControl(RigidBodyControl.class), Vector3f.ZERO, new Vector3f(0f,-1,0f), Vector3f.UNIT_Z, Vector3f.UNIT_Y);
//        getPhysicsSpace().add(joint);
//        
//        
//        griffNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .1f, .1f, .1f)),0.01f);
//        griffNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(1f,-1f,1f));
//        rootNode.attachChild(griffNode);
//        getPhysicsSpace().add(griffNode);
//        
//        
////        //joint
//        ghJoint=new SliderJoint(hammerNode.getControl(RigidBodyControl.class), griffNode.getControl(RigidBodyControl.class), Vector3f.UNIT_Y.negate(), Vector3f.UNIT_Y, false);
//        ghJoint.setUpperLinLimit(.1f);
//        ghJoint.setLowerLinLimit(-.1f);
//
//        getPhysicsSpace().add(ghJoint);
//    }

    @Override
    public void simpleUpdate(float tpf) {
//        if(bremse){
//        	bremsen = -bremsen;
//        	System.out.println(bremsen);
//        	joint.enableMotor(true, bremsen, .1f);
//        }
    }


}