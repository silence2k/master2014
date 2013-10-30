package ui;

import static org.lwjgl.opengl.GL11.glColor3f;
import starter.Main;
import steuerung.Joystick;

public class JoystickUI implements GUI {
	
	Joystick joystick = new Joystick();
	
	// drawposition
	
	float size = 150f;
	

	
	float centerX = Main.DISPLAY_WIDTH-size/2;
	float centerY = Main.DISPLAY_HEIGHT-size/2;

	@Override
	public void draw() {
		// hintergrund
		glColor3f(1f / 255f, 36f / 255f, 59f / 255f);
		GUIutil.fillQuadCenter(centerX, centerY, size);
		
		// stiel
		glColor3f(0.7f, 0.7f, 0.7f);
		GUIutil.fillLine(joyX(), joyY(), centerX, centerY, 4);
		
		
		
		// joystickKnopf
		glColor3f(1f, 1f, 1f);
		GUIutil.fillQuadCenter(joyX(), joyY(), 14);
	}
	
	private float joyX(){
		return joystick.getPositionX()+centerX;
	}
	
	private float joyY(){
		return joystick.getPositionY()+centerY;
	}

}
