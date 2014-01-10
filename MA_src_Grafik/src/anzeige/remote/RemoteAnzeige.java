package anzeige.remote;

import schalttafel.artefakte.Artefakt;
import amq.RemoteEmpfaenger;
import amqdata.Hand;
import anzeige.Anzeige;

public abstract class RemoteAnzeige extends Anzeige {
	
	private RemoteEmpfaenger empfaenger;
	
	private long lasttime = System.currentTimeMillis();
	private long deltaTime = 0;
	
	@Override
	public void simpleInitApp() {
		
		this.flyCam.setEnabled(false);
		
		empfaenger = new RemoteEmpfaenger();
	}
	
	public void onAnalog(String binding, float value, float tpf) {
		switch (binding) {
		case "ende":
			empfaenger.setWeiter(false);
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public void simpleUpdate(float tpf) {
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		refreshTime();

		for (Artefakt arte : artefakte) {
			arte.update();
		}
		Hand h = empfaenger.getLinks();
		
		handLinks.update(h.getX(), h.getY(), h.getZ(), h.isGrab());
		
		h = empfaenger.getRechts();
		handRechts.update(h.getX(), h.getY(), h.getZ(), h.isGrab());
		
	}
	
	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}

}
