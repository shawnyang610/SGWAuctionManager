package URL_Processor;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author alkyo
 */
public class URL_Analyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int index =0;
        String inFileName =null;
        String outFileName=null;
        Scanner inFile = null;
        PrintWriter outFile = null;
        String line = null;
        
        
        if (args.length<4){
            System.out.println("Please specify required flags and files");
            System.exit(0);
        }
        
        while (index < args.length) {
            if (args[index].equals("-i") || args[index].equals("-input")){
                index ++;
                inFileName = args[index];
                index ++;
            }
            else if (args[index].equals("-o")|| args[index].equals("-output")){
                index ++;
                outFileName=args[index];
                index ++;
            }
            else {
                System.out.println("Please provide 1 input file and 1 output file in the format of following and try again: \nURL_Analyzer -i input.txt -o output.txt");
            }
        }
        System.out.println(inFileName+outFileName);
        
        try {
            inFile = new Scanner (new FileReader (inFileName));
            outFile =new PrintWriter(outFileName);
        } catch (Exception e) {
            System.out.println(e);
        }
        //start URL_Processor instance, passing I/O objects in
        URL_Processor UP = new URL_Processor(inFile, outFile);
        UP.processStart();
    }
    
}
