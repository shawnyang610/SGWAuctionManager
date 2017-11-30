package URL_Processor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import TextExtractor.TextDigger;


public class OnlineSearch2ItemsList {
    String strURL;
    URL URL=null;
    String lineWebPage;
    String itemNum;
    int intTotallNumPages;
    String strTotalNumPages;
    BufferedReader bufferedWebPage=null;
    ArrayList<String> itemsList;
    int currentPageNum;
    PrintWriter outDebug;
    public OnlineSearch2ItemsList(String in_strURL, PrintWriter inOutDebug){
        strURL=in_strURL;
        itemsList=new ArrayList<>();
        currentPageNum=1;
        outDebug=inOutDebug;
    }
    public void start() throws IOException {
        //step1 html<- search online using keyword(URL) and get search result
        URL = getURL(strURL);
        bufferedWebPage = getBufferedWebPage(URL);
        //step2 ItemsList <- go thru all lines looking for item numbers, if found push to list
        System.out.println("Gathering Item numbers from page:"+currentPageNum );
        outDebug.println("Gathering Item numbers from page:"+currentPageNum );
        outDebug.flush();
        while ((lineWebPage = bufferedWebPage.readLine())!=null) {
            if (!(strTotalNumPages = TextDigger.getTotalPageNumbers(lineWebPage)).equals("NOT FOUND"))
                intTotallNumPages = Integer.parseInt(strTotalNumPages);
            if (!(itemNum = TextDigger.getItemNumber(lineWebPage)).equals("NOT FOUND"))
                itemsList.add(itemNum);
        }
        //step3 check total number of pages, if more than 1 page then repeat step 1 (with page para=2)
        //and step2 until the end of pages
        while (currentPageNum++<intTotallNumPages){
            URL= getURL(TextDigger.changePageNumInURL(strURL, currentPageNum+""));//get URL for next page
            //repeat step1
            bufferedWebPage = getBufferedWebPage(URL);
            //repeat step2
            System.out.println("Gathering Item numbers from page:"+currentPageNum );
            outDebug.println("Gathering Item numbers from page:"+currentPageNum );
            outDebug.flush();
            while ((lineWebPage = bufferedWebPage.readLine())!=null) {
                if (!(itemNum = TextDigger.getItemNumber(lineWebPage)).equals("NOT FOUND"))
                    itemsList.add(itemNum);
            }
        }
    }
    public ArrayList<String> getItemsList(){
        return itemsList;
    }

    URL getURL(String inStrURL) throws MalformedURLException{
        return new URL(inStrURL);
    }
    BufferedReader getBufferedWebPage (URL inURL){
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(inURL.openStream()));
        }catch (IOException e){
            System.out.println("error connecting to URL:" +inURL.toString());
        }
        return br;
    }

}
