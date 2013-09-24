package util.source;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class SourceHistory implements Comparable<SourceHistory> {
	
	private static Map<Integer, SourceHistory> map = new HashMap<Integer, SourceHistory>();
	private static int historySize = 10;
	
	private SourceCopy[] history = new SourceCopy[historySize];
	private int id;
//	private int size = 0;
	private int first = 0;
	private int last = 0;
	
	private SourceHistory(Source source){
		history[0] =  SourceImpl.getCopy(source); 
		id = source.getID();
	}
	
	public static SourceHistory instanceOf(Source source){
		SourceHistory sh = getSourceHistory(source.getID());
		if(sh == null){
			sh = new SourceHistory(source);
			map.put(source.getID(), sh);
		}
		return sh;
	}
	
	public static void setSize(int size){
		for(SourceHistory sh : map.values()){
			int overflow = sh.first-(size-1);
			
			// TODO TEST TEST TEST weather this does what it is supposed to be!!!!
			if(overflow > 0){
				for(int i = overflow; i <= sh.first; i++){
					sh.history[i-overflow] = sh.history[i];
				}
				sh.last = 0;
			}
		}
		historySize = size;
	}
	
	
	public static Set<SourceHistory> put(Set<Source> sourceSet){
		for(Source source : sourceSet){
			SourceHistory.put(source);
		}
		return getAll();
	}
	
	public static void put(Source source){
		SourceHistory sh = getSourceHistory(source.getID());
		if(sh == null){
			sh = SourceHistory.instanceOf(source);
		}
		SourceCopy toPut = (source.isCopy()) ? (SourceCopy)source : SourceImpl.getCopy(source);
		sh.history[sh.last] = toPut;
		sh.shift();
	}
	
	public static Set<SourceHistory> getAll(){
		Set<SourceHistory> result = new HashSet<SourceHistory>();
		for(SourceHistory sh : map.values()){
			result.add(sh);
		}
		return result;
	}
	
	public static SourceHistory getSourceHistory(Integer sourceID){
		return map.get(sourceID);
	}
	
	public static Integer getSize(){
		return historySize;
	}

	
	// This function has been taken over by public static void setSize(int size){...}
//	public void setSize(int size){
//		
//		int overflow = first-(size-1);
//		
//		// TODO TEST TEST TEST weather this does what it is supposed to be!!!!
//		if(overflow > 0){
//			for(int i = overflow; i <= first; i++){
//				history[i-overflow] = history[i];
//			}
//			last = 0;
//		}
//		this.size = size;
//	}
	
	// This function has been taken over by public static void put(Source source){...}
//	private boolean put(Source source){
//		SourceCopy toPut = (source.isCopy()) ? (SourceCopy)source : SourceImpl.getTemporalCopy(source);
//		if(source.getID().equals(id)){
//			history[last] = toPut;
//			shift();
//			return true;
//		}
//		// TODO Exception?
//		return false;
//	}
	
	public Source getFirst(){
		return history[first];
	}
	
	public Source getSecond(){
		if(first == 0)
			return history[history.length-1];
		return history[first-1];
	}
	
	public Source getLast(){
		return history[last];
	}
	
	public Source getHistoryItem(int i){
		return (i>historySize-1) ? null : history[i];
	}
	
	// TODO Should SourceCopys be mutable or immutable? If they are mutable this method should only return copies!
	// Otherwise it can just return a new array with the same objects.
	// The new array is necessary, so the history can not be manipulated. This has to be in the methoddescription too!

	/**
	 * Returns an array containing the SourceCopies contained in this SourceHistory in order from newest to oldest.<br><br>
	 * Mutation of this array or its elements does not effect the SourceHistory.
	 * @return
	 */
	public Source[] getHistory(){
		Source[] ordered = new Source[history.length];
		int elementcounter = 0;
		
		int newPosition = 0;
		// Iterate from the first-pointer to the start of the array.
		for(int i = first; i>=0 ; i--){
			if(history[i] != null){
				ordered[newPosition] = SourceImpl.getCopy(history[i]);
				elementcounter++;
			}else{
				ordered[newPosition] = null;
			}
			newPosition++;
		}
		if(last != 0){ 	// In case, that the last-pointer points to the first element (0) of the array, 
						// it has already been processed by the first for-loop. 
						// Therefore this is unnecessary and would result in a outOfBoundsException
			for(int i = history.length-1; i>=last; i--){
				// Iterate from the end of the array to the last-pointer.
				if(history[i] != null){
					ordered[newPosition] = SourceImpl.getCopy(history[i]);
					elementcounter++;
				}else{
					ordered[newPosition] = null;
				}
				newPosition++;
			}
		}
		
		Source[] result = new Source[elementcounter];
		
		for(int i = 0; i<ordered.length; i++){
			if(ordered[i] != null)
				result[i] = ordered[i];
		}
		
		return result;
	}
	
	//	public Source[] getHistory(){
//		Source[] result = new Source[history.length];
//		for(int i = 0; i < history.length; i++){
//			if(history[i] != null){
//				result[i] = SourceImpl.getTemporalCopy(history[i]);
//			}else{
//				result[i] = null;
//			}
//			
//		}
//		return result;
//	}
	
	public Integer getSourceID(){
		return id;
	}
	
	private void shift(){
		first = last;
		last = (last == historySize-1) ? 0 : last+1;
	}
	
	public String toString(){
		String result = "SouceHistory(ID:"+id+", [";
		
		int counter = 0;
		boolean atLeastOne = false;
		for(Source s : getHistory()){
			if(s != null){
				atLeastOne = true;
				result = result + counter+":" + s.toString() + ", ";
				counter++;	
			}
			
		}
		
		result = atLeastOne ?  new String(result.toCharArray(), 0, result.length()-3) : result;
		result = result + "])";
		return result;
	}
	
	public int hashCode(){
		return getSourceID();
	}

	@Override
	public int compareTo(SourceHistory arg) {
		return Double.compare(this.getSourceID(), arg.getSourceID());
	}
	
	public boolean equals(Object obj){
		// TODO
		return true;
	}
	

	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName()+": ";
	
	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object) (for debugging), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj){
		System.out.println(deb+obj.toString());
	}
}
