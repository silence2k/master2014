package util.filter;

import java.util.HashSet;
import java.util.Set;

import util.source.Source;


public abstract class AbstractFilter {

	public Set<Source> filter(Set<Source> sourceSet) {
		Set<Source> result = new HashSet<Source>();
		for(Source source : sourceSet){
			result.add(this.filter(source));
		}
		return result;
	}
	
	public abstract Source filter(Source source);
	
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
