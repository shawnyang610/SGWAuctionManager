package URL_Processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class URLBuilder {
    static String urlBase = "https://www.shopgoodwill.com/Listings?";
    static String st_keyWord = "";
    static String sg_specialGroup ="";
    static String c_category="";
    static String s_seller="";
    static String lp_lowPrice="0";
    static String hp_highPrice="999999";
    static String sbn_showBuyItNowOnly="false";
    static String spo_showPickupOnly="false";
    static String snpo_hidePickupOnlyItems="false";
    static String socs_show1CentShippingOnly="false";
    static String sd_searchDescription="false";
    static String sca_showClosedAuction="false";
    //default value for caed is today's date
    static String caed_closedAuctionEndingDate="";
    static {
        //the following 2 lines are inspired by https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        caed_closedAuctionEndingDate = dateFormat.format(new Date());
    }
    static String cadb_closedAuctionDateBack ="7";
    static String scs_internationalShippingCanada="false";
    static String sis_internationalShippingOutsideUSCanada="false";
    static String col="0";
    static String p_page="1";
    static String ps="40";
    static String desc_inDescOrder="false";
    static String ss="0";
    static String useBuyerPrefs="true";


    public static String buildURL(String inKeyword){
        String keyWordWithNoSpaces = inKeyword.replaceAll("\\s", "%20");
        return urlBase+"st="+keyWordWithNoSpaces+"&sg="+sg_specialGroup+"&c="+c_category+"&s="+s_seller+"&lp="+lp_lowPrice+
                "&hp="+hp_highPrice+"&sbn="+sbn_showBuyItNowOnly+"&spo="+spo_showPickupOnly+"&snpo="+snpo_hidePickupOnlyItems+
                "&socs="+socs_show1CentShippingOnly+"&sd="+sd_searchDescription+"&sca="+sca_showClosedAuction+"&caed="+
                caed_closedAuctionEndingDate+"&cadb="+cadb_closedAuctionDateBack+"&scs="+scs_internationalShippingCanada+
                "&sis="+sis_internationalShippingOutsideUSCanada+"&col="+col+"&p="+p_page+"&ps="+ps+"&desc="+desc_inDescOrder+
                "&ss="+ss+"&UseBuyerPrefs="+useBuyerPrefs;
    };
    public static String buildURL (String inKeyword, String in_sg_specialGroup, String in_c_category, String in_s_seller,
                                   String in_lp_lowPrice, String in_hp_highPrice, String in_sbn_showBuyItNowOnly,
                                   String in_spo_showPickupOnly, String in_snpo_hidePickupOnlyItems,
                                   String in_socs_show1CentShippingOnly, String in_sd_searchDescription,
                                   String in_sca_showClosedAuction, String in_caed_closedAuctionEndingDate,
                                   String in_cadb_closedAuctionDateBack, String in_scs_internationalShippingCanada,
                                   String in_col, String in_p_page, String in_ps, String in_desc_inDescOrder,
                                   String in_ss, String in_useBuyerPrefs){
        String keyWordWithNoSpaces = inKeyword.replaceAll("\\s", "%20");
        String def= "default";
        String url = urlBase+"st="+keyWordWithNoSpaces;
        url+= "&sg="+in_sg_specialGroup;
        url+= "&c="+in_c_category;
        url+= "&s="+in_s_seller;
        url+= "&lp="+in_lp_lowPrice;
        url+= "&hp="+in_hp_highPrice;
        url+= "&sbn="+in_sbn_showBuyItNowOnly;
        url+= "&spo="+in_spo_showPickupOnly;
        url+= "&snpo="+in_snpo_hidePickupOnlyItems;
        url+= "&socs="+in_socs_show1CentShippingOnly;
        url+= "&sd="+in_sd_searchDescription;
        url+= "&sca="+in_sca_showClosedAuction;
        url+= "&caed="+in_caed_closedAuctionEndingDate;
        url+= "&cadb="+in_cadb_closedAuctionDateBack;
        url+= "&scs="+in_scs_internationalShippingCanada;
        url+= "&col="+in_col;
        url+= "&p="+in_p_page;
        url+= "&ps="+in_ps;
        url+= "&desc="+in_desc_inDescOrder;
        url+= "&ss="+in_ss;
        url+= "&useBuyerPrefs="+in_useBuyerPrefs;
        return url;
    };
}
