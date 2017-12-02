
import Database.CustomizedHashMap;
import Database.LogRecorder;
import IOManager.*;
import URL_Processor.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class CommandLineMode {
    private ArrayList<String>keywordsList=null;
    private ArrayList<String>returnedItemsList=null;
    private ArrayList<String>dataEntry=null;
    private String url =null;
    Scanner infile;
    PrintWriter outfile;
    PrintWriter outDebug;
    CustomizedHashMap hashMap;
    LogRecorder logger;



    CommandLineMode(String inInfileName, String inOutfileName, CustomizedHashMap in_hashMap, LogRecorder in_logger){
        System.out.println("* * * Command Line Mode * * *");
        logger = in_logger;
        hashMap = in_hashMap;
        try {
            infile = new Scanner(new FileReader(inInfileName));
            outfile= new PrintWriter(inOutfileName);
            outDebug = new PrintWriter("debug.txt");

        } catch (FileNotFoundException e){
            System.out.println("Can not open input/output files. or debug.txt is missing");
            System.exit(0);
        }
        System.out.println("Open Files: \r\n"+ inInfileName+ " OK \r\n" +inOutfileName+ " OK \r\n");
        outDebug.println("Open Files: \r\n"+ inInfileName+ " OK \r\n" +inOutfileName+ " OK \r\n");
        outDebug.flush();
    }
    //main sequence for the CommandLineMode
    public void start() throws IOException {
        Item2Data item2data=null;
        ArrayList<String> itemDataList, itemImageNamesList;
        //step1, keywordsList <- read all keywords from input file (2nd data on every row, starting from 2nd row)
        Pattern pattern = Pattern.compile("(?<=\\d{1,10}\\s\\|\\s)(.{0,50})(?=\\s\\|\\s\\d*\\s\\|.*\\|.*\\|.*)");//this pattern matches 2nd col. in input.txt
        keywordsList = IOManager.getKeywordsList(pattern,infile);
        //step1 ends
        while (!keywordsList.isEmpty()) {
            //step2, generate URL with each keyword read from step1
            url = URLBuilder.buildURL(keywordsList.get(0));
            keywordsList.remove(0);
            System.out.println(url);//debug use
            outDebug.println(url);
            outDebug.flush();
            //step3, search online using each keyword
            //step4, analyze the return page, returnedItemsList <- get item# for each item
            OnlineSearch2ItemsList onlineSearch2ItemsList = new OnlineSearch2ItemsList(url, outDebug);
            onlineSearch2ItemsList.start();
            returnedItemsList = onlineSearch2ItemsList.getItemsList();
            //for debugging
            for (String item:returnedItemsList) {
                System.out.println("returnedItemsList: "+item);
                outDebug.println("returnedItemsList: "+item);
            } //for debugging ends

            for (String i: returnedItemsList) {
                //step5, dataEntry <- data mine each item in the itemsList
                System.out.println("Getting data for item# "+i);
                item2data =new Item2Data(i, outDebug);
                item2data.start();
                itemDataList = item2data.getItemDataList();
                itemImageNamesList = item2data.getItemImageNamesList();

                //step6, database <- insert the dataEntry into database
                CustomizedHashMap.appendList2Header(hashMap.header, itemDataList);//give each element a header
                hashMap.putList(itemDataList);
                CustomizedHashMap.appendList2Header("ImagesForItemNum", itemImageNamesList);
                hashMap.putList(itemImageNamesList);
                //step7, log <- write to log
                logger.writeInfo("Write to database", itemDataList);
                logger.writeInfo("Save images to database", itemImageNamesList);

                //step8, outfile <- write the dataEntry to outfile.
                for (String j: itemDataList) {
                    outfile.print(j+" ");
                }
                outfile.println("");
            }//step9, repeat step5~8 until no more items in itemsList
        }//step10, repeat step 2~9 until no more keyword in keywordsList.

        //step11, close I/O. terminate program
        infile.close();
        outfile.close();
        outDebug.close();

    }


}
