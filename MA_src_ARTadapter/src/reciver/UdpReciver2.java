package reciver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import reciver.parser.DataParser;



public class UdpReciver2 implements Runnable{
	
//	public static interface Listener {
//		void newPosition(Standard6DBody body);
//	}
	
//	private int trackerMinX = TRACKER_MIN_X;
//	private int trackerMinY = TRACKER_MIN_Y;
//	private int trackerMinZ = TRACKER_MIN_Z;
	private boolean alive = true;
//	volatile LinkedBlockingQueue<LinkedList<NormPos>> posHist= new LinkedBlockingQueue<LinkedList<NormPos>>();
	private InetAddress group;
	private DatagramPacket packet;
//	private DatagramSocket socket;
	private MulticastSocket mSocket;
	private double norm[] = {1, 1, 1};
	
	public static String Verzeichnis = "../MA_src_ARTadapter/file/";//"../file/";
    public static FileOutputStream output;
    public static PrintStream file;
	
	private List<DataParser> parserlist = new ArrayList<DataParser>();
	
	
	public static final int UDP_MAX_PACKET_SIZE = 20000;
	public static final int POINTDATA_SIZE = 10000;
	
//	public static final String IP ="230.0.0.1"; 
	public static final String IP ="230.230.230.230"; // HAW
	
//	public static final int PORT =4446; 
	public static final int PORT =5230; // HAW

	private byte[] buffer = new byte[UDP_MAX_PACKET_SIZE];
	
	
	public UdpReciver2() {
		
		
		
		packet = new DatagramPacket( buffer, POINTDATA_SIZE);

		try {
			NetworkInterface.getNetworkInterfaces();
			
			
			group = InetAddress.getByName(IP);
			mSocket = new MulticastSocket(PORT);
			
			mSocket.joinGroup(group);
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
			
		} catch (IOException ie) {
			ie.printStackTrace();
			
		}
	}
	
	public void addDataParser(DataParser parser){
		parserlist.add(parser);
	}
	
	public void run() {
		
		try {
			String pfad = buildPfad();
			System.out.println(pfad);
			output = new FileOutputStream(pfad);
			file = new PrintStream(output);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		for(DataParser p: parserlist){
			p.start();
		}
		
		
		while(alive) {
			String content = "";
			try {
				
//				System.out.println("udp vor receive");
				mSocket.receive(packet);
//				System.out.println("udp nach receive");
				int receivedSize = mSocket.getReceiveBufferSize();
//				System.out.println("receivedSize: "+receivedSize);
				content = new String(packet.getData(), 0, packet.getLength());
				   
			}
			catch( IOException ioe ) {
				System.err.println( "ERROR: IO Error: " + ioe );
			}
			
//			System.out.print(content);
			file.print(content);
			
			for(DataParser p :parserlist){
				p.parse(content);
			}
		}

		
		for(DataParser p: parserlist){
			p.stop();
		}
		
		file.close();
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("...Ende");
	}
	
	public void end() {
		this.alive = false;;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			mSocket.leaveGroup(group);
			mSocket.close();
			
			
		} finally {
			super.finalize();
		}
	}
	
	private String buildPfad()
	{
		Calendar c = new GregorianCalendar();
		  c.setTime(new Date(System.currentTimeMillis()));
		  StringBuilder sb = new StringBuilder();
		  sb.append(Verzeichnis);
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
