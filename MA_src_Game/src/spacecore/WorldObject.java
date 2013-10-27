package spacecore;

import org.lwjgl.util.vector.Vector3f;

public class WorldObject implements Collision {
	public Vector3f Pt  = new Vector3f();;
	public Model model;
	public float Yaw;
	public boolean hasCollision = true;

	private WorldObject() {
		
	}
	
	public WorldObject(boolean hasCollision) {
			this();
			this.hasCollision = hasCollision;
	}
	
	public WorldObject(Vector3f pt) {
		Pt = pt;
	}

	public Vector3f getPt() {
		return Pt;
	}

	public void setPt(Vector3f pt) {
		Pt = pt;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public float getYaw() {
		return Yaw;
	}

	public void setYaw(float yaw) {
		Yaw = yaw;
	}
	
	@Override
	public boolean hasCollision() {
		return hasCollision;
	}

	public void setHasCollision(boolean hasCollision) {
		this.hasCollision = hasCollision;
	}
	
	
	
}