package reciver.parser;

public class Parser {
	
	public static int parseFrame(String s){
		String[] arr = s.split(" ");
		return Integer.parseInt(arr[1]);
	}

}
