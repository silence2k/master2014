package util;

import util.values.Length;

public interface Straight {
	
	public double getM();
	public Length getB();
	
	public Length getX(Length y);
	public Length getY(Length x);

}
