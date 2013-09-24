package util.filter;

import util.source.Source;


public class Jitter extends AbstractFilter implements Filter{

	private Jitter(){
		
	}
	
	public static Jitter instanceOf(){
		return new Jitter();
	}

	@Override
	public Source filter(Source source) {
		// TODO Auto-generated method stub
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
	private String deb = getClass().getName()+": ";
	
	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj){
		System.out.println(deb+obj.toString());
	}




}
