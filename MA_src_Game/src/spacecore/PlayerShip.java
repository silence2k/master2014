package spacecore;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import steuerung.Steuerung;

public class PlayerShip {

	Steuerung steuerung;

	// Global position and local vectors
	private Vector3f Position;
	private Vector3f Forward, vNeigung, vRollen, vQuer;

	// Pitch and rolls
	private float neigung, rollen, quer;

	// TEST VARIABLE
	Quaternion QResult;
	
	private float fahrwerkPostion = 0;
	private float fahrwerkDX = 0.01f;
	private float fahrwerkMax = 0.12f;

	// Ship variable
	Model model = null;
	Model fahrgestell = null;

	// Player ship has a current velocity and target velocity
	float RealVelocity, TargetVelocity;

	// Velocities
	public static float VEL_dMAX = 0.005f;
	public static float VEL_MAX = 0.15f;

	// Did we crash or bounce?
	boolean Bounced;
	boolean Crashed;

	// Constructor does nothign
	public PlayerShip(Steuerung steuerung) {
		this.steuerung = steuerung;
		// Default data
		InitShip();
		model = OBJLoader.loadModel("src/Fighter2");
		fahrgestell = OBJLoader.loadModel("src/fahrgestell");
	}

	public void InitShip() {
		// Default position slight above ground
		Position = new Vector3f(0, 0.1f, 0);

		fahrgestellAusfahren();
		
		// Set forward to Z+
		Forward = new Vector3f(0, 0, 1);
		vNeigung = new Vector3f(0, 1, 0);
		vRollen = new Vector3f(-1, 0, 0);
		vQuer = new Vector3f(-1, 0, 0);

		// Blah testing...
		QResult = new Quaternion();

		// Default velocities to zero
		RealVelocity = TargetVelocity = 0;
		neigung = rollen = 0.0f;

		// No nown states
		Bounced = false;
		Crashed = false;
	}
	
	private void fahrgestellBewegen(float dx){
		if(fahrgestell!=null){
		for (Vector3f v : fahrgestell.vertices) {
			v.y+=dx;
		}
		}
	}
	
	private void fahrgestellAusfahren(){
		fahrgestellBewegen(-fahrwerkPostion);
		fahrwerkPostion = 0;
	}

