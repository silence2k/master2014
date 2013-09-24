package util;

public class Tuple<A, B> {

	private A object1;
	private B object2;
	
	public Tuple(A o1, B o2){
		this.object1 = o1;
		this.object2 = o2;
	}
	
	public A getO1(){
		return object1;
	}
	
	public B getO2(){
		return object2;
	}
	
	@Override
	public String toString(){
		return "Tuple("+getO1()+", "+getO2()+")";
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
