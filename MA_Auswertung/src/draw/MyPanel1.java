package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel1 extends JPanel {
	
	String[] names = {"eins", "zwei", "drei", "vier", "f�nf"};
	int[] werte = {1,2,3,4,5};
	int[] anzahl = {0,1,5,1,0};
//	int[] values = {5,0,0,0,0};
	
	
	Stab stab = new Stab1("",names, werte, anzahl);
	
	
	List<Stab> stabs;
	
	public MyPanel1(Stab... stabs) {
		this.stabs = new ArrayList<>();
		for(Stab s: stabs){
			this.stabs.add(s);
		}
		
		stab = this.stabs.get(0);
	}
	
	
	@Override
	public void update(Graphics arg0) {
		// TODO Auto-generated method stub
		super.update(arg0);
		paint(arg0);
	}

	public void paint(Graphics g) {

	//	stab.draw(100, 150, g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1500);
		
		int x = 100;
		int y = 150;
		
		int dx = 400;
		int dy = 300;
		int j = 0;
		
		for(int i = 0; i < stabs.size(); i = i+2){
			j = i%2;
			System.out.println("i: "+i+" j:"+j);
			stabs.get(i).draw(0,i/2*dy,x+j*dx, y, g);
			if(i+1 < stabs.size())
			stabs.get(i+1).draw(0,i/2*dy,x+(j+1)*dx, y, g);
		}
		
	}
	

}
