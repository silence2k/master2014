package draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.SwingUtilities;

import util.Mathe;
import util.Paar;
import util.StringHelper;
import util.ValueMaker;


public class Stab1 extends Stab{
	
	int abstandBalken = 32;
	int yAbstandSchrift = 12;
	
	int yPro = 5;
	
	int breite = 20;
	
	int breite_MW = 3;
	int hoehe_MW = 6;
	
	int hoehe_Staw = 6;
	
	// position mittelWERT
	
	int xAussenRahmen1 = -100;
	int yAussenRahmen1 = 10;
	
	int xAussenRahmen2 = 300;
	int yAussenRahmen2 = 300;

	
	int yPosition1 = 70;
	int yPosition2 = 80;
	
	Color color_hintergrund = Color.WHITE;
	Color color_mittelwert = Color.RED;
	Color color_balken = Color.CYAN;
	
	
	public Stab1(String name, String[] names, int[] werte, int[] daten) {
		super(name,names,werte,ValueMaker.werteZaehlen(daten, werte),daten.length);
	}

	@Override
	public void draw(int xStart, int yStart,int xOffset, int yOffset, Graphics g) {
		
		final FontMetrics fm = g.getFontMetrics();
		
		int tmpX1 = 0;
		
		int xStartTmp = Integer.MAX_VALUE;
		int xEnde = Integer.MIN_VALUE;
		
//		g.setColor(color_hintergrund);
//		g.fillRect(xStart, yStart, xStart+xAussenRahmen2, yStart+yAussenRahmen2);
		
		g.setColor(Color.BLACK);
		g.drawString(this.name, xOffset-60, yOffset-110);
		
		drawAussenRahmen(xOffset,g);
		
		for(int i = 0; i < anzahl.length; i++){
			
			g.setColor(Color.BLACK);
			//g.fillRect(,yOffset, breite, values[i]*yPro);
			tmpX1 = abstandBalken*i+xOffset-breite/2;
			
			xStartTmp = Math.min(xStartTmp, tmpX1);
			xEnde = Math.max(xEnde, tmpX1);
			
			g.drawLine(tmpX1, yOffset+4, tmpX1, yOffset);
			
			//drawString(names[i], tmpX1, yOffset,g);
			
//			g.drawImage(imageString(names[i]), tmpX1, yOffset, null);
			
			drawBalken(yOffset, g, tmpX1, i);
			
			drawName(names[i], tmpX1, yOffset+5, g);
		}
		
		
		
		List<Paar> paare = Mathe.paare(werte,anzahl);
		double mittelwert = Mathe.mittelWert(paare);
		double standartAbweichung = Mathe.standardabweichung(paare, mittelwert);
		
		
		drawRahmen(xOffset,yOffset,g);
		
		drawStandardabweichung(mittelwert, standartAbweichung, xStartTmp, g);
		drawMittelwert(mittelwert, xStartTmp,g);
		
		drawSchriften(xOffset, yPosition2, mittelwert, standartAbweichung, g);
		
//		Draw start und ende
//		g.setColor(Color.YELLOW);
//		g.drawLine(xStart, yOffset+8, xStart, yOffset+4);
//		g.setColor(Color.CYAN);
//		g.drawLine(xEnde, yOffset+8, xEnde, yOffset+4);
		
		
	}
	
	private void drawName(String name, int tmpX1, int yOffset, Graphics g){
		g.drawImage(imageString(name), tmpX1-5, yOffset, null);
	}
	
	private void drawSchriften(int xOffset, int yOffset,double mittelwert, double standartAbweichung,Graphics g){
		g.setColor(Color.BLACK);
		String n = "n ="+anzahlAntworten;
		String mw = "mw = "+StringHelper.doubleToString(mittelwert,1);
		String s = "s = "+StringHelper.doubleToString(standartAbweichung, 2);
		String e = "E = "+(13-anzahlAntworten);
		
		int x = xOffset+this.werte.length*abstandBalken;
		int y = yOffset;
		
		g.drawString(n, x, y);
		y = y + yAbstandSchrift;
		g.drawString(mw, x, y);
		y = y + yAbstandSchrift;
		g.drawString(s, x, y);
		y = y + yAbstandSchrift;
		g.drawString(e, x, y);
	}

