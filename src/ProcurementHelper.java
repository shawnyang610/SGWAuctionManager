import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import Database.CustomizedHashMap;
import Database.LogRecorder;
import GUI.GUI;
import IOManager.IOManager;

public class ProcurementHelper {
    public static void main(String[] args) throws IOException {
        int argsIndex=0;
        String infileName=null;
        String outfileName=null;
        Scanner inPermanentData = null;
        PrintWriter outDebug=null;
        //instantiate logging module
        LogRecorder logger = new LogRecorder("logFile.txt");
        
        try {
			outDebug = new PrintWriter("debug.txt");
		} catch (Exception e) {
			System.out.println("unable to open debug.txt");
			System.exit(1);
		}

        //opening the permanent data file.
        try {
			inPermanentData = new Scanner(new FileReader("PermanentData.txt"));
		} catch (Exception e) {
			System.out.println("Problem opening PermanentData.txt");
			System.exit(1);
		}
        //instantiate storage
        CustomizedHashMap hashMap =null;
        hashMap = IOManager.readToHashMap(inPermanentData); //read all data to the hashMap
        
        inPermanentData.close();
        
        if (args.length==0){
            // GUI Mode
            // call main GUI
        	System.out.println("Entering GUI Mode");
        	GUI guiMode = new GUI(hashMap, "PermanentData.txt", logger, "normal Mode", outDebug);
        	guiMode.setVisible(true);
        }
        else {
            // Command mode
            while (argsIndex < args.length){
                if (args[argsIndex].equals("-i")){
                    argsIndex++;
                    if (args[argsIndex].startsWith("-")) {
                        throw new IllegalArgumentException("argument after -i must be input file path.");
                    }
                    else {
                        infileName = args[argsIndex];
                        argsIndex++;
                    }
                }
                else if (args[argsIndex].equals("-o")){
                    argsIndex++;
                    if (args[argsIndex].startsWith("-")){
                        throw new IllegalArgumentException("argument after -o must be output file path.");
                    }
                    else {
                        outfileName=args[argsIndex];
                        argsIndex++;
                    }
                }
                else
                    throw new IllegalArgumentException("no flags recognizable.");
            }
            // instantiate the main class for the CommandLineMode
            CommandLineMode cmdMode = new CommandLineMode(infileName, outfileName, hashMap, logger, outDebug);
            cmdMode.start();
        }
    }
}
