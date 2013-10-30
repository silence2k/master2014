package spacecore;

public abstract class Hand {
	
	private float x;
	private float y;
	
	private boolean grab = false;
	
	
	abstract public boolean isLeftHand();
	
	public boolean isRightHand(){
		return !isLeftHand();
	}
	
	
	public static Hand instanceLeftHand(){
		return new Hand() {

			@Override
			public boolean isLeftHand() {
				return true;
			}
			
		
		};
	}
	
	public static Hand instanceRightHand(){
		return new Hand() {
			@Override
			public boolean isLeftHand() {
				return false;
			}
		};
	}

	
	public void update(float x, float y, boolean grab){
		this.x = x;
		this.y = y;
		this.grab = grab;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isGrab() {
		return grab;
	}
	
	
	
}
