package Database;

import java.util.ArrayList;

public class CHMAssistant {
	
	public static ArrayList<ArrayList<String>> filterLists (String[] in_header, ArrayList<ArrayList <String>> in_list){
		ArrayList<ArrayList<String>> tempList = new ArrayList<>();
		for (ArrayList<String> a: in_list) {
			if (a.get(0).startsWith(in_header[0])) {
				tempList.add(a);
			}
		}
		return tempList;
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
	
}

