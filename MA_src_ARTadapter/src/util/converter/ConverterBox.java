package util.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


import util.filter.Filter;
import util.source.Source;
import util.source.SourceImpl;

/**
 * The ConverterBox is an ordered container for Converters.<br><br>
 * It extends ArrayList&lt;Converter&gt; only by the two convert-methods. Therefore it can be used like an ArrayList.
 * @author Malle
 *
 */
public class ConverterBox extends ArrayList<Converter> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6556997859865722479L;

	private ConverterBox(){
		
	}
	
	/**
	 * Creates a new ConverterBox.
	 * @return A new ConverterBox.
	 */
	public static ConverterBox instanceOf(){
		return new ConverterBox();
	}
	
	/**
	 * Creates a new ConverterBox with the given Converters while keeping the order.
	 * @param converters - An array of Converters.
	 * @return A new ConverterBox with the given Converters already set.
	 */
	public static ConverterBox instanceOf(Converter[] converters){
		ConverterBox converterBox = new ConverterBox();
		for(Converter converter : converters){
			if(converter != null){
				converterBox.add(converter);
			}
		}
		return converterBox;
	}
	
	/**
	 * Creates a new ConverterBox with the given Converters while keeping the order.
	 * @param converters - An ArrayList of Converters.
	 * @return A new ConverterBox with the given Converters already set.
	 */
	public static ConverterBox instanceOf(ArrayList<Converter> converters){
		ConverterBox converterBox = new ConverterBox();
		for(Converter converter : converters){
			if(converter != null){
				converterBox.add(converter);
			}
		}
		return converterBox;
	}

	/**
	 * This method only calls the convert(Source source)-method for all Sources in the given Set. 
	 * Therefore running all the given Sources through all the converters in this ConverterBox.<br><br>
	 * See public Source convert(Source source) for more details.
	 * @param sourceSet - A Set of Sources to be converted.
	 * @return A new Set with the Sources that were returned by convert(Source source).
	 */
	public Set<Source> convert(Set<Source> sourceSet){
		Set<Source> result = new HashSet<Source>();
		
		for(Source source : sourceSet){
			result.add(convert(source));
		}
		return result;
	}
	
	/**
	 * Converts the given Source by running the Source through all the Converters in this ConverterBox.<br><br>
	 * While the Converters are supposed to not change the given Source in the process of the conversion 
	 * and instead return a new Source, the ConverterBox does not guarantee this.
	 * @param source - A Source to be converted.
	 * @return A Source, which is the result of all the conversions.
	 */
	public Source convert(Source source){		
		Source result = source;
		for(Converter converter : this){
			result = converter.convert(result);
		}
		return result;
	}
	
	public String toString(){
		String result = "ConverterBox[";
		ListIterator<Converter> itr = this.listIterator();
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
