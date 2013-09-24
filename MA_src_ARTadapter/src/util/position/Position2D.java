package util.position;

import util.values.Length;
import util.values.LengthInM;
import util.values.Values;

public class Position2D extends AbstractPosition {

	private Position2D(Length x, Length y){
		this.x = x;
		this.y = y;
		this.z = LengthInM.valueOf(0);
	}
	
	public static Position2D instanceOf(Length x, Length y){
		return new Position2D(x, y);
	}
	
	public Length getX() {
		return x;
	}

	public Length getY() {
		return y;
	}
	
	public Position2D add(Position2D position){
		return Position2D.instanceOf(this.getX().add(position.getX()), this.getY().add(position.getY()));
	}
	
	public Position2D subtract(Position2D position){
		return Position2D.instanceOf(this.getX().sub(position.getX()), this.getY().sub(position.getY()));
	}
	
	public Position2D mul(double value){
		return Position2D.instanceOf(this.getX().mul(value), this.getY().mul(value));
	}
	
	public Position2D div(double value){
		return Position2D.instanceOf(this.getX().div(value), this.getY().div(value));
	}
	
	public Length distanceTo(Position3D position){
		double x1 = this.getX().m();
		double y1 = this.getY().m();

		double x2 = position.getX().m();
		double y2 = position.getY().m();

		double v1 = Math.pow((x2-x1), 2);
		double v2 = Math.pow((y2-y1), 2);
		
		double pResult = Math.sqrt((v1) + (v2));
		
		return Values.lengthInM(pResult);
	}
	
	public int compareTo(Position3D position){
		Length obj1 = (this.getX()).add(this.getY());
		Length obj2 = (position.getX()).add(position.getY());
		
		return obj1.compareTo(obj2);
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Position2D))
			return false;
		Position2D p = (Position2D)obj;
		if(this.getX().equals(p.getX()) && this.getY().equals(p.getY()))
			return true;
		return false;
	}
	
	public String toString(){
		return "Position(X:"+x+", Y:"+y+")";
	}
	
	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName() + ": ";

	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj) {
		System.out.println(deb + obj.toString());
	}
}