	// Check for user events
	public void Update(List<WorldObject> collisionWorldObjects) {
		// If we ever crash, reset everything
		
		
		
		
		if (Crashed) {
			System.out.println("Crashed!");
			InitShip();
		}

		// Possible angle change
		float dNeigen = 0;
		float dRollen = 0;
		float dQuer = 0;

		// Changing pitch and roll (Pitch is on Z axis)

		if (steuerung.isNaseRunter())
			dNeigen -= 0.03;
		if (steuerung.isNaseHoch())
			dNeigen += 0.03;

		// Roll is on post-pitch X acis
		if (steuerung.isRollenLinks())
			dRollen += 0.05;
		if (steuerung.isRollenRechts())
			dRollen -= 0.05;

		// Update velocities
		
		if(steuerung.getSchub() < 0){
			if (steuerung.isSchubGeben())
				TargetVelocity += VEL_dMAX;
			if (steuerung.isSchubWegnehmen())
				TargetVelocity -= VEL_dMAX;
		}else{
			TargetVelocity = VEL_MAX * steuerung.getSchub() / 100f;
		}
		
		if(steuerung.isFahrwerkHoch() && fahrwerkPostion < fahrwerkMax){
			fahrwerkPostion += fahrwerkDX;
			fahrgestellBewegen(fahrwerkDX);
		}
		
		if(steuerung.isFahrwerkRunter() && fahrwerkPostion > 0){
			fahrwerkPostion -= fahrwerkDX;
			fahrgestellBewegen(-fahrwerkDX);
		}


		if (steuerung.isSeitenruderLinks())
			dQuer += 0.05;
		if (steuerung.isSeitenruderRechts())
			dQuer -= 0.05;

		// Bounds check the target velocity
		if (TargetVelocity > VEL_MAX)
			TargetVelocity = VEL_MAX;
		else if (TargetVelocity < 0.0f)
			TargetVelocity = 0;

		// Update the real velocity over time
		// NOTE: The delta has to be smaller than the target velocity
		if (TargetVelocity > RealVelocity)
			RealVelocity += VEL_dMAX * 0.5f;
		else if (TargetVelocity < RealVelocity)
			RealVelocity -= VEL_dMAX * 0.5f;

		// Save the total pitch and roll
		neigung += dNeigen;
		rollen += dRollen;
		quer += dQuer;

		/*** EULER APPROACH with pure angles (bad) ***/

		// forward = unit(forward * cos(angle) + up * sin(angle));
		// up = right.cross(forward);
		Forward.scale((float) Math.cos(dNeigen));
		vNeigung.scale((float) Math.sin(dNeigen));
		Forward = Vector3f.add(Forward, vNeigung, null);
		vNeigung = Vector3f.cross(vRollen, Forward, null);

		// Normalize
		Forward.normalise();
		vNeigung.normalise();

		// right = unit(right * cos(angle) + up * sin(angle));
		// up = right.cross(forward);
		vRollen.scale((float) Math.cos(dRollen));
		vNeigung.scale((float) Math.sin(dRollen));

		vRollen = Vector3f.add(vRollen, vNeigung, null);
		vNeigung = Vector3f.cross(vRollen, Forward, null);

		// Normalize
		vRollen.normalise();
		vNeigung.normalise();
		vQuer.normalise();

		// Position changes over time based on the forward vector
		// Note we have a tiny bit of lift added
		Vector3f ForwardCopy = new Vector3f(Forward);
		ForwardCopy.scale(RealVelocity);

		// Gravity factor and normalized velocity
		float Gravity = 0.05f;
		float NVelocity = Math.min((RealVelocity / VEL_MAX) * 3, 1); // Note: 4
																		// is to
																		// make
																		// 1/4
																		// the
																		// "total lift"
																		// point

		// Computer the "up" fource that attempts to counter gravity
		Vector3f TotalUp = new Vector3f(vNeigung);
		TotalUp.scale(NVelocity * Gravity); // Linear relationship: the faster,
											// the more lift
		TotalUp.y -= Gravity;

		// Add the lift component to the forward vector
		// Vector3f.add(ForwardCopy, TotalUp, ForwardCopy);
		Vector3f.add(Position, ForwardCopy, Position);

		// Build two quats, for a global roll then pitch
		Quaternion qRollen = new Quaternion();
		qRollen.setFromAxisAngle(new Vector4f(Forward.x, Forward.y, Forward.z,
				dRollen));
		Quaternion qNeigung = new Quaternion();
		qNeigung.setFromAxisAngle(new Vector4f(vRollen.x, vRollen.y, vRollen.z,
				-dNeigen));

		// Note: we must explicitly multiply out each dQ, not just the total
		Quaternion.mul(QResult, qRollen, QResult);
		Quaternion.mul(QResult, qNeigung, QResult);
		QResult.normalise();
	}

	// Render the ship
	public void Render() {
		// Translate to position
		GL11.glPushMatrix();
		GL11.glTranslatef(Position.x, Position.y, Position.z);

		// Why isn't this a built-in feature of LWJGL
		float[] QMatrix = new float[16];
		createMatrix(QMatrix, QResult);

		FloatBuffer Buffer = BufferUtils.createFloatBuffer(16);
		Buffer.put(QMatrix);
		Buffer.position(0);

		GL11.glMultMatrix(Buffer);

//		GL11.glLineWidth(2.0f);
//		GL11.glBegin(GL11.GL_LINES);
//		GL11.glColor3f(1, 0.5f, 0.5f);
//		GL11.glVertex3f(0, 0, 0);
//		GL11.glVertex3f(1, 0, 0);
//
//		GL11.glColor3f(0.5f, 1, 0.5f);
//		GL11.glVertex3f(0, 0, 0);
//		GL11.glVertex3f(0, 1, 0);
//
//		GL11.glColor3f(0.5f, 0.5f, 1);
//		GL11.glVertex3f(0, 0, 0);
//		GL11.glVertex3f(0, 0, 1);
//		GL11.glEnd();

		// Set width to a single line
		GL11.glLineWidth(1);
		

		// Change rendermode
		for (int i = 0; i < 2; i++) {
			if (i == 0)
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			else
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

			// Randomize surface color a bit
			Random SurfaceRand = new Random(123456);

			GL11.glBegin(GL11.GL_TRIANGLES);
			for (Face face : model.faces) {

				GL11.glColor3f(face.mtl.getRed(), face.mtl.getGreen(), face.mtl.getBlue());
				
				// Randomize the color a tiny bit
				Vector3f v1 = model.vertices.get((int) face.vertex.x - 1);
				GL11.glVertex3f(v1.x, v1.y, v1.z);
				Vector3f v2 = model.vertices.get((int) face.vertex.y - 1);
				GL11.glVertex3f(v2.x, v2.y, v2.z);
				Vector3f v3 = model.vertices.get((int) face.vertex.z - 1);
				GL11.glVertex3f(v3.x, v3.y, v3.z);
			}
			GL11.glEnd();
			
			GL11.glBegin(GL11.GL_TRIANGLES);
			for (Face face : fahrgestell.faces) {

				GL11.glColor3f(face.mtl.getRed(), face.mtl.getGreen(), face.mtl.getBlue());
				
				// Randomize the color a tiny bit
				Vector3f v1 = fahrgestell.vertices.get((int) face.vertex.x - 1);
				GL11.glVertex3f(v1.x, v1.y, v1.z);
				Vector3f v2 = fahrgestell.vertices.get((int) face.vertex.y - 1);
				GL11.glVertex3f(v2.x, v2.y, v2.z);
				Vector3f v3 = fahrgestell.vertices.get((int) face.vertex.z - 1);
				GL11.glVertex3f(v3.x, v3.y, v3.z);
			}
			GL11.glEnd();
		}

		// Reset back to regular faces
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		// Done
		GL11.glPopMatrix();

		// Render the shadow (view-volume)
		// Note: we render the shadow independant of the model's translation and
		// rotation
		// THOUGH NOTE: we do translate the shadow up a tiny bit off the ground
		// so it doesnt z-fight
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glPolygonOffset(-1.0f, -1.0f);

		GL11.glTranslatef(0, 0.001f, 0);
		renderShadow(Position);

		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

		GL11.glPopMatrix();
	}

