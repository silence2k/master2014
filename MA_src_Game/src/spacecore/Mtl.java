package spacecore;


/**
 * Material Classe fuer die .mtl Datein von WaveFront
 * @author potrat_o
 * 
 * Da ich keinen Plan hab, was das alles für Werte sind, die die Mlt Datei hat, rate ich einfach.
 * Ich nehme Kd als Farbe, das muss nicht richtig sein, sollte jemand es besser wissen
 * moege er/sie es bitte anpassen / aendern.
 * 
 * Nicht fertig
 *
 */

public class Mtl 
{
	public String name;
	
	/*
	 * newmtl wall_L
	 * Ns 96.078431
     * Ka 0.000000 0.000000 0.000000
     * Kd 0.784314 0.784314 0.784314
     * Ks 0.000000 0.000000 0.000000
     * Ni 1.000000
     * d  1.000000
     * illum 1
	 */
	
	private Color Kd;
	
	public String getName() {
		return name;
	}

	/**
	 * Erzeugt eine Mtl mit dem uebergebenen Namen
	 * @param name
	 */
	public Mtl(String name)
	{
		this.name = name;
	}
	/**
	 * Erzeugt eine Mtl mit dem uebergebenen Namen und Color
	 * @param name
	 * @param kd
	 */
	public Mtl(String name, Color kd)
	{
		this(name);
		this.Kd = kd;
	}

	protected Color getKd() 
	{
		return Kd;
	}

	protected void setKd(Color kd) 
	{
		Kd = kd;
	}
	
	public float getRed(){
		return Kd.r;
	}
	
	public float getGreen(){
		return Kd.g;
	}
	
	public float getBlue(){
		return Kd.b;
	}

}
