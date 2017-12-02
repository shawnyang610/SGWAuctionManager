package GUI;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GUIAssistant {

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
