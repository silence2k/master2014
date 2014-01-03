package ui;

import static org.lwjgl.opengl.GL11.glColor3f;
import data.Hand_alt;

public class HandUI {
	
	public final float handSize = 10f;
	
	Hand_alt hand;
	
	
	
	public void draw(){
		 if(hand.isGrab())
	        	glColor3f(0f / 255f, 255f / 255f, 0f / 255f);
	       else
	         	 glColor3f(255F / 255f, 0f / 255f, 0F / 255f);
		 
		 GUIutil.fillQuad(30, 800, handSize);
	}

}
