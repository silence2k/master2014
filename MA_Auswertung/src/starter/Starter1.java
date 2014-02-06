package starter;



import javax.swing.JFrame;

import draw.MyPanel1;

public class Starter1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		

		
		


		final JFrame frame = new JFrame("Draw");
		
		frame.getContentPane().add(new MyPanel1());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLocation(200, 200);
		frame.setVisible(true);
		
		

	}

}
