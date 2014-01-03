package ui;

import steuerung.Steuerung;
import data.Hand_alt;

public class HandUI_Local implements SteuerungHaende{
	
	Hand_alt left;
	Hand_alt right;
	
	HandUI ui_left;
	HandUI ui_right;
	

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		ui_left.draw();
		ui_right.draw();
	}
		
	

}