	private void drawBalken(int yOffset, Graphics g, int tmpX1, int i) {
		int tmpX2;
		int tmpY1;
		int tmpY2;
		
		tmpX1 = tmpX1-breite/2;
		tmpX2 = tmpX1+breite;
		tmpY1 = yOffset;
		tmpY2 = tmpY1 - anzahl[i]*yPro;
		
		drawProzent(tmpX1, anzahl[i], g);
		
		g.setColor(color_balken);
		g.fillPolygon(new int[]{tmpX1,tmpX1,tmpX2,tmpX2}, new int[]{tmpY1,tmpY2,tmpY2,tmpY1}, 4);
	}
	
	private void drawProzent(int x, int anzahl, Graphics g){
		
		double d =  (100.0 / anzahlAntworten)*anzahl;
		
		Font tmpFont = g.getFont();
		
		String s = StringHelper.doubleToString(d, 1)+"%";
		g.setColor(Color.BLACK);
		g.setFont(new Font(tmpFont.getName(), tmpFont.getStyle(), 10));
		g.drawString(s, x, yPosition1-2);
		g.setFont(tmpFont);
	}
	
	
	private void drawRahmen(int xOffset,int yOffset,Graphics g){
		
		int x1 = xOffset-25;
		
		g.setColor(Color.BLACK);
		g.drawLine(x1, yPosition1, x1+anzahl.length*abstandBalken, yPosition1);
		g.drawLine(x1, yOffset, x1+anzahl.length*abstandBalken, yOffset);
		
		g.drawLine(x1, yPosition1, x1, yOffset);
		g.drawLine(x1+anzahl.length*abstandBalken, yPosition1, x1+anzahl.length*abstandBalken, yOffset);
	}
	
	private void drawAussenRahmen(int xOffset, Graphics g){
		
		
		g.setColor(Color.BLACK);
		g.drawLine(xAussenRahmen1+xOffset, yAussenRahmen1, xAussenRahmen2+xOffset, yAussenRahmen1);
		g.drawLine(xAussenRahmen1+xOffset, yAussenRahmen2, xAussenRahmen2+xOffset, yAussenRahmen2);
		
		g.drawLine(xAussenRahmen1+xOffset, yAussenRahmen1, xAussenRahmen1+xOffset, yAussenRahmen2);
		g.drawLine(xAussenRahmen2+xOffset, yAussenRahmen1, xAussenRahmen2+xOffset, yAussenRahmen2);
		
	}

	private void drawMittelwert(double mittelwert, int xStart, Graphics g) {
		int xMittel = (int)((mittelwert-1)*abstandBalken+xStart);
		g.setColor(this.color_mittelwert);
		g.fillRect(xMittel-breite_MW/2, yPosition2, breite_MW, hoehe_MW);
	}
	
	private void drawStandardabweichung(double mittelwert, double standartAbweichung, int xStart, Graphics g){
		int xSt1 = (int)((mittelwert-1-standartAbweichung)*abstandBalken+xStart);
		int xSt2 = (int)((mittelwert-1+standartAbweichung)*abstandBalken+xStart);
		
		g.setColor(Color.BLACK);
		
		g.drawLine(xSt1, yPosition2, xSt2, yPosition2);
		g.drawLine(xSt1, yPosition2-hoehe_Staw/2, xSt1, yPosition2+hoehe_Staw/2);
		g.drawLine(xSt2, yPosition2-hoehe_Staw/2, xSt2, yPosition2+hoehe_Staw/2);
	}
	
	private Image imageString(String s){
		
		int width = 14;
		int height = 120;
		
		Image img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = (Graphics2D)img.getGraphics();
//		g2d.setColor(hintergrund);
		
	    g2d.setColor(color_hintergrund);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.BLACK);
		
		
	    // clockwise 90 degrees
	    AffineTransform at = new AffineTransform();
	   // at.setToRotation(-Math.PI/2.0, width/2.0, height/2.0);
//	    at.setToRotation(-Math.PI/2.0, 30, 25);
	    at.setToRotation(-Math.PI/2.0, 30, 25);
	    g2d.setTransform(at);
	    final FontMetrics fm = g2d.getFontMetrics();
//	    int x = SwingUtilities.computeStringWidth(fm, s);
//	    System.out.println("x: "+x);
	    
	    
	    
	    g2d.drawString(s, xMap.get(s)-55, 5);
	    
	    
	    
	    return img;
	}
	 
	

	
	
}
