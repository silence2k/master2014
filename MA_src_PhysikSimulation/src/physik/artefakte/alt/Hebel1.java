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

import com.bulletphysics.dynamics.vehicle.WheelInfo;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.CylinderCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.joints.SliderJoint;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.bullet.util.Converter;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;

public class Hebel1 extends SimpleApplication implements AnalogListener {
    private BulletAppState bulletAppState;
    private HingeJoint joint;
    
    boolean verbunden = false;
    float bremsen = 0.01f;
    
    private Node holderNode;
    private  Node hammerNode;
    private Node hand;
    
    
    private float dx = 0.001f;
    
    private SixDofJoint slider;
    
    String lastBinding = "";

    public static void main(String[] args) {
        Hebel1 app = new Hebel1();
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
    	System.out.println(binding);
    	hammerNode.getControl(RigidBodyControl.class).activate();
    	hand.getControl(RigidBodyControl.class).activate();
    	
    	if(!lastBinding.equals(binding)){
    		lastBinding = binding;
        if(binding.equals("Left")){
            joint.enableMotor(true, 1, .1f);
        }
        else if(binding.equals("Right")){
            joint.enableMotor(true, -1, .1f);
        }
        else if(binding.equals("Swing")){
        	if(verbunden){
        		if(getPhysicsSpace().getJointList().contains(slider)){
        			getPhysicsSpace().remove(slider);
        		}
                slider.destroy();
                joint.enableMotor(false, 0, 0);
                verbunden = false;
        	}else{
                slider=new SixDofJoint(hand.getControl(RigidBodyControl.class), hammerNode.getControl(RigidBodyControl.class), new Vector3f(), new Vector3f(0, 0, 0), true);
            
                if(!getPhysicsSpace().getJointList().contains(slider)){
                getPhysicsSpace().add(slider);
                }
        		joint.enableMotor(true, 0, .1f);
                verbunden = true;
        	}

        }
    	}
        else if(binding.equals("hoch")){
        	 Vector3f v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
        	 v.y = v.y+dx;
        	 hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
        }
        else if(binding.equals("runter")){
        	Vector3f v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
       	 v.y = v.y-dx;
       	 hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
        }
        else if(binding.equals("links")){
        	Vector3f v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
       	 v.x = v.x-dx;
       	 hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
        }
        else if(binding.equals("rechts")){
        	Vector3f v = hand.getControl(RigidBodyControl.class).getPhysicsLocation(null);
       	 v.x = v.x+dx;
       	 hand.getControl(RigidBodyControl.class).setPhysicsLocation(v);
        }
    	
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        setupKeys();
        setupJoint();
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

    public void setupJoint() {
        holderNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .1f, .1f, .1f)),0);
        holderNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,0,0f));
        rootNode.attachChild(holderNode);
        getPhysicsSpace().add(holderNode);
        
        

        hammerNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .3f, .3f, .3f)),1);
        hammerNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,-1,0f));
        rootNode.attachChild(hammerNode);
        getPhysicsSpace().add(hammerNode);
       // hammerNode.getControl(RigidBodyControl.class).setSleepingThresholds(0.001f, 0.001f);

        joint=new HingeJoint(holderNode.getControl(RigidBodyControl.class), hammerNode.getControl(RigidBodyControl.class), Vector3f.ZERO, new Vector3f(0f,-1,0f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
        joint.setLimit(0, 1);
        getPhysicsSpace().add(joint);
        
        
        
        VehicleWheel rad;
    }
    
    

    
    
    public void setupAufbau() {
        holderNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .1f, .1f, .1f)),0);
        holderNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,0,0f));
        rootNode.attachChild(holderNode);
        getPhysicsSpace().add(holderNode);
        
        

        hammerNode=PhysicsTestHelper.createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .3f, .3f, .3f)),1);
        hammerNode.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,-1,0f));
        rootNode.attachChild(hammerNode);
        getPhysicsSpace().add(hammerNode);

        joint=new HingeJoint(holderNode.getControl(RigidBodyControl.class), hammerNode.getControl(RigidBodyControl.class), Vector3f.ZERO, new Vector3f(0f,-1,0f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
        getPhysicsSpace().add(joint);
    }

    @Override
    public void simpleUpdate(float tpf) {
//        if(bremse){
//        	bremsen = -bremsen;
//        	System.out.println(bremsen);
//        	joint.enableMotor(true, bremsen, .1f);
//        }
    }
    
    
    private void buildRad(){
        // Add a physics cylinder to the world
        Node physicsCylinder = PhysicsTestHelper.createPhysicsTestNode(assetManager, new CylinderCollisionShape(new Vector3f(1f, 1f, 1.5f)), 1);
        physicsCylinder.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(2, 2, 0));
        rootNode.attachChild(physicsCylinder);
        getPhysicsSpace().add(physicsCylinder);
    }
    
    
