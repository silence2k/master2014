package bastel2;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.util.vector.Vector3f;

public class RotationTest extends JPanel{
	
	static int centerX = 200;
	static int centerY = 200;
	static int pointSize = 4;
	
	Vector3f point = new Vector3f(50, 0, 0);
	Vector3f tmpPoint = new Vector3f();
	
	
	double alpha;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JPanel rt = new RotationTest();
		JFrame jf = new JFrame("Test");
		jf.add(rt);
		jf.setLocation(100, 100);
		jf.setSize(centerX * 2, centerY * 2);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

	}
	
	
	public RotationTest(){
		Timer r = new Timer(this);
		new Thread(r).start();
	}

	public void myUpdate(long delta){
		alpha = berechneAlpha(delta);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawCenter(g);
		updatePoint();
		System.out.println(tmpPoint);
		drawPoint(tmpPoint,g);
	}
	
	public void updatePoint(){
		rotate(point, alpha);
	}
	
	private double berechneAlpha(long tNow){
		
		return ((tNow%3600) / 3600.0) * 2 * Math.PI;
	}
	
	private void rotate(Vector3f point, double alpha){
		tmpPoint.x = (float) (point.x *Math.cos(alpha) - point.y *Math.sin(alpha));
		tmpPoint.y = (float) (point.x *Math.sin(alpha) + point.y *Math.cos(alpha));
		System.out.println(tmpPoint);
	}


	private void drawPoint(Vector3f point, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)(point.x)+centerX-(pointSize/2), (int)(point.y)+centerY-(pointSize/2), pointSize, pointSize);
	}
	
	private void drawCenter(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(centerX-5, centerY-1, 10, 2);
		g.fillRect(centerX-1, centerY-5, 2, 10);
	}

	
	private class Timer implements Runnable{

		long firstTime;

		RotationTest rt;
		
		
		
		public Timer(RotationTest rt) {
			super();
			this.rt = rt;
		}



		@Override
		public void run() {
			firstTime = System.currentTimeMillis();
			
			while(true){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rt.myUpdate(System.currentTimeMillis()-firstTime);
			}
			// TODO Auto-generated method stub
			
		}
		
		
		
	}
	

}
