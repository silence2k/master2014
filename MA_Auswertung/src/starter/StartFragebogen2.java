package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartFragebogen2 {

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
	
		//String[] names1a = new String[]{"Keine Vorkenntisse","schwer","mittelmäßig","gut","sehr gut"};
		String[] names1a = new String[]{"18 - 20","21 - 24","25 - 29","30 - 35","36 - 45","45 +"};
		String[] names1b = new String[]{"Männlich","Weiblich",""};
		String[] names1c = new String[]{"Ja","Nein"};
		String[] names1d = new String[]{"M. Sc.","B. Sc."};
		
		String[] names1e = new String[]{"Informatik","Ang. Informatik","Tech Informatik"};
		String[] names1f = new String[]{"1 – 2","3 – 4","5 – 6","7 – 9","10 – 12", "12 +"};
		String[] names1g = new String[]{"Ja","Nein"};
		String[] names1h = new String[]{"sehr schlecht","schlecht","mittelmäßig","gut","sehr gut"};
		

		String[] names2 = new String[]{"keiner","ART-Tracker","Kinect","Beide gleich"};
		
//		JPanel p = new MyPanel1(new Stab1[]{
//				new Stab1("(1.1) Bitte geben Sie Ihr Alter an",names1a, werte6, new int[]{3,3,2,3,2,3,3,3,3,3,3,5,5}),
//				new Stab1("(1.2) Bitte geben Sie Ihr Geschlecht an",names1b, werte3, new int[]{1,1,1,1,1,1,1,1,1,1,2,1,1}),
//				new Stab1("(1.3) Sind Sie zur Zeit (WiSe 2013/2014) ","eingeschriebener Student einer Hochschule?",names1c, werte2, new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1}),
//				new Stab1("(1.3) a) Welchen Abschluss streben Sie aktuell an?",names1d, werte2, new int[]{1,2,2,2,2,1,2,1,2,1,1,1,1})});
//				
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(1.3) b) Welchem Studienrichtung gehören Sie an?",names1e, werte3, new int[]{1,2,2,1,3,1,3,1,1,1,1,1,1}),
				new Stab1("(1.3) c) Im wievielten Studiensemester befinden Sie ","sich (Gesamtzahl)?",names1f, werte6, new int[]{6,1,1,4,3,4,3,5,5,5,6,5,6}),
				new Stab1("(1.4) Tragen Sie eine Brille?",names1g, werte2, new int[]{2,1,2,2,2,2,2,2,1,2,1,1,2})});
		
		

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
