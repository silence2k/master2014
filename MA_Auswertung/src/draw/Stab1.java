package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import util.Mathe;
import util.Paar;


public class Stab1 extends Stab{
	
	int abstand = 40;
	
	int yPro = 10;
	
	int breite = 20;
	
	int breite_MW = 3;
	int hoehe_MW = 6;
	
	int hoehe_Staw = 6;
	
	// position mittelWERT
	int fixIT = 80;
	
	
	public Stab1(String[] names, int[] gewichte, int[] values) {
		super(names,gewichte,values);
	}

	@Override
	public void draw(int xOffset, int yOffset, Graphics g) {
		
		int tmpX1 = 0;
		
		int xStart = Integer.MAX_VALUE;
		int xEnde = Integer.MIN_VALUE;
		
		
		
		for(int i = 0; i < values.length; i++){
			
			g.setColor(Color.BLACK);
			//g.fillRect(,yOffset, breite, values[i]*yPro);
			tmpX1 = abstand*i+xOffset-breite/2;
			
			xStart = Math.min(xStart, tmpX1);
			xEnde = Math.max(xEnde, tmpX1);
			
			g.drawLine(tmpX1, yOffset+4, tmpX1, yOffset);
			
			drawBalken(yOffset, g, tmpX1, i);
		}
		List<Paar> paare = Mathe.paare(gewichte,values);
		double mittelwert = Mathe.mittelWert(paare);
		double standartAbweichung = Mathe.standardabweichung(paare, mittelwert);
		
		drawStandardabweichung(mittelwert, standartAbweichung, xStart, g);
		
		drawMittelwert(mittelwert, xStart,g);
		
//		Draw start und ende
//		g.setColor(Color.YELLOW);
//		g.drawLine(xStart, yOffset+8, xStart, yOffset+4);
//		g.setColor(Color.CYAN);
//		g.drawLine(xEnde, yOffset+8, xEnde, yOffset+4);
		
	}

	private void drawBalken(int yOffset, Graphics g, int tmpX1, int i) {
		int tmpX2;
		int tmpY1;
		int tmpY2;
		g.setColor(Color.RED);
		tmpX1 = tmpX1-breite/2;
		tmpX2 = tmpX1+breite;
		tmpY1 = yOffset;
		tmpY2 = tmpY1 - values[i]*yPro;
		
		
		g.fillPolygon(new int[]{tmpX1,tmpX1,tmpX2,tmpX2}, new int[]{tmpY1,tmpY2,tmpY2,tmpY1}, 4);
	}

	private void drawMittelwert(double mittelwert, int xStart, Graphics g) {
		int xMittel = (int)((mittelwert-1)*abstand+xStart);
		g.setColor(Color.CYAN);
		g.fillRect(xMittel-breite_MW/2, fixIT, breite_MW, hoehe_MW);
	}
	
	private void drawStandardabweichung(double mittelwert, double standartAbweichung, int xStart, Graphics g){
		int xSt1 = (int)((mittelwert-1-standartAbweichung)*abstand+xStart);
		int xSt2 = (int)((mittelwert-1+standartAbweichung)*abstand+xStart);
		
		g.setColor(Color.BLACK);
		
		g.drawLine(xSt1, fixIT, xSt2, fixIT);
		g.drawLine(xSt1, fixIT-hoehe_Staw/2, xSt1, fixIT+hoehe_Staw/2);
		g.drawLine(xSt2, fixIT-hoehe_Staw/2, xSt2, fixIT+hoehe_Staw/2);
	}
	
	
	

	
	
}
