package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartAufgabenbogen1a {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		int[] werte3 = new int[]{1,2,3};

	
		String[] namesTyp1 = new String[]{"nein","ein wenig","viel"};
		
		
		
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(5.1) Hilfestellung beim Erkennen des senkrechten Schieber",namesTyp1, werte3, new int[]{1,1,1,1,1,2,1,1,1,1,1,1,1}),
				new Stab1("(5.2) Hilfestellung bei Funktionsweise des senkrechten Schieber",namesTyp1, werte3, new int[]{1,1,1,1,2,2,1,1,1,1,1,1,1}),
				new Stab1("(6.1) Hilfestellung beim Erkennen des waagerechten Schieber",namesTyp1, werte3, new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1}),
				new Stab1("(6.2) Hilfestellung bei Funktionsweise des waagerechten Schieber",namesTyp1, werte3, new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1})});
//				
		JPanel p2 = new MyPanel1(new Stab1[]{
		new Stab1("(7.1) Hilfestellung beim Erkennen des Knopf",namesTyp1, werte3, new int[]{1,1,2,2,2,2,1,1,1,1,1,2,1}),
		new Stab1("(7.2) Hilfestellung bei Funktionsweise des Knopf",namesTyp1, werte3, new int[]{1,1,1,2,2,1,1,1,1,1,1,1,1}),
		new Stab1("(8.1) Hilfestellung beim Erkennen des Kippschalter",namesTyp1, werte3, new int[]{1,1,1,1,1,1,1,2,1,1,1,1,1}),
		new Stab1("(8.2) Hilfestellung bei Funktionsweise des Kippschalter",namesTyp1, werte3, new int[]{1,2,1,1,1,2,1,1,1,1,1,1,1})});
		
		

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
