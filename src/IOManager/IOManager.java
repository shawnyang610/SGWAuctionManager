package IOManager;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.CustomizedHashMap;

public class IOManager {

    public static ArrayList<String> getKeywordsList(Pattern inPattern, Scanner inInfile){
        ArrayList<String> keywordsList = new ArrayList<>();
        String line  = inInfile.nextLine(); //get pass heading
        Matcher matcher=null;
        System.out.println("Keywords to report on:");
        while (inInfile.hasNext()){
            line = inInfile.nextLine();
            matcher = inPattern.matcher(line);
            if (matcher.find()){
                System.out.println(matcher.group(1));
                keywordsList.add(matcher.group(1));
            }
        }
        return keywordsList;
    }
    
    
    public static CustomizedHashMap readToHashMap(Scanner inFile) {
    	CustomizedHashMap temp_hashMap=null;
    	int lineCount=0;
    	String line="";
    	String header[]=null;
    	ArrayList<String[]> listInputData = new ArrayList<>();;
    	while (inFile.hasNextLine()) {
			lineCount++;
			line = inFile.nextLine();
			if (lineCount == 1 ) {
				header = line.split(" \\| ");
			}
			else {
				listInputData.add(line.split(" \\| "));
			}
		}
    	
    	//use hash, store all data except the first row(header) into hash
		temp_hashMap = new CustomizedHashMap(header);
		for (String[] e: listInputData) {
			ArrayList<String> newList =new ArrayList<>(Arrays.asList(e)); //convert each row of data into a new instance of ArrayList<String>
			temp_hashMap.putList(newList); //add it to hash
		}
		//log.writeInfo("Hashmap constructed with data from "+inputFileName);
		//mainGUI = new GUI(cusMap, header, outputFileName, log, selector);
		//log.writeInfo("GUI instantiated");
    	return temp_hashMap;
    }
    
    public static void reportDataWriter(PrintWriter in_outfile, String[][] in_data) {    	
    	for (int i=0; i<in_data.length;i++) {
    		in_outfile.print(in_data[i][0]);
    		for (int j=1; j<in_data[i].length; j++) {
    			in_outfile.print(" | "+in_data[i][j]);
    		}
    		in_outfile.println("");
    	}
    	in_outfile.flush();
    }
    
    public static void reportHeaderWriter (PrintWriter in_outfile, String[] in_header) {
    	in_outfile.print(in_header[0]);
    	for (int i=1; i<in_header.length; i++) {
    		in_outfile.print(" | "+in_header[i]);
    	}
    	in_outfile.println("");
    	in_outfile.flush();
    }
    
    
}
