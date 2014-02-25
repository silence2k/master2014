package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartFragebogen1a {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] werte2 = new int[]{1,2};
		int[] werte3 = new int[]{1,2,3};
		int[] werte4 = new int[]{1,2,3,4};
		int[] werte5 = new int[]{1,2,3,4,5};
		int[] werte6 = new int[]{1,2,3,4,5,6};
		int[] werte7 = new int[]{1,2,3,4,5,6,7};
	
		//String[] names1a = new String[]{"Keine Vorkenntisse","schwer","mittelm‰ﬂig","gut","sehr gut"};
		String[] names1a = new String[]{"Keine Vorkenntisse","Bis zu einem Jahr","1 ñ 5","5 +"};
		String[] names1b = new String[]{"nie","0 ñ 5","5 ñ 15","15 ñ 25","25 ñ 40","40 +"};
		String[] names1c = new String[]{"keine","wenig","mittelm‰ﬂig","gute Kenntnisse"};
		String[] names1d = new String[]{"keine","wenig","mittelm‰ﬂig","gute Kenntnisse"};
		

		String[] names1e = new String[]{"nie","selten","gelegentlich","h‰ufig"};
		String[] names1f = new String[]{"Ja","Nein"};
		String[] names1g = new String[]{"keine","wenig","mittelm‰ﬂig","gute Kenntnisse"};
		String[] names1h = new String[]{"Informatik","Ang. Informatik","Tech Informatik"};
		

		String[] names2 = new String[]{"keiner","ART-Tracker","Kinect","Beide gleich"};
//		
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(2.1) Wie lange verwenden Sie bereits Computer?",names1a, werte4, new int[]{4,4,4,4,4,4,4,4,4,4,4,4,4}),
				new Stab1("(2.2) Wie lange verwenden Sie einen Computer pro Woche?",names1b, werte6, new int[]{5,5,5,2,2,5,5,6,6,6,6,5,6}),
				new Stab1("(2.3) Haben Sie allgemeine Kenntnisse in Gestensteuerung?",names1c, werte4, new int[]{3,2,2,1,1,1,1,4,4,4,2,4,4}),
				new Stab1("(2.4) Haben Sie bereits Kenntnisse ¸ber 3D-Gestensteuerung?",names1d, werte4, new int[]{2,1,1,1,1,1,1,4,2,3,3,3,3}),
				new Stab1("(2.5) Haben Sie bereits eine 3D-Gestensteuerung Verwendet?",names1e, werte4, new int[]{2,1,1,1,1,1,1,4,2,3,2,3,2})});
////				
//		JPanel p = new MyPanel1(new Stab1[]{
//				new Stab1("(2.5) Haben Sie bereits eine 3D-Gestensteuerung Verwendet?",names1e, werte4, new int[]{2,1,1,1,1,1,1,4,2,3,2,3,2}),
//				new Stab1("(2.6) Haben Sie bereits am Versuch zum Schaltbord ","teilgenommen?",names1f, werte2, new int[]{2,3,2,2,2,2,3,2,1,1,1,1,1}),
//				new Stab1("(2.7) Haben Sie Erfahrungen mit (Spiel-) Flugsimulationen?",names1g, werte4, new int[]{3,3,2,3,3,3,2,4,2,3,2,3,2})});
		
		

		final JFrame frame = new JFrame("Draw");
		
		JScrollPane jsp = new JScrollPane(p,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		frame.getContentPane().add(jsp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setLocation(200, 200);
		frame.setVisible(true);
		
		
		

	}

}
