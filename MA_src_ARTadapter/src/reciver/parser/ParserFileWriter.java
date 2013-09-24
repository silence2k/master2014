package reciver.parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ParserFileWriter implements DataParser{

	private String verzeichnis = "";
    private FileOutputStream output;
    private PrintStream file;
    
    private boolean parsen = false;
	
    
    public ParserFileWriter(String verzeichnis){
    	this.verzeichnis = verzeichnis;
    }
    
    
	@Override
	public void parse(String s) {
		// TODO: spaeter sollen nur die daten gespeichert werden, keine commandos
		if(parsen){
			file.print(s);
		}
		
	}

	@Override
	public void start() {
		
		if(parsen == true){
			stop();
		}
		
		try {
			output = new FileOutputStream(buildPfad());
			file = new PrintStream(output);	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		parsen = true;
	}

	@Override
	public void stop() {
		parsen = false;
		try {
			file.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = null;
		output = null;
		
	}
	
	
	
	private String buildPfad()
	{
		Calendar c = new GregorianCalendar();
		  c.setTime(new Date(System.currentTimeMillis()));
		  StringBuilder sb = new StringBuilder();
		  sb.append(verzeichnis);
		  sb.append(c.get(GregorianCalendar.YEAR));
		  sb.append("-");
		  sb.append(c.get(GregorianCalendar.MONTH)+1);
		  sb.append("-");
		  sb.append(c.get(GregorianCalendar.DAY_OF_MONTH));
		  sb.append("_");
		  sb.append(c.get(GregorianCalendar.HOUR_OF_DAY));
		  sb.append("-");
		  sb.append(c.get(GregorianCalendar.MINUTE));
		  sb.append("-");
		  sb.append(c.get(GregorianCalendar.SECOND));
		  sb.append("_STREAMDATA.txt");
		  
		  return sb.toString();
	}

}
