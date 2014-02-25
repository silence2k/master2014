package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartFragebogen1b {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] werte1 = new int[]{1,2,3,4,5};
	
		//String[] names1a = new String[]{"Keine Vorkenntisse","schwer","mittelm��ig","gut","sehr gut"};
		String[] names1a = new String[]{"gar nicht","schwer","mittelm��ig","gut","sehr gut"};
		String[] names1b = new String[]{"sehr schlecht","schlecht","mittelm��ig","gut","sehr gut"};
		
		int[] werte2 = new int[]{1,2,3,4};
		String[] names2 = new String[]{"keiner","ART-Tracker","Kinect","Beide gleich"};
		
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(3.1.1) Wie intuitiv war f�r Sie die Steuerung der H�nde",names1a, werte1, new int[]{4,5,5,4,1,3,5,2,4,5,4,4,4}),
				new Stab1("(3.1.2) Wie Bewerten Sie deren Funktionsweise",names1b, werte1, new int[]{3,4,4,4,4,4,5,3,4,4,3,4,4}),
				new Stab1("(3.2.1) Wie intuitiv war f�r Sie die Greif / Loslassgeste",names1a, werte1, new int[]{5,5,4,5,4,3,5,5,4,5,3,5,4}),
				new Stab1("(3.2.2) Wie Bewerten Sie deren Funktionsweise",names1b, werte1, new int[]{3,3,4,4,4,3,5,4,3,4,2,5,4})});
				
//		JPanel p = new MyPanel1(new Stab1[]{
//				new Stab1("(3.3.1) Umgang mit dem ART-Tracking Sensor",names1a, werte1, new int[]{2,2,4,5,4,3,4,1,3,4,3,2,2}),
//				new Stab1("(3.3.2) Umgang mit dem Kinect Sensor",names1b, werte1, new int[]{4,4,5,4,5,4,5,3,4,3,3,5,2}),
//				new Stab1("(3.3.3) Welcher Sensor gefiel Ihnen Besser?",names2, werte2, new int[]{3,3,3,2,3,3,3,3,3,2,3,3,4}),
//				new Stab1("(3.2.2) Wie Bewerten Sie deren Funktionsweise",names1b, werte1, new int[]{3,3,4,4,4,3,5,4,3,4,2,5,4})});
		
		

		final JFrame frame = new JFrame("Draw");
		
		JScrollPane jsp = new JScrollPane(p,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		frame.getContentPane().add(jsp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setLocation(200, 200);
		frame.setVisible(true);
		frame.setResizable(false);
		
		

	}

}