//    private void buildPlayer() {
//        float stiffness = 120.0f;//200=f1 car
//        float compValue = 0.2f; //(lower than damp!)
//        float dampValue = 0.3f;
//        final float mass = 400;
//
//        //Load model and get chassis Geometry
//        carNode = (Node)assetManager.loadModel("Models/Ferrari/Car.scene");
//        carNode.setShadowMode(ShadowMode.Cast);
//        Geometry chasis = findGeom(carNode, "Car");
//        BoundingBox box = (BoundingBox) chasis.getModelBound();
//
//        //Create a hull collision shape for the chassis
//        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);
//
//        //Create a vehicle control
//        player = new VehicleControl(carHull, mass);
//        carNode.addControl(player);
//
//        //Setting default values for wheels
//        player.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
//        player.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
//        player.setSuspensionStiffness(stiffness);
//        player.setMaxSuspensionForce(10000);
//
//        //Create four wheels and add them at their locations
//        //note that our fancy car actually goes backwards..
//        Vector3f wheelDirection = new Vector3f(0, -1, 0);
//        Vector3f wheelAxle = new Vector3f(-1, 0, 0);
//
//        Geometry wheel_fr = findGeom(carNode, "WheelFrontRight");
//        wheel_fr.center();
//        box = (BoundingBox) wheel_fr.getModelBound();
//        wheelRadius = box.getYExtent();
//        float back_wheel_h = (wheelRadius * 1.7f) - 1f;
//        float front_wheel_h = (wheelRadius * 1.9f) - 1f;
//        player.addWheel(wheel_fr.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
//                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);
//
//        Geometry wheel_fl = findGeom(carNode, "WheelFrontLeft");
//        wheel_fl.center();
//        box = (BoundingBox) wheel_fl.getModelBound();
//        player.addWheel(wheel_fl.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
//                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);
//
//        Geometry wheel_br = findGeom(carNode, "WheelBackRight");
//        wheel_br.center();
//        box = (BoundingBox) wheel_br.getModelBound();
//        player.addWheel(wheel_br.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
//                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);
//
//        Geometry wheel_bl = findGeom(carNode, "WheelBackLeft");
//        wheel_bl.center();
//        box = (BoundingBox) wheel_bl.getModelBound();
//        player.addWheel(wheel_bl.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
//                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);
//
//        player.getWheel(2).setFrictionSlip(4);
//        player.getWheel(3).setFrictionSlip(4);
//
//        rootNode.attachChild(carNode);
//        getPhysicsSpace().add(player);
//    }
    
