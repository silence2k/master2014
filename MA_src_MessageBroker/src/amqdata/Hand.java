package amqdata;


public class Hand {
	
//	protected int typ;
	
	protected float x;
	protected float y;
	protected float z;
	
	protected boolean grab;


	public Hand() {
		super();
	}
	

	public Hand(String amq){
		parse(amq);
	}
	

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public boolean isGrab() {
		return grab;
	}


	@Override
	public String toString() {
		return "Hand[x=" + x + ";y=" + y + ";z=" + z + ";grab=" + grab + "]";
	}

	private String removeKomma(String tmp){
		String result = tmp;
		if(tmp.contains(",")){
			result = tmp.replace(',', '.');
		}
		return result;
	}
	
	private float parseFloat(String tmp){
		float f = 0;
		try{
			f = Float.parseFloat(tmp);
		}catch(NumberFormatException nfe){
			System.out.println(tmp);
			nfe.printStackTrace();
		}
		return f;
	}

	private void parse(String s){
		//System.out.println("s: "+s );
		String tmp;
		if(s.startsWith("Hand[")&&s.endsWith("]")){
			tmp = s.substring(5,s.length()-1);
			String arr[] = tmp.split(";");
//			typ = Integer.parseInt(arr[0].substring(4, 5));
			
			
			tmp = removeKomma(arr[0].substring(2));
			x = parseFloat(tmp);
			
			tmp = removeKomma(arr[1].substring(2));
			y = parseFloat(tmp);
			
			tmp = removeKomma(arr[2].substring(2));
			z = parseFloat(tmp);
			
			
			grab = arr[3].toLowerCase().contains("true");
			
			//System.out.println("s: "+s+" y: "+arr[1].substring(2) +" ->> "+y);
			
		}else{
			System.err.println("\""+s+"\" kann nicht gepartst werden!!!!");
		}
	}
	
	public void update(Hand hand){
		this.x = hand.x;
		this.y = hand.y;
		this.z = hand.z;
		this.grab = hand.grab;
	}
	
	public static void main(String args[]){
		String s = "Hand[x=100.001;y=200.002;z=300.003;grab=true]";
		Hand h = new Hand(s);
		System.out.println(h.toString());
	}
	
}
