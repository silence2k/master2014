package ui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class GUIutil {
	
	
	
	
	public static void fillQuad(float upperLeftX, float upperLeftY, float size){
    	fillQuad(upperLeftX,upperLeftY,size,size);
    }
    
    public static void fillQuad(float upperLeftX, float upperLeftY, float xSize, float ySize){
    glBegin(GL_QUADS);
         glVertex2f(upperLeftX, upperLeftY);
         glVertex2f(upperLeftX+xSize, upperLeftY);
         glVertex2f(upperLeftX+xSize, upperLeftY+ySize);
         glVertex2f(upperLeftX, upperLeftY+ySize);
     glEnd();
    }
	
	 	public static void fillQuadCenter(float centerX, float centerY, float size){
	    	fillQuadCenter(centerX,centerY,size,size);
	    }
	    
	    public static void fillQuadCenter(float centerX, float centerY, float xSize, float ySize){
	    glBegin(GL_QUADS);
	         glVertex2f(centerX-xSize/2f, centerY+ySize/2f);
	         glVertex2f(centerX+xSize/2f, centerY+ySize/2f);
	         glVertex2f(centerX+xSize/2f, centerY-ySize/2f);
	         glVertex2f(centerX-xSize/2f, centerY-ySize/2f);
	     glEnd();
	    }
	    
	    public static void fillLine(float startX, float startY, float endX, float endY, float size){
	    glBegin(GL_QUADS);
	         glVertex2f(startX-size/2f, startY);
	         glVertex2f(startX+size/2f, startY);
	         glVertex2f(endX+size/2f, endY);
	         glVertex2f(endX-size/2f, endY);
	     glEnd();
	     
		 glBegin(GL_QUADS);
		 	glVertex2f(startX, startY+size/2f);
		 	glVertex2f(startX, startY-size/2f);
		 	glVertex2f(endX, endY-size/2f);
		 	glVertex2f(endX, endY+size/2f);
         glEnd();
	     
//		    glBegin(GL_QUADS);
//	         glVertex2f(startX-size/2f, startY);
//	         glVertex2f(endX+size/2f, startY);
//	         glVertex2f(endX+size/2f, endY);
//	         glVertex2f(startX-size/2f, endY);
//	     glEnd();
	     
//		 glBegin(GL_QUADS);
//	         glVertex2f(startX, startY-size/2f);
//	         glVertex2f(endX, startY-size/2f);
//	         glVertex2f(endX, endY+size/2f);
//	         glVertex2f(startX, endY+size/2f);
//	     glEnd();
	    }

}
