package amq;

import amqdata.Hand;


public class MyHand extends Hand {
	
	private static final float delta = 0.1f;
	
	
	
	
	public MyHand(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void addX(){
		x += delta;
	}
	
	public void addY(){
		y += delta;
	}
	
	public void addZ(){
		z += delta;
	}
	
	public void subX(){
		x -= delta;
	}
	
	public void subY(){
		y -= delta;
	}
	
	public void subZ(){
		z -= delta;
	}
	
	public void toggleGrab(){
		grab = !grab;
	}


}
