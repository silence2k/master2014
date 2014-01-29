package ui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;
import spacecore.Hand;

public class UserInterface
{

	
    
    public UserInterface(Hand leftHand, Hand rightHand) {
		super();
		this.leftHand = leftHand;
		this.rightHand = rightHand;
	}

	Hand leftHand;
    Hand rightHand;
    
  //  JoystickUI jui = new JoystickUI();
    
    // Render
    public void Render(float RealVelocity, float TargetVelocity, float MaxVelocity)
    {
        // Render a speed background bar
        glPushMatrix();
        glLoadIdentity();
        
            // Normalized scale
            float RScale = 1f - (RealVelocity / MaxVelocity);
            float TScale = 1f - (TargetVelocity / MaxVelocity);
            
            // Draw the speed bar
            glColor3f(1f / 255f, 36f / 255f, 59f / 255f);
            glBegin(GL_QUADS);
                glVertex2f(32f, 200f);
                glVertex2f(64f, 200f);
                glVertex2f(64f, 32f);
                glVertex2f(32f, 32f);
            glEnd();
            
            // Real velocity
            glColor3f(157f / 255f, 167f / 255f, 178f / 255f);
            glBegin(GL_QUADS);
                glVertex2f(37f, 195f);
                glVertex2f(50f, 195f);
                glVertex2f(50f, 37f + 158f * RScale);
                glVertex2f(37f, 37f + 158f * RScale);
            glEnd();
            
            // Real velocity
            glColor3f(197f / 255f, 197f / 255f, 197f / 255f);
            glBegin(GL_QUADS);
                glVertex2f(50f, 195f);
                glVertex2f(59f, 195f);
                glVertex2f(59f, 37f + 158f * TScale);
                glVertex2f(50f, 37f + 158f * TScale);
            glEnd();
            
           // jui.draw();
        
        // Pop off the matrix
        glPopMatrix();
    }
    
    
   
}
