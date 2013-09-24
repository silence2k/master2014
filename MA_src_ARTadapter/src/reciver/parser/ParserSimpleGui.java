package reciver.parser;

import javax.swing.JPanel;

import data.Standard6D;

public class ParserSimpleGui implements DataParser {
	
	Standard6D st6d;
	JPanel jp;
	
	





	public ParserSimpleGui(Standard6D st6d, JPanel jp) {
		super();
		this.st6d = st6d;
		this.jp = jp;
	}



	@Override
	public void parse(String content) {
		/*
		 * fr 2358
		 * ts 44979.091483
		 * 6d 1 [12 1.000][-1924.160 80.966 721.650 -171.0167 0.9323 -179.5756][-0.999840 0.009857 -0.014915 0.007406 0.987688 0.156262 0.016272 0.156126 -0.987603] 
		 * 3d 2 [12 1.000][-134.236 1978.431 -1224.665] [24 1.000][-232.520 1925.762 -1271.452] 
		 */
		String[] tmp = content.split("\n");
		
		for (String contentPart : tmp) 
		{
//			System.out.println("s: "+s);
			if(contentPart.startsWith("fr "))
			{
//				parseFrame(contentPart);
			}
			else if(contentPart.startsWith("3d "))
			{
				//welt.put(parsePoints(s));
			}else if(contentPart.startsWith("6d ") && contentPart.contains("["))
			{
				st6d.update(contentPart.substring(contentPart.indexOf("[")));
				jp.updateUI();
			}
		}
		//welt.setFrameNr(tmpFrameId);
	}

	

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
