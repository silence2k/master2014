package starter;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import draw.MyPanel1;
import draw.Stab1;

public class StartFragebogen2b {

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
		String[] names1a = new String[]{"gar nicht","schwer","mittelmäßig","gut","sehr gut"};
		String[] names1b = new String[]{"sehr schlecht","schlecht","mittelmäßig","gut","sehr gut"};
		String[] names1c = new String[]{"gar nicht","schwer","mittelmäßig","gut","sehr gut"};
		String[] names1d = new String[]{"sehr schlecht","schlecht","mittelmäßig","gut","sehr gut"};
		
		String[] names1e = new String[]{"sehr schwer","schwer","mittelmäßig","gut","sehr gut"};
		String[] names1f = new String[]{"akustisches","haptisches","beides","keins"};

		
		
//		JPanel p = new MyPanel1(new Stab1[]{
//				new Stab1("(3.1) Wie intuitiv war für Sie die Steuerung der Hände",names1a, werte5, new int[]{4,4,4,5,3,3,3,5,4,4,5,5,5}),
//				new Stab1("(3.2) Wie Bewerten Sie deren Funktionsweise",names1b, werte5, new int[]{2,4,4,4,3,4,3,3,4,4,5,4,3}),
//				new Stab1("(3.3) Wie Bewerten Sie die Sicht durch das Headup-Display?",names1c, werte5, new int[]{2,3,3,4,3,3,4,3,2,3,4,3,4}),
//				new Stab1("(3.4) Wie bewerten Sie die Steuerung der Flugsimulation?",names1d, werte5, new int[]{1,3,3,2,2,3,3,3,3,4,4,4,3})});
//				
		JPanel p = new MyPanel1(new Stab1[]{
				new Stab1("(3.5) Wie war die Steuerung, wenn man sich auf die","Flugsimulation konzentrierte?",names1e, werte5, new int[]{2,4,3,4,2,3,3,2,2,2,5,3,3}),
				new Stab1("(3.6) Würden weitere Feedback Kanäle eine Nutzung","für Sie vereinfachen?",names1f, werte4, new int[]{3,2,2,2,2,1,3,2,2,1,1,3,3})});
		
		

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
