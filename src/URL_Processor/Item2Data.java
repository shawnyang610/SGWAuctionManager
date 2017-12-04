package URL_Processor;

import TextExtractor.TextDigger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//this class connect to page of an item and get information from html, then store it in a list
//in the order of:
//listSingleItem <- Item Number|Item Title|Item Info|CurrentPrice|Num of Bids| Endding Date|Seller Info|Shipping Polocy
//ImageList <- table_name+Item Number|ImageURL(local)
//ImageList is saved in a separate data set, Item number as foreign key, This dataset is only used for GUI mode.
//Image files are saved in local folder with same image file names so they can be referenced later using ImageList
public class Item2Data {
    public static final String baseURL = "https://www.shopgoodwill.com/Item/";
    ArrayList<String> listSingleItem;
    ArrayList<String> listImageNames4SingleItem;
    PrintWriter outDebug;
    BufferedReader bufferedItemPage;
    String itemNum;
    String itemTitle, itemPrice, itemNumBids, itemEndDate, itemSellerInfo, itemShippingPolicy;
    boolean itemTitleFinished, itemPriceFinished, itemNumBidsFinished, itemEndDateFinished
            ,itemSellerInfoFinished, itemShippingPolicyFinished;
    String currentDate;

    public Item2Data(String inItemNum, PrintWriter inOutDebug){
        listSingleItem =new ArrayList<>();
        listImageNames4SingleItem = new ArrayList<>();
        listImageNames4SingleItem.add(inItemNum);
        outDebug=inOutDebug;
        itemNum=inItemNum;
        itemTitle="";
        itemPrice="";
        itemNumBids="";
        itemEndDate="";
        itemSellerInfo="";
        itemShippingPolicy="";
        itemTitleFinished=false;
        itemPriceFinished=false;
        itemNumBidsFinished=false;
        itemEndDateFinished=false;
        itemSellerInfoFinished=false;
        itemShippingPolicyFinished=false;
        currentDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        //alove line referenced https://stackoverflow.com/questions/23068676/how-to-get-current-timestamp-in-string-format-in-java-yyyy-mm-dd-hh-mm-ss
    }

    public void start (boolean enableImage) throws IOException{
        String line;
        String temp;
        String tempURL;
        //step1 bufferedWebPage <- url
        bufferedItemPage = getBufferedWebPage(getURL(baseURL + itemNum));

        //step2 get one line at a time from bufferedItemPage
        while ((line=bufferedItemPage.readLine())!=null) {
            //step3
            //title <- get title from html
            if ((!itemTitleFinished)&& !((temp=TextDigger.getTitleFromItemPage(line)).equals("NOT FOUND"))){
                //once a pattern is matched, no need to waste cup cycle to check again.
                itemTitle=temp;
                itemTitleFinished=true;
            }
            //currentPrice <- get CurrentPrice from html
            if ((!itemPriceFinished)&& !((temp=TextDigger.getCurrentPriceFromItemPage(line)).equals("NOT FOUND"))){
                itemPrice = temp;
                itemPriceFinished=true;
            }
            //numBids <- get get num of bids from html
            if ((!itemNumBidsFinished)&& !((temp=TextDigger.getNumOfBidsFromItemPage(line)).equals("NOT FOUND"))){
                itemNumBids = temp;
                itemNumBidsFinished=true;
            }
            //endDate <- get Ending date from html
            if ((!itemEndDateFinished)&& !((temp=TextDigger.getEndingDateFromItemPage(line)).equals("NOT FOUND"))){
                itemEndDate = temp;
                itemEndDateFinished=true;
            }
            //SellerInfo <- get Seller info from html
            if ((!itemSellerInfoFinished)&& !((temp=TextDigger.getSellerInfoFromItemPage(line)).equals("NOT FOUND"))){
                itemSellerInfo = temp;
                itemSellerInfoFinished=true;
            }
            //ShippingPolicy <- get Shipping policy from html
            if ((!itemShippingPolicyFinished)&& !((temp=TextDigger.getShippingPolicyFromItemPage(line)).equals("NOT FOUND"))){
                itemShippingPolicy = temp;
                itemShippingPolicyFinished=true;
            }
            //hdd <- look for image url and save
            if (enableImage) {
	            if (!((tempURL=TextDigger.getImageURLFromItemPage(line)).equals("NOT FOUND"))){
	                //save image to hdd
	                URL_Processor.getImage(getURL(tempURL));
	                //save the image file name to a list
	                String imageName = TextDigger.getImageFileNameFromURL(tempURL);
	                listImageNames4SingleItem.add(imageName);
	            }
            }

        }//step4 repeat step2, step3 until eof

        //step5 listSingleItem.add <- itemNum, title, currentPrice, numBids, SellerInfo
        //                            ,shippingPolicy, endDate, currentTimeStamp
        listSingleItem.add(itemNum);
        listSingleItem.add(itemTitle);
        listSingleItem.add(itemPrice);
        listSingleItem.add(itemNumBids);
        listSingleItem.add(itemSellerInfo);
        listSingleItem.add(itemShippingPolicy);
        listSingleItem.add(itemEndDate);
        listSingleItem.add(currentDate);
    }

    public ArrayList<String> getItemDataList(){

        return listSingleItem;
    }

    public ArrayList<String> getItemImageNamesList(){
        return listImageNames4SingleItem;
    }

    public


    URL getURL(String inStrURL) throws MalformedURLException {
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
