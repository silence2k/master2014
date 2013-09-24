package util.converter;

import util.source.Source;
/**
 * The converter-interface is to be implemented by all converterclasses.<br><br>
 * This ensures the uniformity of the converters and make the converters compatible with the ConverterBox.
 * @author Malle
 *
 */

public interface Converter extends Comparable<Converter>{

	/**
	 * This method is the core of each converter.<br><br>
	 * It converts the properties of the given Source by the the specified properties of the converter 
	 * and returns a new source with the converted properties leaving the given source as it was. 
	 * @param source - The source to be converted.
	 * @return A new source, which is a copy of the given source with converted properties. 
	 */
	public Source convert(Source source);
	
	public int hashCode();
	public boolean equals(Object obj);
	
}
