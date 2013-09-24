package util.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import util.source.Source;

public class FilterBox extends ArrayList<Filter> {
	
	private List<Filter> filterBox = new ArrayList<Filter>();
	
	private FilterBox(){
		
	}
	
	public static FilterBox instanceOf(){
		return new FilterBox();
	}
	
	public Set<Source> filter(Set<Source> sourceSet){
		Set<Source> result = new HashSet<Source>();
		for(Filter filter : filterBox){
			result.addAll(filter.filter(sourceSet));
		}
		return result;
	}
	
	public Source filter(Source source){
		Source result = null;
		for(Filter filter : filterBox){
			result = filter.filter(source);
		}
		return result;
	}
	
//	public void add(Filter filter){
//		filterBox.add(filter);
//	}
//	
//	public void add(Filter filter, int index){
//		filterBox.add(index, filter);
//	}
//	
//	public void addAll(Collection<? extends Filter> c){
//		filterBox.addAll(c);
//	}
//
//	public void addAll(Collection<? extends Filter> c, int index){
//		filterBox.addAll(index, c);
//	}
//	
//	public boolean remove(Filter filter){
//		return filterBox.remove(filter);
//	}
	
	public String toString(){
		String result = "FilterBox[";
		ListIterator<Filter> itr = this.listIterator();
		boolean atLeastOne = false;
		while(itr.hasNext()){
			atLeastOne = true;
			result = result + itr.next().toString() + ", ";
		}
		result = atLeastOne ?  new String(result.toCharArray(), 0, result.length()-3) : result;
		result = result + "]";
		return result;
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
