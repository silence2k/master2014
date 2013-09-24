package util.source;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.angle.Angle3D;
import util.position.Position3D;


public class SourceImpl extends AbstractSource implements Source {
	
	private static Map<Integer, Source> sources = new HashMap<Integer, Source>();
	private static int maximumID = 63;
	private static int minimumID = 0;
	
	//>>>>>>>>>> MEMBERS >>>>>>>>>> //
	
		// See AbstractSource
	
	//<<<<<<<<<< MEMBERS <<<<<<<<<< //
	
	//>>>>>>>>>> CONSTRUCTORS >>>>>>>>>> //
	
	private SourceImpl(int id, Position3D position, Angle3D angle){
		if(!(validateID(id)))
			// TODO See public static validateID(id)
			throw new IllegalArgumentException("ID is not valid!");
		else{
			this.id = id;
			this.name = Integer.valueOf(id).toString();
			this.position = position;
			this.angle = angle;
		}
		sources.put(id, this);
		setNewCopy(SourceCopy.instanceOf(this));
	}
	
	//<<<<<<<<<< CONTRUCTORS <<<<<<<<<< //
	
	//>>>>>>>>>> FACTORIES >>>>>>>>>> //
	
	public static Source instanceOf(int id, Position3D position, Angle3D angle){
			return new SourceImpl(id, position, angle);
	}
	
	public static Source instanceOf(int id){
		return new SourceImpl(id, Position3D.instanceOf(), Angle3D.instanceOf());
	}
	
	public static SourceCopy getCopy(Source source){
		return SourceCopy.instanceOf(source);
	}
	
	public static Map<Integer, Source> getNewestCopys(){
		Map<Integer, Source> result = new HashMap<Integer, Source>();
		
		for(Integer i : sources.keySet()){
			result.put(i, SourceImpl.getSource(i).getNewestCopy());
		}
		
		return result;
	}
	
	//<<<<<<<<<< FACTORIES <<<<<<<<<< //
	
	//>>>>>>>>>> STATICS >>>>>>>>>> //
	
	public static Set<Integer> getIDSet(){
		return sources.keySet();
	}
	
	public static Map<Integer, Source> getSourceMap(){
		return sources;
	}
	
	public static Set<Source> getSourceSet(){
		Set<Source> result = new HashSet<Source>();
		for(Source source : sources.values()){
			result.add(source);
		}
		return result;
	}
	
	public static Source getSource(int id){
		Source source = sources.get(id);
		if(source == null){
			source = SourceImpl.instanceOf(id);
		}
		return source;
	}
	
	/**
	 * Validates the id.
	 * @param id - An integer, which is meant to become the id for a new source.
	 * @return True - When the integer is valid to become the id of a new source.<br>
	 * 			false - Otherwise.
	 */
	public static boolean validateID(int id){
		if (getIDSet().contains(id)){
			System.err.println("WFS.source.Source.validateID("+id+"): There is a source with that id already existing! IDs must be unique!");
			return false;
		}
		if (id<minimumID || id>maximumID){ // The WFS-System only supports 64 Sources (0..63)
			System.err.println("WFS.source.Source.validateID("+id+"): The id is not within the valid range of ids. Must be between "+minimumID+" and "+maximumID+" (inclusive)!");
			return false;
		}
		return true;
		// TODO Design and throw new exceptions, which explain what is wrong with the parameter.
	}
	
	//<<<<<<<<<< STATICS <<<<<<<<<< //
	
	//>>>>>>>>>> SETTER >>>>>>>>>> //
	
	public boolean setID(int id){
		if(!(validateID(id)))
			return false;
		this.id = id;
		return true;
	}
	
	@Override
	public void update(long timeStamp, Position3D position, Angle3D angle){
//		this.setTimeStamp(timeStamp);
//		this.setPosition(position);
//		this.setAngle(angle);
		
		this.timeStamp = timeStamp;
		this.position = position;
		this.angle = angle;
		
		setNewCopy(SourceCopy.instanceOf(this));
	}
	
	//<<<<<<<<<< SETTER <<<<<<<<<< //
	
	//>>>>>>>>>> GETTER >>>>>>>>>> //
	

	
	//<<<<<<<<<< GETTER <<<<<<<<<< //
	
	public boolean isCopy(){
		return false;
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
	@SuppressWarnings("unused")
	private void out(Object obj){
		System.out.println(deb+obj.toString());
	}
}
