/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextExtractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author alkyo
 */
public class TextExtractor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inFile = new Scanner (new FileReader (args[0]));
        PrintWriter outFile = new PrintWriter (args[1]);
        System.out.println ("Initializing input and output files:"+args[0]+" ,"+args[1]+" done.");
        String line;
        String newLine;
        String start1=null;
        String end1 =null;
        String start2 = null;
        String end2 = null;
        System.out.print("Enter starting pattern1: ");
        start1 = (new Scanner (System.in)).nextLine();
        System.out.print("\nEnter ending pattern1: ");
        end1 = (new Scanner (System.in)).nextLine();
        
        System.out.print("Enter starting pattern2: ");
        start2 = (new Scanner (System.in)).nextLine();
        System.out.print("\nEnter ending pattern2: ");
        end2 = (new Scanner (System.in)).nextLine();
        
        StringExtractor stringExtractor = null;
        while (inFile.hasNextLine()){
            line= inFile.nextLine();
            stringExtractor = new StringExtractor(line);
            if (stringExtractor.isMatch(start1)&&stringExtractor.isMatch(end1)){
                newLine = stringExtractor.getString(start1, end1);
                outFile.print(newLine);
                System.out.print(newLine);
            }
            stringExtractor = new StringExtractor(line);
            if (stringExtractor.isMatch(start2)&&stringExtractor.isMatch(end2)){
                newLine = stringExtractor.getString(start2, end2);
                outFile.println(" "+newLine);
                System.out.println(" "+newLine);
            }
            
            
        }
        
        inFile.close();
        outFile.close();
        
    }
    
}
