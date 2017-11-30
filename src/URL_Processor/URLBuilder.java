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
        String url = urlBase+keyWordWithNoSpaces;
        url+= in_sg_specialGroup.equals(def)? sg_specialGroup : in_sg_specialGroup;
        url+= in_c_category.equals(def)? c_category:in_c_category;
        url+= in_s_seller.equals(def)? s_seller:in_s_seller;
        url+= in_lp_lowPrice.equals(def)? lp_lowPrice:in_lp_lowPrice;
        url+= in_hp_highPrice.equals(def)? hp_highPrice:in_hp_highPrice;
        url+= in_sbn_showBuyItNowOnly.equals(def)? sbn_showBuyItNowOnly:in_sbn_showBuyItNowOnly;
        url+= in_spo_showPickupOnly.equals(def)? spo_showPickupOnly:in_spo_showPickupOnly;
        url+= in_snpo_hidePickupOnlyItems.equals(def)? snpo_hidePickupOnlyItems: in_snpo_hidePickupOnlyItems;
        url+= in_socs_show1CentShippingOnly.equals(def)? socs_show1CentShippingOnly:in_socs_show1CentShippingOnly;
        url+= in_sd_searchDescription.equals(def)? sd_searchDescription: in_sd_searchDescription;
        url+= in_sca_showClosedAuction.equals(def)? sca_showClosedAuction: in_sca_showClosedAuction;
        url+= in_caed_closedAuctionEndingDate.equals(def)? caed_closedAuctionEndingDate:in_caed_closedAuctionEndingDate;
        url+= in_cadb_closedAuctionDateBack.equals(def)? cadb_closedAuctionDateBack:in_cadb_closedAuctionDateBack;
        url+= in_scs_internationalShippingCanada.equals(def)? scs_internationalShippingCanada:in_scs_internationalShippingCanada;
        url+= in_col.equals(def)? col: in_col;
        url+= in_p_page.equals(def)? p_page: in_p_page;
        url+= in_ps.equals(def)? ps:in_ps;
        url+= in_desc_inDescOrder.equals(def)? desc_inDescOrder:in_desc_inDescOrder;
        url+= in_ss.equals(def)? ss: in_ss;
        url+= in_useBuyerPrefs.equals(def)?useBuyerPrefs:in_useBuyerPrefs;
        return url;
    };
}
