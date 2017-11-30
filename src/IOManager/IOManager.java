package IOManager;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
