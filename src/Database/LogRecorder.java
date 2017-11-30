package Database;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class LogRecorder {
	private String fileName;
	private PrintWriter outFile;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private Timestamp timeStamp;
	LogRecorder (String inFileName) throws IOException{
		fileName = inFileName;
		timeStamp = new Timestamp (System.currentTimeMillis());
		
		outFile = new PrintWriter (new FileWriter(fileName, true));
		outFile.println(sdf.format(timeStamp)+": New system session, Log starts here.");
		outFile.flush();
	}
	public void writeInfo(String input) {
		System.out.println(input);
		outFile.println(sdf.format(timeStamp)+": "+input);
		outFile.flush();
	}
	
	public void close() {
		outFile.println(sdf.format(timeStamp)+": connection to log closes.");
		outFile.close();
	}
}
