package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import reciver.UdpReciver;
import reciver.parser.ParserSimpleGui;


import data.Standard6D;

public class TestAnzeige extends JPanel{
	
	Standard6D st6d;
	JFrame jframe;
	
	

	public TestAnzeige(Standard6D st6d) {
		super();
		this.st6d = st6d;
	}
	
	


	
	 @Override
	public void update(Graphics arg0) {
		// TODO Auto-generated method stub
		super.update(arg0);
		System.out.println("update");
		paint(arg0);
	}

	





	public void paint(Graphics g) {
		    Dimension d = this.getPreferredSize();
		    
		    g.setColor(Color.gray);
		    g.fillRect(0, 0, this.getWidth(), this.getHeight());
		    
		    int fontSize = 20;

		    g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		     
		    g.setColor(Color.red);
		    
		    g.drawString("x: "+st6d.getX(), 10, 20);
		    g.drawString("y: "+st6d.getY(), 10, 40);
		    g.drawString("z: "+st6d.getZ(), 10, 60);
		  }


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Standard6D st6d = new Standard6D();
		TestAnzeige ta = new TestAnzeige(st6d);
		
		UdpReciver reciver = new UdpReciver();
		reciver.addDataParser(new ParserSimpleGui(st6d, ta));
		new Thread(reciver).start();
		
		
		 JFrame frame = new JFrame("Testanzeige");
		    frame.getContentPane().add(ta);

		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(200,200);
		    frame.setLocation(400, 400);
		    frame.setVisible(true);

	}
	
	
	

	
	
	

}
