package schalttafel.artefakte;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public class MyMaterial {

	private String ColorName = "Diffuse";

	private Material material;

	public void setColor(ColorRGBA value) {
		material.setColor(ColorName, value);
	}

	public MyMaterial(Material material) {
		super();
		this.material = material;
	}

}
