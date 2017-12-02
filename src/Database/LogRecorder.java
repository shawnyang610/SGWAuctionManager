package Database;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class LogRecorder {
	private String fileName;
	private PrintWriter outFile;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private Timestamp timeStamp;
	public LogRecorder (String inFileName) throws IOException{
		fileName = inFileName;
		timeStamp = new Timestamp (System.currentTimeMillis());
		
		outFile = new PrintWriter (new FileWriter(fileName, true));
		outFile.println(sdf.format(timeStamp)+": New system session, Log starts here.");
		outFile.flush();
	}
	public void writeInfo(String eventDescription) {
		System.out.println(eventDescription);
		outFile.println(sdf.format(timeStamp)+": "+eventDescription);
		outFile.flush();
	}

	public void writeInfo(String eventDescription, ArrayList<String> in_list) {
		System.out.println(eventDescription);
		outFile.println(sdf.format(timeStamp)+": "+eventDescription);
		for (String e:in_list){
		    outFile.print(e+" ");
        }
        outFile.println("");
        outFile.flush();
	}
	
	public void close() {
		outFile.println(sdf.format(timeStamp)+": connection to log closes.");
		outFile.close();
	}
}
