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

import reciver.UdpReciver;
import reciver.parser.ParserSimpleGui;


import data.DataSource;
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
	
	List<Standard3D> list = new ArrayList<>();
	
	

	public HandAnzeige(DataSource ds) {
		super();
		this.ds = ds;
		
		list = ds.getStandard3dList();
	}
	
	


	
	 @Override
	public void update(Graphics arg0) {
		// TODO Auto-generated method stub
		super.update(arg0);
		paint(arg0);
	}

	




	public void paint(Graphics g) {
		    Dimension d = this.getPreferredSize();
		    g.setColor(Color.black);
		    g.fillRect(0, 0, this.getWidth(), this.getHeight());
		     
		    g.setColor(Color.red);
		    
		    list = ds.getStandard3dList();
		    for(Standard3D s3d : list){
		    	g.fillRect(getX(s3d), getY(s3d), 2, 2);
		    }
		    
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
		
//		Reader r = Reader.instance(new File(Verzeichnis+"Hand_eins.txt"));
		Reader r = Reader.instance(new File(Verzeichnis+"Hand_zwei.txt"));
		final HandAnzeige ta = new HandAnzeige(r);
		
//		UdpReciver reciver = new UdpReciver();
//		reciver.addDataParser(new ParserSimpleGui(st6d, ta));
//		new Thread(reciver).start();
		
		
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
