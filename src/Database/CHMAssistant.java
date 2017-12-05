package Database;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CHMAssistant {
	
	public static ArrayList<ArrayList<String>> filterLists (String[] in_header, ArrayList<ArrayList <String>> in_list){
		ArrayList<ArrayList<String>> tempList = new ArrayList<>();
		for (ArrayList<String> a: in_list) {
			if (a.get(0).startsWith(in_header[0])) {
				tempList.add(a);
			}
		}
		return tempList; //return new instance of list
	}

	public static String[] removeHeadersFromListThenToArray(String[] in_header, ArrayList<String> in_list){
		int i=0;
		String temp="";
		String[] tempAry = new String[in_list.size()];
		for (String s:in_list) {
			temp=s;
			temp=temp.replaceAll(in_header[i]+"=", "");
			tempAry[i]=temp;
			i++;
		}
		return tempAry;
	}
	
	//this method also filters out the entries without the specified headers.
	public static String[][] listTo2DStringAry (String[] in_header, ArrayList<ArrayList <String>> in_list){
		String[][] tempAry=new String[in_list.size()][];
		int i=0;
		for (ArrayList<String> a: in_list) {
			tempAry[i]=removeHeadersFromListThenToArray(in_header, a);
			i++;
		}
		return tempAry;
	}
	
	public static ArrayList<ArrayList<String>> offlineSearch (CustomizedHashMap in_cusMap, boolean in_fuzzySearch, String in_keyWord, String in_s_seller
			, String in_lp_lowprice, String in_hp_highprice, String in_offline_shipping, String in_offline_enddate){
		
		ArrayList<ArrayList<String>> finalAryList = new ArrayList<>();
		ArrayList <ArrayList<String>> tempArrayList=null; 
		
		//step2 search in the database, return a list that fufills all requirements of the search(title, price, seller... etc)
		//step2.1 finalAryList <-- for title, get all list and match the keyword using regex, return all the list that has matching title.
		if (in_fuzzySearch) {
			Pattern pattern = Pattern.compile(".*(?i)"+in_keyWord+".*");
			Matcher matcher = null;
			tempArrayList=in_cusMap.getAllList();
			for (ArrayList<String> a: tempArrayList) {
				for (String s: a) {
					matcher = pattern.matcher(s);
					if (matcher.find()) {
						finalAryList.add(a);
					}
				}
			}
		} else {
			tempArrayList=in_cusMap.getList("Title="+in_keyWord);
			for (ArrayList<String> a:tempArrayList) {
				finalAryList.add(a);
			}
		}
		//step2.2 filter out all list has no matching price
			//filter out low price and high price
		if (true) { //for scope purpose
			Double lowPrice= Double.parseDouble(in_lp_lowprice);
			Double highPrice= Double.parseDouble(in_hp_highprice);
			System.out.println("lp="+lowPrice+" hp="+highPrice);
			Pattern pattern = Pattern.compile("(?<=\\QCurrentPrice=\\E)(.*)");
			Matcher matcher=null;
			if (!finalAryList.isEmpty() && !finalAryList.get(0).get(0).equals("") && !finalAryList.get(0).get(0).equals("EMPTY")) {
				ArrayList<ArrayList<String>> toBeDeletedList = new ArrayList<>();
				for (ArrayList<String> a : finalAryList) {
					matcher=pattern.matcher(a.get(2));
					if ((!matcher.find()) || Double.parseDouble(matcher.group(1))<lowPrice || Double.parseDouble(matcher.group(1))>highPrice) {
						toBeDeletedList.add(a);
					}
				}
				for (ArrayList<String> a: toBeDeletedList) {
					finalAryList.remove(a);
				}
				
			}
		}
		//step2.3 filter out ones without matching seller
		
		//step2.4 filter out ones without specified ending date (by comparing end date and current date)

		
		
		
		return finalAryList;
	}
	
}

