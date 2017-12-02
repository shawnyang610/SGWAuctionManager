/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 
 */
public class StringExtractor {
    private String wholeString = null;
    public StringExtractor (String inWholeString){
        wholeString = inWholeString;
    }
    public String getString (String inStart, String inEnd){
        int indexStart;
        int indexEnd;
        indexStart = getEndIndex(inStart);
        indexEnd = getStartIndex(inEnd);
        return wholeString.substring(indexStart, indexEnd);
    }
    
    private int getEndIndex (String inPattern){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(inPattern);
        matcher = pattern.matcher(wholeString);
        if (matcher.find()){
            return matcher.end();
        }
        else {
            System.out.println ("no match found with pattern: "+ inPattern+" in text: "+wholeString+" from getEndIndex()");
            return -1;
        }
    }
    
    private int getStartIndex (String inPattern){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(inPattern);
        matcher = pattern.matcher(wholeString);
        if (matcher.find()){
            return matcher.start();
        }
        else{
            System.out.println("no match found with pattern: "+ inPattern+" in text:"+wholeString+" from getStartIndex()");
            return -1;
        }
    }
    
    public boolean isMatch(String inPattern) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(inPattern);
        matcher = pattern.matcher(wholeString);
        return matcher.find();
    }
    
}
