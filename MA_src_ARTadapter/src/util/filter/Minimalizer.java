package util.filter;

import util.source.Source;


public class Minimalizer extends AbstractFilter implements Filter {
	
	private double xTolerance, yTolerance, oTolerance;
	// TODO Doesn't work, since the last-values have to be sourcespezific
	private double lastX, lastY;

	private Minimalizer(double x,double  y,double  o){
		this.xTolerance = Math.abs(x);
		this.yTolerance = Math.abs(y);
		this.oTolerance = Math.abs(o);
	}
	
	public static Minimalizer instanceOf(double x, double y, double o){
		return new Minimalizer(x, y, o);
	}

	@Override
	public Source filter(Source source) {
		double x = source.getPosition().getX().m();
		double y = source.getPosition().getY().m();
		
		if(Math.abs(x-lastX) > xTolerance)
			return source;
		if(Math.abs(y-lastY) > yTolerance)
			return source;
		return null;
	}
	
	@Override
	public int compareTo(Filter o) {
		// TODO Auto-generated method stub
		return 0;
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
