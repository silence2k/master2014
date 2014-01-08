package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import reciver.ARTDataSource;
import reciver.UdpReciver;
import reciver.parser.ParserSimpleGui;


import data.Berechne;
import data.DataSource;
import data.HandART;
import data.HandART2;
import data.Standard3D;
import data.Standard6D;
import filereader.Reader;

public class HandAnzeige extends JPanel{
	
	public static String Verzeichnis = "../MA_src_ARTadapter/filedata/";	
	
	JFrame jframe;
	double faktor = 0.2;
	static double verschiebung = 500;
	
	static final long sleepTime = 30;
	
	DataSource ds;
	
	Berechne berechne;
	

	public HandAnzeige(DataSource ds) {
		super();
		this.ds = ds;
		
		berechne = new Berechne(ds);
	}
	
	


	
	 @Override
	public void update(Graphics arg0) {
		// TODO Auto-generated method stub
		super.update(arg0);
		paint(arg0);
	}

	




	public void paint(Graphics g) {
		   printPoints(g);
		   printHands(g);
		  }
	
	private void printPoints(Graphics g){
		 Dimension d = this.getPreferredSize();
		    g.setColor(Color.black);
		    g.fillRect(0, 0, this.getWidth(), this.getHeight());
		     
		    berechne.update();

		    g.setColor(Color.red);
		    for(Standard3D s3d : berechne.getListAll()){
		    	g.fillRect(getX(s3d), getY(s3d), 2, 2);
		    }
		    
		    g.setColor(Color.green);
//		    for(Standard3D s3d : berechne.getListClean()){
//		    	g.fillRect(getX(s3d), getY(s3d), 2, 2);
//		    }
		    
		    for(Standard3D s3d : berechne.getListPuffer()){
		    	g.fillRect(getX(s3d), getY(s3d), 2, 2);
		    }
		    
	}
	
	private void printHands(Graphics g){
		printHand(berechne.getRechteHand(), g);
		printHand(berechne.getLinkeHand(), g);
	}
	
	private void printHand(HandART hand, Graphics g){
		
		g.setColor(Color.cyan);
		g.fillRect(getX(hand.getMittelPunkt()), getY(hand.getMittelPunkt()), 2, 2);
		int tmp = (int)hand.ausdehnung();
		g.drawOval(getX(hand.getMittelPunkt()), getY(hand.getMittelPunkt()), tmp, tmp);
	}
	
	private void printHand(HandART2 hand, Graphics g){
		
		g.setColor(Color.cyan);
		g.fillRect(getX(hand.getMittelPunkt()), getY(hand.getMittelPunkt()), 2, 2);
		int tmp = (int)hand.getAusdehnung();
		g.drawOval(getX(hand.getMittelPunkt())-tmp/2, getY(hand.getMittelPunkt())-tmp/2, tmp, tmp);
		tmp = (int)hand.maxAbstand;
		g.setColor(Color.orange);
		g.drawOval(getX(hand.getMittelPunkt())-tmp/2, getY(hand.getMittelPunkt())-tmp/2, tmp, tmp);
	}

	private int getX(Standard3D s3d){
		double x = s3d.getX()* faktor+verschiebung;
		return (int)x;
	}
	
	private int getY(Standard3D s3d){
		double y = s3d.getY()* faktor+verschiebung;
		return (int)y;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
//		Reader data = Reader.instance(new File(Verzeichnis+"Hand_eins.txt"));
	//	Reader data = Reader.instance(new File(Verzeichnis+"Hand_zwei.txt"));
		
		ARTDataSource data = new ARTDataSource();
		
		UdpReciver reciver = new UdpReciver();
		reciver.addDataParser(data);
		new Thread(reciver).start();
		
		final HandAnzeige ta = new HandAnzeige(data);
		
		
		
		
		 final JFrame frame = new JFrame("HandAnzeige");
		    frame.getContentPane().add(ta);

		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize((int)verschiebung*2,(int)verschiebung*2);
		    frame.setLocation(200, 200);
		    frame.setVisible(true);

		    new Thread(){
		    	public void run(){
		    		try {
		    		while(true){
						Thread.sleep(sleepTime);
						ta.repaint();
		    		}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
		    }.start();
	}
	
	
	

	
	
	

}