//    private void buildPlayer() {
//        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.getAdditionalRenderState().setWireframe(true);
//        mat.setColor("Color", ColorRGBA.Red);
//
//        //create a compound shape and attach the BoxCollisionShape for the car body at 0,1,0
//        //this shifts the effective center of mass of the BoxCollisionShape to 0,-1,0
//        CompoundCollisionShape compoundShape = new CompoundCollisionShape();
//        BoxCollisionShape box = new BoxCollisionShape(new Vector3f(1.2f, 0.5f, 2.4f));
//        compoundShape.addChildShape(box, new Vector3f(0, 1, 0));
//
//        //create vehicle node
//        Node vehicleNode=new Node("vehicleNode");
//        vehicle = new VehicleControl(compoundShape, 400);
//        vehicleNode.addControl(vehicle);
//
//        //setting suspension values for wheels, this can be a bit tricky
//        //see also https://docs.google.com/Doc?docid=0AXVUZ5xw6XpKZGNuZG56a3FfMzU0Z2NyZnF4Zmo&hl=en
//        float stiffness = 60.0f;//200=f1 car
//        float compValue = .3f; //(should be lower than damp)
//        float dampValue = .4f;
//        vehicle.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
//        vehicle.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
//        vehicle.setSuspensionStiffness(stiffness);
//        vehicle.setMaxSuspensionForce(10000.0f);
//
//        //Create four wheels and add them at their locations
//        Vector3f wheelDirection = new Vector3f(0, -1, 0); // was 0, -1, 0
//        Vector3f wheelAxle = new Vector3f(-1, 0, 0); // was -1, 0, 0
//        float radius = 0.5f;
//        float restLength = 0.3f;
//        float yOff = 0.5f;
//        float xOff = 1f;
//        float zOff = 2f;
//
//        Cylinder wheelMesh = new Cylinder(16, 16, radius, radius * 0.6f, true);
//
//        Node node1 = new Node("wheel 1 node");
//        Geometry wheels1 = new Geometry("wheel 1", wheelMesh);
//        node1.attachChild(wheels1);
//        wheels1.rotate(0, FastMath.HALF_PI, 0);
//        wheels1.setMaterial(mat);
//        vehicle.addWheel(node1, new Vector3f(-xOff, yOff, zOff),
//                wheelDirection, wheelAxle, restLength, radius, true);
//
//        Node node2 = new Node("wheel 2 node");
//        Geometry wheels2 = new Geometry("wheel 2", wheelMesh);
//        node2.attachChild(wheels2);
//        wheels2.rotate(0, FastMath.HALF_PI, 0);
//        wheels2.setMaterial(mat);
//        vehicle.addWheel(node2, new Vector3f(xOff, yOff, zOff),
//                wheelDirection, wheelAxle, restLength, radius, true);
//
//        Node node3 = new Node("wheel 3 node");
//        Geometry wheels3 = new Geometry("wheel 3", wheelMesh);
//        node3.attachChild(wheels3);
//        wheels3.rotate(0, FastMath.HALF_PI, 0);
//        wheels3.setMaterial(mat);
//        vehicle.addWheel(node3, new Vector3f(-xOff, yOff, -zOff),
//                wheelDirection, wheelAxle, restLength, radius, false);
//
//        Node node4 = new Node("wheel 4 node");
//        Geometry wheels4 = new Geometry("wheel 4", wheelMesh);
//        node4.attachChild(wheels4);
//        wheels4.rotate(0, FastMath.HALF_PI, 0);
//        wheels4.setMaterial(mat);
//        vehicle.addWheel(node4, new Vector3f(xOff, yOff, -zOff),
//                wheelDirection, wheelAxle, restLength, radius, false);
//
//        vehicleNode.attachChild(node1);
//        vehicleNode.attachChild(node2);
//        vehicleNode.attachChild(node3);
//        vehicleNode.attachChild(node4);
//        rootNode.attachChild(vehicleNode);
//
//        getPhysicsSpace().add(vehicle);
//    }

    private Geometry findGeom(Spatial spatial, String name) {
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (int i = 0; i < node.getQuantity(); i++) {
                Spatial child = node.getChild(i);
                Geometry result = findGeom(child, name);
                if (result != null) {
                    return result;
                }
            }
        } else if (spatial instanceof Geometry) {
            if (spatial.getName().startsWith(name)) {
                return (Geometry) spatial;
            }
        }
        return null;
    }

}