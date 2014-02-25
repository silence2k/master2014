package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartFragebogen1d {

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
	
		String[] namesTyp1 = new String[]{"nein","schwer","mittelm‰ﬂig","gut","sehr gut"};
		String[] namesTyp2 = new String[]{"gar nicht","schwer","mittelm‰ﬂig","gut","sehr gut"};
		String[] namesTyp3 = new String[]{"unbenutzbar","schlecht","mittelm‰ﬂig","gut","sehr gut"};
		
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(4.3.3) Handhabung des Rad",namesTyp3, werte5, new int[]{4,4,5,3,5,4,5,4,4,5,2,5,5}),
				new Stab1("(4.4.1) Haben Sie das Zweihand-Rad erkannt?",namesTyp1, werte5, new int[]{3,5,5,5,5,4,5,6,4,4,5,5,5}),
				new Stab1("(4.4.2) Wie intuitiv war Ihnen die Funktionsweise?",namesTyp2, werte5, new int[]{4,5,5,4,5,3,5,2,4,2,4,4,4}),
				new Stab1("(4.4.3) Handhabung des Zweihand-Rads",namesTyp3, werte5, new int[]{4,3,5,3,5,2,5,2,4,2,2,3,3})});
//				
		JPanel p2 = new MyPanel1(new Stab1[]{
		new Stab1("(4.5.1) Haben Sie den senkrechten Schieber erkannt?",namesTyp1, werte5, new int[]{5,4,5,5,5,4,5,6,4,5,5,5,5}),
		new Stab1("(4.5.2) Wie intuitiv war Ihnen die Funktionsweise?",namesTyp2, werte5, new int[]{5,5,5,5,54,5,4,4,5,5,5,5}),
		new Stab1("(4.5.3) Handhabung des senkrechten Schieber",namesTyp3, werte5, new int[]{4,4,5,5,5,4,5,4,4,5,4,5,5}),
		new Stab1("(4.6.1) Haben Sie den waagerechten Schieber erkannt?",namesTyp1, werte5, new int[]{5,5,5,5,5,4,5,6,4,5,5,5,5})});
		
		

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