	public void renderShadow(Vector3f Translation) {
		// Explicitly copy...
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		for (Vector3f Vertex : model.vertices) {
			// Apply rotation then translation
			Vector3f vt = new Vector3f(Vertex);
			vt = ApplyQuatToPoint(QResult, vt);
			Vector3f.add(vt, Translation, vt);
			vertices.add(vt);
		}

		// NOTE: WE DO THIS COLLISION TEST HERE SINCE WE HAVE THE
		// TRANSLATION MODEL (i.e. global data)

		// Make sure the model never goes below the surface, and if
		// it does, push it back up, but if it does too much, crash ship
		float MaxD = 0.0f;
		for (Vector3f Vertex : vertices) {
			if (Vertex.y < 0.0f && Vertex.y < MaxD)
				MaxD = Vertex.y;
		}

		// Physics check: did we crash, or did we bounce
		if (Math.abs(MaxD) > 0.02)
			Crashed = true;
		else if (Math.abs(MaxD) > 0.0f) {
			Position.y += Math.abs(MaxD);
			Bounced = true;
		}

		// Assume the light source is just high above
		Vector3f LightPos = new Vector3f(0, 1000, 0);

		// For each triangle, project onto the plane XZ-plane
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Face face : model.faces) {
			// Per-face color
			GL11.glColor3f(0.4f, 0.4f, 0.4f);

			// Draw the mode components
			Vector3f v1 = getPlaneIntersect(
					vertices.get((int) face.vertex.x - 1), LightPos);
			GL11.glVertex3f(v1.x, v1.y, v1.z);
			Vector3f v2 = getPlaneIntersect(
					vertices.get((int) face.vertex.y - 1), LightPos);
			GL11.glVertex3f(v2.x, v2.y, v2.z);
			Vector3f v3 = getPlaneIntersect(
					vertices.get((int) face.vertex.z - 1), LightPos);
			GL11.glVertex3f(v3.x, v3.y, v3.z);
		}
		GL11.glEnd();
	}

	public Vector3f ApplyQuatToPoint(Quaternion Q, Vector3f vt) {
		// Just multiply the point against the matrix
		float[] QMatrix = new float[16];
		createMatrix(QMatrix, QResult);

		Vector3f vert = new Vector3f();
		/*
		 * vert.x = QMatrix[0] * vt.x + QMatrix[1] * vt.y + QMatrix[2] * vt.z;
		 * vert.y = QMatrix[4] * vt.x + QMatrix[5] * vt.y + QMatrix[6] * vt.z;
		 * vert.z = QMatrix[8] * vt.x + QMatrix[9] * vt.y + QMatrix[10] * vt.z;
		 */
		vert.x = QMatrix[0] * vt.x + QMatrix[4] * vt.y + QMatrix[8] * vt.z;
		vert.y = QMatrix[1] * vt.x + QMatrix[5] * vt.y + QMatrix[9] * vt.z;
		vert.z = QMatrix[2] * vt.x + QMatrix[6] * vt.y + QMatrix[10] * vt.z;
		return vert;

		/*
		 * float Distance = vt.length(); Vector3f vn = new Vector3f(vt);
		 * vn.normalise();
		 * 
		 * Quaternion vecQuat = new Quaternion(vn.x, vn.y, vn.z, 0.0f);
		 * 
		 * Quaternion QConjugate = new Quaternion(); Quaternion.negate(Q,
		 * QConjugate);
		 * 
		 * Quaternion resQuat = new Quaternion();
		 * 
		 * Quaternion.mul(vecQuat, QConjugate, resQuat); Quaternion.mul(Q,
		 * resQuat, resQuat);
		 * 
		 * // Apply the distances again.. vt.x = resQuat.x * Distance; vt.y =
		 * resQuat.y * Distance; vt.z = resQuat.z * Distance;
		 */
	}

	// Returns the intersection point of the vector (described as two points)
	// onto the y=0 plane (or simply the XZ plane)
	public Vector3f getPlaneIntersect(Vector3f vf, Vector3f vi) {
		Vector3f LineDir = Vector3f.sub(vf, vi, null);
		LineDir.normalise();

		Vector3f PlaneNormal = new Vector3f(0, 1, 0);
		Vector3f neg_Vi = new Vector3f(-vi.x, -vi.y, -vi.z);

		float d = Vector3f.dot(neg_Vi, PlaneNormal)
				/ Vector3f.dot(LineDir, PlaneNormal);
		Vector3f pt = new Vector3f(LineDir);
		pt.scale(d);
		Vector3f.add(pt, vi, pt);

		return pt;
	}

	public void createMatrix(float[] pMatrix, Quaternion q) {
		// Fill in the rows of the 4x4 matrix, according to the quaternion to
		// matrix equations

		// First row
		pMatrix[0] = 1.0f - 2.0f * (q.y * q.y + q.z * q.z);
		pMatrix[1] = 2.0f * (q.x * q.y - q.w * q.z);
		pMatrix[2] = 2.0f * (q.x * q.z + q.w * q.y);
		pMatrix[3] = 0.0f;

		// Second row

		pMatrix[4] = 2.0f * (q.x * q.y + q.w * q.z);
		pMatrix[5] = 1.0f - 2.0f * (q.x * q.x + q.z * q.z);
		pMatrix[6] = 2.0f * (q.y * q.z - q.w * q.x);
		pMatrix[7] = 0.0f;

		// Third row

		pMatrix[8] = 2.0f * (q.x * q.z - q.w * q.y);
		pMatrix[9] = 2.0f * (q.y * q.z + q.w * q.x);
		pMatrix[10] = 1.0f - 2.0f * (q.x * q.x + q.y * q.y);
		pMatrix[11] = 0.0f;

		// Fourth row

		pMatrix[12] = 0;
		pMatrix[13] = 0;
		pMatrix[14] = 0;
		pMatrix[15] = 1.0f;
		// Now pMatrix[] is a 4x4 homogeneous matrix that can be applied to an
		// OpenGL Matrix
	}

	// Get the look vectors for the camera
	public void GetCameraVectors(Vector3f CameraPos, Vector3f CameraTarget,
			Vector3f CameraUp) {
		// Copy all vectors as needed for the camera
		CameraPos.set(Position.x, Position.y, Position.z);
		CameraTarget.set(Forward.x + Position.x, Forward.y + Position.y,
				Forward.z + Position.z);
		CameraUp.set(vNeigung.x, vNeigung.y, vNeigung.z);
	}

	// Get yaw of ship
	public float GetYaw() {
		// Cast down the forward and right vectors onto the XZ plane
		Vector3f FFlat = new Vector3f(Forward.x, 0f, Forward.z);
		Vector3f RFlat = new Vector3f(1f, 0f, 0f);

		// Angle between
		float Ang = Vector3f.angle(RFlat, FFlat);
		if (Vector3f.cross(RFlat, FFlat, null).y < 0)
			Ang = (float) (Math.PI * 2.0) - Ang;
		return Ang;
	}

	// Get velocity
	public float GetRealVelocity() {
		return RealVelocity;
	}

	public float GetTargetVelocity() {
		return TargetVelocity;
	}
}
