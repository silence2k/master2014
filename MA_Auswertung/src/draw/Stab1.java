package draw;

import java.awt.Color;
import java.awt.Graphics;


public class Stab1 extends Stab{
	
	int pixDx = 20;
	
	int yPro = 10;
	
	int breite = 10;
	
	
	
	
	public Stab1(String[] names, int[] gewichte, int[] values) {
		super(names,gewichte,values);
	}

	@Override
	public void draw(int xOffset, int yOffset, Graphics g) {
		
		int tmpX1 = 0;
		int tmpX2 = 0;
		int tmpY1 = 0;
		int tmpY2 = 0;
		
		
		for(int i = 0; i < values.length; i++){
			
			g.setColor(Color.RED);
			//g.fillRect(,yOffset, breite, values[i]*yPro);
			tmpX1 = pixDx*i+xOffset;
			tmpX2 = tmpX1+breite;
			tmpY1 = yOffset;
			tmpY2 = tmpY1 - values[i]*yPro;
			
			
			g.fillPolygon(new int[]{tmpX1,tmpX1,tmpX2,tmpX2}, new int[]{tmpY1,tmpY2,tmpY2,tmpY1}, 4);
		}
		
		
		
		
	}
	
	
	

	
	
}
