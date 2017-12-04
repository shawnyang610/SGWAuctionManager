package GUI;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import URL_Processor.URLBuilder;

public class GUIAssistant {
	String urlBase;
    String st_keyWord;
    String sg_specialGroup;
    String c_category;
    String s_seller;
    String lp_lowPrice;
    String hp_highPrice;
    String sbn_showBuyItNowOnly;
    String spo_showPickupOnly;
    String snpo_hidePickupOnlyItems;
    String socs_show1CentShippingOnly;
    String sd_searchDescription;
    String sca_showClosedAuction;
    String caed_closedAuctionEndingDate;
    String cadb_closedAuctionDateBack;
    String scs_internationalShippingCanada;
    String sis_internationalShippingOutsideUSCanada;
    String col;
    String p_page;
    String ps;
    String desc_inDescOrder;
    String ss;
    String useBuyerPrefs;
    String inventoryFileName;
	
	public GUIAssistant() {
	    urlBase = "https://www.shopgoodwill.com/Listings?";
	    st_keyWord = "";
	    sg_specialGroup ="";
	    c_category="";
	    s_seller="";
	    lp_lowPrice="0";
	    hp_highPrice="999999";
	    sbn_showBuyItNowOnly="false";
	    spo_showPickupOnly="false";
	    snpo_hidePickupOnlyItems="false";
	    socs_show1CentShippingOnly="false";
	    sd_searchDescription="false";
	    sca_showClosedAuction="false";
	    //default value for caed is today's date
	    caed_closedAuctionEndingDate="";
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        caed_closedAuctionEndingDate = dateFormat.format(new Date());
	    cadb_closedAuctionDateBack ="7";
	    scs_internationalShippingCanada="false";
	    sis_internationalShippingOutsideUSCanada="false";
	    col="0";
	    p_page="1";
	    ps="40";
	    desc_inDescOrder="false";
	    ss="0";
	    useBuyerPrefs="true";  
	    inventoryFileName="";
	}
	
	public String buildURL () {
		return URLBuilder.buildURL(st_keyWord, sg_specialGroup, c_category
				, s_seller, lp_lowPrice, hp_highPrice, sbn_showBuyItNowOnly
				, spo_showPickupOnly, snpo_hidePickupOnlyItems
				, socs_show1CentShippingOnly, sd_searchDescription
				, sca_showClosedAuction, caed_closedAuctionEndingDate
				, cadb_closedAuctionDateBack, scs_internationalShippingCanada
				, col, p_page, ps, desc_inDescOrder
				, ss, useBuyerPrefs);
	}

	public static String[] getSellersParametersFromFile()throws IOException {
		Scanner infile = new Scanner(new FileReader("parameters_sellersList.txt"));
		ArrayList <String> tempList = new ArrayList<>();
		String[] tempStringAry;
		while (infile.hasNextLine()) {
			tempList.add(infile.nextLine());
		}
		tempStringAry = new String[tempList.size()];
		tempList.toArray(tempStringAry);
		return tempStringAry;
	}
	
	public static String[] getCategoriesParametersFromFile()throws IOException {
		Scanner infile = new Scanner(new FileReader("parameters_productsCategoriesList.txt"));
		ArrayList <String> tempList = new ArrayList<>();
		String[] tempStringAry;
		while (infile.hasNextLine()) {
			tempList.add(infile.nextLine());
		}
		tempStringAry = new String[tempList.size()];
		tempList.toArray(tempStringAry);
		return tempStringAry;
	}
	
	
}
