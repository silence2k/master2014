package steuerung;

import org.lwjgl.input.Keyboard;

public class KeyboardSteuerung implements Steuerung {

	@Override
	public boolean isNaseHoch() {
		return Keyboard.isKeyDown(Keyboard.KEY_S);
	}

	@Override
	public boolean isNaseRunter() {
		return Keyboard.isKeyDown(Keyboard.KEY_W);
	}

	@Override
	public boolean isRollenLinks() {
		return Keyboard.isKeyDown(Keyboard.KEY_A);
	}

	@Override
	public boolean isRollenRechts() {
		return Keyboard.isKeyDown(Keyboard.KEY_D);
	}

	@Override
	public boolean isSeitenruderLinks() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSeitenruderRechts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSchubGeben() {
		return Keyboard.isKeyDown(Keyboard.KEY_R);
	}

	@Override
	public boolean isSchubWegnehmen() {
		return Keyboard.isKeyDown(Keyboard.KEY_F);
	}
	
	@Override
	public float getSchub() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void cleanUp() {
		// nichts tun
	}

	@Override
	public boolean isFahrwerkHoch() {
		return Keyboard.isKeyDown(Keyboard.KEY_T);
	}

	@Override
	public boolean isFahrwerkRunter() {
		return Keyboard.isKeyDown(Keyboard.KEY_G);
	}
	
	
	

}
