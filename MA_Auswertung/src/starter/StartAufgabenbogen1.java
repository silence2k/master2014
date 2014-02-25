package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartAufgabenbogen1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		int[] werte3 = new int[]{1,2,3};
		
	
		String[] namesTyp1 = new String[]{"nein","ein wenig","viel"};
		
		
		
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(1.1) Hilfestellung beim Erkennen des Hebels",namesTyp1, werte3, new int[]{1,1,1,2,2,2,1,1,1,2,1,1,1}),
				new Stab1("(1.2) Hilfestellung bei Funktionsweise des Hebels",namesTyp1, werte3, new int[]{1,1,1,3,2,2,1,1,1,1,1,1,1}),
				new Stab1("(2.1) Hilfestellung beim Erkennen des Zweihand-Hebels",namesTyp1, werte3, new int[]{2,1,1,1,2,1,1,1,1,1,1,1,1}),
				new Stab1("(2.2) Hilfestellung bei Funktionsweise des Zweihand-Hebels",namesTyp1, werte3, new int[]{2,1,1,1,2,1,1,1,1,1,1,1,1})});
//				
		JPanel p2 = new MyPanel1(new Stab1[]{
		new Stab1("(3.1) Hilfestellung beim Erkennen des Rads",namesTyp1, werte3, new int[]{1,1,1,1,2,2,1,1,1,1,1,1,1}),
		new Stab1("(3.2) Hilfestellung bei Funktionsweise des Rads",namesTyp1, werte3, new int[]{1,1,1,1,1,2,1,1,1,1,1,1,1}),
		new Stab1("(4.1) Hilfestellung beim Erkennen des Zweihand-Rads",namesTyp1, werte3, new int[]{2,1,1,1,1,3,2,1,1,1,1,1,1}),
		new Stab1("(4.2) Hilfestellung bei Funktionsweise des Zweihand-Rads",namesTyp1, werte3, new int[]{1,1,1,1,1,3,1,1,1,1,1,1,1})});
		
		

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
