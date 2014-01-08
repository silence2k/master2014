package reciver.parser;

public class Parser {
	
	public static int parseFrame(String s){
		String[] arr = s.split(" ");
		return Integer.parseInt(cleanInt(arr[1]));
	}
	
	
	private static String cleanInt(String s){
		char[] arr = s.toCharArray();
		for(int i = 0; i < s.length(); i++){
			if(arr[i]>='0' && arr[i] <= '9'){
				
			}else{
				return s.substring(0, i);
			}
		}
		return s;
	}

}
