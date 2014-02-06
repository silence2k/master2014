package draw;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MyPanel1 extends JPanel {
	
	String[] names = {"eins", "zwei", "drei", "vier", "fünf"};
	int[] gewichte = {1,2,3,4,5};
	int[] values = {5,3,10,2,1};
	
	
	Stab stab = new Stab1(names, gewichte, values);
	
	
	
	@Override
	public void update(Graphics arg0) {
		// TODO Auto-generated method stub
		super.update(arg0);
		paint(arg0);
	}

	public void paint(Graphics g) {

		stab.draw(30, 200, g);
		
	}
	

}
