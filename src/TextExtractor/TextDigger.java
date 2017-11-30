package TextExtractor;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class is made specifically to get matching strings from shopgoodwill.com pages.
public class TextDigger {
    static Pattern itemNumPattern;
    static Pattern pageNumPattern;
    static Pattern currentPageNumInURLPattern;
    static Pattern titleFromItemPagePattern;
    static Pattern currentPriceFromItemPagePattern;
    static Pattern numOfBidsFromItemPagePattern;
    static Pattern endingDateFromItemPagePattern;
    static Pattern sellerInfoFromItemPagePattern;
    static Pattern shippingPolicyFromItemPagePattern;
    static Pattern imageURLFromItemPagePattern;
    static Pattern imageFileNameFromURL;
    static{
        itemNumPattern = Pattern.compile("(?<=\\Q<a class=\"product\" href=\"/Item/\\E)(\\d{8,10})(?=\\Q\">\\E)");
        pageNumPattern = Pattern.compile("(?<=\\Q<li><a id=\"last\" data-page=\"\\E)(\\d{1,3})(?=\\Q\" onclick=\"selectPage(\\E.*)");
        currentPageNumInURLPattern = Pattern.compile("(?<=\\Q&p=\\E)(\\d{1,3})(?=&)");
        titleFromItemPagePattern = Pattern.compile("(?<=\\Q<h1 class=\"product-title\">\\E)(.*)(?=\\Q</h1>\\E)");
        currentPriceFromItemPagePattern = Pattern.compile("(?<=\\Q<li><b>Current Price:</b><span class=\"current-price\">$\\E)([\\d.]*)(?=\\Q</span></li>\\E)");
        numOfBidsFromItemPagePattern = Pattern.compile("(?<=\\Q<b>Number of Bids:</b><span class=\"num-bids\">\\E)(\\d*)(?=\\Q</span>\\E)");
        endingDateFromItemPagePattern = Pattern.compile("(?<=\\Q<b>Ends On: </b>\\E)(\\d{1,2}/\\d{1,2}/\\d{4}\\s.*)(?=\\Q Pacific Time</li>\\E)");
        sellerInfoFromItemPagePattern = Pattern.compile("(?<=\\Q<li><b>Seller: </b><a href=\\E.{2,30}>)([\\w\\s]*)(?=\\Q</a></li>\\E)");
        shippingPolicyFromItemPagePattern = Pattern.compile("(?<=\\Q<li><b>Shipping Price:</b>\\E.{0,50})(Pickup Only|Estimate Shipping|\\$0\\.01)");
        imageURLFromItemPagePattern = Pattern.compile("(?<=\\Q<img src=\"\\E)(.*\\Q-thumb.jpg\\E)(?=\\Q\">\\E)");
        imageFileNameFromURL = Pattern.compile("(?<=/)([^/]*thumb.jpg$)");
    }

    public static String getItemNumber(String inLine){
        Matcher matcher = itemNumPattern.matcher(inLine);
        if (matcher.find())
            return matcher.group(1);
        else
            return "NOT FOUND";
    }

    public static String getTotalPageNumbers(String inLine){
        Matcher matcher = pageNumPattern.matcher(inLine);
        if (matcher.find())
            return matcher.group(1);
        else
            return "NOT FOUND";
    }

    public static String changePageNumInURL (String inOriginalURL, String inPageNum){
        String newURL=null;
        newURL=inOriginalURL.replaceAll("(?<=\\Q&p=\\E)(\\d{1,3})(?=&)", inPageNum);
        System.out.println("URL for next page:"+ newURL);
        return newURL;
    }

    public static String getTitleFromItemPage (String inLine){
        Matcher matcher = titleFromItemPagePattern.matcher(inLine);
        if (matcher.find()){
            return matcher.group(1);
        }
        else return "NOT FOUND";
    }

    public static String getCurrentPriceFromItemPage (String inLine){
        Matcher matcher = currentPriceFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getNumOfBidsFromItemPage (String inLine){
        Matcher matcher = numOfBidsFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getEndingDateFromItemPage (String inLine){
        Matcher matcher = endingDateFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getSellerInfoFromItemPage (String inLine){
        Matcher matcher = sellerInfoFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getShippingPolicyFromItemPage (String inLine){
        Matcher matcher = shippingPolicyFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getImageURLFromItemPage (String inLine){
        Matcher matcher = imageURLFromItemPagePattern.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

    public static String getImageFileNameFromURL (String inLine){
        Matcher matcher = imageFileNameFromURL.matcher(inLine);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            return "NOT FOUND";
        }
    }

}
